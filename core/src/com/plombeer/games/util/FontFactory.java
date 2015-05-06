package com.plombeer.games.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.plombeer.games.MyGdxGame;

public class FontFactory {

    // Font names
    public static final String GAME_FONT_NAME = "fonts/Minecrafter_3.ttf";
    public static final String MENU_FONT_NAME = "fonts/menu.ttf";

    // Russian cyrillic characters
    public static final String RUSSIAN_CHARACTERS = "ABCDEFGHIJKLMNOPQSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz"
            + "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
            + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
            + "1234567890.,:;_¡!¿?\"'+-*/()[]={}";

    // Singleton: unique instance
    private static FontFactory instance;

    private BitmapFont font;

    /** Private constructor for singleton pattern */
    private FontFactory() { super(); }

    public static synchronized FontFactory getInstance() {
        if (instance == null) {
            instance = new FontFactory();
        }
        return instance;
    }



    public void initialize(MyGdxGame.Typ type) {
        // If fonts are already generated, dispose it!
        if (font != null) font.dispose();
        if(type.equals(MyGdxGame.Typ.game))
            font = generateFont(GAME_FONT_NAME, RUSSIAN_CHARACTERS); else
            font = generateFont(MENU_FONT_NAME, RUSSIAN_CHARACTERS);
    }



    /**
     * Generate a BitmapFont with font name and characters received as params
     *
     * @param fontName    Font name
     * @param characters  Characters to generate
     *
     * @return Generated BitmapFont
     */
    private BitmapFont generateFont(String fontName, String characters) {

        // Configure font parameters
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.characters = characters;
        parameter.size = 124;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 0;
        parameter.borderColor = Color.BLACK;
        // Generate font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator( Gdx.files.internal(fontName) );
        BitmapFont newFont = generator.generateFont(parameter);

        // Dispose resources
        generator.dispose();

        return newFont;
    }

    public BitmapFont getFont() {
        return font;
    }

    /**
     * Dispose resources
     */
    public void dispose() {
        font.dispose();
    }
}
