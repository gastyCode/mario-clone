package com.cockatielstudios;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import static com.cockatielstudios.Constants.WIDTH;
import static com.cockatielstudios.Constants.HEIGHT;

/**
 * Trieda, ktorá slúži ako spúšťač pre PC.
 *
 * Táto trieda je predvytvorená frameworkom LibGDX.
 */
// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(WIDTH, HEIGHT);
		config.setTitle("Super Mario Clone");
 		new Lwjgl3Application(new MainGame(), config);
	}
}
