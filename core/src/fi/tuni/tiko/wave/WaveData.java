package fi.tuni.tiko.wave;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;

import fi.tuni.tiko.gameObject.student.StudentType;

/**
 * Deserialized data from the json files
 */
public class WaveData implements Json.Serializable {

    public HashMap<fi.tuni.tiko.gameObject.student.StudentType, StudentData> students;
    public int worker_reward = 0;
    public int fund_reward = 0;

    @Override
    public void write(Json json) {

    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        students = new HashMap<>();
        JsonValue currentField = jsonData.child();
        while (currentField != null) {
            switch (currentField.name) {
                case "composition":
                    JsonValue studentField = currentField.child();
                    while (studentField != null) {
                        students.put(StudentType.valueOf(studentField.name), json.readValue(StudentData.class, studentField));
                        studentField = studentField.next;
                    }
                    break;
                case "fund_reward":
                    fund_reward = currentField.asInt();
                    break;
                case "worker_reward":
                    worker_reward = currentField.asInt();
                    break;
            }
            currentField = currentField.next;
        }
    }
}
