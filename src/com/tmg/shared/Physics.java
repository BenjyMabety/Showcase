/**
 * 
 */
package com.tmg.shared;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

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
	double friction = -0.2;
	private boolean bounce = false;

	public Physics() {
		// TODO Auto-generated constructor stub
	}

	public int getForce(int mass, boolean isFalling, boolean bounce) {
		if (isFalling) {
			force = mass * gravity;
			return force;
		} else if (isFalling && bounce) {
			force = (int) (Integer.valueOf(mass * gravity) * friction);
			Window.alert("force is: " + force);

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

}
