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
        availableMaps.add(new Map("map3.tmx", "map1.json"));
        availableMaps.add(new Map("map4.tmx", "map1.json"));
        availableMaps.add(new Map("map5.tmx", "map1.json"));
        availableMaps.add(new Map("map6.tmx", "map1.json"));
        availableMaps.add(new Map("map7.tmx", "map1.json"));
        availableMaps.add(new Map("map8.tmx", "map1.json"));
        availableMaps.add(new Map("map9.tmx", "map1.json"));
        availableMaps.add(new Map("map10.tmx", "map1.json"));
    }

    public static boolean isLastMap() {
        return selectedMap == availableMaps.get(availableMaps.size() - 1);
    }

    public static Map selectMap(int index) {
        return selectedMap = availableMaps.get(index);
    }

    public static Map getMap(int index) {
        return availableMaps.get(index);
    }

    public static int getIndex(Map map) {
        return availableMaps.indexOf(map);
    }

    public static Map getSelectedMap() {
        return selectedMap;
    }

    public static void render() {
        if (selectedMap == null) return;
        selectedMap.render();
    }
}
