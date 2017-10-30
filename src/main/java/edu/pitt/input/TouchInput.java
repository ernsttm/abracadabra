package edu.pitt.input;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class will handle polling the potentiometer, as well as passing any meaningful messages to the lock manager.
 */
public class TouchInput
{
    /**
     * A manager for a single potentiometer.
     *
     * @param channelNumber the channel the potentiometer communicates over.
     * @param manager the NumpadInputManager which oversees this touch interface.
     * @throws IOException if unable to configure this Touch Input.
     */
    public TouchInput(int channelNumber, NumpadInputManager manager) throws IOException
    {
        channelNumber_ = channelNumber;
        inputManager_ = manager;

        adc_ = new Adc(channelNumber);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new PollTask(), 0, POLLING_INTERVAL);
    }

    /**
     * @return the channel number this TouchInput manages.
     */
    public int getChannelNumber()
    {
        return channelNumber_;
    }

    /**
     * An internal class for polling the channel for "interesting" updates.
     */
    private class PollTask extends TimerTask
    {
        private TouchZone prevState_;

        PollTask()
        {
            prevState_ = TouchZone.NO_CONTACT;
        }

        @Override
        public void run()
        {
            try
            {
                TouchZone currentValue = processInput(adc_.read());
                if (prevState_ != currentValue)
                {
                    if (currentValue != TouchZone.NO_CONTACT)
                    {
                        inputManager_.receiveInput(channelNumber_, currentValue);
                    }

                    prevState_ = currentValue;
                }
            }
            catch (IOException e)
            {
                System.err.println("Potentiometer malfunction : " + e.getLocalizedMessage());
            }
        }

        private TouchZone processInput(int currentValue)
        {
            if (currentValue < TouchZone.NO_CONTACT.getUpperLimit())
            {
                return TouchZone.NO_CONTACT;
            }
            else if (currentValue < TouchZone.ZONE_0.getUpperLimit())
            {
                return TouchZone.ZONE_0;
            }
            else if (currentValue < TouchZone.ZONE_1.getUpperLimit())
            {
                return TouchZone.ZONE_1;
            }
            else if (currentValue < TouchZone.ZONE_2.getUpperLimit())
            {
                return TouchZone.ZONE_2;
            }
            else
            {
                return TouchZone.NO_CONTACT;
            }
        }
    }

    // The interval between polling potentiometer adc.
    private static final int POLLING_INTERVAL = 10;

    private final int channelNumber_;

    private final Adc adc_;
    private final NumpadInputManager inputManager_;

}
