package fi.tuni.tiko.map;

import java.util.ArrayList;

import fi.tuni.tiko.coordinateSystem.MapPosition;

public class Path {

    public final ArrayList<MapPosition> checkPoints;

    public Path(Path clone) {
        checkPoints = new ArrayList<>(clone.checkPoints);
    }

    public Path(MapPosition startingPoint) {
        checkPoints = new ArrayList<>();
        checkPoints.add(startingPoint);
    }

    public MapPosition getLast() {
        return checkPoints.get(checkPoints.size() - 1);
    }
}
