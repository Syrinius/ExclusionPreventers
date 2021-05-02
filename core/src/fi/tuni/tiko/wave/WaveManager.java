package fi.tuni.tiko.wave;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.hud.WaveClearedPopOutMenu;
import fi.tuni.tiko.gameObject.GameObject;
import fi.tuni.tiko.gameObject.student.StudentContainer;
import fi.tuni.tiko.gameObject.student.StudentType;
import fi.tuni.tiko.map.Map;

public class WaveManager implements GameObject {
    private final fi.tuni.tiko.map.Map map;
    private float cooldown = 0;
    private int remaining = 0;
    private int currentWave = 0;
    private final HashMap<fi.tuni.tiko.gameObject.student.StudentType, Integer> studentCountList;

    public WaveManager(Map map) {
        this.map = map;
        studentCountList = new HashMap<>();
        nextWave();
        map.getGameObjectManager().addGameObject(this);
    }

    public void studentRemoved() {
        if(--remaining == 0) waveCleared();
    }

    private void waveCleared() {
        fi.tuni.tiko.wave.WaveData wave = map.mapData.waves.get(currentWave);
        GameLogic.addFunds(wave.fund_reward);
        GameLogic.addWorkers(wave.worker_reward);

        if(++currentWave == map.mapData.waves.size) {
            GameLogic.SetState(GameLogic.GameState.WIN_SCREEN);
            return;
        }

        nextWave();
    }

    private void nextWave() {
        WaveData wave = map.mapData.waves.get(currentWave);
        for (fi.tuni.tiko.gameObject.student.StudentType type :
                wave.students.keySet()) {
            remaining += wave.students.get(type).amount;
            studentCountList.put(type, wave.students.get(type).amount);
        }
        cooldown = 0;
        fi.tuni.tiko.GameLogic.Pause();
        WaveClearedPopOutMenu.GetInstance(map.getHudElementManager(), wave);
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
                        new StudentContainer(student.type, map, map.getRandomPath(), studentData.resistance, studentData.affinity);
                        studentCountList.put(student, studentCountList.get(student) - 1);
                        break;
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
