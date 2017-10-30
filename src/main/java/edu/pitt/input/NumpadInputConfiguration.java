package edu.pitt.input;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A simple configuration class for the NumpadInput manager
 */
public class NumpadInputConfiguration
{
    public List<Integer> getChannels()
    {
        return channels_;
    }

    @SerializedName("channels")
    private List<Integer> channels_;
}
