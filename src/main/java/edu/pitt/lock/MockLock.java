package edu.pitt.lock;

/**
 * A fake lock to be used during testing and debugging.  It merely prints lock/unlock to the screen during the
 * respective operations, and sets a variable accordingly.
 */
public class MockLock implements Lock
{
    public boolean isUnlocked()
    {
        return unlocked_;
    }

    @Override
    public void lock()
    {
        unlocked_ = true;
        System.out.println("Locking box");
    }

    @Override
    public void unlock()
    {
        unlocked_ = false;
        System.out.println("Unlocking");
    }

    private boolean unlocked_ = true;
}
