package com.barrypress.dd.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.barrypress.dd.core.DD;

public class DDDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = 1440;
        config.height = 900;
		new LwjglApplication(new DD(), config);
	}
}
