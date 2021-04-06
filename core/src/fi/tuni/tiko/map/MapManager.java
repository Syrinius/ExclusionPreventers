package fi.tuni.tiko.map;

import java.util.ArrayList;

/**
 * Includes a static list of all implemented maps
 * Handles selection of maps
 * Passes reference to selected maps
 */
public class MapManager {

    static ArrayList<Map> availableMaps = new ArrayList<>();
    static Map selectedMap;

    static {
        availableMaps.add(new Map("map1.tmx", "map1.json"));
        availableMaps.add(new Map("map2.tmx", "map1.json"));
    }

    public static Map selectMap(int index) {
        return selectedMap = availableMaps.get(index);
    }

    public static Map getMap(int index) {
        return availableMaps.get(index);
    }

    public static Map getSelectedMap() {
        return selectedMap;
    }

    public static void render() {
        if (selectedMap == null) return;
        selectedMap.render();
    }
}
