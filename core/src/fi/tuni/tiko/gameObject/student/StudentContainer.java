package fi.tuni.tiko.gameObject.student;

import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.gameObject.GameObjectSprite;
import fi.tuni.tiko.map.Map;

public class StudentContainer extends GameObjectSprite {

    private Student student;

    public StudentContainer(Student student, MapPosition position, Map map) {
        super(student.getTextureRegion(), position, map);
        this.student = student;
    }

    public void setStudent(Student student) {
        this.student = student;
        setRegion(student.getTextureRegion());
    }

    @Override
    public TYPE getType() {
        return TYPE.STUDENT;
    }

    @Override
    public void onTick(float deltaTime) {
        student.tick(this);
    }
}
