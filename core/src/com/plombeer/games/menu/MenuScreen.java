package com.plombeer.games.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.plombeer.games.*;

/**
 * Created by hh on 30.04.2015.
 */
public class MenuScreen implements Screen{
    MyGdxGame game;
    Button newGameButton;
    SpriteBatch batch;
    private Sprite background;

    @Override
    public void show() {
        batch = new SpriteBatch();
        newGameButton = new Button(GamePole.SCREEN_WIDTH / 2 - 100, GamePole.SCREEN_HEIGHT / 2, 200, 50, "textures/menu_button.png", "New Game", MyGdxGame.Typ.menu);
        newGameButton.setOnClickListener(new ButtonInterface() {
            @Override
            public void onClick(Button button) {
                game.setScreen(game.gameScreen);
            }
        });
        background = new Sprite(new Texture("textures/background.png"));
        background.setSize(GamePole.SCREEN_WIDTH, GamePole.SCREEN_HEIGHT);
        background.setPosition(0, 0);

        Gdx.input.setInputProcessor(new InputListener(this));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        newGameButton.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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

    public MenuScreen(MyGdxGame game) {
        this.game = game;
    }
}
