package com.plombeer.games;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.plombeer.games.util.FontFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by hh on 28.04.2015.
 */
public class Table {
    Card.Suit trump; //Козырь
    int suitsPriority[]; //Определяет приоритет масти в зависимости от козыря(если козырь, то старший)


    Deck deck;
    Player player[];

    ArrayList<Card> defender; // Защищающийся
    ArrayList<Card> attack; // Атакующий игрок
    ArrayList<Card> bitoCards; //Карты в бите
    int defenderPlayerIndex, attackPlayerIndex; //Индексы в массиве player защищающегося и атакущего игрока

    Button button; //Кнопка биты и забирания карты

    Table(){
        deck = new Deck(this);
        player = new Player[2];
        player[0] = new Player(this, true);
        player[1] = new Player(this, false);
        trump = deck.cards.get(0).suit;
        suitsPriority = new int[4];
        suitsPriority[getSuitIndex(trump)] = 4;
        int sum = 1;
        for (int i = 0; i < 4; i++) {
            if(suitsPriority[i] != 4){
                suitsPriority[i] = sum;
                sum++;
            }
        }

        for (int i = 0; i < 6; i++) {
            deck.giveCard(player[0], Deck.Status.close);
            deck.giveCard(player[1], Deck.Status.open);
        }
        defenderPlayerIndex = 0;
        attackPlayerIndex = 1;
        defender = new ArrayList<Card>();
        attack = new ArrayList<Card>();
        bitoCards = new ArrayList<Card>();
        button = new Button( 7 * GamePole.SCREEN_WIDTH / 8 - 50, GamePole.SCREEN_HEIGHT / 10 - 25, 100, 50, MyGdxGame.Typ.game);
        button.setText("bito");
        button.createTexture("textures/quad.png");
        button.setOnClickListener(new ButtonInterface() {
            @Override
            public void onClick(Button button) {
                if(button.getText().equals("bito")){
                    bito();
                }else
                if(button.getText().equals("take")){
                    take();
                }
            }
        });
    }

    public void drawTable(SpriteBatch batch, Sprite sprites[][], Sprite shirtSprite){

        for (int i = 0; i < bitoCards.size(); i++) {
            bitoCards.get(i).draw(batch, sprites, shirtSprite);
        }

        for (int i = 0; i < deck.cards.size(); i++) {
            deck.cards.get(i).draw(batch, sprites, shirtSprite);
        }

        for (int i = 0; i < player.length; i++) {
            player[i].drawCards(batch, sprites, shirtSprite);
        }


        for (int i = 0; i < attack.size(); i++) {
            attack.get(i).draw(batch, sprites, shirtSprite);
        }

        for (int i = 0; i < defender.size(); i++) {
            defender.get(i).draw(batch, sprites, shirtSprite);
        }



        button.draw(batch);

        //float k = Card.HEIGHT / 6;
        //FontFactory.getInstance().getFont().setScale(k / 124f);
        String text = String.valueOf(deck.cards.size());
        BitmapFont.TextBounds bounds = FontFactory.getInstance().getFont().getBounds(text);
        FontFactory.getInstance().getFont().draw(batch, text, GamePole.SCREEN_WIDTH - Card.WIDTH / 2 - bounds.width / 2, GamePole.SCREEN_HEIGHT / 2);

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

        for (int i = 0; i < bitoCards.size(); i++) {
            if(bitoCards.get(i).isFlying) {
                bitoCards.get(i).move(delta);
            }
        }
        if(defenderPlayerIndex == 0)
            player[0].make_hod(Player.TypeOfHod.protection); else
            player[0].make_hod(Player.TypeOfHod.attack);

        String text;
        if(attack.size() == defender.size()) text = "bito"; else text = "take";
        button.setText(text);

    }

    enum Type{
        def, card, attack, def_attack, bito
    }

