package com.plombeer.games;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by hh on 28.04.2015.
 */
public class Card {
    enum Suit{
        spades, hearts, diamonds, clubs
    }

    Suit suit; // �����
    int value; // �������� �����(6 - 10, 11 - �����, 12 - ����, 13 - ������, 14 - ���)


    int centerX, centerY, angle, width, height; // ���������� ������, ���� ��������, ������� �����
}
