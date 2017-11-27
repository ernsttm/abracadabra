package edu.pitt.lock;

import org.junit.*;
import static org.junit.Assert.*;

public class SoleniodLockTests
{
    @Test
    public void SoleniodTest()
    {
        SoleniodLock lock = new SoleniodLock(8);

        for (int i = 0; i < 5; i++)
        {
            try
            {
                lock.unlock();
                Thread.sleep(1000);
                lock.lock();
                Thread.sleep(1000);
            }
            catch (Exception e)
            {
                fail(e.getMessage());
            }
        }
    }
}
