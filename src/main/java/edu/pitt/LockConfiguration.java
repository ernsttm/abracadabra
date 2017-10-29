package edu.pitt;

import com.google.gson.annotations.SerializedName;
import edu.pitt.lock.LockType;
import edu.pitt.tumbler.TumblerConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * System wide configurations for the LockBox
 */
public class LockConfiguration
{
    public LockType getLockType()
    {
        return lockType_;
    }

    public int getLockPint()
    {
        return lockPin_;
    }

    public List<TumblerConfiguration> getTumblerConfigurations()
    {
        return Arrays.asList(tumblerConfigurations_);
    }

    @SerializedName("tumblers")
    private TumblerConfiguration[] tumblerConfigurations_;

    @SerializedName("lockType")
    private LockType lockType_;

    @SerializedName("lockPin")
    private int lockPin_;
}
