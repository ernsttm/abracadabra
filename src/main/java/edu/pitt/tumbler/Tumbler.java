package edu.pitt.tumbler;

/**
 * The API definition for a Tumbler in the lock box.  It will receive input from the lock, and then synchronously
 * update and report its locked status.
 */
public interface Tumbler
{
    /**
     * Receive a TumblerMessage from the lock, and process the next step in the finite machine.
     *
     * @param message the message to process.
     * @return true if the Tumbler is unlocked, false otherwise.
     */
    boolean receiveTumblerMessage(TumblerMessage message);

    /**
     * @return true if this Tumbler is unlocked, false otherwise.
     */
    boolean isUnlocked();
}
