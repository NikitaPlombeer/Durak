package com.plombeer.games;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hh on 28.04.2015.
 */
public class Deck {
    ArrayList<Card> cards;
    Table table;
    public Deck(Table table) {
        this.table = table;
        cards = new ArrayList<Card>();
        for (int i = 0; i < 4; i++) {
            for (int j = 6; j <= 14; j++) {
                cards.add(new Card(i, j));
            }
        }
        for (int i = 1; i < cards.size(); i++) {
            cards.get(i).setPosition(GamePole.SCREEN_WIDTH - 5 * cards.get(i).width / 6, (int) (GamePole.SCREEN_HEIGHT / 2 + i / (float)cards.size() * (cards.get(i).height * 0.2f)));
            cards.get(i).angle = 10;
        }
        cards.get(0).setPosition(GamePole.SCREEN_WIDTH - 5 * cards.get(0).width / 6 - cards.get(0).height / 4, (int) (GamePole.SCREEN_HEIGHT / 2));
        cards.get(0).angle = -80;
        cards.get(0).shirtUp = false;
        mix();
    }

    public Card getLastCard(){
        Card last = cards.get(cards.size() - 1);
        //last.angle = 0;
        last.shirtUp = false;
        cards.remove(cards.size() - 1);
        return last;
    }



    private void mix(){
        Random rand = new Random();
        int times = 50 + rand.nextInt(100);
        for (int i = 0; i < times; i++) {
            int first = rand.nextInt(cards.size());
            int second = rand.nextInt(cards.size());
            int tmpValue = cards.get(first).value;
            Card.Suit tmpSuit = cards.get(first).suit;
            cards.get(first).suit =  cards.get(second).suit;
            cards.get(first).value = cards.get(second).value;
            cards.get(second).suit =  tmpSuit;
            cards.get(second).value = tmpValue;
        }
    }


    public void giveCard(Player player, boolean up){
        player.cards.add(getLastCard());
        table.link(player, up, Table.Type.card);

    }
}
