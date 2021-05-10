package fi.tuni.tiko.map;

import java.util.ArrayList;

/**
 * Includes a static list of all implemented maps
 * Handles selection of maps
 * Passes reference to selected maps
 */
public class MapManager {

    static ArrayList<fi.tuni.tiko.map.Map> availableMaps = new ArrayList<>();
    static fi.tuni.tiko.map.Map selectedMap;

    static {
        availableMaps.add(new fi.tuni.tiko.map.Map("map1.tmx", "map1.json"));
        availableMaps.add(new fi.tuni.tiko.map.Map("map2.tmx", "map2.json"));
        availableMaps.add(new fi.tuni.tiko.map.Map("map3.tmx", "map1.json"));
        availableMaps.add(new fi.tuni.tiko.map.Map("map4.tmx", "map3.json"));
        availableMaps.add(new fi.tuni.tiko.map.Map("map5.tmx", "map1.json"));
        availableMaps.add(new fi.tuni.tiko.map.Map("map6.tmx", "map3.json"));
        availableMaps.add(new fi.tuni.tiko.map.Map("map7.tmx", "map1.json"));
        availableMaps.add(new fi.tuni.tiko.map.Map("map8.tmx", "map2.json"));
        availableMaps.add(new fi.tuni.tiko.map.Map("map9.tmx", "map3.json"));
        availableMaps.add(new fi.tuni.tiko.map.Map("map10.tmx", "map3.json"));
    }

    public static boolean isLastMap() {
        return selectedMap == availableMaps.get(availableMaps.size() - 1);
    }

    public static fi.tuni.tiko.map.Map selectMap(int index) {
        return selectedMap = availableMaps.get(index);
    }

    public static fi.tuni.tiko.map.Map getMap(int index) {
        return availableMaps.get(index);
    }

    public static int getIndex(fi.tuni.tiko.map.Map map) {
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
