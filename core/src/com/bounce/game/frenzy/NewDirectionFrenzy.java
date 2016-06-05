package com.bounce.game.frenzy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bounce.game.entity.BallEntity;

public class NewDirectionFrenzy extends Frenzy {
	
	float dirSwitchInc;
	float dirSwitchTime;
	
	float arrowSpinInc;
	float arrowSpinTime = 0.5f;
	
	float parentSpeed;
	int runs = 0;
	int maxRuns = 5;

	public NewDirectionFrenzy(BallEntity p, float d) {
		super(p, d);
		
		dirSwitchTime = super.duration / 3;
		scoreMulti = 1.0f;
	}
	
	public void update() {
		super.update();
		
		dirSwitchInc += Gdx.graphics.getDeltaTime();
		if(dirSwitchInc >= dirSwitchTime) {
			
			parent.newDirection();
			parent.makeSpawning();
			runs++;
			dirSwitchInc = 0;
		}
		if (runs >= maxRuns) {
			shouldKill = true;
		}
		
	}
	
	public void render(SpriteBatch b) {
		super.render(b);
	}

	@Override
	public void saveContext() {
		parentSpeed = parent.getSpeed();
	}

	@Override
	public void restoreContext() {
		parent.setSpeed(parentSpeed);
		parent.shouldDrawArrow(false);
	}

}
