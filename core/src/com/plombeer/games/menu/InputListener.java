package com.plombeer.games.menu;

import com.badlogic.gdx.InputProcessor;
import com.plombeer.games.GamePole;

/**
 * Created by hh on 30.04.2015.
 */
public class InputListener implements InputProcessor{

    MenuScreen screen;

    public InputListener(MenuScreen screen) {
        this.screen = screen;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screen.newGameButton.touch(screenX, GamePole.SCREEN_HEIGHT - screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
