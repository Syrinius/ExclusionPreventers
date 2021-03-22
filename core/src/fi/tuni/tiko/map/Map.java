package fi.tuni.tiko.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import fi.tuni.tiko.MainGame;
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
 * Automatically generates all possible paths for student movement on maps
 */
public class Map extends Timer.Task {

    String fileLocation;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    boolean initialized;
    Action toExecute;
    ArrayList<TowerLocation> towerLocations;
    private int width;
    private int height;
    private GameObjectManager gameObjectManager;
    private GameScene scene;
    public ArrayList<Path> paths = new ArrayList<>();

    public GameObjectManager getGameObjectManager() {
        return gameObjectManager;
    }
    public HudElementManager getHudElementManager() {
        return scene.hudElementManager;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight(){
        return height;
    }

    public Map(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public void LoadMap(GameScene scene, Action mapLoaded) {
        this.scene = scene;
        toExecute = mapLoaded;
        Timer.schedule(this, 0.02f, 0, 0);
    }

    public void render() {
        if (!initialized) return;
        MapPosition.camera.update();
        tiledMapRenderer.setView(MapPosition.camera);
        tiledMapRenderer.render();
        gameObjectManager.render();
    }

    public String getTileType(MapPosition at) {
        return getTileType((int)at.x, (int)at.y);
    }

    private String getTileType(int x, int y) {
        if (!initialized) return "";
        TiledMapTileLayer.Cell cell = ((TiledMapTileLayer)tiledMap.getLayers().get(0)).getCell(x, y);
        if (cell == null || cell.getTile() == null) return "";
        MapProperties props = cell.getTile().getProperties();
        if (!props.containsKey("type")) return "";
        return (String)props.get("type");
    }

    private void generatePaths(Path path, MapPosition currentPosition, String type) {
        paths.add(path);
        MapPosition aheadPosition;
        String aheadType;
        boolean end = false;
        do {
            aheadPosition = nextPosition(type, currentPosition);
            aheadType = getTileType(aheadPosition);
            if(!aheadType.equals("corridor")) {
                if (aheadType.equals(type)) {
                    path.checkPoints.add(currentPosition);
                    for (String to : Direction.GetDirection(type)) {
                        if (getTileType(nextPosition(to, currentPosition)).equals(to)) {
                            generatePaths(new Path(path), nextPosition(to, currentPosition), to);
                        }
                    }

                } else if (aheadType.equals("end")) {
                    path.checkPoints.add(currentPosition);
                    end = true;
                } else {
                    path.checkPoints.add(currentPosition);
                    boolean first = true;
                    for (String to : Direction.GetDirection(type)) {
                        if (getTileType(nextPosition(to, currentPosition)).equals(to) || getTileType(nextPosition(to, currentPosition)).equals("corridor")) {
                            if (first) {
                                aheadPosition = nextPosition(to, currentPosition);
                                type = to;
                            } else
                                generatePaths(new Path(path), nextPosition(to, currentPosition), to);
                            first = false;
                        }
                    }
                }
            }
            currentPosition = aheadPosition;
        } while (!end);
    }

    private static MapPosition nextPosition(String direction, MapPosition position) {
        switch (direction) {
            case "up":
                return new MapPosition(position.x, position.y + 1);
            case "right":
                return new MapPosition(position.x + 1, position.y);
            case "down":
                return new MapPosition(position.x, position.y - 1);
            case "left":
                return new MapPosition(position.x - 1, position.y);
        }
        return null;
    }

    @Override
    public void run() {
        tiledMap = new TmxMapLoader().load(fileLocation);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, MapPosition.SCALE);
        initialized = true;
        gameObjectManager = new GameObjectManager();
        towerLocations = new ArrayList<>();
        height = ((TiledMapTileLayer)tiledMap.getLayers().get(0)).getHeight();
        width = ((TiledMapTileLayer)tiledMap.getLayers().get(0)).getWidth();
        MapPosition startPosition = null;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                String currentType = getTileType(j, i);
                if (currentType.equals("tower")){
                    towerLocations.add(new TowerLocation(new MapPosition(j, i), this));
                } else if (currentType.equals("start")){
                    if (startPosition != null) {
                        MainGame.SetDebugText("Too many starting points!");
                        return;
                    }
                    startPosition = new MapPosition(j, i);
                }
            }
        }
        if (startPosition == null) {
            MainGame.SetDebugText("No starting point found!");
            return;
        }
        String startDirection = startPosition.x == 0 ? "right" : startPosition.x == width - 1 ? "left" : startPosition.y == 0 ? "up" : "down";
        startPosition.add(0.5f, 0.5f);
        generatePaths(new Path(startPosition), startPosition, startDirection);
        MapPosition.camera.position.set(width/2f, height/2f, 0);
        toExecute.run();
    }
}

class Direction {

    private static final java.util.Map<String, List<String>> directions = new HashMap<>();
    static {
        directions.put("down", Arrays.asList("right", "left"));
        directions.put("up", Arrays.asList("left", "right"));
        directions.put("right", Arrays.asList("down", "up"));
        directions.put("left", Arrays.asList("up", "down"));
    }

    static List<String> GetDirection(String to) {
        return directions.get(to);
    }
}