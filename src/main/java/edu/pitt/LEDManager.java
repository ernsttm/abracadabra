package edu.pitt;

import com.pi4j.io.gpio.*;

public class LEDManager
{
	public LEDManager(int greenPin, int redPin)
	{
		GpioController gpio = GpioFactory.getInstance();
		
		redPin_ = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(redPin), "Red", PinState.LOW);
		redPin_.setShutdownOptions(true, PinState.LOW);
		
		greenPin_ = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(greenPin), "Green", PinState.LOW);
		greenPin_.setShutdownOptions(true, PinState.LOW);
	}
	
	public void indicateLowPower()
	{
		redPin_.high();
	}
	
	public void indicateInput()
	{
		Thread flashThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try
				{
						  greenPin_.high();
						Thread.sleep(500);
						greenPin_.low();
				}
				catch(InterruptedException e)
				{
				  System.out.println("Flash failed");
				}
			}
		});
		
		flashThread.start();
	}
	
	public void indicateUnlock()
	{
		Thread flashThread = new Thread(new Runnable() {
			@Override
			public void run() {
        try
        {
	  			greenPin_.high();
		  		Thread.sleep(500);
			  	greenPin_.low();
	  			Thread.sleep(100);
	  			greenPin_.high();
	  			Thread.sleep(500);
		  		greenPin_.low();
        }
        catch (InterruptedException e)
        {
          System.out.println("Flash Failed");
        }
			}
		});
	}
	
	private final GpioPinDigitalOutput redPin_;
	private final GpioPinDigitalOutput greenPin_;
}
