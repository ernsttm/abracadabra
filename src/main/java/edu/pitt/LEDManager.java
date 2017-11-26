package edu.pitt;

import com.pi4j.io.gpio.*;

public class LEDManager
{
	public LEDManager(int greenPin, int redPin)
	{
		GpioController gpio = GpioFactory.getInstance();
		
		redPin_ = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(greenPin), "Green", PinState.LOW);
		redPin_.setShutDownOptions(true, PinState.LOW);
		
		greenPin_ = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(redPin), "Red", PinStage.LOW);
		greenPin_.setShutDownOptions(true, PinState.LOW);
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
				greenPin_.high();
				Thread.sleep(500);
				greenPin_.low();
			}
		}
		
		flashThread.start();
	}
	
	public void indicateUnlock()
	{
		Thread flashThread = new Thread(new Runnable() {
			@Override
			public void run() {
				greenPin_.high();
				Thread.sleep(500);
				greenPin_.low();
				Thread.sleep(100);
				greenPin_.high();
				Thread.sleep(500);
				greenPin_.low();
			}
		}
	}
	
	private final GpioPinDigitalOutput redPin_;
	private final GpioPinDigitalOutput greenPin_;
}