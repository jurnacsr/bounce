package com.bounce.game;

import java.util.Random;


public class Rumble {
	
	Random random;
	float x, y;
	float power;
	float current_power;
	private GameManager gm;
	
	public Rumble(float power, GameManager gm) {
		this.power = power;
		current_power = 0;
		random = new Random();
		this.gm = gm;
	}
	
	public void update() {
		x = (random.nextFloat() - 0.5f) * 2 * power;
		y = (random.nextFloat() - 0.5f) * 2 * power;
		
		gm.camera.translate(-x, -y);
	}
	
	public static void rumble(float power, GameManager gm) {
		float x = (float) ((Math.random() - 0.5f) * 2 * power);
		float y = (float) ((Math.random() - 0.5f) * 2 * power);
		
		gm.camera.translate(x, y);
	}

}
