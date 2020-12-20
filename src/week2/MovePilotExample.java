package week2;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.*;

public class MovePilotExample {

	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(
			MotorPort.A);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(
			MotorPort.B);

	public static void main(String[] args) throws Exception {

		// get EV3 brick
		EV3 ev3brick = (EV3) BrickFinder.getLocal();

		// instantized LCD class for displaying and Keys class for buttons
		Keys buttons = ev3brick.getKeys();

		// block the thread until a button is pressed
		buttons.waitForAnyPress();

		// setup the wheel diameter of left (and right) motor in centimeters,
		// i.e. 2.8 cm
		// the offset number is the distance between the center of wheel to
		// the center of robot, i.e. half of track width
		Wheel wheel1 = WheeledChassis.modelWheel(LEFT_MOTOR, 5.5).offset(-6.6);
		Wheel wheel2 = WheeledChassis.modelWheel(RIGHT_MOTOR, 5.5).offset(6.6);
		

		// set up the chassis type, i.e. Differential pilot
		Chassis chassis = new WheeledChassis(new Wheel [] {wheel1,  wheel2},
				WheeledChassis.TYPE_DIFFERENTIAL);

		MovePilot pilot = new MovePilot(chassis);
		// travel 100 centimetres
		pilot.forward();
		
		
		

		// rotate 90 degrees
		//pilot.rotate(90);
		
		

		// press the ESCAPE button to stop moving
		while(pilot.isMoving()) {
			
			if(buttons.getButtons() == Keys.ID_ESCAPE) {
				pilot.stop();
			}
			
		}

		// block the thread until a button is pressed
		buttons.waitForAnyPress();
	}
}
