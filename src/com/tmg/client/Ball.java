package com.tmg.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.tmg.client.Resources.Resources;

/**
 * 
 */
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

	private PushButton upButton;
	private PushButton downButton;
	private PushButton leftButton;
	private PushButton rightButton;
	private PushButton pbBall;
	private ToggleButton tbFriction;

	/**
	 * 
	 */
	public Ball() {
		initWidget(uiBinder.createAndBindUi(this));
		Image up = new Image(resources.up());
		Image down = new Image(resources.down());
		Image left = new Image(resources.left());
		Image right = new Image(resources.right());

		upButton = new PushButton(up);
		downButton = new PushButton(down);
		leftButton = new PushButton(left);
		rightButton = new PushButton(right);

		pbBall = new PushButton("Ball (Physics)");
		tbFriction = new ToggleButton("Friction");
		tbFriction.setValue(true);

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

	/**
	 * @return
	 */
	public ToggleButton getTbFriction() {
		return tbFriction;
	}

	/**
	 * @param tbFriction
	 */
	public void setTbFriction(ToggleButton tbFriction) {
		this.tbFriction = tbFriction;
	}

	/**
	 * @return
	 */
	public PushButton getPbBall() {
		return pbBall;
	}

	/**
	 * @param pbBall
	 */
	public void setPbBall(PushButton pbBall) {
		this.pbBall = pbBall;
	}

	/**
	 * @return
	 */
	public PushButton getUpButton() {
		return upButton;
	}

	/**
	 * @param upButton
	 */
	public void setUpButton(PushButton upButton) {
		this.upButton = upButton;
	}

	/**
	 * @return
	 */
	public PushButton getDownButton() {
		return downButton;
	}

	/**
	 * @param downButton
	 */
	public void setDownButton(PushButton downButton) {
		this.downButton = downButton;
	}

	/**
	 * @return
	 */
	public PushButton getLeftButton() {
		return leftButton;
	}

	/**
	 * @param leftButton
	 */
	public void setLeftButton(PushButton leftButton) {
		this.leftButton = leftButton;
	}

	/**
	 * @return
	 */
	public PushButton getRightButton() {
		return rightButton;
	}

	/**
	 * @param rightButton
	 */
	public void setRightButton(PushButton rightButton) {
		this.rightButton = rightButton;
	}

}
