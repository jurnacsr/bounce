package com.bounce.game.screen;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bounce.C;
import com.bounce.game.GameManager;

public class StartGameScreen extends Screen {
	
	BitmapFont font;
	
	String message 					= "Welcome to Bounce!";
	
	String phoneMessage 			= "Keep your finger on the screen to move.";
	String nonPhoneMessage 			= "Use the mouse to move around and dodge.";
	
	String watchOutMessage			= "Watch out for the balls.";
	
	String phoneStartMessage 		= "Hold for {TIME} more {UNIT} to start";
	String nonPhoneStartMessage 	= "Click to start";
	
	List<String> messages 	= new ArrayList<String>();
	GlyphLayout gl 			= new GlyphLayout();
	DecimalFormat formatter = new DecimalFormat("0.0");
	
	float	holdTime 		= 1f;
	float 	holdInc 		= 0f;
	float 	holdSeconds		= holdTime;
	String	unit			= "";

	Texture bg;
	
	public StartGameScreen(GameManager gm) {
		super(gm);

		bg = new Texture("bg.png");
		
		font = new BitmapFont(Gdx.files.internal("font/gameFont.fnt"), 
							  Gdx.files.internal("font/gameFont.png"),
							  false);
		font.setColor(Color.BLACK);

		messages.add(message);
		messages.add(watchOutMessage);
		if (Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS) {
			messages.add(phoneMessage);
			messages.add(phoneStartMessage);
		} else {
			messages.add(nonPhoneMessage);
			messages.add(nonPhoneStartMessage);
		}
		
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

		holdSeconds = holdTime - holdInc;
		if (Gdx.input.isTouched()) {
			
			holdInc += Gdx.graphics.getDeltaTime();
			
			if (holdInc >= holdTime) {
				gm.startGame();				
			}
		} else {
			holdInc = 0;
		}
		
		unit = holdSeconds == 1.0f ? "second" : "seconds";
	}

	@Override
	public void transitionDone() {
		// TODO Auto-generated method stub
		
	}
}
