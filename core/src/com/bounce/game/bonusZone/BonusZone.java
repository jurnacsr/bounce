package com.bounce.game.bonusZone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class BonusZone {
	private float x, y;
	private float w, h;
	private float lifeTime;
	private float lifeInc;
	private float scoreMult;
	private boolean active;
	
	public BonusZone(float x, float y, float w, float h, float lifeTime,
			float scoreMult) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.lifeTime = lifeTime;
		this.lifeInc = 0;
		this.scoreMult = scoreMult;
		this.active = true;
	}
	
	public void update() {
		lifeInc += Gdx.graphics.getDeltaTime();
		if (lifeInc >= lifeTime) {
			lifeInc = 0;
			this.active = false;
		}
	}
	
	public void render(SpriteBatch b) {
		if (active) {
			// draw @ x, y
			// particle effects?
		}
	}
	
	public boolean isIn(Vector2 mouse) {
		return (mouse.x > x && mouse.x < x + w) && (mouse.y > y && mouse.y < y + h);
	}
	
	
}
