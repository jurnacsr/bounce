package com.bounce.game.frenzy;

import com.badlogic.gdx.Gdx;
import com.bounce.C;
import com.bounce.game.entity.BallEntity;

public class PulseFrenzy extends Frenzy {

	private float oldSize;
	private float oldSpeed;

	private float minSize, maxSize;
	private float minSpeed, maxSpeed;
	private boolean goingUp = true;
	
	public PulseFrenzy(BallEntity p, float d) {
		super(p, d);
		
		minSize = oldSize - oldSize * C.PULSE_FRENZY_SIZE_SCALE;
		maxSize = oldSize + oldSize * C.PULSE_FRENZY_SIZE_SCALE;
		
		minSpeed = oldSpeed - oldSpeed * C.PULSE_FRENZY_SIZE_SCALE;
		maxSpeed = oldSpeed + oldSpeed * C.PULSE_FRENZY_SIZE_SCALE;
	}
	
	public void update() {
		float amt;
		amt = 2 * oldSize * C.PULSE_FRENZY_SIZE_SCALE * Gdx.graphics.getDeltaTime();
		amt = goingUp ? amt : -amt;
		parent.setH(parent.getH() + amt);
		parent.setW(parent.getW() + amt);

		amt = 2 * oldSpeed * C.PULSE_FRENZY_SIZE_SCALE * Gdx.graphics.getDeltaTime();
		amt = goingUp ? amt : -amt;
		parent.setSpeed(parent.getH() + amt);
		
		if (goingUp && (parent.getW() >= maxSize || parent.getSpeed() >= maxSpeed)) {
			goingUp = !goingUp;
		}
		if (!goingUp && (parent.getW() <= minSize || parent.getSpeed() <= minSpeed)) {
			goingUp = !goingUp;
		}
	}

	@Override
	public void saveContext() {
		this.oldSize = this.parent.getW();
		this.oldSpeed = this.parent.getSpeed();
	}

	@Override
	public void restoreContext() {
		this.parent.setW(oldSize);
		this.parent.setSpeed(oldSpeed);
	}

}
