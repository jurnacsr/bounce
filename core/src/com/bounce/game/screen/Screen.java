package com.bounce.game.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bounce.game.GameManager;
import com.bounce.game.entity.Entity;

public abstract class Screen {
	
	List<Entity> entityList = new ArrayList<Entity>();
	List<Entity> removeList = new ArrayList<Entity>();
	protected GameManager gm;
	
	public Screen(GameManager gm) {
		this.gm = gm;
	}
	
	public void update() {
		for (Entity e : removeList) {
			if (entityList.contains(e)) {
				entityList.remove(e);
				removeList.remove(e);
			}
		}
		
		for (Entity e : entityList) {
			e.update();
		}
	}
	
	public void render(SpriteBatch batch) {
		for (Entity e : entityList) {
			e.render(batch);
		}
		
	}
	
	public void addEntity(Entity e) {
		entityList.add(e);
	}
	
	public void removeEntity(Entity e) {
		removeList.add(e);
	}
	
	public void removeAllEntities() {
		entityList.clear();
	}
	
	public abstract void transitionDone();
}
