package assignment2;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class rescueMaze3 {

	public static void main(String[] args) {
		EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

		EV3 ev3Brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3Brick.getKeys();
		buttons.waitForAnyPress();

		TextLCD lcddisplay = ev3Brick.getTextLCD(Font.getFont(0, 0, Font.SIZE_SMALL));
		TextLCD lcddisplay2 = ev3Brick.getTextLCD();

		Wheel wheel1 = WheeledChassis.modelWheel(leftMotor, 56).offset(65);
		Wheel wheel2 = WheeledChassis.modelWheel(rightMotor, 56).offset(-65);

		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);

//		EV3GyroSensor ev3GyroSensor = new EV3GyroSensor(SensorPort.S3);

		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);

		EV3MediumRegulatedMotor ev3MediumRegulatedMotor = new EV3MediumRegulatedMotor(MotorPort.C);

		EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S3);
		SampleProvider irDistance = irSensor.getDistanceMode();
		float[] irsample = new float[irDistance.sampleSize()];

		int[][] mazeMap = new int[9][6];
		int currentPosition = 2;
		int mapX = 0;
		int mapY = 5;
		int turnRightNum = 0;
		int redNum = 0;
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 6; y++) {
				mazeMap[x][y] = 0;
			}
		}
		mazeMap[0][5] = 1;

		while (true) {
			
			irDistance.fetchSample(irsample, 0);
			float i1 = irsample[0];
			Sound.playTone((int) (200), 50);

			ev3MediumRegulatedMotor.rotate(90);
			irDistance.fetchSample(irsample, 0);
			float i2 = irsample[0];

			ev3MediumRegulatedMotor.rotate(-180);
			irDistance.fetchSample(irsample, 0);
			float i3 = irsample[0];

			ev3MediumRegulatedMotor.rotate(90);
			
			while(redNum>0&&mazeMap[0][5]!=1) {
				pilot.stop();
			}

			// meet green
			if (colorSensor.getColorID() == 1 || colorSensor.getColorID() == 0) {
				int currentColorID = 0;
				currentColorID = colorSensor.getColorID();
				if (currentColorID == 1)
					lcddisplay2.drawString("Green, turn round", 0, 7);
				if (currentColorID == 0)
					lcddisplay2.drawString("Red,End, go back", 0, 7);
				pilot.setAngularSpeed(80);
				pilot.rotate(160);
				pilot.setLinearSpeed(135);
				pilot.forward();
				Delay.msDelay(3000);
				pilot.stop();
				if (currentColorID == 1) {
					if (turnRightNum % 4 == 0) {
						mazeMap[mapX][mapY] = -1;
						mapY++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum = turnRightNum + 2;
					} else if (turnRightNum % 4 == 1) {
						mazeMap[mapX][mapY] = -1;
						mapX--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum = turnRightNum + 2;
					} else if (turnRightNum % 4 == 2) {
						mazeMap[mapX][mapY] = -1;
						mapY--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum = turnRightNum + 2;

					} else if (turnRightNum % 4 == 3) {
						mazeMap[mapX][mapY] = -1;
						mapX++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum = turnRightNum + 2;
					} else if (turnRightNum == -1) {
						mazeMap[mapX][mapY] = -1;
						mapX++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum = turnRightNum + 2;
					} else if (turnRightNum == -2) {
						mazeMap[mapX][mapY] = -1;
						mapY--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum = turnRightNum + 2;
					} else if (turnRightNum == -3) {
						mazeMap[mapX][mapY] = -1;
						mapX--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum++;
					}
				}
				else if(currentColorID==0){
						if (turnRightNum % 4 == 0) {
							mazeMap[mapX][mapY] = -2;
							mapY++;
							mazeMap[mapX][mapY] = currentPosition++;
							turnRightNum = turnRightNum + 2;
							redNum++;
						} else if (turnRightNum % 4 == 1) {
							mazeMap[mapX][mapY] = -2;
							mapX--;
							mazeMap[mapX][mapY] = currentPosition++;
							turnRightNum = turnRightNum + 2;
							redNum++;
						} else if (turnRightNum % 4 == 2) {
							mazeMap[mapX][mapY] = -2;
							mapY--;
							mazeMap[mapX][mapY] = currentPosition++;
							turnRightNum = turnRightNum + 2;
							redNum++;
						} else if (turnRightNum % 4 == 3) {
							mazeMap[mapX][mapY] = -2;
							mapX++;
							mazeMap[mapX][mapY] = currentPosition++;
							turnRightNum = turnRightNum + 2;
							redNum++;
						} else if (turnRightNum == -1) {
							mazeMap[mapX][mapY] = -2;
							mapX++;
							mazeMap[mapX][mapY] = currentPosition++;
							turnRightNum = turnRightNum + 2;
							redNum++;
						} else if (turnRightNum == -2) {
							mazeMap[mapX][mapY] = -2;
							mapY--;
							mazeMap[mapX][mapY] = currentPosition++;
							turnRightNum = turnRightNum + 2;
							redNum++;
						} else if (turnRightNum == -3) {
							mazeMap[mapX][mapY] = -2;
							mapX--;
							mazeMap[mapX][mapY] = currentPosition++;
							turnRightNum++;
							redNum++;
						}
				}
			}

			// forward
			else {
				if (i1 > 30 && i3 < 30) {
					lcddisplay2.drawString("go forward", 0, 7);
					pilot.setLinearSpeed(135);
					pilot.forward();
					Delay.msDelay(3000);
					pilot.stop();
					lcddisplay.clear();

//					if(i1>30&&i2<30&&i3<30&&turnRightNum==0) {
//						mazeMap[mapX][mapY] = currentPosition++;
//						mapY--;
//					}
//					else if(i1>30&&i2>30&&i3<30&&turnRightNum==0) {
//						mazeMap[mapX][mapY] = currentPosition++;
//						mapY--;
//					}
//					else if(i1>30&&i2<30&&i3<30&&turnRightNum==1) {
//						mazeMap[mapX][mapY] = currentPosition++;
//						mapX++;
//					}
//					if (i1 > 30 && i2 < 30 && i3 < 30) {
					if (turnRightNum % 4 == 0) {
						mapY--;
						mazeMap[mapX][mapY] = currentPosition++;
					} else if (turnRightNum % 4 == 1) {
						mapX++;
						mazeMap[mapX][mapY] = currentPosition++;
					} else if (turnRightNum == -1) {
						mapX--;
						mazeMap[mapX][mapY] = currentPosition++;
					} else if (turnRightNum % 4 == 2) {
						mapY++;
						mazeMap[mapX][mapY] = currentPosition++;
					} else if (turnRightNum == -2) {
						mapY++;
						mazeMap[mapX][mapY] = currentPosition++;
					} else if (turnRightNum % 4 == 3) {
						mapX--;
						mazeMap[mapX][mapY] = currentPosition++;
					} else if (turnRightNum == -3) {
						mapX++;
						mazeMap[mapX][mapY] = currentPosition++;
					}
				}
//					} else if (i1 > 30 && i2 > 30 && i3 < 30) {
//						if (turnRightNum == 0) {
//							mapY--;
//							mazeMap[mapX][mapY] = currentPosition++;
//						} else if (turnRightNum == 1) {
//							mapX++;
//							mazeMap[mapX][mapY] = currentPosition++;
//						} else if (turnRightNum == -1) {
//							mapX--;
//							mazeMap[mapX][mapY] = currentPosition++;
//						} else if (turnRightNum == 2) {
//							mapY++;
//							mazeMap[mapX][mapY] = currentPosition++;
//						} else if (turnRightNum == -2) {
//							mapY++;
//							mazeMap[mapX][mapY] = currentPosition++;
//						}
//					}
//				}

				// turn left
				if (i1 < 30 && i3 > 30) {
					lcddisplay2.drawString("turn left", 0, 7);
					pilot.setAngularSpeed(80);
					pilot.rotate(-90);
					pilot.setLinearSpeed(135);
					pilot.forward();
					Delay.msDelay(3000);
					pilot.stop();
					lcddisplay.clear();
					if (turnRightNum % 4 == 0) {
						mapX--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum--;
					} else if (turnRightNum % 4 == 1) {
						mapY--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum--;
					} else if (turnRightNum % 4 == 2) {
						mapX++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum--;
					} else if (turnRightNum % 4 == 3) {
						mapY++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum--;
					} else if (turnRightNum == -1) {
						mapY++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum--;
					} else if (turnRightNum == -2) {
						mapX++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum--;
					} else if (turnRightNum == -3) {
						mapY--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum--;
					}
				}
				if (i1 > 30 && i3 > 30) {
					lcddisplay2.drawString("turn left", 0, 7);
					pilot.setAngularSpeed(80);
					pilot.rotate(-90);
					pilot.setLinearSpeed(135);
					pilot.forward();
					Delay.msDelay(3000);
					pilot.stop();
					lcddisplay.clear();
					if (turnRightNum % 4 == 0) {
						mapX--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum--;
					} else if (turnRightNum % 4 == 1) {
						mapY--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum--;
					} else if (turnRightNum % 4 == 2) {
						mapX++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum--;
					} else if (turnRightNum % 4 == 3) {
						mapY++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum--;
					} else if (turnRightNum == -1) {
						mapY++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum--;
					} else if (turnRightNum == -2) {
						mapX++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum--;
					} else if (turnRightNum == -3) {
						mapY--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum--;
					}
				}

				// turn right
				if (i1 < 30 && i3 < 30 && i2 > 30) {
					lcddisplay2.drawString("turn right", 0, 7);
					pilot.setAngularSpeed(80);
					pilot.rotate(85);
					pilot.setLinearSpeed(135);
					pilot.forward();
					Delay.msDelay(3200);	
					pilot.stop();
					lcddisplay.clear();
					if (turnRightNum % 4 == 0) {
						mapX++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum++;
					} else if (turnRightNum % 4 == 1) {
						mapY++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum++;
					} else if (turnRightNum % 4 == 2) {
						mapX--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum++;
					} else if (turnRightNum % 4 == 3) {
						mapY--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum++;
					} else if (turnRightNum == -1) {
						mapY--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum++;
					} else if (turnRightNum == -2) {
						mapX--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum++;
					} else if (turnRightNum == -3) {
						mapY++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum++;
					}
				}

				// turn round
				if (i1 < 30 && i2 < 30 && i3 < 30) {
					lcddisplay2.drawString("turn around", 0, 6);
					pilot.setAngularSpeed(80);
					pilot.rotate(180);
					pilot.setLinearSpeed(135);
					pilot.forward();
					Delay.msDelay(3200);
					pilot.stop();
					lcddisplay.clear();
					if (turnRightNum % 4 == 0) {
						mapY++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum = turnRightNum + 2;
					} else if (turnRightNum % 4 == 1) {
						mapX--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum = turnRightNum + 2;
					} else if (turnRightNum % 4 == 2) {
						mapY--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum = turnRightNum + 2;

					} else if (turnRightNum % 4 == 3) {
						mapX++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum = turnRightNum + 2;
					} else if (turnRightNum == -1) {
						mapX++;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum = turnRightNum + 2;
					} else if (turnRightNum == -2) {
						mapY--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum = turnRightNum + 2;
					} else if (turnRightNum == -3) {
						mapX--;
						mazeMap[mapX][mapY] = currentPosition++;
						turnRightNum++;
					}
				}

//				for (int x = 0; x < 9; x++) {
//					for (int y = 0; y < 6; y++) {
//						lcddisplay.drawString(mazeMap[x][y] + "|", x, y);
//					}
//				}
			}

			lcddisplay.drawString(mazeMap[0][0] + " " + mazeMap[1][0] + " " + mazeMap[2][0] + " " + mazeMap[3][0] + " "
					+ mazeMap[4][0] + " " + mazeMap[5][0] + " " + mazeMap[6][0] + " " + mazeMap[7][0] + " "
					+ mazeMap[8][0] + " ", 0, 0);
			lcddisplay.drawString(mazeMap[0][1] + " " + mazeMap[1][1] + " " + mazeMap[2][1] + " " + mazeMap[3][1] + " "
					+ mazeMap[4][1] + " " + mazeMap[5][1] + " " + mazeMap[6][1] + " " + mazeMap[7][1] + " "
					+ mazeMap[8][1] + " ", 0, 1);
			lcddisplay.drawString(mazeMap[0][2] + " " + mazeMap[1][2] + " " + mazeMap[2][2] + " " + mazeMap[3][2] + " "
					+ mazeMap[4][2] + " " + mazeMap[5][2] + " " + mazeMap[6][2] + " " + mazeMap[7][2] + " "
					+ mazeMap[8][2] + " ", 0, 2);
			lcddisplay.drawString(mazeMap[0][3] + " " + mazeMap[1][3] + " " + mazeMap[2][3] + " " + mazeMap[3][3] + " "
					+ mazeMap[4][3] + " " + mazeMap[5][3] + " " + mazeMap[6][3] + " " + mazeMap[7][3] + " "
					+ mazeMap[8][3] + " ", 0, 3);
			lcddisplay.drawString(mazeMap[0][4] + " " + mazeMap[1][4] + " " + mazeMap[2][4] + " " + mazeMap[3][4] + " "
					+ mazeMap[4][4] + " " + mazeMap[5][4] + " " + mazeMap[6][4] + " " + mazeMap[7][4] + " "
					+ mazeMap[8][4] + " ", 0, 4);
			lcddisplay.drawString(mazeMap[0][5] + " " + mazeMap[1][5] + " " + mazeMap[2][5] + " " + mazeMap[3][5] + " "
					+ mazeMap[4][5] + " " + mazeMap[5][5] + " " + mazeMap[6][5] + " " + mazeMap[7][5] + " "
					+ mazeMap[8][5] + " ", 0, 5);

//				lcddisplay.drawString(mazeMap[0][0]+" "+mazeMap[1][0]+" "+mazeMap[2][0]+" "+mazeMap[3][0]+" "+mazeMap[4][0]+" "+mazeMap[5][0]+" "+mazeMap[6][0]+" "+mazeMap[7][0]+" "+mazeMap[8][0]+" "+"\n"+
//									  mazeMap[0][1]+" "+mazeMap[1][1]+" "+mazeMap[2][1]+" "+mazeMap[3][1]+" "+mazeMap[4][1]+" "+mazeMap[5][1]+" "+mazeMap[6][1]+" "+mazeMap[7][1]+" "+mazeMap[8][1]+" "+"\n"+
//						              mazeMap[0][2]+" "+mazeMap[1][2]+" "+mazeMap[2][2]+" "+mazeMap[3][2]+" "+mazeMap[4][2]+" "+mazeMap[5][2]+" "+mazeMap[6][2]+" "+mazeMap[7][2]+" "+mazeMap[8][2]+" "+"\n"+
//									  mazeMap[0][3]+" "+mazeMap[1][3]+" "+mazeMap[2][3]+" "+mazeMap[3][3]+" "+mazeMap[4][3]+" "+mazeMap[5][3]+" "+mazeMap[6][3]+" "+mazeMap[7][3]+" "+mazeMap[8][3]+" "+"\n"+
//						              mazeMap[0][4]+" "+mazeMap[1][4]+" "+mazeMap[2][4]+" "+mazeMap[3][4]+" "+mazeMap[4][4]+" "+mazeMap[5][4]+" "+mazeMap[6][4]+" "+mazeMap[7][4]+" "+mazeMap[8][4]+" "+"\n"+
//									  mazeMap[0][5]+" "+mazeMap[1][5]+" "+mazeMap[2][5]+" "+mazeMap[3][5]+" "+mazeMap[4][5]+" "+mazeMap[5][5]+" "+mazeMap[6][5]+" "+mazeMap[7][5]+" "+mazeMap[8][5]+" ", 0, 0);

		}
	}
}
