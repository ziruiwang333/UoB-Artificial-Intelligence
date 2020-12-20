package test;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;

public class testIRSensor {

	public static void main(String[] args) {
		EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S3);
		
		EV3 ev3brick = (EV3)BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		buttons.waitForAnyPress();
		
		TextLCD lcddisplay = ev3brick.getTextLCD();
		
		SampleProvider irdistance = irSensor.getDistanceMode();
		float[] irsample = new float[irdistance.sampleSize()];
		
		while(true) {
			irdistance.fetchSample(irsample, 0);
			lcddisplay.clear();
			float x=irsample[0];
			lcddisplay.drawString("distance: "+x, 0, 0);
			Sound.playTone((int) (200 * x), 50);
		}
	}

}

//while x=18 turn right
//while x>20 turn left
