/**
 * 
 */
package com.tmg.shared;

/**
 * 
 */
public class Physics {

	/**
	 * 
	 */
	int velocity = 0;
	int g = 10;
	int a = 15;
	boolean suspended = true;

	public Physics() {
		// TODO Auto-generated constructor stub
	}

	public int getForce(int mass, boolean isFalling) {
		if (isFalling) {
			return mass * g;
		}
		return mass * a;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

}
