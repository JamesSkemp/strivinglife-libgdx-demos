package com.jamesrskemp.strivinglifelibgdxdemos.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jamesrskemp.strivinglifelibgdxdemos.SLDGame;
import com.jamesrskemp.strivinglifelibgdxdemos.objects.Box;

/**
 * Created by James on 7/23/2015.
 */
public class TimerDemoScreen implements Screen {
	private final static String TAG = TimerDemoScreen.class.getName();

	final SLDGame game;

	/**
	 * Basic camera for the main 'world.'
	 */
	OrthographicCamera camera;

	/**
	 * Main game stage, housing our actionable actors.
	 */
	Stage stage;

	Label informationLabel;
	Box box1;
	Box box2;

	public TimerDemoScreen(final SLDGame game) {
		this.game = game;
		// TODO add at least one image that has an auto increment based upon a timer; actually do one that's in-game timer and one that's real time timer

		// Create a camera and for now just set it equal to the size of the display.
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Create a stage that fills the entire display.
		stage = new Stage(new FitViewport(800, 480), game.batch);
		stage.getViewport().setCamera(camera);

		informationLabel = new Label("Tap an image to begin.", new Label.LabelStyle(new BitmapFont(), Color.CYAN));
		informationLabel.setName("informationLabel");
		informationLabel.setPosition(10, 5);

		stage.addActor(informationLabel);

		camera.update();

		// Create a box to display in the left half of the screen.
		box1 = new Box();
		box1.setName("box1");
		box1.setPosition(0, Gdx.graphics.getHeight() / 2 - box1.getHeight() / 2);
		stage.addActor(box1);

		// Create another box to display in the right half of the screen.
		box2 = new Box();
		box2.setName("box2");
		box2.setPosition(Gdx.graphics.getWidth() - box2.getWidth(), Gdx.graphics.getHeight() / 2 - box2.getHeight() / 2);
		stage.addActor(box2);

		addMenuButton();

		// Create a multiplexer so we can later handle multiple processors (such as a camera or HUD).
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		// We want our boxes to respond to touch, so add the stage as an input processor.
		inputMultiplexer.addProcessor(stage);

		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	/**
	 * Adds a button to the display to go back to the main menu.
	 */
	private void addMenuButton() {
		TextButton.TextButtonStyle textButtonStyle;
		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = new BitmapFont();
		textButtonStyle.fontColor = Color.WHITE;

		TextButton exitButton = new TextButton("Back to the Menu", textButtonStyle);
		exitButton.pad(1.0f);
		exitButton.setPosition(stage.getWidth() - exitButton.getWidth(), 0);

		exitButton.addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				game.setScreen(new MenuScreen(game));
			}
		});

		stage.addActor(exitButton);
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

		if (box1.totalTaps > 0 || box2.totalTaps > 0) {
			informationLabel.setText(String.format("Box 1 has %s taps and box 2 has %s taps.", box1.totalTaps, box2.totalTaps));
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
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
		box1.dispose();
		box2.dispose();
		stage.dispose();
	}
}
