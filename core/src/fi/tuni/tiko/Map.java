package fi.tuni.tiko;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import fi.tuni.tiko.coordinateSystem.MapPosition;

public class Map {

    String path;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    boolean initialized;

    public Map(String path) {
        this.path = path;
    }

    public void LoadMap() {
        tiledMap = new TmxMapLoader().load(path);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, MapPosition.SCALE);
        initialized = true;
    }

    public void render() {
        if (!initialized) LoadMap();
        tiledMapRenderer.setView(MapPosition.camera);
        tiledMapRenderer.render();
    }

    public int getTileType(MapPosition at) {
        if (!initialized) LoadMap();
        TiledMapTileLayer.Cell cell = ((TiledMapTileLayer)tiledMap.getLayers().get(0)).getCell((int)at.x, (int)at.y);
        if (cell == null || cell.getTile() == null) return -1;
        MapProperties props = cell.getTile().getProperties();
        if (!props.containsKey("type")) return -1;
        return (int)props.get("type");
    }
}
