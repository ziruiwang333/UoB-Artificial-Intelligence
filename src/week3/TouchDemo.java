package week3;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

public class TouchDemo {



	public static void main(String[] args) throws InterruptedException {

		// get EV3 brick
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		
		// LCD class for displaying and Keys class for buttons
		Keys buttons = ev3brick.getKeys();
		
		TextLCD lcddisplay = ev3brick.getTextLCD();
		
		// block the thread until a button is pressed
		buttons.waitForAnyPress();
		
		// get a port instance
		Port portS3 = ev3brick.getPort("S3");
		
		// Get an instance of the touch EV3 sensor
		//EV3TouchSensor touchSensor = new EV3TouchSensor(portS3);
		
		TouchSensor touchSensor = new TouchSensor(portS3);		
		// get an instance of this sensor in measurement mode
		
		
		// initialize an array of floats for fetching samples
		//float [] samplevalue = new float[toucher.sampleSize()];
		
		lcddisplay.clear();
		
		while (buttons.getButtons() != Keys.ID_ESCAPE) {
			// fetch a sample
			//toucher.fetchSample(samplevalue, 0);
			boolean pressed = touchSensor.isPressed();
			lcddisplay.drawString("value: " + pressed, 0, 0);
			Delay.msDelay(20);
		}
		
		lcddisplay.clear();
		System.out.println("EXIT");
		System.exit(0);
	}

}
