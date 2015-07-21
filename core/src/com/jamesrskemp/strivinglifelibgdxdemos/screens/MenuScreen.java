package com.jamesrskemp.strivinglifelibgdxdemos.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jamesrskemp.strivinglifelibgdxdemos.SLDGame;

/**
 * Created by James on 7/21/2015.
 */
public class MenuScreen implements Screen {
	private final static String TAG = MenuScreen.class.getName();

	final SLDGame game;

	Stage stage;

	public MenuScreen(final SLDGame game) {
		this.game = game;

		stage = new Stage(new FitViewport(800, 480), game.batch);
		//stage.setDebugAll(true);

		Table table = new Table();
		table.pad(1.0f);
		table.setFillParent(true);
		table.setWidth(stage.getWidth());
		table.setHeight(stage.getHeight());

		TextButton.TextButtonStyle textButtonStyle;
		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = new BitmapFont();

		TextButton tappingDemoButton = new TextButton("Tapping Demo", textButtonStyle);
		//tappingDemoButton.setPosition(0, 0);
		tappingDemoButton.addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				game.setScreen(new TappingDemoScreen(game));
			}
		});

		table.add(tappingDemoButton)
				//.expandX()
				.expandY()
				.top().center();

		stage.addActor(table);

		// Handle inputs.
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		// Add any game-related actions you want to handle before they control the camera.
		inputMultiplexer.addProcessor(stage);

		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
