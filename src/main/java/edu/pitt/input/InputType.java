package edu.pitt.input;

/**
 * An enumeration of the types of Inputs available as well as the class used in the configurations.
 */
public enum InputType
{
    Touch(NumpadInputConfiguration.class);

    public Class getConfigClass()
    {
        return configClass_;
    }

    InputType(Class configClass)
    {
        configClass_ = configClass;
    }

    private Class configClass_;
}
