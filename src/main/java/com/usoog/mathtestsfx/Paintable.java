/*
 * Copyright (C) 2010-2015  Jimmy Axenhus
 * Copyright (C) 2010-2015  Hylke van der Schaaf
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.usoog.mathtestsfx;

/**
 *
 * @author Hylke van der Schaaf
 */
public interface Paintable {

	public void setParent(PaintingPanel parent);

	public double getWidth();

	public double getHeight();

	public double getMinX();

	public double getMaxX();

	public double getMinY();

	public double getMaxY();

}
