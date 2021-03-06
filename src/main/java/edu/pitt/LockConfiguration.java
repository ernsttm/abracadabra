package edu.pitt;

import com.google.gson.annotations.SerializedName;
import edu.pitt.input.InputConfiguration;
import edu.pitt.input.NumpadInputConfiguration;
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
	
	public int getBatteryChannel()
	{
		return batteryChannel_;
	}

    public int getLockPin()
    {
        return lockPin_;
    }
	
	public int getRedLEDPin()
	{
		return redPin_;
	}
	
	public int getGreenLEDPin()
	{
		return greenPin_;
	}

    public List<InputConfiguration> getInputConfigurations()
    {
        return Arrays.asList(inputConfigurations_);
    }

    public List<TumblerConfiguration> getTumblerConfigurations()
    {
        return Arrays.asList(tumblerConfigurations_);
    }

    @SerializedName("inputs")
    private InputConfiguration[] inputConfigurations_;

    @SerializedName("tumblers")
    private TumblerConfiguration[] tumblerConfigurations_;

    @SerializedName("lockType")
    private LockType lockType_;
	
	@SerializedName("batteryChannel")
	private int batteryChannel_;

    @SerializedName("lockPin")
    private int lockPin_;
	
	@SerializedName("redPin")
	private int redPin_;
	
	@SerializedName("greenPin")
	private int greenPin_;
}
