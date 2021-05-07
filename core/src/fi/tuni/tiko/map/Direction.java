package fi.tuni.tiko.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Helper class for generating paths
 */
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
