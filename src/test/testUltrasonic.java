package test;

import org.freedesktop.DBus.Binding.Tests;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class testUltrasonic {

	public static void main(String[] args) {
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();

		TextLCD lcddisplay = ev3brick.getTextLCD();

		buttons.waitForAnyPress();

		EV3UltrasonicSensor sonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
		SampleProvider sonicDistance = sonicSensor.getDistanceMode();

		float[] sample = new float[sonicDistance.sampleSize()];

		while (true) {

			sonicDistance.fetchSample(sample, 0);
			lcddisplay.clear();
			float x = sample[0];
			lcddisplay.drawString("distance: " + x * 10, 0, 0);
			Sound.playTone((int) (200 * x), 50);

		}
	}
}

//while x<0.6 turn right


