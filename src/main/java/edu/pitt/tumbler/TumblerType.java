package edu.pitt.tumbler;

import edu.pitt.tumbler.numpad.NumpadTumblerConfiguration;
import edu.pitt.tumbler.pin.PinTumblerConfiguration;

/**
 * An enumeration of the types of Tumblers available as well as the class used in the configurations.
 */
public enum TumblerType
{
    Pin(PinTumblerConfiguration.class),
    NumPad(NumpadTumblerConfiguration.class);

    public Class getConfigClass()
    {
        return configClass_;
    }

    TumblerType(Class configClass)
    {
        configClass_ = configClass;
    }

    private Class configClass_;
}
