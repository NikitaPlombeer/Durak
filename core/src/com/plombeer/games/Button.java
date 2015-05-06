package com.plombeer.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.plombeer.games.util.FontFactory;

/**
 * Created by hh on 30.04.2015.
 */
public class Button extends Rectangle{

    Texture image;
    boolean visible = true; // Видна кнопка или нет
    public ButtonInterface mOnClickListener;
    private String text;

    public Button(float x, float y, float width, float height, MyGdxGame.Typ type) {
        super(x, y, width, height);
        FontFactory.getInstance().initialize(type);
        BitmapFont.TextBounds bounds = FontFactory.getInstance().getFont().getBounds(text == null ? "bito" : text);
        float k = width * 124f / (5 * bounds.width / 4);
        FontFactory.getInstance().getFont().setScale(k / 124f);
    }

    public Button(float x, float y, float width, float height, String path, String text, MyGdxGame.Typ type) {
        super(x, y, width, height);
        createTexture(path);
        FontFactory.getInstance().initialize(type);
        BitmapFont.TextBounds bounds = FontFactory.getInstance().getFont().getBounds(text == null ? "bito" : text);
        float k = width * 124f / (5 * bounds.width / 4);
        FontFactory.getInstance().getFont().setScale(k / 124f);
        this.text = text;
    }

    public void createTexture(String path){
        image = new Texture(path);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOnClickListener(ButtonInterface b){
        this.mOnClickListener = b;
    }

     public void touch(int x, int y){
         if(visible && this.x < x && this.x + this.width > x &&
                 this.y < y && this.y + this.height > y){
             mOnClickListener.onClick(this);
         }
     }

    public void draw(SpriteBatch batch){
        if(visible) {
            batch.draw(image, x, y, width, height);
            BitmapFont.TextBounds bounds = FontFactory.getInstance().getFont().getBounds(text);
            FontFactory.getInstance().getFont().draw(batch, text, x + width / 2 - bounds.width / 2,
                    y + height / 2 + bounds.height / 2);
        }
    }
}
