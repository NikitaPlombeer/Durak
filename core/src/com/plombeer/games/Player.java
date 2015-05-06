package com.plombeer.games;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by hh on 28.04.2015.
 */
public class Player {


    ArrayList<Card> cards;
    Table table;
    boolean sorted = false;
    boolean up;

    Player(Table table, boolean up){
        this.table = table;
        this.up = up;
        cards = new ArrayList<Card>();
    }
    enum TypeOfHod{
        protection, attack
    }
    public void make_hod(TypeOfHod typeOfHod){
        boolean make = true;
        for (int i = 0; i < table.defender.size(); i++) {
            if(table.defender.get(i).isFlying) {
                make = false;
                break;
            }
        }
        if(!make) return;

        for (int i = 0; i < table.attack.size(); i++) {
            if(table.attack.get(i).isFlying) {
                make = false;
                break;
            }
        }
        if(!make) return;

        if(typeOfHod.equals(TypeOfHod.protection) && table.attack.size() == 0)
            return;

        if(typeOfHod.equals(TypeOfHod.protection) && table.attack.size() == table.defender.size()) return;

        if(typeOfHod.equals(TypeOfHod.protection)){
            make = false;
            for (int i = 0; i < cards.size(); i++) {
                if(table.canPutCardOnTable(Table.Type.def, cards.get(i))){
                    make = true;
                    table.onTouchCard(cards.get(i).center.x - Card.WIDTH / 2 + 1, cards.get(i).center.y);
                    break;
                }
            }
            if(!make) table.take();
        } else{
            make = false;


            int sumOfNTrumps = 0;
            for (int i = cards.size() - 1; i >= 0; i--) {
                if(!cards.get(i).suit.equals(table.trump)) {
                    sumOfNTrumps = i + 1;
                    break;
                }
            }

            ArrayList<Integer> indexes = new ArrayList<Integer>();
            while (true) {
                int minIndex = -1;
                for (int i = 0; i < cards.size(); i++) {
                    if ((indexes.size() >= sumOfNTrumps || !cards.get(i).suit.equals(table.trump)) && (minIndex == -1 || cards.get(minIndex).value > cards.get(i).value)) {
                        if(indexes.indexOf(i) == -1)
                            minIndex = i;
                    }
                }
                if (table.canPutCardOnTable(Table.Type.attack, cards.get(minIndex))) {
                    table.onTouchCard(cards.get(minIndex).center.x - Card.WIDTH / 2 + 1, cards.get(minIndex).center.y);
                    make = true;
                    break;
                } else {
                    indexes.add(minIndex);
                    if(indexes.size() == cards.size()){
                        table.bito();
                        break;
                    }
                }
            }

//            for (int i = 0; i < cards.size(); i++) {
//                if(table.canPutCardOnTable(Table.Type.attack, cards.get(i))){
//                    table.onTouchCard(cards.get(i).center.x - Card.WIDTH / 2 + 1, cards.get(i).center.y);
//                    make = true;
//                    break;
//                }
//            }
//            if(!make) table.bito();
        }
    }



    public void drawCards(SpriteBatch batch, Sprite sprites[][], Sprite shirtSprite){
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).draw(batch, sprites, shirtSprite);
        }
    }



    public void setCardCoordinate(){
        for (int i = 0; i < cards.size(); i++) {
            float x = GamePole.SCREEN_WIDTH / 4f + (i) / (float)cards.size() * GamePole.SCREEN_WIDTH / 2 + Card.WIDTH / 2f;
            float y = Card.HEIGHT / 2;
            if(up) y = GamePole.SCREEN_HEIGHT - Card.HEIGHT / 2;

            cards.get(i).angle = 0;
            cards.get(i).setPosition(x, y);
        }
    }


}
