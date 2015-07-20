package com.jamesrskemp.strivinglifelibgdxdemos.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Disposable;

/**
 * Box object that can be acted upon. For our purposes it can be tapped on.
 */
public class Box extends Actor implements Disposable {
	private final static String TAG = Box.class.getName();

	/**
	 * Total number of times the box has been tapped.
	 */
	public int totalTaps;

	/**
	 * Image to be used when rendering this object.
	 */
	Texture img;

	public Box() {
		// Initialize the object.
		this.totalTaps = 0;

		// For now just use the sample graphic.
		img = new Texture("badlogic.jpg");

		setBounds(getX(), getY(), img.getWidth(), img.getHeight());
		setTouchable(Touchable.enabled);

		// We'll add a listener for when the actor has been tapped.
		addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				// Increase by one and ignore the count.
				totalTaps++;
				// TODO Do something with this so the user knows it has been acted upon.
				Gdx.app.log(TAG, "tap on actor " + event.getTarget() + ": <" + x + "," + y + "> " + count + " by " + button);
				Gdx.app.log(TAG, "Total taps on actor: " + totalTaps);
				super.tap(event, x, y, count, button);
			}
		});
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(img, getX(), getY());
	}

	@Override
	public void dispose() {
		img.dispose();
	}
}
