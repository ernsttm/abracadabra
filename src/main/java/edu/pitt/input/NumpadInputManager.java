package edu.pitt.input;

import edu.pitt.LockManager;
import edu.pitt.tumbler.numpad.NumpadTumblerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for managing multiple TouchInputs to create the abstraction of a single Number pad for the
 * lock management logic.
 */
public class NumpadInputManager
{
    /**
     * Create a manager for the configured TouchInputs, to create single Number Pad abstraction.
     *
     * @param config the Touch configurations for this Number pad.
     * @param lockManager the lock manager to forward "interesting" messages to.
     * @throws IOException if unable to create the configured SPI touch inputs.
     */
    public NumpadInputManager(int tumblerId, NumpadInputConfiguration config, LockManager lockManager) throws IOException
    {
        lockManager_ = lockManager;

        tumblerId_ = tumblerId;
        touchInputs_ = new ArrayList<>();
        for (int channel : config.getChannels())
        {
            TouchInput touchInput = new TouchInput(channel, this);
            touchInputs_.add(touchInput);
        }
    }

    /**
     * Receive a "touch" from one of the Touch Inputs managed by this NumberPadManager
     *
     * @param channelNumber the channel which received the touch.
     * @param touchZone the touch zone the channel received a contact from.
     */
    public void receiveInput(int channelNumber, TouchZone touchZone)
    {
        for (int channelIndex = 0; channelIndex < touchInputs_.size(); channelIndex++)
        {
            if (channelNumber == touchInputs_.get(channelIndex).getChannelNumber())
            {
                int keyId = (channelIndex * 3) + touchZone.getKeyOffset();
                NumpadTumblerMessage message = new NumpadTumblerMessage(tumblerId_, keyId);
                lockManager_.receiveMessage(message);
            }
        }
    }

    private final int tumblerId_;
    private final LockManager lockManager_;
    private final List<TouchInput> touchInputs_;
}
