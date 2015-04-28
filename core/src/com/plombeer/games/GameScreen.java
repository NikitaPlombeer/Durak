package com.plombeer.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by hh on 28.04.2015.
 */
public class GameScreen implements Screen{
    private Sprite background;
    private SpriteBatch batch;
    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Sprite(new Texture("textures/background.png"));
        background.setSize(GamePole.SCREEN_WIDTH, GamePole.SCREEN_HEIGHT);
        background.setPosition(0, 0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        GamePole.SCREEN_HEIGHT = height;
        GamePole.SCREEN_WIDTH = width;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
