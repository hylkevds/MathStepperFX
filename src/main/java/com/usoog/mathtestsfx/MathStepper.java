/*
 * Copyright (C) 2016 Hylke van der Schaaf
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.usoog.mathtestsfx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Hylke van der Schaaf
 */
public class MathStepper {

	private static final Logger LOGGER = LoggerFactory.getLogger(MathStepper.class.getName());
	private int step = 0;
	private int length = 0;
	private double x = 0;
	private double y = 0;
	private boolean moved = false;
	private double angle;
	private double deltaAngle;

	public MathStepper(double startAngle, double deltaAngle) {
		this.angle = startAngle;
		this.deltaAngle = deltaAngle;
	}

	public void reset(double startAngle, double deltaAngle) {
		this.angle = startAngle;
		this.deltaAngle = deltaAngle;
		this.x = 0;
		this.y = 0;
		this.step = 0;
		this.length = 0;
	}

	public void doStep() {
		if (evenOnes(step)) {
			moveForward();
		} else {
			increaseAngle();
		}
		step++;
	}

	private void increaseAngle() {
		angle += deltaAngle;
		if (angle >= 360) {
			angle -= 360;
		}
		moved = false;
		LOGGER.info("Step {}: Angle is now {}.", step, angle);
	}

	private void moveForward() {
		double rad = Math.toRadians(angle);
		double dx = -Math.sin(rad);
		double dy = -Math.cos(rad);
		x += dx;
		y += dy;
		length++;
		moved = true;
		LOGGER.info("Step {}: Line length {} to {}, {}.", step, length, x, y);
	}

	public boolean isMoved() {
		return moved;
	}

	private boolean evenOnes(int value) {
		int ones = 0;
		while (value != 0) {
			if (value % 2 != 0) {
				ones++;
			}
			value = value >>> 1;
		}
		return ones % 2 == 0;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAngle() {
		return angle;
	}

	public int getLength() {
		return length;
	}

}
