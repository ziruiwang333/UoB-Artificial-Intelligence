package test;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.TextLCD;
import lejos.utility.Delay;

public class testLCD {

	public static void main(String[] args) {
		EV3 ev3Brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3Brick.getKeys();
		buttons.waitForAnyPress();

		TextLCD lcddisplay = ev3Brick.getTextLCD();
		int[][] mazeMap = new int[9][6];
		int currentPosition = 2;
		int mapX = 0;
		int mapY = 5;
		int turnRightNum = 0;
		mazeMap[0][5] = 1;
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 6; y++) {
				mazeMap[x][y] = 0;
			}
		}
		TextLCD textLCD = ev3Brick.getTextLCD(Font.getFont(0, 0, Font.SIZE_SMALL));
		while(true) {
			
			textLCD.drawString("1234", 0, 0);
			
//			lcddisplay.drawString(mazeMap[0][0]+" "+mazeMap[1][0]+" "+mazeMap[2][0]+" "+mazeMap[3][0]+" "+mazeMap[4][0]+" "+mazeMap[5][0]+" "+mazeMap[6][0]+" "+mazeMap[7][0]+" "+mazeMap[8][0]+" ",0,0);
//			lcddisplay.drawString(mazeMap[0][1]+" "+mazeMap[1][1]+" "+mazeMap[2][1]+" "+mazeMap[3][1]+" "+mazeMap[4][1]+" "+mazeMap[5][1]+" "+mazeMap[6][1]+" "+mazeMap[7][1]+" "+mazeMap[8][1]+" ",0,1);
//		    lcddisplay.drawString(mazeMap[0][2]+" "+mazeMap[1][2]+" "+mazeMap[2][2]+" "+mazeMap[3][2]+" "+mazeMap[4][2]+" "+mazeMap[5][2]+" "+mazeMap[6][2]+" "+mazeMap[7][2]+" "+mazeMap[8][2]+" ",0,2);
//			lcddisplay.drawString(mazeMap[0][3]+" "+mazeMap[1][3]+" "+mazeMap[2][3]+" "+mazeMap[3][3]+" "+mazeMap[4][3]+" "+mazeMap[5][3]+" "+mazeMap[6][3]+" "+mazeMap[7][3]+" "+mazeMap[8][3]+" ",0,3);
//		    lcddisplay.drawString(mazeMap[0][4]+" "+mazeMap[1][4]+" "+mazeMap[2][4]+" "+mazeMap[3][4]+" "+mazeMap[4][4]+" "+mazeMap[5][4]+" "+mazeMap[6][4]+" "+mazeMap[7][4]+" "+mazeMap[8][4]+" ",0,4);
		    lcddisplay.drawString(mazeMap[0][5]+" "+mazeMap[1][5]+" "+mazeMap[2][5]+" "+mazeMap[3][5]+" "+mazeMap[4][5]+" "+mazeMap[5][5]+" "+mazeMap[6][5]+" "+mazeMap[7][5]+" "+mazeMap[8][5]+" ",0,5);
			Delay.msDelay(1000);
		}
	}

}
