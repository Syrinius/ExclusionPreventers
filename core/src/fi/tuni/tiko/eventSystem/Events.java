package fi.tuni.tiko.eventSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;

import fi.tuni.tiko.coordinateSystem.ScreenPosition;

public class Events implements InputProcessor {

    private static Events singleton;

    public static void Initialize() {
        if (singleton != null) return;
        singleton = new Events();
        Gdx.input.setInputProcessor(singleton);
    }

    private static final ArrayList<TouchListener> touchListeners = new ArrayList<>();

    public static void AddListener(TouchListener listener){
        touchListeners.add(listener);
    }

    public static void RemoveListener(TouchListener listener){
        touchListeners.remove(listener);
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
        for (TouchListener touchListener : touchListeners) {
            touchListener.onTouchDown(new ScreenPosition(screenX, screenY), pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (TouchListener touchListener : touchListeners) {
            touchListener.onTouchUp(new ScreenPosition(screenX, screenY), pointer);
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (TouchListener touchListener : touchListeners) {
            touchListener.onTouchDragged(new ScreenPosition(screenX, screenY), pointer);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
