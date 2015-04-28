package com.plombeer.games;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by hh on 28.04.2015.
 */
public class Table {
    Card.Suit trump;
    Deck deck;
    Player player[];

    Player defender; // Защищающийся
    Player attack; // Атакующий игрок

    Table(){
        deck = new Deck();
        player = new Player[2];
        player[0] = new Player();
        player[1] = new Player();

        for (int i = 0; i < 10; i++) {
            player[0].cards.add(deck.getLastCard());
            player[1].cards.add(deck.getLastCard());
        }

        player[0].setCardCoordinate(true);
        player[1].setCardCoordinate(false);

        trump = deck.cards.get(0).suit;

    }

    public void drawTable(SpriteBatch batch, Sprite sprites[][], Sprite shirtSprite){
        for (int i = 0; i < player.length; i++) {
            player[i].drawCards(batch, sprites, shirtSprite);
        }

        for (int i = 0; i < deck.cards.size(); i++) {
            deck.cards.get(i).draw(batch, sprites, shirtSprite);
        }
    }
}
