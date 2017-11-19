package edu.pitt.tumbler.pin;

import com.google.gson.annotations.SerializedName;

/**
 * The configuration options specific to a Pin Tumbler.
 */
public class PinTumblerConfiguration
{
    /**
     * @return the amount of time to allow a truth pin state to remain valid, in seconds
     */
    public int getTimeout()
    {
        return timeout_;
    }

    @SerializedName("timeoutInSeconds")
    private int timeout_;
}
