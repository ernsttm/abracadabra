package edu.pitt.tumbler.numpad;

import com.google.gson.Gson;
import org.junit.*;
import static org.junit.Assert.*;

public class NumpadTumblerTests
{
    @Test
    public void BasicNumpadTest()
    {
        Gson test = new Gson();
        String configString = "{timeoutInSeconds: 5, keyPresses: [1]}";
        NumpadTumblerConfiguration config = test.fromJson(configString, NumpadTumblerConfiguration.class);
        NumpadTumbler tumbler = new NumpadTumbler(config);

        String messageString = "{tumblerId: 1, keyId: 1}";
        NumpadTumblerMessage message = test.fromJson(messageString, NumpadTumblerMessage.class);

        assertTrue(tumbler.receiveTumblerMessage(message));
    }

    @Test
    public void TwoStepNumpad()
    {
        Gson test = new Gson();
        String configString = "{timeoutInSeconds: 5, keyPresses: [1, 2]}";
        NumpadTumblerConfiguration config = test.fromJson(configString, NumpadTumblerConfiguration.class);
        NumpadTumbler tumbler = new NumpadTumbler(config);

        String messageString = "{tumblerId: 1, keyId: 1}";
        NumpadTumblerMessage message = test.fromJson(messageString, NumpadTumblerMessage.class);
        assertFalse(tumbler.receiveTumblerMessage(message));
        assertFalse(tumbler.isUnlocked());

        messageString = "{tumblerId: 1, keyId: 2}";
        message = test.fromJson(messageString, NumpadTumblerMessage.class);
        assertTrue(tumbler.receiveTumblerMessage(message));
        assertTrue(tumbler.isUnlocked());
    }

    @Test
    public void TwoStepSlightPause() throws Exception
    {
        Gson test = new Gson();
        String configString = "{timeoutInSeconds: 5, keyPresses: [1, 2]}";
        NumpadTumblerConfiguration config = test.fromJson(configString, NumpadTumblerConfiguration.class);
        NumpadTumbler tumbler = new NumpadTumbler(config);

        String messageString = "{tumblerId: 1, keyId: 1}";
        NumpadTumblerMessage message = test.fromJson(messageString, NumpadTumblerMessage.class);
        assertFalse(tumbler.receiveTumblerMessage(message));
        assertFalse(tumbler.isUnlocked());

        // Sleep for 1 second, to give a slight pause, but not nearly enough to trigger a timeout.
        Thread.sleep(1000);

        messageString = "{tumblerId: 1, keyId: 2}";
        message = test.fromJson(messageString, NumpadTumblerMessage.class);
        assertTrue(tumbler.receiveTumblerMessage(message));
        assertTrue(tumbler.isUnlocked());
    }

    @Test
    public void ResetByPause() throws Exception
    {
        Gson test = new Gson();
        String configString = "{timeoutInSeconds: 1, keyPresses: [1, 2]}";
        NumpadTumblerConfiguration config = test.fromJson(configString, NumpadTumblerConfiguration.class);
        NumpadTumbler tumbler = new NumpadTumbler(config);

        String messageString = "{tumblerId: 1, keyId: 1}";
        NumpadTumblerMessage message = test.fromJson(messageString, NumpadTumblerMessage.class);
        assertFalse(tumbler.receiveTumblerMessage(message));
        assertFalse(tumbler.isUnlocked());

        // Sleep for 1 second, to give a slight pause, but not nearly enough to trigger a timeout.
        Thread.sleep(2000);

        messageString = "{tumblerId: 1, keyId: 2}";
        message = test.fromJson(messageString, NumpadTumblerMessage.class);
        assertFalse(tumbler.receiveTumblerMessage(message));
        assertFalse(tumbler.isUnlocked());

        // Retry the unlock sequence and verify it succeeds
        messageString = "{tumblerId: 1, keyId: 1}";
        message = test.fromJson(messageString, NumpadTumblerMessage.class);
        assertFalse(tumbler.receiveTumblerMessage(message));
        assertFalse(tumbler.isUnlocked());

        messageString = "{tumblerId: 1, keyId: 2}";
        message = test.fromJson(messageString, NumpadTumblerMessage.class);
        assertTrue(tumbler.receiveTumblerMessage(message));
        assertTrue(tumbler.isUnlocked());
    }
}
