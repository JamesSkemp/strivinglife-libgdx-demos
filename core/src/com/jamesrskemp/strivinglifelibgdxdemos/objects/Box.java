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
	private boolean autoTapPaused;
	private float tapsPerSecond;
	private float actionTime;
	private float actionPause;
	private boolean actionRepeat;

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

	public void enableAutomaticTapping(){
		enableAutomaticTapping(this.tapsPerSecond, this.actionTime, this.actionPause, this.actionRepeat);
	}

	public void enableAutomaticTapping(final float tapsPerSecond, final float actionTime, final float actionPause, boolean actionRepeat) {
		this.autoTap = true;
		this.tapsPerSecond = tapsPerSecond;
		this.actionTime = actionTime;
		this.actionPause = actionPause;
		this.actionRepeat = actionRepeat;

		if (autoTapTimer == null || autoTapTimerTask == null) {
			autoTapTimer = new Timer();

			autoTapTimerTask = autoTapTimer.scheduleTask(new Timer.Task() {
				@Override
				public void run() {
					if (autoTap && !autoTapPaused) {
						totalTaps += tapsPerSecond;
						Gdx.app.log(TAG, "Running task 1");
					}
					if (actionPause > 0) {
						Gdx.app.log(TAG, "Pause > 0");
						autoTapTimer.scheduleTask(new Timer.Task() {
							@Override
							public void run() {
								Gdx.app.log(TAG, "Running task 2");
								autoTapPaused = true;
								if (!isScheduled()) {
									Gdx.app.log(TAG, "Not scheduled");
									autoTapPaused = false;
								}
							}
						}, actionTime, actionPause, 0);
						/*autoTapTimer.scheduleTask(new Timer.Task() {

						});*/
					}
				}
			}, 0, 1);
		} else {
			if (!autoTapTimerTask.isScheduled()) {
				autoTapTimer.start();
			}
		}
	}

	public void disableAutomaticTapping(boolean disableRepeat) {
		this.autoTap = false;
		if (autoTapTimer != null) {
			autoTapTimer.stop();
		}
		if (disableRepeat) {
			this.actionRepeat = false;
		}
		if (this.actionRepeat) {
			new Timer().scheduleTask(new Timer.Task() {
				@Override
				public void run() {
					enableAutomaticTapping();
				}
			}, this.actionPause, 1);
		}
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
