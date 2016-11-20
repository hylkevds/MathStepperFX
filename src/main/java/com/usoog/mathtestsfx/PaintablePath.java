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

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineJoin;

/**
 *
 * @author Hylke van der Schaaf
 */
public class PaintablePath implements Paintable {

	private PaintingPanel parent;
	private final int lengthLimit = 100;
	private final List<Path> paths = new ArrayList<>();

	private Path path;

	private int currentLength = 0;

	private Bounds bbox = new BoundingBox(0, 0, 0, 0);

	private boolean staticChanged;
	private Circle c;
	private double strokeWidth = 1;

	public PaintablePath() {
		path = stylePath(new Path());
		path.getElements().add(new MoveTo(0, 0));
	}

	public void clear() {
		parent.getChildren().clear();
		paths.clear();
		path = stylePath(new Path());
		path.getElements().add(new MoveTo(0, 0));
		parent.getChildren().add(path);
		bbox = new BoundingBox(0, 0, 0, 0);
	}

	private Path stylePath(Path p) {
		p.setStroke(Color.WHITE);
		p.setStrokeWidth(strokeWidth);
		p.setStrokeLineJoin(StrokeLineJoin.BEVEL);
		return p;
	}

	public void setStrokeWidth(double strokeWidth) {
		this.strokeWidth = strokeWidth;
		path.setStrokeWidth(strokeWidth);
		for (Path path : paths) {
			path.setStrokeWidth(strokeWidth);
		}
	}

	public Path getShape() {
		return path;
	}

	public List<Path> getStaticShapes() {
		return paths;
	}

	public void moveTo(final double x, final double y) {
		final Path localPath = path;
		localPath.getElements().add(new MoveTo(x, y));
		c.setCenterX(x);
		c.setCenterY(y);
	}

	public void lineTo(double x, double y) {
		final double xNew = x * 10;
		final double yNew = y * 10;
		LineTo lineTo = new LineTo(xNew, yNew);
		path.getElements().add(lineTo);
		currentLength++;
		if (currentLength >= lengthLimit) {
			currentLength = 0;
			paths.add(path);
			path = stylePath(new Path());
			final Path localPath = path;
			localPath.getElements().add(new MoveTo(xNew, yNew));
			parent.getChildren().add(localPath);
		}

		updateBoundingBox(xNew, yNew);
		c.setCenterX(xNew);
		c.setCenterY(yNew);
	}

	private void updateBoundingBox(double x, double y) {
		if (bbox == null) {
			bbox = path.getBoundsInLocal();
			parent.displayBounds(bbox);
		} else {
			if (bbox.contains(x, y)) {
				return;
			}
			double minX = bbox.getMinX();
			double maxX = bbox.getMaxX();
			double minY = bbox.getMinY();
			double maxY = bbox.getMaxY();
			if (x > maxX) {
				maxX = x * 1.2;
			}
			if (x < minX) {
				minX = x * 1.2;
			}
			if (y > maxY) {
				maxY = y * 1.2;
			}
			if (y < minY) {
				minY = y * 1.2;
			}
			double height = maxY - minY;
			double width = maxX - minX;
			bbox = new BoundingBox(minX, minY, width, height);
			parent.displayBounds(bbox);
		}
	}

	@Override
	public void setParent(PaintingPanel parent) {
		this.parent = parent;

		c = new Circle(10, 10, 2);
		c.setFill(Color.BLUE);
		parent.getChildren().add(c);

		parent.getChildren().add(path);
	}

	@Override
	public double getWidth() {
		return bbox.getWidth();
	}

	@Override
	public double getHeight() {
		return bbox.getHeight();
	}

	@Override
	public double getMinX() {
		return bbox.getMinX();
	}

	@Override
	public double getMaxX() {
		return bbox.getMaxX();
	}

	@Override
	public double getMinY() {
		return bbox.getMinY();
	}

	@Override
	public double getMaxY() {
		return bbox.getMaxY();
	}

	public boolean isStaticChanged() {
		return staticChanged;
	}

}
