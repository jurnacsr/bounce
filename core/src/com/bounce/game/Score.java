package com.bounce.game;

public class Score implements Comparable<Score>{
	public float score;
	public float session;
	public Score(float score2, float session2) {
		score = score2;
		session = session2;
	}
	@Override
	public int compareTo(Score o) {
		return Float.compare(o.score, score);
	}
}
