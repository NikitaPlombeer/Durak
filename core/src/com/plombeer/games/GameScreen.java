package com.plombeer.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by hh on 28.04.2015.
 */
public class GameScreen implements Screen{
    private Sprite background;
    private SpriteBatch batch;
    private Sprite deckSprites[][];
    private Sprite shirtSprite;

    Card card;
    Table table;
    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Sprite(new Texture("textures/background.png"));
        background.setSize(GamePole.SCREEN_WIDTH, GamePole.SCREEN_HEIGHT);
        background.setPosition(0, 0);

        Texture deckTexture = new Texture("textures/deck.png");

        deckSprites = new Sprite[9][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                deckSprites[j][i] = new Sprite(new TextureRegion(deckTexture, j * 73, i * 98, 73, 98));
            }
        }

        shirtSprite = new Sprite(new Texture("textures/shirt.png"));

        card = new Card(Card.Suit.diamonds, 13);
        card.setPosition(GamePole.SCREEN_WIDTH / 2, GamePole.SCREEN_HEIGHT / 2);
        card.angle = 30;
        table = new Table();
        Gdx.input.setInputProcessor(new InputListener(table));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        table.drawTable(batch, deckSprites, shirtSprite);
        batch.end();


        table.moveCards(delta);
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
