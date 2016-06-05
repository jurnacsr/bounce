package com.bounce;

import com.badlogic.gdx.Gdx;

public class C {

	// size of the playing surface
	public static int SX 								= Gdx.graphics.getWidth();
	public static int SY 								= Gdx.graphics.getHeight();
	
	// font Y offset
	public static final int FONT_Y_OFFSET 				= 120;
	
	// time it takes for the ball to fade in 
	public static final float BALL_FADE_IN_TIME 		= 1f;
	
	// image scale
	public static final int BALL_SIZE_SCALE 			= 5;
	public static final float ARROW_SIZE_SCALE			= 2f;
	
	// time it takes for the game over screen to fade in
	public static final float GAME_OVER_FADE_IN 		= 1.0f;
	
	// speed at which the NOT TOUCHING banner flies in
	public static final float GAME_NO_TOUCH_FLYIN_SPEED	= 500;
	
	// base ball sizes
	public static final float BASE_BALL_SIZE_SCALE		= 0.1f;
	public static final float BASE_BALL_SPEED_SCALE		= 0.12f;
	public static final float BASE_BALL_SIZE 			= SX * BASE_BALL_SIZE_SCALE;
	public static final float BASE_BALL_SPEED			= (float) (Math.sqrt(Math.pow(SX, 2) + Math.pow(SY, 2)) * BASE_BALL_SPEED_SCALE );
	
	// frenzy vars
	public static final float FRENZY_THETA_ROTATION 	= 8.0f;
	public static final float PULSE_FRENZY_SIZE_SCALE 	= 0.2f;
	
	public static boolean FRENZY_READY					= true;
	
	/**
	 * We use Logarithmic scaling for both of these functions so the scaling increases 
	 * quickly, but tapers as we advance.  
	 */
	
	// speed calculation from number of triggers
	public static float SPEED_FROM_TRIGGERS(float t) {
		return (float) (C.BASE_BALL_SPEED + (C.BASE_BALL_SPEED * C.TRIGGER_SCALE(t)));
	}
	
	// size calculation from number of triggers
	public static float SIZE_FROM_TRIGGERS(float t) {
		return (float) (C.BASE_BALL_SIZE + (C.BASE_BALL_SIZE * C.TRIGGER_SCALE(t)));
	}
	
	// get the plain scaling for this number of triggers
	public static float TRIGGER_SCALE(float t) {
		if (t == 0) {
			return 0;
		}
		return (float) (Math.log(t) / 8);
		
	}
	
}
