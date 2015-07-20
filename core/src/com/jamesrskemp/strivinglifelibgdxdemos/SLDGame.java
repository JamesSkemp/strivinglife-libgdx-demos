package com.jamesrskemp.strivinglifelibgdxdemos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jamesrskemp.strivinglifelibgdxdemos.screens.TappingDemoScreen;

public class SLDGame extends Game {
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new TappingDemoScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}
}
