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
        clubs ~ ������
        spades ~ ����
        hearts ~ �����
        diamonds ~ ����
     */

    Suit suit; // �����

    boolean shirtUp = true; // �������� ����� ��� ����

    int value; // �������� �����(6 - 10, 11 - �����, 12 - ����, 13 - ������, 14 - ���)


    Vector2 center; // ��������� ������ ����� �� ������
    float angle = 0f; // ���� ��������

    //��������
    Vector2 velocity; // �������� �������� �����
    float angleVel = 0f; //�������� �������� ����
    float newAngle = 0f, angle0 = 0f; // ����� ���� � �������� �� ���������
    float time = 0f, maxTime; // �����, ������� ����� �����, � ������������ �����
    boolean isFlying = false; //����� ����� � ���
    Vector2 start; // ��������� ��������� �����, �� ������� �� ��������
    Vector2 end; // �������� ��������� ����� � �������� �� ���������

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

    // ������� ����� � ����������� �� ������� ����� �������
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

    //������ ����� �� SpriteBatch
    public void draw(SpriteBatch batch, Sprite sprites[][], Sprite shirtSprite){


        Sprite toDraw = shirtSprite; // ���������� ������������, ��� ����� �������� �����

        //���� �������� ����, �� ������ �����
        if(!shirtUp){
            int index = 0;
            if(suit.equals(Suit.spades)) index = 1; else
            if(suit.equals(Suit.hearts)) index = 2; else
            if(suit.equals(Suit.diamonds)) index = 3;
            toDraw = sprites[value - 6][index];
        }

        toDraw.setPosition(center.x - WIDTH / 2, center.y - HEIGHT / 2);
        toDraw.setSize(WIDTH, HEIGHT);
        toDraw.rotate(angle); // ������������ ������ �� �������� ����
        toDraw.draw(batch);
        toDraw.rotate(-angle); // ������������ ������ �������, �� ��������� ������

    }

    //������� ��������, ����� ����� ������ � ���������� (xEnd, yEnd) �� ����� time
    public void setVelocity(float xEnd,float yEnd, float time){
        velocity.x = (xEnd - center.x) / (time);
        velocity.y = (yEnd - center.y) / (time);
        end.x = xEnd;
        end.y = yEnd;
    }

    //������ ����� � ���������� (x, y), ���� ��� �� ��� ����� time � ����� ���� ����� newAngle
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