    public void link(Player player, Type type){
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
        } else
        if(type.equals(Type.bito)){
            cards = bitoCards;
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

        if(type.equals(Type.card)) {
            player.setCardCoordinate();
        }else
        if(type.equals(Type.def_attack) || type.equals(Type.def) || type.equals(Type.attack))
            setTableCardsCoordinate(); else
        if(type.equals(Type.bito))
            setBitoCoordinate();

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
        } else
        if (attack.size() == 2){
            attack.get(0).setPosition(3 * GamePole.SCREEN_WIDTH / 8, GamePole.SCREEN_HEIGHT / 2);
            attack.get(0).angle = 40;
            attack.get(1).setPosition(5 * GamePole.SCREEN_WIDTH / 8, GamePole.SCREEN_HEIGHT / 2);
            attack.get(1).angle = 40;
        }  else
        if (attack.size() == 3){
            attack.get(0).setPosition(GamePole.SCREEN_WIDTH / 4, GamePole.SCREEN_HEIGHT / 2);
            attack.get(0).angle = 40;
            attack.get(1).setPosition(GamePole.SCREEN_WIDTH / 2, GamePole.SCREEN_HEIGHT / 2);
            attack.get(1).angle = 40;
            attack.get(2).setPosition(3 * GamePole.SCREEN_WIDTH / 4, GamePole.SCREEN_HEIGHT / 2);
            attack.get(2).angle = 40;
        } else
        if (attack.size() == 4){
            attack.get(0).setPosition(GamePole.SCREEN_WIDTH / 4, GamePole.SCREEN_HEIGHT /3);
            attack.get(0).angle = 40;
            attack.get(1).setPosition(GamePole.SCREEN_WIDTH / 2, GamePole.SCREEN_HEIGHT / 3);
            attack.get(1).angle = 40;
            attack.get(2).setPosition(3 * GamePole.SCREEN_WIDTH / 4, GamePole.SCREEN_HEIGHT / 3);
            attack.get(2).angle = 40;
            attack.get(3).setPosition(GamePole.SCREEN_WIDTH / 2, 2 * GamePole.SCREEN_HEIGHT / 3);
            attack.get(3).angle = 40;
        }
        else
        if (attack.size() == 5){
            attack.get(0).setPosition(GamePole.SCREEN_WIDTH / 4, GamePole.SCREEN_HEIGHT /3);
            attack.get(0).angle = 40;
            attack.get(1).setPosition(GamePole.SCREEN_WIDTH / 2, GamePole.SCREEN_HEIGHT / 3);
            attack.get(1).angle = 40;
            attack.get(2).setPosition(3 * GamePole.SCREEN_WIDTH / 4, GamePole.SCREEN_HEIGHT / 3);
            attack.get(2).angle = 40;
            attack.get(3).setPosition(GamePole.SCREEN_WIDTH / 4, 2 * GamePole.SCREEN_HEIGHT / 3);
            attack.get(3).angle = 40;
            attack.get(4).setPosition(GamePole.SCREEN_WIDTH / 2, 2 * GamePole.SCREEN_HEIGHT / 3);
            attack.get(4).angle = 40;
        }
        else
        if (attack.size() == 6){
            attack.get(0).setPosition(GamePole.SCREEN_WIDTH / 4, GamePole.SCREEN_HEIGHT /3);
            attack.get(0).angle = 40;
            attack.get(1).setPosition(GamePole.SCREEN_WIDTH / 2, GamePole.SCREEN_HEIGHT / 3);
            attack.get(1).angle = 40;
            attack.get(2).setPosition(3 * GamePole.SCREEN_WIDTH / 4, GamePole.SCREEN_HEIGHT / 3);
            attack.get(2).angle = 40;
            attack.get(3).setPosition(GamePole.SCREEN_WIDTH / 4, 2 * GamePole.SCREEN_HEIGHT / 3);
            attack.get(3).angle = 40;
            attack.get(4).setPosition(GamePole.SCREEN_WIDTH / 2, 2 * GamePole.SCREEN_HEIGHT / 3);
            attack.get(4).angle = 40;
            attack.get(5).setPosition(3 * GamePole.SCREEN_WIDTH / 4, 2 * GamePole.SCREEN_HEIGHT / 3);
            attack.get(5).angle = 40;
        }

