package fi.tuni.tiko.gameObject.student;

import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.gameObject.GameObjectSprite;
import fi.tuni.tiko.map.Map;

public class CheeringContainer extends GameObjectSprite {
    private Student student;
    private float animationTime = 0;

    public CheeringContainer(Student student, MapPosition position, Map map) {
        super(student.getCheeringTextureRegion(0), position, map);
        this.student = student;
        setRotation((float)(Math.random() * 360));
    }

    @Override
    public TYPE getType() {
        return TYPE.CINEMATIC;
    }

    @Override
    public void onTick(float deltaTime, boolean revalidate) {
        setRegion(student.getCheeringTextureRegion(animationTime += deltaTime));
    }
}
