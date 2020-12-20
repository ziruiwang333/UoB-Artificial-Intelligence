package week3;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

/**
 * Class that will move a robot in a straight line and when it bumps into something it will rotate and go the other way
 * @author Administrator
 *
 */

public class BumperCar {
	
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(
			MotorPort.A);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(
			MotorPort.B);

	public static void main(String[] args) {
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();

		Keys buttons = ev3brick.getKeys();

		buttons.waitForAnyPress();

		Wheel wheel1 = WheeledChassis.modelWheel(LEFT_MOTOR, 56).offset(65);
		Wheel wheel2 = WheeledChassis.modelWheel(RIGHT_MOTOR, 56).offset(-65);
		
		Chassis chassis = new WheeledChassis(new Wheel [] {wheel1,  wheel2},
				WheeledChassis.TYPE_DIFFERENTIAL);

		MovePilot pilot = new MovePilot(chassis);
		
		Port s3 = ev3brick.getPort("S3");
		
		TouchSensor touch = new TouchSensor(s3);

		pilot.forward();
		
		while (true) {
			Delay.msDelay(2);
			if(touch.isPressed()) {
				pilot.stop();
				pilot.travel(-5);
				pilot.rotate(180);
				pilot.forward();
			} 
			
			if(Button.ESCAPE.isDown()) {
				pilot.stop();
				touch.close();
				System.exit(0);
			}
			
		}
	}
}
