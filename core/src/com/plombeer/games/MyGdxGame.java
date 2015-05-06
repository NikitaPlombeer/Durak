package com.plombeer.games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.plombeer.games.menu.MenuScreen;

public class MyGdxGame extends Game {

	public enum Typ{
		menu, game
	}
	public GameScreen gameScreen;
	MenuScreen menuScreen;
	@Override
	public void create() {
		gameScreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}
}
