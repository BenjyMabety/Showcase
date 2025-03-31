/**
 * 
 */
package com.tmg.shared;

import com.google.gwt.user.client.Timer;

/**
 * 
 */
public class Physics {

	/**
	 * 
	 */
	private int force = 0;
	private int gravity = 10;
	int acceleration = 15;
	Timer t;
	double drag = 0.2;
	int resistance = 0;
	private boolean bounce = false;
	boolean friction = true;

	public Physics() {
		// TODO Auto-generated constructor stub
	}

	public int getForce(int mass, boolean isFalling, boolean bounce) {
		if (isFalling && !bounce) {
			force = mass * gravity;
			return force;
		} else if (isFalling && bounce) {
			resistance = (int) (Integer.valueOf(mass * gravity) * drag);
			// Window.alert("friction force:" + friction);
			force = (mass * gravity) - resistance;
			// Window.alert("minus force:" + force);
			return -force;
		}
		force = mass * acceleration;
		return force;
	}

	public Timer getT() {
		return t;
	}

	public void setT(Timer t) {
		this.t = t;
	}

	public int getGravity() {
		return gravity;
	}

	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	public boolean isFriction() {
		return friction;
	}

	public void setFriction(boolean friction) {
		this.friction = friction;
	}

}
