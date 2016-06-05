package com.bounce.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bounce.C;
import com.bounce.game.screen.GameOverScreen;
import com.bounce.game.screen.GameScreen;
import com.bounce.game.screen.Screen;
import com.bounce.game.screen.StartGameScreen;

public class GameManager {
	
	float 	mx, my;
	int 	dispScore;
	float 	score;
	boolean active 				= true;
	
	boolean gameOverTriggered	= false;
	float 	gameOverTime 		= 3f;
	float	gameOverInc			= 0f;
	
	GameScreen 			gameScreen;
	StartGameScreen 	startScreen;
	GameOverScreen		gameOverScreen;
	Screen 				currentScreen;
	ShapeRenderer		sr;
	OrthographicCamera	camera;
	Color				bgColor;
	
	
	public GameManager() {
		init();
	}

	public void init() {		
		
		score = 0f;
		camera = new OrthographicCamera(C.SX, C.SY);
		setCameraPosition();
		sr = new ShapeRenderer();
		
		startScreen = new StartGameScreen(this);
		gameScreen = new GameScreen(this);

		bgColor = randomColor();
		bgColor.a = 0.5f;
		
		currentScreen = startScreen;
	}

	public void setCameraPosition() {
		
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight/2, 0);
		camera.update();
		
	}

	public void update() {
		
		camera.update();
		
		// get mouse coordinates
		mx = Gdx.input.getX();
		my = Gdx.input.getY();
		
		currentScreen.update();
	}

	public void render(SpriteBatch batch) {
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		
		currentScreen.render(batch);
		
		batch.end();
		
	}

	
	public void tooClose() {
		gameScreen.tooClose();
	}
	
	public Vector2 unproject(float x, float y) {
		Vector3 v = camera.unproject(new Vector3(x, y, 0));
		return new Vector2(v.x, v.y);
	}

	public float getMx() {
		return mx;
	}

	public void setMx(float mx) {
		this.mx = mx;
	}

	public float getMy() {
		return my;
	}
	
	public Vector2 getMouse() {
		Vector3 mm = new Vector3(mx, my, 0);
		mm = camera.unproject(mm);
		return new Vector2(mm.x, mm.y);
	}

	public void setMy(float my) {
		this.my = my;
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}

	public void gameOver() {
		active = false;		
	}
	
	public void updateScore(float inc) {
		this.score += inc;
	}

	public float getScore() {
		return score;
	}

	public void startGame() {
		currentScreen = gameScreen;
	}
	
	public Vector2 getDims() {
		return new Vector2(C.SX, C.SY);
	}

	public void setScreen(String string) {
		switch (string) {
		case "gameOver":
			{
				currentScreen = new GameOverScreen(this);
			}
		}
		
	}

	public void restartGame() {
		this.currentScreen = gameScreen;
	}

	public void log(String msg) {
		Gdx.app.log("BounceInfo", msg);
	}

	public Color randomColor() {
		float r = MathUtils.random();
		float g = MathUtils.random();
		float b = MathUtils.random();
		return new Color(r, g, b, 1.0f);
	}
	
	public void setBgColor(Color c) {
		this.bgColor = c;
	}

	public Color getBgcolor() {
		return this.bgColor;
	}
}
