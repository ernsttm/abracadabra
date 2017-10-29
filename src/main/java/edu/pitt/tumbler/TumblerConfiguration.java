package edu.pitt.tumbler;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * This serves as the Java representation of the LockManager File, it should contain a series of generic configurations
 * as well as a set of specific Tumbler configurations.
 */
public class TumblerConfiguration
{
    public int getTumblerId()
    {
        return tumblerId_;
    }

    public TumblerType getTumblerType()
    {
        return tumblerType_;
    }

    public JsonElement getTumblerConfig()
    {
        return tumblerConfig_;
    }

    @SerializedName("tumblerId")
    private int tumblerId_;

    @SerializedName("tumblerType")
    private TumblerType tumblerType_;

    @SerializedName("config")
    private JsonElement tumblerConfig_;
}
