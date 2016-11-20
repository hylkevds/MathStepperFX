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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FXMLController implements Initializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(FXMLController.class.getName());
	@FXML
	private Spinner<Double> spinnerStartAngle;

	@FXML
	private Spinner<Double> spinnerDeltaAngle;

	@FXML
	private Spinner<Integer> spinnerDelay;

	@FXML
	private Spinner<Double> spinnerStrokeWidth;

	@FXML
	private PaintingPanel painter;

	private Thread runThread;
	private boolean running;
	private int frameTime = 10;
	private double strokeWith = 1;
	private double startAngle = 120;
	private double deltaAngle = 120;
	private final PaintablePath path = new PaintablePath();
	private final MathStepper stepper = new MathStepper(startAngle, deltaAngle);

	@FXML
	private void reset() {
		stopRunning();
		path.clear();
		stepper.reset(startAngle, deltaAngle);
	}

	@FXML
	private void step1() {
		doStep();
		doDraw();
	}

	@FXML
	private void step10() {
		for (int i = 0; i < 10; i++) {
			doStep();
			doDraw();
		}
	}

	@FXML
	private synchronized void startRunning() {
		running = true;
		if (runThread == null) {
			LOGGER.info("Starting paint thread.");
			runThread = new Thread(new Runnable() {
				@Override
				public void run() {
					paintLoop();
					runThread = null;
					LOGGER.info("Paint thread ended.");
				}
			}, "Runner");
			runThread.setDaemon(true);
			runThread.start();
		}
	}

	@FXML
	private synchronized void stopRunning() {
		if (runThread != null) {
			running = false;
			runThread.interrupt();
		}
	}

	/**
	 * The paintLoop listening to the gameLoop.
	 */
	private void paintLoop() {
		while (running) {
			doStep();
			doDraw();
			try {
				Thread.sleep(frameTime);
			} catch (InterruptedException ex) {
			}
		}
	}

	private void doDraw() {
		if (stepper.isMoved()) {
			final double x = stepper.getX();
			final double y = stepper.getY();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					path.lineTo(x, y);
				}
			});
		}
	}

	private void doStep() {
		stepper.doStep();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		painter.init();
		path.setParent(painter);
		spinnerDelay.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				frameTime = newValue;
			}
		});
		spinnerStrokeWidth.valueProperty().addListener(new ChangeListener<Double>() {
			@Override
			public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
				strokeWith = newValue;
				path.setStrokeWidth(strokeWith);
			}
		});
		spinnerStartAngle.valueProperty().addListener(new ChangeListener<Double>() {
			@Override
			public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
				startAngle = newValue;
			}
		});
		spinnerDeltaAngle.valueProperty().addListener(new ChangeListener<Double>() {
			@Override
			public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
				deltaAngle = newValue;
			}
		});
	}
}
