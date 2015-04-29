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
    int defenderPlayerIndex, attackPlayerIndex;

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

    }

    public void drawTable(SpriteBatch batch, Sprite sprites[][], Sprite shirtSprite){
        for (int i = 0; i < player.length; i++) {
            player[i].drawCards(batch, sprites, shirtSprite);
        }

        for (int i = 0; i < deck.cards.size(); i++) {
            deck.cards.get(i).draw(batch, sprites, shirtSprite);
        }

        for (int i = 0; i < attack.size(); i++) {
            attack.get(i).draw(batch, sprites, shirtSprite);
        }

        for (int i = 0; i < defender.size(); i++) {
            defender.get(i).draw(batch, sprites, shirtSprite);
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

        for (int i = 0; i < attack.size(); i++) {
            if(attack.get(i).isFlying) {
                attack.get(i).move(delta);
            }
        }

        for (int i = 0; i < defender.size(); i++) {
            if(defender.get(i).isFlying) {
                defender.get(i).move(delta);
            }
        }


    }

    enum Type{
        def, card, attack, def_attack
    }

    public void link(Player player, boolean up, Type type){
        ArrayList<Card> cards = player.cards;
        if(type.equals(Type.def)) cards = defender;else
        if(type.equals(Type.attack)) cards = attack; else
        if(type.equals(Type.def_attack)){
            cards = new ArrayList<Card>();
            for (int i = 0; i < defender.size(); i++) {
                cards.add(defender.get(i));
            }
            for (int i = 0; i < attack.size(); i++) {
                cards.add(attack.get(i));
            }
        }


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
        setTableCardsCoordinate();
        for (int i = 0; i < cards.size(); i++) {
            x1[i] = cards.get(i).center.x;
            y1[i] = cards.get(i).center.y;
            angle1[i] = cards.get(i).angle;
        }

        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setPosition(x2[i], y2[i]);
            cards.get(i).angle = angle2[i];
            cards.get(i).throwTo(x1[i], y1[i], 1, angle1[i]);
        }
    }


    public void setTableCardsCoordinate(){
        if(attack.size() == 1){
            attack.get(0).setPosition(GamePole.SCREEN_WIDTH / 2, GamePole.SCREEN_HEIGHT / 2);
            attack.get(0).angle = 40;

            for (int i = 0; i < defender.size(); i++) {
                defender.get(i).center.set(attack.get(i).center);
            }
        } else
        if (attack.size() == 2){
            attack.get(0).setPosition(3 * GamePole.SCREEN_WIDTH / 8, GamePole.SCREEN_HEIGHT / 2);
            attack.get(0).angle = 40;
            attack.get(1).setPosition(5 * GamePole.SCREEN_WIDTH / 8, GamePole.SCREEN_HEIGHT / 2);
            attack.get(1).angle = 40;

            for (int i = 0; i < defender.size(); i++) {
                defender.get(i).center.set(attack.get(i).center);
            }
        }  else
        if (attack.size() == 3){
            attack.get(0).setPosition(GamePole.SCREEN_WIDTH / 4, GamePole.SCREEN_HEIGHT / 2);
            attack.get(0).angle = 40;
            attack.get(1).setPosition(GamePole.SCREEN_WIDTH / 2, GamePole.SCREEN_HEIGHT / 2);
            attack.get(1).angle = 40;
            attack.get(2).setPosition(3 * GamePole.SCREEN_WIDTH / 4, GamePole.SCREEN_HEIGHT / 2);
            attack.get(2).angle = 40;

            for (int i = 0; i < defender.size(); i++) {
                defender.get(i).center.set(attack.get(i).center);
            }
        }
    }


    public void onTouchCard(int x, int y){

        for (int i = 0; i < player.length; i++) {
            for (int j = 0; j < player[i].cards.size(); j++) {
                if(player[i].cards.get(j).center.x - player[i].cards.get(j).width / 2 < x &&
                   player[i].cards.get(j).center.x + player[i].cards.get(j).width / 2 > x &&
                   player[i].cards.get(j).center.y - player[i].cards.get(j).height / 2 < y &&
                   player[i].cards.get(j).center.y + player[i].cards.get(j).height / 2 > y){
                    if((i == 0 && canPutCardOnTable(Type.def,player[i].cards.get(j) ))||(i == 1 && canPutCardOnTable(Type.attack,player[i].cards.get(j)))) {
                        if (i == 0)
                            defender.add(player[i].cards.get(j));
                        else
                            attack.add(player[i].cards.get(j));
                        player[i].cards.remove(j);
                        link(player[i], i == 0, Type.def_attack);

                        link(player[i], i == 0, Type.card);

                    }
                    break;
                }
            }
        }
    }

    boolean canPutCardOnTable(Type type, Card card){
        if(type.equals(Type.def)) {
            if ((attack.get(attack.size() - 1).value < card.value)) {
                if (attack.get(attack.size() - 1).suit.equals(card.suit)) return true; //Если масти совпадают
                if (card.suit.equals(trump)) return true; // Если козырная карта
            } else {
                return (!attack.get(attack.size() - 1).suit.equals(trump) && card.equals(trump));
            }
        }else
        if(type.equals(Type.attack)){
            if(attack.size() == 0) return true;
            for (int i = 0; i < attack.size(); i++) {
                if(attack.get(i).value == card.value) return true;
            }

            for (int i = 0; i < defender.size(); i++) {
                if(defender.get(i).value == card.value) return true;
            }
        }
        return false;
    }
}
