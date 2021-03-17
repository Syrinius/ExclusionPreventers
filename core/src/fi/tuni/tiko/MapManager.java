package fi.tuni.tiko;

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
        //Test map, won't be in the final game
        availableMaps.add(new Map("map1.tmx"));
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
