package assignment1;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.filter.MeanFilter;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class rescueLine {

	public static void main(String[] args) {
		EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);

		EV3ColorSensor LeftCS = new EV3ColorSensor(SensorPort.S1);
		EV3ColorSensor RightCS = new EV3ColorSensor(SensorPort.S2);

//			EV3UltrasonicSensor US = new EV3UltrasonicSensor(SensorPort.S4);
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys button = ev3brick.getKeys();
		button.waitForAnyPress();

		Wheel wheel1 = WheeledChassis.modelWheel(LEFT_MOTOR, 56).offset(65);
		Wheel wheel2 = WheeledChassis.modelWheel(RIGHT_MOTOR, 56).offset(-65);

		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);

		MovePilot pilot = new MovePilot(chassis);
		Port portS4 = ev3brick.getPort("S4");

		EV3UltrasonicSensor sonicSensor = new EV3UltrasonicSensor(portS4);
		while (true) {
			SampleProvider sonicdistance = sonicSensor.getDistanceMode();

			SampleProvider average = new MeanFilter(sonicdistance, 5);

			float[] sample = new float[average.sampleSize()];
			
			sonicdistance.fetchSample(sample, 0);
			float x = sample[0];
			
			while (x > 0.5) {
				while (LeftCS.getColorID() == 6 && RightCS.getColorID() == 6) {
					LEFT_MOTOR.setSpeed(180);
					RIGHT_MOTOR.setSpeed(180);
					LEFT_MOTOR.forward();
					RIGHT_MOTOR.forward();
				}
				while (LeftCS.getColorID() == 7 && RightCS.getColorID() == 6) {
					LEFT_MOTOR.setSpeed(50);
					LEFT_MOTOR.backward();
					RIGHT_MOTOR.setSpeed(180);
					RIGHT_MOTOR.forward();
				}
				while (LeftCS.getColorID() == 6 && RightCS.getColorID() == 7) {
					RIGHT_MOTOR.setSpeed(50);
					RIGHT_MOTOR.backward();
					LEFT_MOTOR.setSpeed(180);
					LEFT_MOTOR.forward();
				}
				while (LeftCS.getColorID() == 0 && RightCS.getColorID() == 0) {
					LEFT_MOTOR.stop();
					RIGHT_MOTOR.stop();
				}
				while (LeftCS.getColorID() == 1 && RightCS.getColorID() == 1) {
					pilot.rotate(180);
				}
				while ((LeftCS.getColorID() == 0 && RightCS.getColorID() != 0)
						|| (LeftCS.getColorID() != 0 && RightCS.getColorID() == 0)) {
					LEFT_MOTOR.setSpeed(180);
					RIGHT_MOTOR.setSpeed(180);
					LEFT_MOTOR.forward();
					RIGHT_MOTOR.forward();
				}
				while ((LeftCS.getColorID() == 1 && RightCS.getColorID() != 1)) {
					LEFT_MOTOR.setSpeed(180);
					RIGHT_MOTOR.setSpeed(180);
					LEFT_MOTOR.forward();
					RIGHT_MOTOR.forward();
					Delay.msDelay(1000);
					pilot.rotate(90);
				}
				while (RightCS.getColorID() == 1 && LeftCS.getColorID() != 1) {
					LEFT_MOTOR.setSpeed(180);
					RIGHT_MOTOR.setSpeed(180);
					LEFT_MOTOR.forward();
					RIGHT_MOTOR.forward();
					Delay.msDelay(1000);
					pilot.rotate(-90);
				}
				while ((LeftCS.getColorID() == 0 && RightCS.getColorID() != 0)
						|| (RightCS.getColorID() == 0 && LeftCS.getColorID() != 0)) {
					LEFT_MOTOR.setSpeed(180);
					RIGHT_MOTOR.setSpeed(180);
					LEFT_MOTOR.forward();
					RIGHT_MOTOR.forward();
				}

				while (x <= 0.5) {
					pilot.rotate(90);
					pilot.forward();
					Delay.msDelay(500);
					pilot.rotate(-90);
					pilot.forward();
					Delay.msDelay(1000);
					pilot.rotate(90);
					pilot.forward();
					Delay.msDelay(500);
				}
			}
		}

	}

}
