package com.plombeer.games;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by hh on 28.04.2015.
 */
public class Card {

    public static float HEIGHT = GamePole.SCREEN_HEIGHT / 5;
    public static float WIDTH = HEIGHT * 73 / 98;

    enum Suit{
        clubs, spades, hearts, diamonds
    }
    /*\
        clubs ~ крести
        spades ~ пики
        hearts ~ черви
        diamonds ~ буби
     */

    Suit suit; // Масть

    boolean shirtUp = true; // Рубашкой вверх или вниз

    int value; // Значение карты(6 - 10, 11 - Валет, 12 - Дама, 13 - Король, 14 - Туз)


    Vector2 center; // Положение центра карты на экране
    float angle = 0f; // угол поворота

    //Анимация
    Vector2 velocity; // Скорость жвижения карты
    float angleVel = 0f; //Скорость поворота угла
    float newAngle = 0f, angle0 = 0f; // Новый угол к которому мы стремимся
    float time = 0f, maxTime; // Время, которое карта летит, и максимальное время
    boolean isFlying = false; //Летит карта и нет
    Vector2 start; // Начальное положение карты, от которой мы движемся
    Vector2 end; // Конечное положение карты к которому мы стремимся

    public Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
        center = new Vector2();
        velocity = new Vector2();
        end = new Vector2();
        start = new Vector2();
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
        velocity = new Vector2();
        end = new Vector2();
        start = new Vector2();
    }

    public void setPosition(float x, float y){
        center.set(x, y);
    }

    // Двигаем карту в зависимости от времени между кардами
    public void move(float t){
        time += t;

        angle = angle0 + angleVel * time;
        center.x = start.x + velocity.x * time;
        center.y = start.y + velocity.y * time;

        if(time > maxTime){
            isFlying = false;
            time = 0f;
            angle = newAngle;
            center.set(end);
        }
    }

    //Рисуем карту на SpriteBatch
    public void draw(SpriteBatch batch, Sprite sprites[][], Sprite shirtSprite){


        Sprite toDraw = shirtSprite; // Изначально предполагаем, что карта рубашкой вверх

        //Если рубашкой вниз, то рисуем карту
        if(!shirtUp){
            int index = 0;
            if(suit.equals(Suit.spades)) index = 1; else
            if(suit.equals(Suit.hearts)) index = 2; else
            if(suit.equals(Suit.diamonds)) index = 3;
            toDraw = sprites[value - 6][index];
        }

        toDraw.setPosition(center.x - WIDTH / 2, center.y - HEIGHT / 2);
        toDraw.setSize(WIDTH, HEIGHT);
        toDraw.rotate(angle); // Поворачиваем спрайт на заданный угол
        toDraw.draw(batch);
        toDraw.rotate(-angle); // Поворачиваем спрайт обратно, во избежание ошибок

    }

    //Задание скорости, чтобы карта попала в координату (xEnd, yEnd) за время time
    public void setVelocity(float xEnd,float yEnd, float time){
        velocity.x = (xEnd - center.x) / (time);
        velocity.y = (yEnd - center.y) / (time);
        end.x = xEnd;
        end.y = yEnd;
    }

    //Кидаем карту в координаты (x, y), даем ему на это время time и новый угол будет newAngle
    public void throwTo(float x, float y, float time, float newAngle){
        isFlying = true;
        setVelocity(x, y, time);
        maxTime = time;
        angle0 = angle;
        this.newAngle = newAngle;
        angleVel = (newAngle - angle) / (time);
        start.set(center);
    }

    public boolean isTouched(float x, float y, float width){
        return (center.x - WIDTH / 2 < x && center.x - WIDTH / 2 + width> x &&
                center.y - HEIGHT / 2 < y && center.y + HEIGHT / 2 > y);
    }



}
