package test;

import java.awt.Button;

import lejos.ev3.tools.LCDDisplay;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;

public class testMediumMotor {

	public static void main(String[] args) {
		EV3MediumRegulatedMotor ev3MediumRegulatedMotor = new EV3MediumRegulatedMotor(MotorPort.C);
		EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S4);
		SampleProvider irDistance = irSensor.getDistanceMode();
		float[] irsample = new float[irDistance.sampleSize()];
		EV3 ev3Brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3Brick.getKeys();
		buttons.waitForAnyPress();
		TextLCD lcddisplay = ev3Brick.getTextLCD();

		while(true) {
			ev3MediumRegulatedMotor.rotate(90);
			irDistance.fetchSample(irsample, 0);
			float i2 = irsample[0];
			Sound.playTone((int) (200), 50);
			lcddisplay.drawString("i1 = "+i2, 0, 0);
			
			ev3MediumRegulatedMotor.rotate(-180);
			irDistance.fetchSample(irsample, 0);
			float i3 = irsample[0];
			Sound.playTone((int) (200), 50);
			lcddisplay.drawString("i3 = "+i3, 0, 2);
			
			ev3MediumRegulatedMotor.rotate(90);
			irDistance.fetchSample(irsample, 0);
			float i1 = irsample[0];
			Sound.playTone((int) (200), 50);
			lcddisplay.drawString("i2 = "+i1, 0, 1);
			
		}
	}

}
