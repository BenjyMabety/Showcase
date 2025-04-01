/**
 * 
 */
package com.tmg.shared;

/**
 * Physics engine
 */
public class Physics {

	private int force = 0;
	private int gravity = 10;
	private int acceleration = 15;
	private double drag = 0.2;
	private int resistance = 0;
	private boolean friction = true;

	public Physics() {
	}

	/**
	 * @param mass
	 * @param isFalling
	 * @param bounce
	 * @return
	 */
	public int getForce(int mass, boolean isFalling, boolean bounce) {
		if (isFalling && !bounce) {
			force = mass * gravity;
			return force;
		} else if (isFalling && bounce) {
			resistance = (int) (Integer.valueOf(mass * gravity) * drag);
			force = (mass * gravity) - resistance;
			return -force;
		}
		force = mass * acceleration;
		return force;
	}

	/**
	 * @return
	 */
	public int getGravity() {
		return gravity;
	}

	/**
	 * @param gravity
	 */
	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	/**
	 * @return
	 */
	public boolean isFriction() {
		return friction;
	}

	/**
	 * @param friction
	 */
	public void setFriction(boolean friction) {
		this.friction = friction;
	}

}
