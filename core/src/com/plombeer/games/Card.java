package com.plombeer.games;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by hh on 28.04.2015.
 */
public class Card {
    enum Suit{
        spades, hearts, diamonds, clubs
    }

    Suit suit; // Масть
    int value; // Значение карты(6 - 10, 11 - Валет, 12 - Дама, 13 - Король, 14 - Туз)


    int centerX, centerY, angle, width, height; // Координаты центра, угол поворота, размеры карты
}
