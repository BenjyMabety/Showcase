package com.tmg.client.SpaceForce;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Image;
import com.tmg.client.Resources.Resources;
import com.tmg.shared.Movable;
import com.tmg.shared.Physics;

public class Asteroid extends Movable {

	Resources resources = GWT.create(Resources.class);
	Physics physics = new Physics();
	int hits = 0;

	public Asteroid() {
		// TODO Auto-generated constructor stub
		// image.setResource(resources.asteroid());
		image = new Image(resources.asteroid());
		mass = 5;
		rightStep = 0;
		topStep = 0;
		image.setVisible(false);
	}

	@Override
	public void moveX(int value) {

		image.getElement().getStyle().setPosition(Position.ABSOLUTE);
		image.getElement().getStyle().setTop(180, Unit.PX);
		image.getElement().getStyle().setLeft(getRightStep() + value, Unit.PX);
		image.setVisible(true);
		setRightStep(getRightStep() + value);

	}

	@Override
	public void moveY(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return image;
	}

	public int getHits() {
		// TODO Auto-generated method stub
		return hits;
	}

	public void setHits(int hits) {
		this.hits += hits;
	}

}
