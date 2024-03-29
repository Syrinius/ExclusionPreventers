package fi.tuni.tiko.gameObject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MapPosition;

/**
 * Dispatches Ontick and render events to GameObjects
 * All GameObjects should subscribe to gameObjects list
 * Will be instanced in Map class
 */
public class GameObjectManager extends Timer.Task {

    private final ArrayList<fi.tuni.tiko.gameObject.GameObject> gameObjects = new ArrayList<>();
    private final ArrayList<fi.tuni.tiko.gameObject.GameObject> delayedAdd = new ArrayList<>();
    private final ArrayList<fi.tuni.tiko.gameObject.GameObject> delayedRemove = new ArrayList<>();
    private boolean locked;
    private long lastTick;
    private final SpriteBatch batch;
    private boolean active = false;
    private boolean dirty = false;
    private boolean dispose = false;
    private boolean disposed = false;

    public void invalidate() {
        dirty = true;
    }

    public void pause() {
        active = false;
    }

    public void resume() {
        active = true;
    }

    public boolean isPaused() {
        return !active;
    }

    public void addGameObject(fi.tuni.tiko.gameObject.GameObject object){
        if(locked) delayedAdd.add(object);
        else gameObjects.add(object);
    }

    public void removeGameObject(fi.tuni.tiko.gameObject.GameObject object){
        if(locked) delayedRemove.add(object);
        else gameObjects.remove(object);
    }

    public GameObjectManager() {
        Timer.schedule(this, 0, 0.04f);
        lastTick = System.nanoTime();
        batch = new SpriteBatch();
    }

    public void dispose() {
        dispose = true;
        if (locked || disposed) return;
        locked = true;
        active = false;
        gameObjects.clear();
        delayedAdd.clear();
        delayedRemove.clear();
        batch.dispose();
        cancel();
        disposed = true;
    }

    public void render() {
        batch.setProjectionMatrix(MapPosition.camera.combined);
        batch.begin();
        locked = true;
        for (fi.tuni.tiko.gameObject.GameObject object : gameObjects) {
            object.render(batch);
        }
        locked = false;
        batch.end();
    }

    @Override
    public void run() {
        long currentTime = System.nanoTime();
        float delta = ((currentTime - lastTick) / 1000000000f) * GameLogic.getCurrentSpeedMultiplier(); //to seconds
        lastTick = currentTime;
        if (!active) return;
        locked = true;
        boolean revalidate = dirty;
        dirty = false;
        for (GameObject object : gameObjects) {
            object.onTick(delta, revalidate);
        }
        locked = false;
        if (dispose) {
            dispose();
            return;
        }
        if (!delayedAdd.isEmpty()) {
            gameObjects.addAll(delayedAdd);
            delayedAdd.clear();
        }
        if (!delayedRemove.isEmpty()) {
            gameObjects.removeAll(delayedRemove);
            delayedRemove.clear();
        }
    }
}