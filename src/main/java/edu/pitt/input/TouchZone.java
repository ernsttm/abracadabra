package edu.pitt.input;

/**
 * An enumeration encapsulating the various "zones" of contact for a single potentiometer.
 */
public enum TouchZone
{
    NO_CONTACT(-1, 300),
    ZONE_0(0, 420),
    ZONE_1(1, 540),
    ZONE_2(2, 660);

    public int getKeyOffset()
    {
        return keyOffset_;
    }

    public int getUpperLimit()
    {
        return upperLimit_;
    }

    TouchZone(int keyOffest, int upperLimit)
    {
        keyOffset_ = keyOffest;
        upperLimit_ = upperLimit;
    }

    private final int keyOffset_;
    private final int upperLimit_;
}
