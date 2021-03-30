package fi.tuni.tiko.wave;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;

import fi.tuni.tiko.gameObject.student.StudentType;

public class WaveData implements Json.Serializable {

    public HashMap<StudentType, StudentData> students;

    @Override
    public void write(Json json) {

    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        students = new HashMap<>();
        JsonValue currentField = jsonData.child();
        while (currentField != null) {
            students.put(StudentType.valueOf(currentField.name), json.readValue(StudentData.class, currentField));
            currentField = currentField.next;
        }
    }
}
