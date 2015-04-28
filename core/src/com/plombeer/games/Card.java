package com.plombeer.games;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by hh on 28.04.2015.
 */
public class Card {
    enum Suit{
        clubs, spades, hearts, diamonds
    }

    Suit suit; // Масть

    boolean shirtUp = true;

    int value; // Значение карты(6 - 10, 11 - Валет, 12 - Дама, 13 - Король, 14 - Туз)


    Vector2 center;
    int angle = 0, height = GamePole.SCREEN_HEIGHT / 5, width = height * 73 / 98; // Координаты центра, угол поворота, размеры карты


    public Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
        center = new Vector2();
    }

    public Card(int suit, int value) {
        switch (suit){
            case 0:
                this.suit = Suit.clubs;
                break;
            case 1:
                this.suit = Suit.spades;
                break;
            case 2:
                this.suit = Suit.hearts;
                break;
            case 3:
                this.suit = Suit.diamonds;
                break;
        }
        this.value = value;
        center = new Vector2();
    }

    public void setPosition(int x, int y){
        center.set(x, y);
    }

    public void draw(SpriteBatch batch, Sprite sprites[][], Sprite shirtSprite){

        Sprite toDraw = shirtSprite;
        int index = 0;
        if(suit.equals(Suit.spades)) index = 1; else
        if(suit.equals(Suit.hearts)) index = 2; else
        if(suit.equals(Suit.diamonds)) index = 3;
        if(!shirtUp) toDraw = sprites[value - 6][index];

        toDraw.setPosition(center.x - width / 2, center.y - height / 2);
        toDraw.setSize(width, height);
        toDraw.rotate(angle);
        toDraw.draw(batch);
        toDraw.rotate(-angle);

    }


}
