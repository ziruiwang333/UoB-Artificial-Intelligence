package test;

import lejos.hardware.BrickFinder;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;

public class testColorSensor {

	public static void main(String[] args) {
		EV3ColorSensor aColorSensor = new EV3ColorSensor(SensorPort.S1);
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		TextLCD lcddisplay = ev3brick.getTextLCD();
		
		while(true) {
			lcddisplay.clear();
			lcddisplay.drawString("Rgb:"+aColorSensor.getColorID(), 0, 0);
			Sound.playTone((int) (200), 50);
		}
	}
}