        for (int i = 0; i < defender.size(); i++) {
            defender.get(i).center.set(attack.get(i).center);
            defender.get(i).angle = 15;
            defender.get(i).shirtUp = false;
        }
        for (int i = 0; i < attack.size(); i++) {
            attack.get(i).shirtUp = false;
        }
    }


    public int getSuitIndex(Card.Suit suit){
        if(suit.equals(Card.Suit.clubs)) return 0;
        if(suit.equals(Card.Suit.spades)) return 1;
        if(suit.equals(Card.Suit.hearts)) return 2;
        if(suit.equals(Card.Suit.diamonds)) return 3;
        return 0;
    }

    public void onTouchCard(float x, float y){

        for (int i = 0; i < player.length; i++) {
            if(player[i].cards.size() == 0) continue;
            float width = Card.WIDTH;
            if(GamePole.SCREEN_WIDTH / 2 / player[i].cards.size() < width) width = GamePole.SCREEN_WIDTH / 2 / player[i].cards.size();
            for (int j = 0; j < player[i].cards.size(); j++) {
                if(j == player[i].cards.size() - 1) width = Card.WIDTH;
                if(player[i].cards.get(j).isTouched(x, y, width)){
                    if((i == defenderPlayerIndex && defender.size() < attack.size() && canPutCardOnTable(Type.def,player[i].cards.get(j)) )||(i == attackPlayerIndex && canPutCardOnTable(Type.attack,player[i].cards.get(j)))) {
                        if (i == defenderPlayerIndex)
                            defender.add(player[i].cards.get(j));
                        else
                            attack.add(player[i].cards.get(j));
                        player[i].cards.remove(j);
                        link(player[i], Type.def_attack);

                        link(player[i], Type.card);

                    }
                    break;
                }
            }
        }
    }


    public void sort(ArrayList<Card> cards){
        bubbleSort(cards, 0, cards.size(), 1);
        int index = 0;
        for (int i = 0; i < cards.size() - 1; i++) {
            if(!cards.get(i).suit.equals(cards.get(i + 1).suit)){
                bubbleSort(cards, index, i + 1, 0);
                index = i + 1;
            }
        }
        bubbleSort(cards, index, cards.size(), 0);
    }

    //Функция определяющая можем мы положить карту на стол, в зависимости от того кто положил карту и логики игры
    boolean canPutCardOnTable(Type type, Card card){
        if(type.equals(Type.def)) {
            if ((attack.get(defender.size()).value < card.value)) {
                if (attack.get(defender.size()).suit.equals(card.suit)) return true; //Если масти совпадают
                if (card.suit.equals(trump)) return true; // Если козырная карта
            } else {
                return (!attack.get(defender.size()).suit.equals(trump) && card.suit.equals(trump));
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

    //Очищаем стол
    private void clearTable(){
        attack.clear();
        defender.clear();
    }

    //Перемещаем карты со стола в ту колоду cards
    private void transparentCardsOnTable(ArrayList<Card> cards){
        for (int i = 0; i < attack.size(); i++) {
            cards.add(attack.get(i));
        }
        for (int i = 0; i < defender.size(); i++) {
            cards.add(defender.get(i));
        }

    }

    public void bubbleSort(ArrayList<Card> cards, int start, int end, int param){
        for (int i = end - 1; i > start; i--) {
            for (int j = start; j < i; j++) {
                if ((param == 0 && cards.get(j).value > cards.get(j + 1).value) ||
                        (param == 1 && suitsPriority[getSuitIndex(cards.get(j).suit)] > suitsPriority[getSuitIndex(cards.get(j + 1).suit)])){
                    Collections.swap(cards, j, j + 1);
                }
            }
        }
    }
    // Задает координаты новым картам, которые полетят в биту
    public void setBitoCoordinate(){
        Random rand = new Random();
        for (int i = bitoCards.size() - attack.size() - defender.size(); i < bitoCards.size(); i++) {
            bitoCards.get(i).setPosition(rand.nextInt((int) (GamePole.SCREEN_WIDTH / 6 - Card.WIDTH / 2)), GamePole.SCREEN_HEIGHT / 4 + rand.nextInt(GamePole.SCREEN_HEIGHT / 2));
            bitoCards.get(i).shirtUp = true;
        }

        clearTable();
    }

    // Игрок берет карты со стола
    public void take(){

        //Сначала мы перекидываем игроку все карты на столе
        transparentCardsOnTable(player[defenderPlayerIndex].cards);
        if(defenderPlayerIndex == 0)
            for (int i = 0; i < player[defenderPlayerIndex].cards.size(); i++) {
                player[defenderPlayerIndex].cards.get(i).shirtUp = true;
            }
        sort(player[defenderPlayerIndex].cards);
        clearTable();

        //Делаем анимацию передвижения карты у отбивающегося игрока
        link(player[defenderPlayerIndex], Type.card);

        //Добавляем недостающие карты другим игрокам
        addCardsToPlayers();
    }

    //Добавляем игрокам количество карт, которого им не хватает(в данном случае до 6)
    public void addCardsToPlayers(){
        for (int i = 0; i < player.length; i++) {
            int sum = 6 - player[i].cards.size();
            if(deck.cards.size() < sum) sum = deck.cards.size();
            for (int j = 0; j < sum; j++) {
                deck.giveCard(player[i], i == 0 ? Deck.Status.close : Deck.Status.open);
            }
        }
    }

    // Действие, когда нажали на кнопку биты
    public void bito(){
        //Сначала проверяем, можно ли кинуть карты в биту, для этого количество отбитых карт
        //должно быть равно количеству карт, которые кинул отбивающийся игрок
        if(defender.size() == attack.size()){

            //Перемещаем все карты на столе в биту
            transparentCardsOnTable(bitoCards);

            //Запускаем анимацию перемещения в биту
            link(player[0], Type.bito);

            addCardsToPlayers();

            //Меняем местами атакающего и отбивающегося игроков
            int tmp = attackPlayerIndex;
            attackPlayerIndex = defenderPlayerIndex;
            defenderPlayerIndex = tmp;

        }
    }

}
