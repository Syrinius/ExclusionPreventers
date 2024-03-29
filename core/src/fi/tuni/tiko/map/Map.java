package fi.tuni.tiko.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.MainGame;
import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.wave.MapData;
import fi.tuni.tiko.wave.WaveManager;
import fi.tuni.tiko.GameLogicListener;
import fi.tuni.tiko.gameObject.GameObjectManager;
import fi.tuni.tiko.gameObject.tower.TowerLocation;
import fi.tuni.tiko.hud.HudElementManager;
import fi.tuni.tiko.sceneSystem.GameScene;
import fi.tuni.tiko.utilities.Action;

/**
 * Handles all functionalities of maps
 * All individual maps will be implementations of this class
 * Includes methods to fetch properties from tiles in the map
 * Fetches all initial information such as tower locations upon map load
 * Extends timer to add a delay to map load so other code can perform actions before execution freezes during map load
 * Automatically generates all possible paths for student movement on maps
 */
public class Map extends Timer.Task implements GameLogicListener {

    final String fileLocation;
    final String jsonLocation;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    boolean initialized;
    fi.tuni.tiko.utilities.Action toExecute;
    public ArrayList<fi.tuni.tiko.gameObject.tower.TowerLocation> towerLocations;
    private int width;
    private int height;
    private fi.tuni.tiko.gameObject.GameObjectManager gameObjectManager;
    private fi.tuni.tiko.sceneSystem.GameScene scene;
    public ArrayList<fi.tuni.tiko.map.Path> paths = new ArrayList<>();
    public MapData mapData;
    public WaveManager waveManager;
    private boolean firstTimeCreated = true;

    public fi.tuni.tiko.map.Path getRandomPath() {
        return paths.get((int)(Math.random() * (float)paths.size()));
    }

    public fi.tuni.tiko.gameObject.GameObjectManager getGameObjectManager() {
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

    public Map(String fileLocation, String jsonLocation) {
        this.fileLocation = fileLocation;
        this.jsonLocation = jsonLocation;
    }

    public void dispose() {
        gameObjectManager.dispose();
        for (fi.tuni.tiko.gameObject.tower.TowerLocation location : towerLocations) location.destroy();
        GameLogic.RemoveListener(this);
    }

    /**
     * Uses a timer so other code can finish before game freezes during map load
     * @param scene current instance of GameScene
     * @param mapLoaded Action to be performed after map is succesfully loaded
     */
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

    /**
     * only the one using MapPosition is accessible outside to only allow correct usage
     * @param at the tile being checked
     * @return Returns the type of the tile as given as property on the tileset as a String
     */
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

    /**
     * Creates new paths by calling itself at crossroads
     * Creates all the checkpoints for the path from start tile to end tile
     * Generates all the possible paths in the currently loading map
     * @param path The path being saved as an instance of Path class
     * @param currentPosition current tile the function is iterating through
     * @param type property of the tile being checked, determines how the function behaves
     */
    private void generatePaths(fi.tuni.tiko.map.Path path, MapPosition currentPosition, String type) {
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
                            generatePaths(new fi.tuni.tiko.map.Path(path), nextPosition(to, currentPosition), to);
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
                                generatePaths(new fi.tuni.tiko.map.Path(path), nextPosition(to, currentPosition), to);
                            first = false;
                        }
                    }
                }
            }
            currentPosition = aheadPosition;
        } while (!end);
    }

    /**
     * helper function for generating paths
     * @param direction Direction gives as a string
     * @param position the position from which the next position is returned
     * @return returns the next position in the give direction from given tile as MapPosition
     */
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

    /**
     * Most of the actions needed to load the map happen here
     * Calls to initialize all the other required classes for a map
     * Iterates through all the tiles on a map to find tower locations and starting tile for paths
     * Sets ingame resources to correct starting amounts
     */
    @Override
    public void run() {
        if (firstTimeCreated) {
            tiledMap = new TmxMapLoader().load(fileLocation);
            tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, MapPosition.SCALE);
            initialized = true;
        }

        GameLogic.AddListener(this);
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
                } else if (firstTimeCreated && currentType.equals("start")){
                    if (startPosition != null) {
                        MainGame.SetDebugText("Too many starting points!");
                        return;
                    }
                    startPosition = new MapPosition(j, i);
                }
            }
        }

        if (firstTimeCreated) {
            if (startPosition == null) {
                fi.tuni.tiko.MainGame.SetDebugText("No starting point found!");
                return;
            }
            String startDirection = startPosition.x == 0 ? "right" : startPosition.x == width - 1 ? "left" : startPosition.y == 0 ? "up" : "down";
            startPosition.add(0.5f, 0.5f);
            generatePaths(new Path(startPosition), startPosition, startDirection);
            MapPosition.camera.position.set(width / 2f, height / 2f, 0);
            Json json = new Json();
            mapData = json.fromJson(MapData.class, Gdx.files.internal(jsonLocation));
        }

		waveManager = new WaveManager(this);
        GameLogic.setFunds(mapData.starting_funds);
        GameLogic.setLives(mapData.starting_lives);
        GameLogic.setWorkers(mapData.starting_workers);
        GameLogic.setScore(0);
        fi.tuni.tiko.GameLogic.setCurrentSpeedMultiplier(1);
        firstTimeCreated = false;
        toExecute.run();
    }

    @Override
    public void onPause() {
        if (gameObjectManager != null) gameObjectManager.pause();
    }

    @Override
    public void onResume() {
         if (gameObjectManager != null) gameObjectManager.resume();
    }

    @Override
    public void onLivesChanged(int newValue) {

    }

    @Override
    public void onFundsChanged(int newValue) {

    }

    @Override
    public void onWorkersChanged(int newValue) {

    }

    @Override
    public void onScoreChanged(int newValue) {

    }
}