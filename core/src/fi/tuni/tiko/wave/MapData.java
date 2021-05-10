package fi.tuni.tiko.wave;

import com.badlogic.gdx.utils.Array;

/**
 * Deserialized data from the json files
 */
public class MapData {

    public Array<WaveData> waves;
    public int starting_funds;
    public int starting_lives;
    public int starting_workers;
}
