package edu.pitt.lock;

/**
 * A class representing the physical lock.
 */
public interface Lock
{
    /**
     * Lock the physical lock.
     */
    void lock();

    /**
     * Unlock the physical lock.
     */
    void unlock();
}
