package com.tmg.client.SpaceForce;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import com.tmg.client.Resources.Resources;
import com.tmg.shared.Movable;
import com.tmg.shared.Physics;

public class Bullet extends Movable {

	Resources resources = GWT.create(Resources.class);
	Physics physics = new Physics();

	public Bullet() {
		image = new Image(resources.bullet());
		mass = 1;
		rightStep = 0;
		topStep = 0;
		image.setVisible(false);
	}

	@Override
	public void moveX(int value) {
		image.getElement().setAttribute("style",
				"position:absolute;left:" + (getRightStep() + value) + "px;top:" + getTopStep() + "px;");
		image.setVisible(true);
		setRightStep(getRightStep() + value);

	}

	@Override
	public void moveY(int value) {

	}

	@Override
	public Image getImage() {
		return image;
	}

}
