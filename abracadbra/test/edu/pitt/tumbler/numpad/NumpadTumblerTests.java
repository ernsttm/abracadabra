package edu.pitt.tumbler.numpad;


import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NumpadTumblerTests
{
    private static final Gson gson = new Gson();

    @Test
    void BasicNumpadTest()
    {
        String configString = "{timeoutInSeconds: 5, keyPresses: [1]}";
        NumpadTumblerConfiguration config = gson.fromJson(configString, NumpadTumblerConfiguration.class);
        NumpadTumbler tumbler = new NumpadTumbler(config);

        String messageString = "{tumblerId: 1, keyId: 1}";
        NumpadTumblerMessage message = gson.fromJson(messageString, NumpadTumblerMessage.class);

        assertTrue(tumbler.receiveTumblerMessage(message));
    }

    @Test
    void TwoStepNumpad()
    {
        String configString = "{timeoutInSeconds: 5, keyPresses: [1, 2]}";
        NumpadTumblerConfiguration config = gson.fromJson(configString, NumpadTumblerConfiguration.class);
        NumpadTumbler tumbler = new NumpadTumbler(config);

        String messageString = "{tumblerId: 1, keyId: 1}";
        NumpadTumblerMessage message = gson.fromJson(messageString, NumpadTumblerMessage.class);
        assertFalse(tumbler.receiveTumblerMessage(message));
        assertFalse(tumbler.isUnlocked());

        messageString = "{tumblerId: 1, keyId: 2}";
        message = gson.fromJson(messageString, NumpadTumblerMessage.class);
        assertTrue(tumbler.receiveTumblerMessage(message));
        assertTrue(tumbler.isUnlocked());
    }

    @Test
    void TwoStepSlightPause() throws Exception
    {
        String configString = "{timeoutInSeconds: 5, keyPresses: [1, 2]}";
        NumpadTumblerConfiguration config = gson.fromJson(configString, NumpadTumblerConfiguration.class);
        NumpadTumbler tumbler = new NumpadTumbler(config);

        String messageString = "{tumblerId: 1, keyId: 1}";
        NumpadTumblerMessage message = gson.fromJson(messageString, NumpadTumblerMessage.class);
        assertFalse(tumbler.receiveTumblerMessage(message));
        assertFalse(tumbler.isUnlocked());

        // Sleep for 1 second, to give a slight pause, but not nearly enough to trigger a timeout.
        Thread.sleep(1000);

        messageString = "{tumblerId: 1, keyId: 2}";
        message = gson.fromJson(messageString, NumpadTumblerMessage.class);
        assertTrue(tumbler.receiveTumblerMessage(message));
        assertTrue(tumbler.isUnlocked());
    }

    @Test
    void ResetByPause() throws Exception
    {
        String configString = "{timeoutInSeconds: 1, keyPresses: [1, 2]}";
        NumpadTumblerConfiguration config = gson.fromJson(configString, NumpadTumblerConfiguration.class);
        NumpadTumbler tumbler = new NumpadTumbler(config);

        String messageString = "{tumblerId: 1, keyId: 1}";
        NumpadTumblerMessage message = gson.fromJson(messageString, NumpadTumblerMessage.class);
        assertFalse(tumbler.receiveTumblerMessage(message));
        assertFalse(tumbler.isUnlocked());

        // Sleep for 1 second, to give a slight pause, but not nearly enough to trigger a timeout.
        Thread.sleep(2000);

        messageString = "{tumblerId: 1, keyId: 2}";
        message = gson.fromJson(messageString, NumpadTumblerMessage.class);
        assertFalse(tumbler.receiveTumblerMessage(message));
        assertFalse(tumbler.isUnlocked());

        // Retry the unlock sequence and verify it succeeds
        messageString = "{tumblerId: 1, keyId: 1}";
        message = gson.fromJson(messageString, NumpadTumblerMessage.class);
        assertFalse(tumbler.receiveTumblerMessage(message));
        assertFalse(tumbler.isUnlocked());

        messageString = "{tumblerId: 1, keyId: 2}";
        message = gson.fromJson(messageString, NumpadTumblerMessage.class);
        assertTrue(tumbler.receiveTumblerMessage(message));
        assertTrue(tumbler.isUnlocked());
    }
}
