package com.plombeer.games;

import java.util.ArrayList;
import java.util.Collections;
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
            cards.get(i).setPosition(GamePole.SCREEN_WIDTH - 5 * Card.WIDTH / 6, (int) (GamePole.SCREEN_HEIGHT / 2 + i / (float)cards.size() * (Card.HEIGHT * 0.2f)));
            cards.get(i).angle = 10;
        }
        cards.get(0).setPosition(GamePole.SCREEN_WIDTH - 5 * Card.WIDTH / 6 - Card.HEIGHT  / 4, (int) (GamePole.SCREEN_HEIGHT / 2));
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



    enum Status{
        close, open
    }
    public void giveCard(Player player, Status status){
        Card newCard = getLastCard();

        player.cards.add(newCard);
        newCard.shirtUp = status.equals(Status.close);
        table.bubbleSort(player.cards, 0, player.cards.size(), 1);
        int index = 0;
        for (int i = 0; i < player.cards.size() - 1; i++) {
            if(!player.cards.get(i).suit.equals(player.cards.get(i + 1).suit)){
                table.bubbleSort(player.cards, index, i + 1, 0);
                index = i + 1;
            }
        }
        table.bubbleSort(player.cards, index, player.cards.size(), 0);

        player.sorted = false;
        table.link(player, Table.Type.card);

    }
}
