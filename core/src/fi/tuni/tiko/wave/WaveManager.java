package fi.tuni.tiko.wave;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.gameObject.GameObject;
import fi.tuni.tiko.gameObject.student.StudentContainer;
import fi.tuni.tiko.gameObject.student.StudentType;
import fi.tuni.tiko.map.Map;

public class WaveManager implements GameObject {
    private final Map map;
    private float cooldown = 0;
    private int remaining = 1;
    private int currentWave = -1;
    private final HashMap<StudentType, Integer> studentCountList;

    public WaveManager(Map map) {
        this.map = map;
        studentCountList = new HashMap<>();
        studentRemoved();
        map.getGameObjectManager().addGameObject(this);
    }

    public void studentRemoved() {
        if(--remaining == 0) {
            if(++currentWave == map.mapData.waves.size) {
                GameLogic.SetState(GameLogic.GameState.END_SCREEN);
                return;
            }

            WaveData wave = map.mapData.waves.get(currentWave);
            for (StudentType type :
                    wave.students.keySet()) {
                remaining += wave.students.get(type).amount;
                studentCountList.put(type, wave.students.get(type).amount);
            }
            cooldown = 0;
            map.pause();
        }
    }

    @Override
    public TYPE getType() {
        return TYPE.SYSTEM;
    }

    @Override
    public void onTick(float deltaTime, boolean revalidate) {
        if(cooldown == 0) {
            double completeWeight = 0.0;
            for (Integer amount : studentCountList.values()) completeWeight += amount;
            if(completeWeight > 0) {
                double r = Math.random() * completeWeight;
                double countWeight = 0.0;
                for (StudentType student : studentCountList.keySet()) {
                    countWeight += studentCountList.get(student);
                    if (countWeight >= r) {
                        StudentData studentData = map.mapData.waves.get(currentWave).students.get(student);
                        cooldown = studentData.baseCooldown;
                        new StudentContainer(student.type, map, map.getRandomPath());
                        studentCountList.put(student, studentCountList.get(student) - 1);
                    }
                }
            }
        } else cooldown = Math.max(0, cooldown - deltaTime);
    }

    @Override
    public void render(SpriteBatch renderer) {

    }

    @Override
    public void destroy() {
        map.getGameObjectManager().removeGameObject(this);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
