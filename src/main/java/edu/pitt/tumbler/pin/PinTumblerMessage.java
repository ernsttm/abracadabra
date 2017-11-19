package edu.pitt.tumbler.pin;

import com.google.gson.annotations.SerializedName;
import edu.pitt.tumbler.TumblerMessage;

/**
 * A standard message to send/receive Pin tumbler updates.
 */
public class PinTumblerMessage extends TumblerMessage
{
    /**
     * Create a PinTumblerMessage which indicates the truth state of a given pin.
     *
     * @param pin the truth state of the given pin.
     */
    public PinTumblerMessage(int tumblerId, boolean pin)
    {
        super(tumblerId);
        pin_ = pin;
    }

    /**
     * @return the truth value of the pin.
     */
    public boolean getPin()
    {
        return pin_;
    }

    @SerializedName("pin")
    private boolean pin_;
}
