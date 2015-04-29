package com.plombeer.games;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by hh on 28.04.2015.
 */
public class Player {

    ArrayList<Card> cards;

    Player(){
        cards = new ArrayList<Card>();
    }

    public void drawCards(SpriteBatch batch, Sprite sprites[][], Sprite shirtSprite){
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).draw(batch, sprites, shirtSprite);
        }
    }

    public void setCardCoordinate(boolean up){
        for (int i = 0; i < cards.size(); i++) {
            float x = GamePole.SCREEN_WIDTH / 4f +i / (float)cards.size() * GamePole.SCREEN_WIDTH / 2 + cards.get(cards.size() - 1).width / 2f;
            int y;
            if(up) y = GamePole.SCREEN_HEIGHT - cards.get(0).height / 2; else
            y = cards.get(0).height / 2;
            cards.get(i).angle = 0;
            cards.get(i).setPosition((int)x, y);
        }
    }


}
