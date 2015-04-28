package com.plombeer.games.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.plombeer.games.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 800;
		config.width = config.height * 16 / 9;
		config.title = "Дурак";
		config.resizable = true;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
