package edu.pitt;

import edu.pitt.input.Adc;
import edu.pitt.lock.Lock;

import java.util.TimerTask;

public class BatterySensor extends TimerTask
{
	public BatterySensor(int channelNumber, LEDManager led, Lock lock)
		throws IOException
	{
		adc_ = new Adc(channelNumber);
	
		led_ = led;
		lock_ = lock;
	}
	
	@Override
	public void run()
	{
		try 
		{
			if (adc_.read() < 542)
			{
				led_.indicateLowPower();
				lock_.unlock();
			}
		}
		catch (Exception e)
		{
			System.out.println("Failed to read battery sensor");
		}
	}
	
	private final Adc adc_;
	private final Lock lock_;
	private final LEDManager led_;
}