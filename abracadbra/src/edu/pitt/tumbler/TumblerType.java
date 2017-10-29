package edu.pitt.tumbler;

import edu.pitt.tumbler.numpad.NumpadTumblerConfiguration;

/**
 * An enumeration of the types of Tumblers available as well as the class used in the configurations.
 */
public enum TumblerType
{
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
