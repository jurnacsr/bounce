package com.bounce.tracking;

import java.util.ArrayList;
import java.util.List;

public class SingleGameTrack{
	public float 	gameTime;
	public float 	finalScore;
	public float 	numBalls;
	public float 	numFrenzies;
	public float	activeTime;
	
	public List<BallData> 	balls = new ArrayList<>();
	public List<FrenzyData> frenzies = new ArrayList<>();
	
	
}
