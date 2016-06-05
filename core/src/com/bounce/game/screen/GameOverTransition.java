package com.bounce.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.bounce.C;
import com.bounce.game.GameManager;
import com.bounce.game.Rumble;

// game over transition adds camera shake and fadeout
public class GameOverTransition extends Transition {
	
	private GameManager gm;
	private Texture		gameOverFade;
	
	boolean noBatch = true;

	public GameOverTransition(GameManager gm, Screen s) {
		super(s, C.GAME_OVER_FADE_IN);		
		this.gm = gm;
		gameOverFade = new Texture("gameOverFade.png");
	}

	@Override
	public void render(SpriteBatch batch) {
		
		Color c = batch.getColor();
		float a = (lifeInc / lifeTime) * .75f;
		batch.setColor(new Color(1, 1, 1, a));
		batch.draw(gameOverFade, 0, 0, C.SX, C.SY);
		batch.setColor(c);
		
	}

	@Override
	public void update() {
		super.update();
		
		gm.setCameraPosition();
		
		// rumble
		Rumble.rumble(20, gm);

		// fade
		
	}

}
