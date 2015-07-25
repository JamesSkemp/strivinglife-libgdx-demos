package com.jamesrskemp.strivinglifelibgdxdemos.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;

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
	 * If true, this will automatically generate taps per second.
	 */
	private boolean autoTap;
	/**
	 * If true the automatic tapping has been paused.
	 */
	private boolean autoTapPaused;
	/**
	 * Number of automatic taps per second.
	 */
	private float autoTapsPerSecond;

	/**
	 * Get whether automatic tapping has been enabled, but paused.
	 * @return True if automatic tapping has been enabled, but is paused.
	 */
	public boolean isAutoTapPaused()
	{
		return autoTap && autoTapPaused;
	}

	/**
	 * Image to be used when rendering this object.
	 */
	Texture img;

	Timer autoTapTimer;
	Timer.Task autoTapTimerTask;

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

	private void incrementTapCount(float taps) {
		this.totalTaps += taps;
	}

	/**
	 * Enables automatic tapping on the object.
	 * @param autoTapsPerSecond Number of taps to register per second.
	 */
	public void enableAutomaticTapping(final float autoTapsPerSecond) {
		if (!this.autoTap) {
			this.autoTap = true;
			this.autoTapPaused = false;
			this.autoTapsPerSecond = autoTapsPerSecond;

			// Setup a timer task on the object to run once every second.
			if (autoTapTimer == null || autoTapTimerTask == null) {
				autoTapTimer = new Timer();

				autoTapTimerTask = autoTapTimer.scheduleTask(new Timer.Task() {
					@Override
					public void run() {
						// Automatic tapping must be enabled, and not paused.
						if (autoTap && !autoTapPaused) {
							incrementTapCount(autoTapsPerSecond);
						}
					}
				}, 0, 1);
			}
		}
	}

	/**
	 * Temporarily pause automatic tapping.
	 */
	public void pauseAutomaticTapping() {
		this.autoTapPaused = true;
		if (autoTapTimer != null) {
			autoTapTimer.stop();
		}
	}

	/**
	 * Resume automatic tapping, if it was setup.
	 */
	public void resumeAutomaticTapping(){
		if (this.autoTap && this.autoTapPaused) {
			autoTapTimer.start();
			this.autoTapPaused = false;
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(img, getX(), getY());
	}

	@Override
	public void dispose() {
		if (autoTapTimer != null) {
			autoTapTimer.clear();
		}
		img.dispose();
	}
}
