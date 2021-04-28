package fi.tuni.tiko.eventSystem;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.MainGame;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;

/**
 * Dispatcher for Ontouch events
 * All elements that need user input should subscribe to touchListeners
 * Arranges the listeners in priority list so higher priority elements can consume touch events first
 * Usually higher priority elements should be elements that are on top of everything else, such as in a popout element
 */
public class Events implements InputProcessor {

    private static Events singleton;
    private static Comparator<TouchListener> comparator = new Comparator<TouchListener>() {
        @Override
        public int compare(TouchListener a, TouchListener b) {
            int aValue = a.getTouchListenerPriority().ordinal();
            int bValue = b.getTouchListenerPriority().ordinal();
            //noinspection UseCompareMethod
            return aValue == bValue? 0 : aValue < bValue? 1 : -1;
        }
    };

    public enum Priority { LOW, MEDIUM, HIGH }

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
        else {
            touchListeners.add(listener);
            Collections.sort(touchListeners, comparator);
        }
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
            if (touchListener.onTouchDown(new ScreenPosition(screenX, screenY), pointer)) break;
        }
        locked = false;
        updateList();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        locked = true;
        for (TouchListener touchListener : touchListeners) {
            if (touchListener.onTouchUp(new ScreenPosition(screenX, screenY), pointer)) break;
        }
        locked = false;
        updateList();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        locked = true;
        for (TouchListener touchListener : touchListeners) {
            if (touchListener.onTouchDragged(new ScreenPosition(screenX, screenY), pointer)) break;
        }
        locked = false;
        updateList();
        return false;
    }

    private void updateList() {
        //MainGame.SetDebugText("" + touchListeners.size());
        if (!delayedAdd.isEmpty()) {
            touchListeners.addAll(delayedAdd);
            Collections.sort(touchListeners, comparator);
            delayedAdd.clear();
        }
        if (!delayedRemove.isEmpty()) {
            touchListeners.removeAll(delayedRemove);
            delayedRemove.clear();
        }
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
