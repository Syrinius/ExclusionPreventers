package fi.tuni.tiko.gameObject.student;

import com.badlogic.gdx.math.Vector2;

import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.gameObject.GameObjectSprite;
import fi.tuni.tiko.map.Map;
import fi.tuni.tiko.map.Path;
import fi.tuni.tiko.utilities.FancyMath;

public class StudentContainer extends GameObjectSprite {

    private Student student;
    private float animationTime = 0;
    private Path path;
    private int nextCheckpoint = 1;

    public StudentContainer(Student student, Map map, Path path) {
        super(student.getTextureRegion(0), path.checkPoints.get(0), map);
        this.student = student;
        this.path = path;
        setRotation((float) FancyMath.angleDegs(path.checkPoints.get(0), path.checkPoints.get(1)));
    }

    public void setStudent(Student student) {
        this.student = student;
        setRegion(student.getTextureRegion(animationTime));
    }

    @Override
    public TYPE getType() {
        return TYPE.STUDENT;
    }

    @Override
    public void onTick(float deltaTime) {
        student.tick(this);
        setRegion(student.getTextureRegion(animationTime += deltaTime));
        float remainingMove = student.getSpeed();
        while (remainingMove > 0) {
            MapPosition thisPosition = new MapPosition(getX(), getY());
            float distance = path.checkPoints.get(nextCheckpoint).clone().sub(thisPosition).len();
            float alpha = Math.min(remainingMove/distance, 1);
            Vector2 newPosition = thisPosition.lerp(path.checkPoints.get(nextCheckpoint), alpha);
            if (alpha >= 1) {
                nextCheckpoint++;
                if (path.checkPoints.size() == nextCheckpoint) {
                    reachedEnd();
                    return;
                }
                setRotation((float) FancyMath.angleDegs(newPosition, path.checkPoints.get(nextCheckpoint)));
            }
            remainingMove -= (1 / alpha) * remainingMove;
            setPosition(newPosition.x, newPosition.y);
        }
    }

    public void reachedEnd() {
        new StudentContainer(Student1.getInstance(), map, map.paths.get((int)Math.round(Math.random()))); //temporary stuff
        destroy();
        //TODO
    }
}