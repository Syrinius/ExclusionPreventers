package fi.tuni.tiko;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

import fi.tuni.tiko.coordinateSystem.MapPosition;

public class GameObjectManager extends Timer.Task {

    private static final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private static final ArrayList<GameObject> delayedAdd = new ArrayList<>();
    private static final ArrayList<GameObject> delayedRemove = new ArrayList<>();
    private static boolean locked;
    private static long lastTick;
    private static SpriteBatch batch;

    static void AddGameObject(GameObject object){
        if(locked) delayedAdd.add(object);
        else gameObjects.add(object);
    }

    static void RemoveGameObject(GameObject object){
        if(locked) delayedRemove.add(object);
        else gameObjects.remove(object);
    }

    private static GameObjectManager singleton;

    public static void Initialize() {
        if(singleton != null) return;
        singleton = new GameObjectManager();
        Timer.schedule(singleton, 0, 0.04f);
        lastTick = System.nanoTime();
        batch = new SpriteBatch();
    }

    public static void Dispose() {
        if(singleton == null) return;
        if (!locked) gameObjects.clear();
        delayedAdd.clear();
        delayedRemove.clear();
        singleton.cancel();
        batch.dispose();
    }

    public static void Render() {
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