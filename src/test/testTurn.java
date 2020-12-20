package test;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class testTurn {

	public static void main(String[] args) {
		EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

		EV3 ev3Brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3Brick.getKeys();
		buttons.waitForAnyPress();
		
		Wheel wheel1 = WheeledChassis.modelWheel(leftMotor, 56).offset(65);
		Wheel wheel2 = WheeledChassis.modelWheel(rightMotor, 56).offset(-65);
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		while(true) {
			pilot.setAngularSpeed(80);
			pilot.rotate(-80);
			Delay.msDelay(3000);
			
			pilot.setAngularSpeed(80);
			pilot.rotate(76);
			Delay.msDelay(3000);
			
			pilot.setAngularSpeed(80);
			pilot.rotate(160);
			Delay.msDelay(3000);
		}
	}

}
