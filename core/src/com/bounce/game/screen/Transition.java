package com.bounce.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Transition {
	
	protected float lifeTime;
	protected float lifeInc;
	protected boolean active = true;
	protected Screen s;
	
	public Transition(Screen s, float lt) {
		this.s = s;
		this.lifeTime = lt;
	}
	
	public abstract void render(SpriteBatch batch);
	public void update() {
		
		if (active) {
			this.lifeInc += Gdx.graphics.getDeltaTime();
			
			if (this.lifeInc >= this.lifeTime) this.finish();			
		}
	}
	
	public void finish() {
		active = false;
		s.transitionDone();
	}
}
