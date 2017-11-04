package edu.pitt.input;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * A base class for the various input configurations required by the system. Requires each input to specify the
 * Tumbler they relate to.
 */
public class InputConfiguration
{
    public InputType getInputType()
    {
        return inputType_;
    }

    public int getTumbler()
    {
        return tumblerId_;
    }

    public JsonElement getInputConfig()
    {
        return inputConfig_;
    }

    @SerializedName("inputType")
    private InputType inputType_;

    @SerializedName("tumbler")
    private int tumblerId_;

    @SerializedName("config")
    private JsonElement inputConfig_;
}
