package edu.pitt.tumbler.numpad;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The configuration options specific to a Numpad Tumbler.
 */
public class NumpadTumblerConfiguration
{
    /**
     * @return the amount of time in seconds allowed to pass between individual keypresses before the sequence is
     * considered invalid.  It also denotes the amount of time the Numpad tumbler will remain in the unlocked state once
     * it has received the last keypressed message.
     */
    public int getTimeout()
    {
        return timeout_;
    }

    /**
     * @return the list of keypresses in sequence which will result in the unlocked condition.
     */
    public List<Integer> getKeyPresses()
    {
        return keyPresses_;
    }

    @SerializedName("timeoutInSeconds")
    private int timeout_;

    @SerializedName("keyPresses")
    private List<Integer> keyPresses_;
}
