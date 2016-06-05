package com.bounce.game.frenzy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bounce.C;
import com.bounce.game.entity.BallEntity;

public abstract class Frenzy {
	float 	duration;
	float	durationInc;
	float 	scoreMulti;
	boolean shouldKill;
	
	BallEntity	parent;
	
	
	public Frenzy(BallEntity p, float d) {
		this.parent = p;
		this.duration = d;

		saveContext();
	}
	
	public void update() {
		C.FRENZY_READY = false;
		if (shouldKill) {
			C.FRENZY_READY = true;
			parent.removeFrenzy();
			restoreContext();			
		}
		durationInc += Gdx.graphics.getDeltaTime();
	}
	
	public void render(SpriteBatch b) {
		
	}
	
	public abstract void saveContext();
	public abstract void restoreContext();

	public float getScoreMulti() {
		return scoreMulti;
	}
}
