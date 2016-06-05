package com.bounce.game.frenzy;

import com.bounce.game.entity.BallEntity;

public class SimpleSpeedFrenzy extends Frenzy {
	
	float 	oldVelocity;
	float	velocityMulti = 1.66f;
	float	scoreScale = 0.5f;

	public SimpleSpeedFrenzy(BallEntity parent, float duration) {
		super(parent, duration);
		
		parent.setSpeed(oldVelocity * velocityMulti);
		scoreMulti = velocityMulti * scoreScale;
	}
	
	public void update() {

		super.update();
		
		if (durationInc >= duration) {
			shouldKill = true;
			durationInc = 0;
		}
	}

	@Override
	public void saveContext() {
		oldVelocity = parent.getSpeed();
	}

	@Override
	public void restoreContext() {
		parent.setSpeed(oldVelocity);
	}

}
