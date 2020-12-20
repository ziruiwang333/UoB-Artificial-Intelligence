package week2;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class RotationTest {

	public static void main(String[] args) {

		EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
		// get EV3 brick
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		// instantiated LCD class for displaying and Keys class // for buttons
		Keys buttons = ev3brick.getKeys();
		// block the thread until we get input
		buttons.waitForAnyPress();
		
		// set motor to move 720 degrees per second
		LEFT_MOTOR.setSpeed(720);
		
		// rotate the motor one full revolution
		LEFT_MOTOR.rotate(360);
		
		// display the tacho count in row 0
		LCD.drawString("Tacho Read: " + LEFT_MOTOR.getTachoCount(), 0, 0);
		
		// rotate *to* the angle 360
		LEFT_MOTOR.rotateTo(360);
		
		// display the tacho count in row 1
		LCD.drawString("Tacho Read: " + LEFT_MOTOR.getTachoCount(), 0, 1);
		
		// block the thread until a button is pressed
		buttons.waitForAnyPress();
		LCD.clear();
	}

}
