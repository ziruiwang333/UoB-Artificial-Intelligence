package week4;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * This is based upon sample code from the BehaviorBasedBumperCar
 * 
 * Demonstration of the Behavior subsumption classes.
 * 
 * Requires a wheeled vehicle with two independently controlled motors connected
 * to motor ports B and C, and an ultrasonic sensor connected to port 4;
 * 
 * @author Brian Bagnall and Lawrie Griffiths, modified by Roger Glassey
 *
 */
public class BehaviorBasedBumperCar {
	// static RegulatedMotor leftMotor = MirrorMotor.invertMotor(Motor.A);
	// static RegulatedMotor rightMotor = MirrorMotor.invertMotor(Motor.B);
	static RegulatedMotor leftMotor = Motor.B;
	static RegulatedMotor rightMotor = Motor.C;
	static IRSensor sensor;

	// Use these definitions instead if your motors are inverted
	// static RegulatedMotor leftMotor = MirrorMotor.invertMotor(Motor.A);
	// static RegulatedMotor rightMotor = MirrorMotor.invertMotor(Motor.C);

	
	public static void main(String[] args) {
		

		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		leftMotor.rotateTo(0);
		rightMotor.rotateTo(0);
		leftMotor.setSpeed(400);
		rightMotor.setSpeed(400);
		leftMotor.setAcceleration(800);
		rightMotor.setAcceleration(800);
		sensor = new IRSensor();
		sensor.setDaemon(true);
		sensor.start();
		Behavior b1 = new DriveForward();
		Behavior b2 = new DetectWall();
		Behavior[] behaviorList = { b1, b2 };
		Arbitrator arbitrator = new Arbitrator(behaviorList);
		LCD.drawString("Bumper Car", 0, 1);
		Button.LEDPattern(6);
		Button.waitForAnyPress();
		arbitrator.go();
	}
}

class IRSensor extends Thread {
	EV3IRSensor ir = new EV3IRSensor(SensorPort.S4);
	SampleProvider sp = ir.getDistanceMode();
	public int control = 0;
	public int distance = 70;

	IRSensor() {

	}

	public void run() {
		while (!Button.ESCAPE.isDown()) {
			float[] sample = new float[sp.sampleSize()];
			sp.fetchSample(sample, 0);
			distance = (int) sample[0];
			System.out.println(" Distance: " + distance);

		}
		System.exit(0);

	}
}

class DriveForward implements Behavior {

	private boolean _suppressed = false;

	public boolean takeControl() {
		if (Button.readButtons() != 0) {
			_suppressed = true;
			BehaviorBasedBumperCar.leftMotor.stop();
			BehaviorBasedBumperCar.rightMotor.stop();
			Button.LEDPattern(6);
			Button.discardEvents();
			System.out.println("Button pressed");
			if ((Button.waitForAnyPress() & Button.ID_ESCAPE) != 0) {
				Button.LEDPattern(0);
				System.exit(1);
			}
			System.out.println("Button pressed 2");
			Button.waitForAnyEvent();
			System.out.println("Button released");
		}
		return true;
	}

	public void suppress() {
		_suppressed = true;// standard practice for suppress methods
	}

	public void action() {
		_suppressed = false;
		BehaviorBasedBumperCar.leftMotor.forward();
		BehaviorBasedBumperCar.rightMotor.forward();
		while (!_suppressed) {
			BehaviorBasedBumperCar.leftMotor.forward();
			BehaviorBasedBumperCar.rightMotor.forward();

			Thread.yield(); // don't exit till suppressed
		}
		BehaviorBasedBumperCar.leftMotor.stop(true);
		BehaviorBasedBumperCar.rightMotor.stop(true);
	}
}

class DetectWall implements Behavior {

	public DetectWall() {

	}

	private boolean checkDistance() {

		int dist = BehaviorBasedBumperCar.sensor.distance;
		if (dist < 30) {
			Button.LEDPattern(2);
			return true;
		} else {
			Button.LEDPattern(1);
			return false;
		}
	}

	public boolean takeControl() {
		return checkDistance();
	}

	public void suppress() {
		// Since this is highest priority behavior, suppress will never be called.
	}

	public void action() {
		BehaviorBasedBumperCar.leftMotor.rotate(-180, true);// start Motor.A rotating backward
		BehaviorBasedBumperCar.rightMotor.rotate(-180); // rotate C farther to make the turn
		if ((System.currentTimeMillis() & 0x1) != 0) {
			BehaviorBasedBumperCar.leftMotor.rotate(-180, true);// start Motor.A rotating backward
			BehaviorBasedBumperCar.rightMotor.rotate(180); // rotate C farther to make the turn
		} else {
			BehaviorBasedBumperCar.rightMotor.rotate(-180, true);// start Motor.A rotating backward
			BehaviorBasedBumperCar.leftMotor.rotate(180); // rotate C farther to make the turn
		}
	}

}
