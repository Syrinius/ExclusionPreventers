package fi.tuni.tiko;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.gameObject.GameObjectManager;
import fi.tuni.tiko.hud.HudElementManager;
import fi.tuni.tiko.sceneSystem.GameScene;
import fi.tuni.tiko.gameObject.tower.TowerLocation;
import fi.tuni.tiko.utilities.Action;

/**
 * Handles all functionalities of maps
 * All individual maps will be implementations of this class
 * Includes methods to fetch properties from tiles in the map
 * Fetches all initial information such as tower locations upon map load
 * Extends timer to add a delay to map load so other code can perform actions before execution freezes during map load
 *
 */
public class Map extends Timer.Task {

    String path;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    boolean initialized;
    Action toExecute;
    ArrayList<TowerLocation> towerLocations;
    int width;
    int height;

    private GameObjectManager gameObjectManager;
    private GameScene scene;

    public GameObjectManager getGameObjectManager() {
        return gameObjectManager;
    }
    public HudElementManager getHudElementManager() {
        return scene.hudElementManager;
    }

    public Map(String path) {
        this.path = path;
    }

    public void LoadMap(GameScene scene, Action mapLoaded) {
        this.scene = scene;
        toExecute = mapLoaded;
        Timer.schedule(this, 0.02f, 0, 0);
    }

    public void render() {
        if (!initialized) return;
        tiledMapRenderer.setView(MapPosition.camera);
        tiledMapRenderer.render();
        gameObjectManager.render();
    }

    public int getTileType(MapPosition at) {
        return getTileType((int)at.x, (int)at.y);
    }

    private int getTileType(int x, int y) {
        if (!initialized) return -1;
        TiledMapTileLayer.Cell cell = ((TiledMapTileLayer)tiledMap.getLayers().get(0)).getCell(x, y);
        if (cell == null || cell.getTile() == null) return -1;
        MapProperties props = cell.getTile().getProperties();
        if (!props.containsKey("type")) return -1;
        return (int)props.get("type");
    }

    @Override
    public void run() {
        tiledMap = new TmxMapLoader().load(path);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, MapPosition.SCALE);
        initialized = true;
        gameObjectManager = new GameObjectManager();
        towerLocations = new ArrayList<>();
        height = ((TiledMapTileLayer)tiledMap.getLayers().get(0)).getHeight();
        width = ((TiledMapTileLayer)tiledMap.getLayers().get(0)).getWidth();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (getTileType(j, i) == 1){
                    towerLocations.add(new TowerLocation(new MapPosition(j, i), this));
                }
            }
        }

        toExecute.run();
    }
}
