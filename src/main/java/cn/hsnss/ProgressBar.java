package cn.hsnss;

import java.text.DecimalFormat;

public class ProgressBar {

	private long min = 0;

	private long max = 100;

	private long length = 100;

	private char showChar = '#';

	private DecimalFormat formater = new DecimalFormat("#.##%");

	public ProgressBar(long min, long max, long len, char showChar) {
		this.min = min;
		this.max = max;
		this.length = len;
		this.showChar = showChar;
	}

	private void reset() {
		System.out.print('\r');
	}

	private void afterComplete() {
		System.out.print('\n');
	}

	private String format(float num) { return formater.format(num); }

	private void draw(long len, float rate) {
		System.out.print('[');
		for (int i = 0; i < len; i++)
			System.out.print(showChar);
		System.out.print("] ");
		System.out.print(format(rate));
	}

	public void show(long value) {
		if (value < min || value > max)
			return;

		reset();
		min = value;
		float rate = (float) (min * 1.0 / max);
		long len = (long) (rate * length);
		draw(len, rate);
		if (min == max)
			afterComplete();
	}
}
