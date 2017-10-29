package edu.pitt;

import edu.pitt.lock.Lock;
import edu.pitt.tumbler.Tumbler;
import edu.pitt.tumbler.TumblerMessage;

import java.util.Map;

/**
 * This class serves as the central processing of the lock box.  It will receive input from various servers, dispatch
 * that input to the appropriate tumblers, and when all tumblers indicate they are unlocked, will trigger the unlock
 * logic.
 */
public class LockManager
{
    public LockManager(Map<Integer, Tumbler> mappedTumblers, Lock lock)
    {
        lock_ = lock;
        mappedTumblers_ = mappedTumblers;
    }

    public void receiveMessage(TumblerMessage message)
    {
        if (!mappedTumblers_.containsKey(message.getTumblerId()))
        {
            System.out.println("The lock doesn't contain the given tumbler : " + message.getTumblerId());
            return;
        }

        Tumbler tumbler = mappedTumblers_.get(message.getTumblerId());
        if (tumbler.receiveTumblerMessage(message))
        {
            testAndUnlock();
        }
    }

    // Test all the tumblers are unlocked, and if so trigger the physical lock to open.
    private void testAndUnlock()
    {
        // Don't unlock if any of the tumblers are still in the locked position.
        for (Tumbler tumbler : mappedTumblers_.values())
        {
            if (!tumbler.isUnlocked())
            {
                return;
            }
        }

        lock_.unlock();
    }

    private final Lock lock_;
    private final Map<Integer, Tumbler> mappedTumblers_;
}
