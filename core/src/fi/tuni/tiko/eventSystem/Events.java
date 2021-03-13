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
    private static final ArrayList<TouchListener> delayedAdd = new ArrayList<>();
    private static final ArrayList<TouchListener> delayedRemove = new ArrayList<>();
    private static boolean locked;

    public static void AddListener(TouchListener listener){
        if (locked) delayedAdd.add(listener);
        else touchListeners.add(listener);
    }

    public static void RemoveListener(TouchListener listener){
        if (locked) delayedRemove.add(listener);
        else touchListeners.remove(listener);
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
        locked = true;
        for (TouchListener touchListener : touchListeners) {
            touchListener.onTouchDown(new ScreenPosition(screenX, screenY), pointer);
        }
        locked = false;
        if (!delayedAdd.isEmpty()) {
            touchListeners.addAll(delayedAdd);
            delayedAdd.clear();
        }
        if (!delayedRemove.isEmpty()) {
            touchListeners.removeAll(delayedRemove);
            delayedRemove.clear();
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        locked = true;
        for (TouchListener touchListener : touchListeners) {
            touchListener.onTouchUp(new ScreenPosition(screenX, screenY), pointer);
        }
        locked = false;
        if (!delayedAdd.isEmpty()) {
            touchListeners.addAll(delayedAdd);
            delayedAdd.clear();
        }
        if (!delayedRemove.isEmpty()) {
            touchListeners.removeAll(delayedRemove);
            delayedRemove.clear();
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        locked = true;
        for (TouchListener touchListener : touchListeners) {
            touchListener.onTouchDragged(new ScreenPosition(screenX, screenY), pointer);
        }
        locked = false;
        if (!delayedAdd.isEmpty()) {
            touchListeners.addAll(delayedAdd);
            delayedAdd.clear();
        }
        if (!delayedRemove.isEmpty()) {
            touchListeners.removeAll(delayedRemove);
            delayedRemove.clear();
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
