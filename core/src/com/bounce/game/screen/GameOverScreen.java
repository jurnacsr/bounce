package com.bounce.game.screen;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bounce.C;
import com.bounce.game.GameManager;

public class GameOverScreen extends Screen {
	
	BitmapFont font;
	
	String 			gameOverMessage 		= "Game Over";
	String 			scoreMessage			= "Score: {SCORE}";
	String 			phoneStartMessage 		= "Hold for {TIME} more {UNIT} to start";
	List<String> 	messages 				= new ArrayList<String>();
	DecimalFormat 	formatter 				= new DecimalFormat("0.0");
	GlyphLayout		gl						= new GlyphLayout();
	Texture			bg;
	
	float	holdTime 		= 1f;
	float 	holdInc 		= 0f;
	float 	holdSeconds		= holdTime;
	String	unit			= "";
	
	boolean	canTouch = false;
	
	public GameOverScreen(GameManager gm) {
		super(gm);
		
		font = new BitmapFont(Gdx.files.internal("font/gameFont.fnt"), 
							  Gdx.files.internal("font/gameFont.png"),
							  false);
		font.setColor(Color.BLACK);
		
		messages.add(gameOverMessage);
		messages.add(scoreMessage);
		messages.add(phoneStartMessage);

		bg = new Texture("bg.png");
	}
	
	public void render(SpriteBatch batch) {
		super.render(batch);
		
		Color oc = batch.getColor();
		batch.setColor(gm.getBgcolor());
		batch.draw(bg, 0, 0, C.SX, C.SY, 0, 0, bg.getWidth(), bg.getHeight(), false, false);
		batch.setColor(oc);

		float y = C.SY * 0.25f;
		float x = C.SX;
		Vector2 pos;
		
		for (String s : messages) {
			
			s = s.replace("{SCORE}", formatter.format(gm.getScore()));
			
			s = s.replace("{TIME}", formatter.format(holdSeconds));
			s = s.replace("{UNIT}", unit);
			
			gl.setText(font, s);
			x = (C.SX - gl.width) / 2;
			pos = gm.unproject(x, y);
			
			font.draw(batch, s, pos.x, pos.y);
			
			y = y + C.FONT_Y_OFFSET;
			pos = gm.unproject(x, y);
		}
	}
	
	public void update() {
		super.update();

		gm.setCameraPosition();


		holdSeconds = holdTime - holdInc;
		if (Gdx.input.isTouched()) {
			
			holdInc += Gdx.graphics.getDeltaTime();
			
			if (holdInc >= holdTime) {
				gm.init();
				gm.restartGame();
			}
		} else {
			holdInc = 0;
		}
		
		unit = holdSeconds == 1.0f ? "second" : "seconds";
		
//		if (Gdx.input.isTouched()) {
//			gm.init();
//			gm.restartGame();
//		}
	}

	@Override
	public void transitionDone() {
		
	}
}
