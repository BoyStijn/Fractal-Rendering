package lls.fractaldemo;

import lls.fractaldemo.engine.GameEngine;
import lls.fractaldemo.engine.IGameLogic;

public class App 
{
	public static void main(String[] args) {
		try {
			boolean vSync = true;
			IGameLogic gameLogic = new Demo();
			GameEngine gameEng = new GameEngine("Demo", 1920, 1080, vSync, true, gameLogic);
			gameEng.run();
		} catch (Exception excp) {
			excp.printStackTrace();
			System.exit(-1);
		} 
	}
}

