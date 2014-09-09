package com.springone2gx.sdc.demo.test.sdc;

import java.util.Random;

public enum Sensor {

	TRACTOR_BEAM_12, SUPERLASER, WARP_DRIVE, FLUX_CAPACITOR, TARDIS, TRICORDER;

	public static final Random RNG = new Random();

	public static Sensor randomSensor() {
		Sensor[] values = values();
		return values[Math.abs(RNG.nextInt()) % values.length];
	}
}
