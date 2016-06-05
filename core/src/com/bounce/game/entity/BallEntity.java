package com.bounce.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bounce.C;
import com.bounce.game.GameManager;
import com.bounce.game.frenzy.Frenzy;

public class BallEntity extends Entity {
	
	float speed;
	
	Vector2 direction;
	Vector2 velocity;
	Vector2 position;
	Vector2 movement;
	Vector2 center;
	Color	c = new Color(1, 1, 1, 1);
	float dist;
	
	float r;
	float rr;
	
	Vector2 al;
	float ah, aw;
	boolean arrowFlipX, arrowFlipY;
	boolean drawArrow;
	float theta;
	
	float deltaTime;
	private GameManager gm;
	
	float createTimeInc;
	
	float alpha;
	
	Frenzy frenzy = null;
	// score per second per ball
	float score = 0.25f;
	
	enum state {
		inactive, active, spawning
	};
	state st;

	Texture arrow;

	public BallEntity(String imagePath, float x, float y, float w, float h, float speed, GameManager gm) {
		init(imagePath, x, y, w, h, speed, gm);
	}

	public BallEntity(String imagePath, int triggers, GameManager gm) {
		
		// random ball placement
		float lx = gm.getDims().x * 0.1f;
		float hx = gm.getDims().x * 0.9f;
		float ly = gm.getDims().y * 0.1f;
		float hy = gm.getDims().y * 0.9f;
		
		float x = MathUtils.random(lx, hx);
		float y = MathUtils.random(ly, hy);
		float s = C.SIZE_FROM_TRIGGERS(triggers);
		float v = C.SPEED_FROM_TRIGGERS(triggers);
		
		init(imagePath, x, y, s, s, v, gm);
	}
	
	private void init(String imagePath, float x, float y, float w, float h, float speed, GameManager gm) {
		this.gm = gm;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.speed = speed;
		this.img = new Texture(imagePath);
		this.arrow = new Texture("arrow.png");

		c = gm.randomColor();
		
		//direction = new Vector2(xdir, ydir);
		//direction = new Vector2(xd, yd);
		direction = new Vector2();
		newDirection();
		
		velocity = new Vector2();
		movement = new Vector2();
		position = new Vector2(x, y);
		center = new Vector2(position.x + (w / 2), position.y + (h / 2));
		r = (float) (Math.random() * 10);
		
		r = (float) (Math.random() * 5) + 3;
		rr = newRotation();
		
		st = state.spawning;
		
		ah = arrow.getHeight() * C.ARROW_SIZE_SCALE;
		aw = arrow.getWidth() * C.ARROW_SIZE_SCALE;
		
		theta = direction.angle() - 90;
		
		al = new Vector2();
		al.x = (MathUtils.sinDeg(-theta) * w) + (position.x + w/2 - aw/2);
		al.y = (MathUtils.cosDeg(-theta) * h) + (position.y + h/2 - ah/2);
	}

	@Override
	public void update() {
		
		if (st == state.spawning) {
			
			createTimeInc += Gdx.graphics.getDeltaTime();
			
			alpha = createTimeInc / C.BALL_FADE_IN_TIME;
			drawArrow = true;
			
			if ( createTimeInc >= C.BALL_FADE_IN_TIME) {
				st = state.active;
				drawArrow = false;
				createTimeInc = 0f;
			}
			
		} else {
			
			alpha = 1;
			
			checkEdges();
			
			deltaTime = Gdx.graphics.getDeltaTime();
			
			velocity.set(direction).scl(speed);
			movement.set(velocity).scl(deltaTime);
			position.add(movement);
			center.x = position.x + w/2;
			center.y = position.y + h/2;
			
			dist = center.dst(gm.getMouse());
			
			if (st == state.active && dist <= (w * 0.33f)) {
				gm.tooClose();
			}
			
			if (frenzy != null) {
				frenzy.update();
			}
			
			r += rr;
		}
		
	}

	@Override
	public void render(SpriteBatch b) {
		
		Color oc = b.getColor();
		b.setColor(new Color(c.r, c.g, c.b, alpha));
		b.draw(img, position.x, position.y, w / 2, h / 2, w, h, 1, 1, r, 0, 0, img.getWidth(), img.getHeight(), false, false);	
		
		if (drawArrow) {
			b.draw(arrow, al.x, al.y, aw/2, ah/2, aw, ah, 0.25f, 0.25f, theta, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);	
		}
		b.setColor(oc);
		
		if (frenzy != null) {
			frenzy.render(b);
		}
	}
	
	private void checkEdges() {
		Vector2 tempPos = position;
		tempPos.add(movement);
		
		if (tempPos.x < 0) {
			position.x = 0;
			//direction.x = direction.x < 0 ? direction.x + 1 : direction.x - 1;
			direction.x *= -1;
			rr = newRotation();
		}
		if (tempPos.y < 0) {
			position.y = 0;
			//direction.y = direction.y < 0 ? direction.y + 1 : direction.y - 1;
			direction.y *= -1;
			rr = newRotation();
		}
		
		if (tempPos.x + w > gm.getDims().x) {
			position.x = gm.getDims().x - w;
			//direction.x = direction.x < 0 ? direction.x + 1 : direction.x - 1;
			direction.x *= -1;
			rr = newRotation();
		}
		if (tempPos.y + h > gm.getDims().y) {
			position.y = gm.getDims().y - h;
			//direction.y = direction.y < 0 ? direction.y + 1 : direction.y - 1;
			direction.y *= -1;	
			rr = newRotation();		
		}
	}
	
	public float getScore() {
		if (frenzy == null && (st == state.spawning || st == state.inactive)) return 0.0f;
		float s = score * Gdx.graphics.getDeltaTime();
		if (frenzy != null) s = s + (s * frenzy.getScoreMulti());
		return s;
	}
	
	public float newRotation() {
		return (float) (3 - (Math.random() * 6)); 
	}

	public void removeFrenzy() {
		this.frenzy = null;
		C.FRENZY_READY = true;
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float s) {
		this.speed = s;
	}

	public void giveFrenzy(Frenzy frenzy) {
		this.frenzy = frenzy;
	}
	
	public boolean hasFrenzy() {
		return this.frenzy != null;
	}

	public float getTheta() {
		return theta;
	}

	public void setTheta(float theta) {
		this.theta = theta;
	}

	public void shouldDrawArrow(boolean b) {
		this.drawArrow = b;		
	}

	public void newDirection() {
		/*float xd = MathUtils.random(-1f, 1f);
		float yd = MathUtils.random(-1f, 1f);*/
		
		float nt = MathUtils.random(-180f, 180f);
		
		direction.x = MathUtils.sin(nt);
		direction.y = MathUtils.cos(nt);
	}
	
	public void makeSpawning() {
		this.alpha = 0f;
		theta = direction.angle() - 90;
		al.x = (MathUtils.sinDeg(-theta) * w) + (position.x + w/2 - aw/2);
		al.y = (MathUtils.cosDeg(-theta) * h) + (position.y + h/2 - ah/2);
		this.st = state.spawning;
	}

}
