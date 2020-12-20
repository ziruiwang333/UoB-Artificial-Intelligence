package week2;

import lejos.hardware.BrickFinder;
import lejos.hardware.Key;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class BasicMotor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		
		Keys buttons = ev3brick.getKeys();
		
		buttons.waitForAnyPress();
		
		LEFT_MOTOR.forward();
		RIGHT_MOTOR.forward();
		LCD.drawString("ONWARDDS!", 0, 0);
		
		buttons.waitForAnyPress();
		
		LEFT_MOTOR.backward();
		RIGHT_MOTOR.backward();
		LCD.drawString("backwarD", 0, 1);
		
		buttons.waitForAnyPress();
		
		LEFT_MOTOR.stop();
		RIGHT_MOTOR.stop();
		LCD.drawString("stahp", 0, 2);
		
		buttons.waitForAnyPress();
		
	}

}
