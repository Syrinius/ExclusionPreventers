package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.tuni.tiko.eventSystem.Events;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.eventSystem.TouchListener;
import fi.tuni.tiko.gameObject.student.StudentType;
import fi.tuni.tiko.utilities.Action;
import fi.tuni.tiko.utilities.NextButtonPosition;
import fi.tuni.tiko.wave.StudentData;
import fi.tuni.tiko.wave.WaveData;

/**
 * The ingame menu for displaying information about next wave after each wave of students
 * Automatically opens after each wave ends if the game didn't end
 * Instanciates all the elements necessary for that menu
 */
public class WaveClearedPopOutMenu extends HudSprite implements TouchListener, PopOut {
    private static final Texture backgroundTexture = new Texture("menu/wave_cleared_background.png");
    private static final Texture okTexture = new Texture("menu/ok.png");
    private static final float BACKGROUND_SIZE = 150;
    private final HudElementManager manager;
    private final fi.tuni.tiko.wave.WaveData data;
    private final Button okButton;
    private final fi.tuni.tiko.hud.StudentInfo[] students;

    public static WaveClearedPopOutMenu GetInstance(HudElementManager manager, fi.tuni.tiko.wave.WaveData data) {
        WaveClearedPopOutMenu toReturn = new WaveClearedPopOutMenu(manager, data);
        manager.AddHudElement(toReturn);
        manager.SetPopOut(toReturn);
        return toReturn;
    }

    private WaveClearedPopOutMenu(final HudElementManager manager, WaveData data) {
        super(backgroundTexture, new MenuPosition(200, 100), BACKGROUND_SIZE);
        this.manager = manager;
        this.data = data;

        students = new fi.tuni.tiko.hud.StudentInfo[data.students.size()];
        int index = 0;
        MenuPosition studentPosition = new MenuPosition(160, 155);
        for (StudentType student : data.students.keySet()) {
            StudentData studentData = data.students.get(student);
            students[index] = new fi.tuni.tiko.hud.StudentInfo(studentPosition, student, studentData.affinity, studentData.resistance, studentData.amount);
            NextButtonPosition.nextMenuPosition(studentPosition, 0, -30);
            index++;
        }

        okButton = new Button(okTexture, new MenuPosition(200, 50), 30, new Action() {
            @Override
            public void run() {
                manager.SetPopOut(null);
            }
        }, Events.Priority.HIGH);

        Events.AddListener(this);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        for (fi.tuni.tiko.hud.StudentInfo studentInfo : students) {
            studentInfo.render(batch);
        }
        okButton.render(batch);
    }

    @Override
    public void dispose() {
        manager.RemoveHudElement(this);
        Events.RemoveListener(this);
        okButton.dispose();
        for (StudentInfo studentInfo : students) {
            studentInfo.dispose();
        }
    }

    @Override
    public boolean onTouchDown(fi.tuni.tiko.coordinateSystem.ScreenPosition position, int pointer) {
        return true;
    }

    @Override
    public boolean onTouchUp(fi.tuni.tiko.coordinateSystem.ScreenPosition position, int pointer) {
        return false;
    }

    @Override
    public boolean onTouchDragged(ScreenPosition position, int pointer) {
        return false;
    }

    @Override
    public fi.tuni.tiko.eventSystem.Events.Priority getTouchListenerPriority() {
        return fi.tuni.tiko.eventSystem.Events.Priority.MEDIUM;
    }

    @Override
    public void close() {
        dispose();
    }
}
