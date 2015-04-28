package com.plombeer.games;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hh on 28.04.2015.
 */
public class Deck {
    ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
        for (int i = 0; i < 4; i++) {
            for (int j = 6; j <= 14; j++) {
                cards.add(new Card(i, j));
            }
        }

        mix();
        int a = 0;
    }

    public Card getLastCard(){
        Card last = cards.get(cards.size() - 1);
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
}
