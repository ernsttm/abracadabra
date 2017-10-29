package edu.pitt.tumbler;

import com.google.gson.annotations.SerializedName;

/**
 * A class for individual Tumbler messages to inherit from.
 */
public abstract class TumblerMessage
{
    /**
     * @return the id of the tumbler this message relates to.
     */
    public int getTumblerId()
    {
        return tumblerId_;
    }

    @SerializedName("tumblerId")
    private int tumblerId_;
}
