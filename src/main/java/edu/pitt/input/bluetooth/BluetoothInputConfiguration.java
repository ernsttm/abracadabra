package edu.pitt.input.bluetooth;

import com.google.gson.annotations.SerializedName;
import edu.pitt.input.InputConfiguration;

/**
 * A simple configuration class for the BluetoothInput
 */
public class BluetoothInputConfiguration extends InputConfiguration
{
    /**
     * @return the string name of the serial file related to the bluetooth communication used by bluetooth input
     */
    public String getSerialChannel()
    {
        return serialChannel_;
    }

    @SerializedName("serialChannel")
    private String serialChannel_;
}
