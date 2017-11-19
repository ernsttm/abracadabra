package edu.pitt.tumbler.pin;

import edu.pitt.tumbler.Tumbler;
import edu.pitt.tumbler.TumblerMessage;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This tumbler functions as a boolean state, with a given timeout.  It takes the value given, and if true reverts to
 * the false when the configured timeout occurs.
 */
public class PinTumbler implements Tumbler
{
    public PinTumbler(PinTumblerConfiguration config)
    {
        unlocked_ = false;
        timeoutInMs_ = config.getTimeout() * 1000;

        timer_ = new Timer();
        tumblerLock_ = new Object();
    }

    @Override
    public boolean receiveTumblerMessage(TumblerMessage message)
    {
        if (!(message instanceof PinTumblerMessage))
        {
            System.out.println("Improperly formatted message delivered to Pin tumbler.");
            return false;
        }

        PinTumblerMessage pinMessage = (PinTumblerMessage)message;
        return processMessage(pinMessage);
    }

    @Override
    public boolean isUnlocked()
    {
        synchronized (tumblerLock_)
        {
            return unlocked_;
        }
    }

    private boolean processMessage(PinTumblerMessage message)
    {
        synchronized (tumblerLock_)
        {
            unlocked_ = message.getPin();

            if (unlocked_)
            {
                timer_.cancel();
                timer_ = new Timer();
                timer_.schedule(new ResetTask(), timeoutInMs_);
            }

            return unlocked_;
        }
    }

    private class ResetTask extends TimerTask
    {
        @Override
        public void run()
        {
            synchronized (tumblerLock_)
            {
                unlocked_ = false;
            }
        }
    }

    private int timeoutInMs_;
    private boolean unlocked_;

    private final Object tumblerLock_;

    private final Timer timer_;
}
