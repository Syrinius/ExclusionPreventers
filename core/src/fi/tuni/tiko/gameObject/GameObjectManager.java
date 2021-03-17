package fi.tuni.tiko.gameObject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

import fi.tuni.tiko.coordinateSystem.MapPosition;

/**
 * Dispatches Ontick and render events to GameObjects
 * All GameObjects should subscribe to gameObjects list
 * Will be instanced in Map class
 */
public class GameObjectManager extends Timer.Task {

    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private final ArrayList<GameObject> delayedAdd = new ArrayList<>();
    private final ArrayList<GameObject> delayedRemove = new ArrayList<>();
    private boolean locked;
    private long lastTick;
    private SpriteBatch batch;

    public void addGameObject(GameObject object){
        if(locked) delayedAdd.add(object);
        else gameObjects.add(object);
    }

    public void removeGameObject(GameObject object){
        if(locked) delayedRemove.add(object);
        else gameObjects.remove(object);
    }

    public GameObjectManager() {
        Timer.schedule(this, 0, 0.04f);
        lastTick = System.nanoTime();
        batch = new SpriteBatch();
    }

    public void dispose() {
        if (locked) return;
        locked = true;
        gameObjects.clear();
        delayedAdd.clear();
        delayedRemove.clear();
        cancel();
        batch.dispose();
    }

    public void render() {
        batch.setProjectionMatrix(MapPosition.camera.combined);
        batch.begin();
        locked = true;
        for (GameObject object : gameObjects) {
            object.render(batch);
        }
        locked = false;
        batch.end();
    }

    @Override
    public void run() {
        long currentTime = System.nanoTime();
        float delta = (currentTime - lastTick) / 1000000000f; //to seconds
        lastTick = currentTime;
        locked = true;
        for (GameObject object : gameObjects) {
            object.onTick(delta);
        }
        locked = false;
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