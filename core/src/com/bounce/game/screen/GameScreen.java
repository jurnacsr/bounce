package com.bounce.game.screen;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bounce.C;
import com.bounce.game.GameManager;
import com.bounce.game.entity.BallEntity;
import com.bounce.game.entity.Entity;
import com.bounce.game.frenzy.FrenzyFactory;

public class GameScreen extends Screen {

	BitmapFont 			font;
	GlyphLayout 		layout;
	DecimalFormat 		formatter = new DecimalFormat("0.0");
	GameOverTransition 	got;
	Texture 			noTouchBanner;
	Texture				bg;
	Texture				bgBorder;
	FrenzyFactory 		frenzyFactory;

	boolean active = true;
	boolean updateEntities = true;
	boolean gameOverTransition = false;
	float bx, by;
	int flyInDirection = 0; // u, d, l, r

	float triggerInc;
	float triggerTime = 5.0f;
	int triggers = 0;

	boolean frenzySpawning = false;
	boolean frenzyActive = false;
	float frenzySpawnInc = 0f;
	float frenzySpawnTime = 1f;

	public GameScreen(GameManager gm) {
		super(gm);

		bx = gm.getDims().x;
		got = new GameOverTransition(gm, this);

		bg = new Texture("bg.png");
		bgBorder = new Texture("bgBorder.png");
		noTouchBanner = new Texture("noTouchBanner.png");
		layout = new GlyphLayout();
		font = new BitmapFont(Gdx.files.internal("font/gameFont.fnt"), 
							  Gdx.files.internal("font/gameFont.png"),
							  false);
		font.setColor(Color.BLACK);

		removeAllEntities();
		addEntity(new BallEntity("ball.png", triggers, gm));

		// dummy creation placeholder
		frenzyFactory = new FrenzyFactory();
	}

	@Override
	public void update() {
		if (active) {

			if (updateEntities) {
				super.update();
				for (Entity e : entityList) {
					float sc = ((BallEntity) e).getScore();
					sc = sc + (sc * C.TRIGGER_SCALE(triggers));
					gm.updateScore(sc);
					
					this.frenzyActive = ((BallEntity)e).hasFrenzy();
				}

				// update timers
				triggerTimerTick();
			}
			bx = gm.getDims().x;

			// are we counting down a frenzy?
			if (frenzySpawning) {
				frenzySpawnInc += Gdx.graphics.getDeltaTime();
				if (frenzySpawnInc >= frenzySpawnTime) {
					frenzySpawnInc = 0f;
					frenzySpawning = false;
					addFrenzy();
				}
			}
		} else {
			bx -= C.GAME_NO_TOUCH_FLYIN_SPEED;
			if (bx <= 0)
				bx = 0;
		}

		if (gameOverTransition) {
			got.update();
		}

		active = Gdx.input.isTouched();
	}

	@Override
	public void render(SpriteBatch batch) {

		Color oc = batch.getColor();
		batch.setColor(gm.getBgcolor());
		batch.draw(bg, 0, 0, C.SX, C.SY, 0, 0, bg.getWidth(), bg.getHeight(), false, false);
		batch.setColor(oc);

		
		Vector2 scoreDrawPos = gm.unproject(20, 20);
		layout.setText(font, "Score: " + formatter.format(gm.getScore()));
		font.draw(batch, layout, scoreDrawPos.x, scoreDrawPos.y);
		
		layout.setText(font, "FPS: " + Gdx.graphics.getFramesPerSecond());
		float x = C.SX - layout.width;
		Vector2 fpsDrawPos = gm.unproject(x - 20, 20);
		font.draw(batch, layout, fpsDrawPos.x, fpsDrawPos.y);
		
		Vector2 frenzyDrawPos;
		if (frenzySpawning || frenzyActive) {
			String frenzyText = frenzyFactory.getFrenzyMessage();
			layout.setText(font, frenzyText);
			float fx = (C.SX - layout.width) / 2;
			frenzyDrawPos = gm.unproject(fx, 20);
			font.draw(batch, frenzyText, frenzyDrawPos.x, frenzyDrawPos.y);
		}

		super.render(batch);

		if (!active) {
			if (!gameOverTransition) {
				batch.draw(noTouchBanner, bx, 0, gm.getDims().x, gm.getDims().y);
			}
		}
		batch.draw(bgBorder, 0, 0, C.SX, C.SY, 0, 0, bgBorder.getWidth(), bgBorder.getHeight(), false, false);

		if (gameOverTransition) {
			got.render(batch);
		}
	}

	private void triggerTimerTick() {
		triggerInc += Gdx.graphics.getDeltaTime();

		if (triggerInc >= triggerTime) {
			// trigger an effect
			effectTrigger();
			triggerInc = 0.0f;
		}
	}

	private void effectTrigger() {

		double r = Math.random();
		
		triggers++;
		
		/*if (triggers >= 1) {
			newFrenzy();
			return;
		}*/

		// new ball spawns
		if (r <= 0.5) {
			spawnNewBall();
		}
		// frenzy
		else if (r <= 0.88) {
			//spawnNewBall();
			newFrenzy();
		}
		// both!
		else {
			spawnNewBall();
			newFrenzy();
		}

	}

	private void spawnNewBall() {
		addEntity(new BallEntity("ball.png", triggers, gm));
	}

	private void newFrenzy() {
		if (!C.FRENZY_READY) return;
		gm.log("Starting frenzy countdown");
		// decide new frenzy
		frenzyFactory = new FrenzyFactory();

		// activate timer
		frenzySpawning = true;
	}

	private void addFrenzy() {
		gm.log("Adding frenzy");
		float frenzyTime = MathUtils.random(triggerTime * 0.75f,
				triggerTime * 1.5f);
		for (Entity e : entityList) {
			((BallEntity) e).giveFrenzy(frenzyFactory.buildFrenzy(
					(BallEntity) e, frenzyTime));
		}

	}

	public void tooClose() {
		gameOverTransition = true;
		updateEntities = false;
	}

	@Override
	public void transitionDone() {

		Gdx.app.log("BounceInfo", "game over screen");
		gm.setScreen("gameOver");

	}

}
