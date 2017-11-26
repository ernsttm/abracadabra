package edu.pitt;

import edu.pitt.input.Adc;

public class BatterySensor extends TimerTask
{
	public BatterySensor(int channelNumber, LEDManager led, Lock lock)
	{
		adc_ = new Adc(channelNumber);
	
		led_ = led;
		lock_ = lock;
	}
	
	@Override
	public void run()
	{
		if (adc_.read() < 542)
		{
			led_.indicateLowPower();
			lock_.unlock();
		}
	}
	
	private final Adc adc_;
	private final Lock lock_;
	private final LEDManager led_;
}