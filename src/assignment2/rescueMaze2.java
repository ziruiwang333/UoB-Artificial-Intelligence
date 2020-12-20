package assignment2;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3SensorConstants;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class rescueMaze2 {

	public static void main(String[] args) {
		EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

		EV3 ev3Brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3Brick.getKeys();
		buttons.waitForAnyPress();

		TextLCD lcddisplay = ev3Brick.getTextLCD();

		Wheel wheel1 = WheeledChassis.modelWheel(leftMotor, 56).offset(65);
		Wheel wheel2 = WheeledChassis.modelWheel(rightMotor, 56).offset(-65);

		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);

//			TouchSensor touchSensor = new TouchSensor(SensorPort.S1);

		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);

		EV3MediumRegulatedMotor ev3MediumRegulatedMotor = new EV3MediumRegulatedMotor(SensorPort.S4);
		
		
		
		EV3UltrasonicSensor sonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
		SampleProvider sonicDistance = sonicSensor.getDistanceMode();
		float[] sonicSample = new float[sonicDistance.sampleSize()];

		EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S3);
		SampleProvider irDistance = irSensor.getDistanceMode();
		float[] irsample = new float[irDistance.sampleSize()];

		pilot.setLinearSpeed(40);
		pilot.forward();
		while (true) {
			sonicDistance.fetchSample(sonicSample, 0);
			float s = sonicSample[0];

			irDistance.fetchSample(irsample, 0);
			float i = irsample[0];

			lcddisplay.drawString("distance: " + s, 0, 0);
			Sound.playTone((int) (200 * i), 50);
			
			//near
			if(i>7&&s*10<0.45) {
				pilot.rotate(15);
				pilot.setLinearSpeed(180);
				pilot.forward();
				Delay.msDelay(300);
				pilot.rotate(-10);
				pilot.setLinearSpeed(180);
				pilot.forward();
				Delay.msDelay(300);
				pilot.setLinearSpeed(40);
				pilot.forward();
			}
			
			//far
			if(s*10>0.8&&s*10<2&&i>7) {
				pilot.rotate(-15);
				pilot.setLinearSpeed(140);
				pilot.forward();
				Delay.msDelay(300);
				pilot.rotate(10);
				pilot.setLinearSpeed(140);
				pilot.forward();
				Delay.msDelay(300);
				pilot.setLinearSpeed(40);
				pilot.forward();
			}
			
			//turn left
			if(i>7&&s*10>3) {
				Delay.msDelay(1500);
				pilot.rotate(-90);
				pilot.setLinearSpeed(40);
				pilot.forward();
				Delay.msDelay(2500);
			}
			
			//turn right
			if(i<7&&s*10<2) {
				pilot.rotate(100);
				pilot.setLinearSpeed(40);
				pilot.forward();
			}
			
		}
	}
}