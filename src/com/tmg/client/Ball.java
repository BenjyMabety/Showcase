package com.tmg.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

	boolean live = false;
	private int rightStep = 0;
	private int topStep = 0;
	private int mass = 3;
	private boolean suspended = true;
	private boolean falling = true;

	Image ball = new Image(resources.ball());

	public Ball() {
		initWidget(uiBinder.createAndBindUi(this));
		ball.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				// Window.alert("clicked");

			}
		});

	}

	/**
	 * @return
	 */
	public Image getBall() {
		return ball;
	}

	public Image setLife() {
		// TODO Auto-generated method stub
		live = true;
		return ball;
	}

	public Image getLife() {
		// TODO Auto-generated method stub
		live = false;
		return ball;
	}

	public boolean isLive() {
		return live;
	}

	public int getRightStep() {
		return rightStep;
	}

	public void setRightStep(int rightStep) {
		this.rightStep = rightStep;
	}

	public int getTopStep() {
		return topStep;
	}

	public void setTopStep(int topStep) {
		this.topStep = topStep;
	}

	public int getMass() {
		return mass;
	}

	public void setMass(int mass) {
		this.mass = mass;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}
}
