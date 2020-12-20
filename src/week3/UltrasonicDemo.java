package week3;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;

public class UltrasonicDemo  {
	public static void main(String[] args) throws Exception {
		// get EV3 brick
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		// LCD class for displaying and Keys class for buttons
		Keys buttons = ev3brick.getKeys();
		TextLCD lcddisplay = ev3brick.getTextLCD();
		// block the thread until a button is pressed
		buttons.waitForAnyPress();
		// get a port instance
		Port portS1 = ev3brick.getPort("S1");
		Port portS4 = ev3brick.getPort("S4");
		
		// Get an instance of the Ultrasonic EV3 sensor
		//EV3UltrasonicSensor sonicSensor = new EV3UltrasonicSensor(portS1);
		EV3IRSensor irSensor = new EV3IRSensor(portS4);
		// get an instance of this sensor in measurement mode
		SampleProvider sonicdistance = irSensor.getDistanceMode();
		// initialize an array of floats for fetching samples
		
		SampleProvider average = new MeanFilter(sonicdistance, 5);
		
		float [] sample = new float[average.sampleSize()];
		// fetch a sample
		//sonicdistance.fetchSample(sample, 0);
		while (buttons.getButtons() != Keys.ID_ESCAPE) {
			sonicdistance.fetchSample(sample, 0);
			lcddisplay.clear();
			float x = sample[0];
			lcddisplay.drawString("distance: " + x, 0, 0);
			Sound.playTone((int) (200*x), 50);
			//Delay.msDelay(1);
		}
	}
}
