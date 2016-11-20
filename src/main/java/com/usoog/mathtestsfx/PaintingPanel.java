/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usoog.mathtestsfx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Hylke van der Schaaf
 */
public class PaintingPanel extends Region {

	private Bounds lastBounds;
	private Rectangle r = new Rectangle();

	public void init() {
		r.setStroke(Color.RED);
		//getChildren().add(r);
		widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				displayBounds(lastBounds);
			}
		});
	}

	public void displayBounds(Bounds bounds) {
		if (bounds == null) {
			return;
		}
		lastBounds = bounds;
		r.setX(bounds.getMinX());
		r.setY(bounds.getMinY());
		r.setWidth(bounds.getWidth());
		r.setHeight(bounds.getHeight());
		double paneWidth = getWidth();
		double paneHeight = getHeight();

		double bWidth = bounds.getWidth();
		double bHeight = bounds.getHeight();

		double wScale = paneWidth / bWidth;
		double hScale = paneHeight / bHeight;
		double scale = Math.min(wScale, hScale);
		setTranslateX(scale * (-bounds.getMinX() + 0.5 * (paneWidth - bWidth)));
		setTranslateY(scale * (-bounds.getMinY() + 0.5 * (paneHeight - bHeight)));
		setScaleX(scale);
		setScaleY(scale);
	}

	/**
	 * Gets the list of children of this {@code Group}.
	 *
	 * @return the list of children of this {@code Group}.
	 */
	@Override
	public ObservableList<Node> getChildren() {
		return super.getChildren();
	}

}
