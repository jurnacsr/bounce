package com.bounce.game.frenzy;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.bounce.game.entity.BallEntity;

public class FrenzyFactory {
	
	FrenzyType type;
	
	public enum FrenzyType {
		SimpleSpeed,
		DirectionChange
		//  PulseFrenzy, ReverseFrenzy, GravityFrenzy, InterruptFrenzy  TODO: NYI Frenzies
	}
	
	public FrenzyFactory() {
		List<FrenzyType> types = Arrays.asList(FrenzyType.values());
		int index = MathUtils.random(types.size() - 1);
		type = types.get(index);
		//type = FrenzyType.DirectionChange;
		//type = FrenzyType.SimpleSpeed;
		Gdx.app.log("BounceInfo", "Creating frenzy factory for type: " + type);
	}
	
	public String getFrenzyMessage() {
		switch (type) {
		case SimpleSpeed: {
			return "Gotta go fast!";
		}
		case DirectionChange: {
			return "Time to Switch it Up!";
		}
	default:
		break;
	}
	return null;
		
	}
	
	public Frenzy buildFrenzy(BallEntity parent, float duration) {
		switch (type) {
			case SimpleSpeed: {
				return new SimpleSpeedFrenzy(parent, duration);
			}
			case DirectionChange: {
				return new NewDirectionFrenzy(parent, duration);
			}
		default:
			break;
		}
		return null;
	}
}
