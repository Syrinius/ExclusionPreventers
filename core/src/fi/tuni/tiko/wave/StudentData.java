package fi.tuni.tiko.wave;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import fi.tuni.tiko.gameObject.tower.TowerType;

/**
 * Deserialized data from the json files
 */
public class StudentData implements Json.Serializable {

    public int amount;
    public float baseCooldown;
    public TowerType resistance;
    public TowerType affinity;

    @Override
    public void write(Json json) {

    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        JsonValue currentField = jsonData.child();
        while (currentField != null) {
            switch (currentField.name) {
                case "affinity":
                    affinity = TowerType.valueOf(currentField.asString());
                    break;
                case "resistance":
                    resistance = TowerType.valueOf(currentField.asString());
                    break;
                case "amount":
                    amount = currentField.asInt();
                    break;
                case "baseCooldown":
                    baseCooldown = currentField.asFloat();
                    break;
            }
            currentField = currentField.next;
        }
    }
}
