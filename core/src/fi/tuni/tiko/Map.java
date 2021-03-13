package fi.tuni.tiko;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Timer;

import fi.tuni.tiko.coordinateSystem.MapPosition;

public class Map extends Timer.Task {

    String path;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    boolean initialized;
    Action toExecute;

    public Map(String path) {
        this.path = path;
    }

    public void LoadMap(Action mapLoaded) {
        toExecute = mapLoaded;
        Timer.schedule(this, 0.02f, 0, 0);
    }

    public void render() {
        if (!initialized) return;
        tiledMapRenderer.setView(MapPosition.camera);
        tiledMapRenderer.render();
    }

    public int getTileType(MapPosition at) {
        if (!initialized) return -1;
        TiledMapTileLayer.Cell cell = ((TiledMapTileLayer)tiledMap.getLayers().get(0)).getCell((int)at.x, (int)at.y);
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
        toExecute.run();
    }
}
