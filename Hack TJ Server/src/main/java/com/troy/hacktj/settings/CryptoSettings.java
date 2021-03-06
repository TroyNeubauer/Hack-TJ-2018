package com.troy.hacktj.settings;

import java.nio.charset.Charset;
import java.text.*;

public class CryptoSettings {
	private int hashBytes;
	private int iterations;
	private int saltBytes;
	private byte[] pepper;
	private double iterationsAddedPerDay;
	private long millisIterationsStartTime;

	public static CryptoSettings defaults() {
		CryptoSettings result = new CryptoSettings();
		try {
			result.hashBytes = 64;
			result.iterations = 10000;// 25K
			result.saltBytes = 32;
			result.pepper = "Suck my ass you hacker skum".getBytes(Charset.forName("UTF-8"));
			result.iterationsAddedPerDay = 4.0;
			result.millisIterationsStartTime = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSSS zzz").parse("Jan 1 2018 00:00:00.0000 EST").getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int getHashBytes() {
		return hashBytes;
	}

	public int getRawIterations() {
		return iterations;
	}

	public int getNowIterations() {
		return (int) (iterations + iterationsAddedPerDay * (((double) System.currentTimeMillis() - millisIterationsStartTime) / 1000.0 / 60.0 / 60.0 / 24.0));
	}

	public int getSaltBytes() {
		return saltBytes;
	}

	public byte[] getPepper() {
		return pepper;
	}

	public double getIterationsAddedPerDay() {
		return iterationsAddedPerDay;
	}

	public long getMillisIterationsStartTime() {
		return millisIterationsStartTime;
	}

}
