package com.plombeer.games;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by hh on 28.04.2015.
 */
public class Table {
    Card.Suit trump;
    Deck deck;
    Player player[];

    ArrayList<Card> defender; // Защищающийся
    ArrayList<Card> attack; // Атакующий игрок

    Table(){
        deck = new Deck(this);
        player = new Player[2];
        player[0] = new Player();
        player[1] = new Player();

//        for (int i = 0; i < 6; i++) {
//            player[0].cards.add(deck.getLastCard());
//            player[1].cards.add(deck.getLastCard());
//        }

        player[0].setCardCoordinate(true);
        player[1].setCardCoordinate(false);
        trump = deck.cards.get(0).suit;
//        player[0].cards.add(deck.getLastCard());
//        player[0].cards.get(player[0].cards.size() - 1).throwTo(GamePole.SCREEN_WIDTH / 2, GamePole.SCREEN_HEIGHT / 2, 1, 0);
        for (int i = 0; i < 6; i++) {
            deck.giveCard(player[0], true);
            deck.giveCard(player[1], false);
        }
        defender = new ArrayList<Card>();
        attack = new ArrayList<Card>();


        //throwDefenderOnTable();
//        link(player[0], true, Type.def);
//        link(player[0], true, Type.card);

    }

    public void drawTable(SpriteBatch batch, Sprite sprites[][], Sprite shirtSprite){
        for (int i = 0; i < player.length; i++) {
            player[i].drawCards(batch, sprites, shirtSprite);
        }

        for (int i = 0; i < deck.cards.size(); i++) {
            deck.cards.get(i).draw(batch, sprites, shirtSprite);
        }

        for (int i = 0; i < defender.size(); i++) {
            defender.get(i).draw(batch, sprites, shirtSprite);
        }

        for (int i = 0; i < attack.size(); i++) {
            attack.get(i).draw(batch, sprites, shirtSprite);
        }

    }

    public void moveCards(float delta){
        for (int i = 0; i < player.length; i++) {
            for (int j = 0; j < player[i].cards.size(); j++) {
                if(player[i].cards.get(j).isFlying) {
                    player[i].cards.get(j).move(delta);
                }
            }
        }

        for (int i = 0; i < defender.size(); i++) {
            if(defender.get(i).isFlying) {
                defender.get(i).move(delta);
            }
        }
    }

    enum Type{
        def, card
    }

    public void link(Player player, boolean up, Type type){
        ArrayList<Card> cards = player.cards;
        if(type.equals(Type.def)) cards = defender;

        float x1[] = new float[cards.size()];
        float x2[] = new float[cards.size()];
        float y1[] = new float[cards.size()];
        float y2[] = new float[cards.size()];
        float angle1[] = new float[cards.size()];
        float angle2[] = new float[cards.size()];

        for (int i = 0; i < cards.size(); i++) {
            x2[i] = cards.get(i).center.x;
            y2[i] = cards.get(i).center.y;
            angle2[i] = cards.get(i).angle;
        }

        if(type.equals(Type.card))
            player.setCardCoordinate(up); else
        if(type.equals(Type.def))
            setDefenderCoordinate();
        for (int i = 0; i < cards.size(); i++) {
            x1[i] = cards.get(i).center.x;
            y1[i] = cards.get(i).center.y;
            angle1[i] = cards.get(i).angle;
        }

        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setPosition(x2[i], y2[i]);
            cards.get(i).angle = angle1[i];
            cards.get(i).throwTo(x1[i], y1[i], 1, 0);
        }
    }


    public void setDefenderCoordinate(){
        if(defender.size() == 1){
            defender.get(0).setPosition(GamePole.SCREEN_WIDTH / 2, GamePole.SCREEN_HEIGHT / 2);
            defender.get(0).angle = 15;
        } else
        if (defender.size() == 2){
            defender.get(0).setPosition(GamePole.SCREEN_WIDTH / 4, GamePole.SCREEN_HEIGHT / 2);
            defender.get(0).angle = 15;
            defender.get(1).setPosition(3*GamePole.SCREEN_WIDTH / 4, GamePole.SCREEN_HEIGHT / 2);
            defender.get(1).angle = 15;
        }
    }

    public void onTouchCard(int x, int y){

        for (int i = 0; i < player.length; i++) {
            for (int j = 0; j < player[i].cards.size(); j++) {
                if(player[i].cards.get(j).center.x - player[i].cards.get(j).width / 2 < x &&
                   player[i].cards.get(j).center.x + player[i].cards.get(j).width / 2 > x &&
                   player[i].cards.get(j).center.y - player[i].cards.get(j).height / 2 < y &&
                   player[i].cards.get(j).center.y + player[i].cards.get(j).height / 2 > y){

                    defender.add(player[i].cards.get(j));
                    player[i].cards.remove(j);
                    link(player[i], i == 0, Type.def);
                    link(player[i], i == 0, Type.card);

                    break;
                }
            }
        }
    }
}
