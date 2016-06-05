package com.bounce;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bounce.game.GameManager;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	GameManager gm;

	@Override
	public void create () {
		
		gm = new GameManager();
		
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// update
		gm.update();
		
		// render
		gm.render(batch);
	}
}
