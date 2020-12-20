package week2;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class TachometerTest {

	public static void main(String[] args) {
		
		EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		
		Keys buttons = ev3brick.getKeys();
		
		buttons.waitForAnyPress();
		
		LEFT_MOTOR.setSpeed(720);
		
		LEFT_MOTOR.forward();
		
		//complete code here
		
		int count = 0;
		
		while (count < 720)
			count = LEFT_MOTOR.getTachoCount();
		
		LEFT_MOTOR.stop();
		
		while (LEFT_MOTOR.getRotationSpeed() > 0);
		
		LCD.drawString("Tacho Read: " + LEFT_MOTOR.getTachoCount(), 0, 0);
		
		buttons.waitForAnyPress();
	}

}
