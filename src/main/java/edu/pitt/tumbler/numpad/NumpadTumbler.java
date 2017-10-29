package edu.pitt.tumbler.numpad;

import edu.pitt.tumbler.Tumbler;
import edu.pitt.tumbler.TumblerMessage;

import java.time.Instant;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This Tumbler mimics the functionality of a standard numpad.  As long as a user enters the correct sequence into
 * the number pad within the designated amount of time, it report an unlocked_ status.
 */
public class NumpadTumbler implements Tumbler
{
    public NumpadTumbler(NumpadTumblerConfiguration config)
    {
        unlocked_ = false;

        currentState_ = 0;
        lastUpdateTime_ = 0;
        keypresses_ = config.getKeyPresses();
        tumblerLock_ = new Object();

        Timer timer = new Timer();
        int timeoutInMilli = config.getTimeout() * 1000;
        timer.scheduleAtFixedRate(new ResetTask(timeoutInMilli), 0, RESET_CHECK_TIME);
    }

    @Override
    public boolean receiveTumblerMessage(TumblerMessage message)
    {
        if (!(message instanceof NumpadTumblerMessage))
        {
            System.out.println("Improperly formatted message delivered to Number pad tumbler.");
            return false;
        }

        NumpadTumblerMessage numpadMessage = (NumpadTumblerMessage)message;
        return processMessage(numpadMessage);
    }

    @Override
    public boolean isUnlocked()
    {
        synchronized (tumblerLock_)
        {
            return unlocked_;
        }
    }

    private boolean processMessage(NumpadTumblerMessage message)
    {
        synchronized (tumblerLock_)
        {
            // The the next expected key has been pressed, advance the state of the Tumbler.
            if (message.getKeyId() == keypresses_.get(currentState_))
            {
                currentState_++;
                lastUpdateTime_ = Instant.now().toEpochMilli();

                // The final key has been pressed, change our state to unlocked, and then reset the tumbler.
                if (currentState_ == keypresses_.size())
                {
                    unlocked_ = true;
                    currentState_ = 0;
                }
            }
            // A key different than the next expected was pressed, reset the Tumbler.
            else
            {
                unlocked_ = false;
                currentState_ = 0;
            }

            return unlocked_;
        }
    }

    // This is a simple recurring task which will reset the lock state if the last update is beyond the configured
    // timeout.
    private class ResetTask extends TimerTask
    {
        ResetTask(int timeout)
        {
            timeout_ = timeout;
        }

        @Override
        public void run()
        {
            synchronized (tumblerLock_)
            {
                long currentTime = Instant.now().toEpochMilli();

                if ((currentTime - lastUpdateTime_) > timeout_)
                {
                    unlocked_ = false;
                    currentState_ = 0;
                }
            }
        }

        private final int timeout_;
    }

    private boolean unlocked_;
    private int currentState_;
    private long lastUpdateTime_;

    private final Object tumblerLock_;
    private final List<Integer> keypresses_;

    // The amount of time in between checking whether the last update was before the configured timeout.  By default
    // this is set to one second.
    private static final int RESET_CHECK_TIME = 1000;
}
