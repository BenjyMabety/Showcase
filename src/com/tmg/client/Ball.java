package com.tmg.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.tmg.client.Resources.Resources;

public class Ball extends Composite {

	private static BallUiBinder uiBinder = GWT.create(BallUiBinder.class);
	Resources resources = GWT.create(Resources.class);

	interface BallUiBinder extends UiBinder<Widget, Ball> {
	}

	private int rightStep = 0;
	private int topStep = 0;
	// arbitrary mass of a ball. Changing this value effects the physics of the
	// object
	private int mass = 3;
	// Suspended in air and subject to gravity
	private boolean suspended = true;
	private boolean moving = true;
	private int distance = 0;
	private boolean stationary = false;

	Image ball = new Image(resources.ball());

	public Ball() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * @return
	 */
	public Image getBall() {
		return ball;
	}

	/**
	 * @return
	 */
	public int getRightStep() {
		return rightStep;
	}

	public void setRightStep(int rightStep) {
		this.rightStep = rightStep;
	}

	public int getTopStep() {
		return topStep;
	}

	/**
	 * @param topStep
	 */
	public void setTopStep(int topStep) {
		this.topStep = topStep;
	}

	/**
	 * @return
	 */
	public int getMass() {
		return mass;
	}

	/**
	 * @param mass
	 */
	public void setMass(int mass) {
		this.mass = mass;
	}

	public boolean isSuspended() {
		return suspended;
	}

	/**
	 * @param suspended
	 */
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	/**
	 * @return
	 */
	public boolean isMoving() {
		return moving;
	}

	/**
	 * @param moving
	 */
	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	/**
	 * @return
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * Used to switch toggle on or off depending on friction setting
	 * 
	 * @param distance
	 */
	public void setRunningDistance(int distance) {
		this.distance += distance;
	}

	/**
	 * @param distance
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	/**
	 * @return
	 */
	public boolean isStationary() {
		return stationary;
	}

	/**
	 * @param stationary
	 */
	public void setStationary(boolean stationary) {
		this.stationary = stationary;
	}

}
