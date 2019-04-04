package com.gosun.xone.core.utils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class XoneSessionListener implements HttpSessionListener {

	private static int onlines = 0;
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		increment(1);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		increment(-1);
	}

	public synchronized void increment(int step) {
		setOnlines(getOnlines() + step);
	}

	public static int getOnlines() {
		return onlines;
	}

	public static void setOnlines(int onlines) {
		XoneSessionListener.onlines = onlines;
	}
}
