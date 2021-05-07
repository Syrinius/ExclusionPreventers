package fi.tuni.tiko.gameObject.student;

import com.badlogic.gdx.math.Vector2;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.gameObject.tower.TowerType;
import fi.tuni.tiko.map.Map;
import fi.tuni.tiko.map.Path;
import fi.tuni.tiko.utilities.FancyMath;
import fi.tuni.tiko.gameObject.GameObjectSprite;
import fi.tuni.tiko.gameObject.tower.TowerLocation;

/**
 * All students are instances of this class and hold a reference to static implementation of the specific kind of student it is
 * Handles all the functionalities of students moving on the map
 */
public class StudentContainer extends GameObjectSprite {

    private Student student;
    private float animationTime = 0;
    private final Path path;
    private int nextCheckpoint = 1;
    private float currentParticipation = 0;
    private final TowerType resistance;
    private final TowerType affinity;

    public StudentContainer(Student student, Map map, Path path, TowerType resistance, TowerType affinity) {
        super(student.getWalkingTextureRegion(0), path.checkPoints.get(0), map);
        this.student = student;
        this.path = path;
        this.resistance = resistance;
        this.affinity = affinity;
        setRotation((float) FancyMath.angleDegs(path.checkPoints.get(0), path.checkPoints.get(1)));
    }

    public void setStudent(Student student) {
        this.student = student;
        setRegion(student.getWalkingTextureRegion(animationTime));
    }

    @Override
    public TYPE getType() {
        return TYPE.STUDENT;
    }

    /**
     * Handles movement of the student through a given path
     * Uses lerp between chackpoints with currently available move distance as alpha, until all movement in current tick has been consumed
     * @param deltaTime time since last tick. Used to ensure consistent pace of movement
     * @param revalidate if true will tell towers to re-check for targets
     */
    @Override
    public void onTick(float deltaTime, boolean revalidate) {
        if(!isValid()) return;
        student.tick(this);
        setRegion(student.getWalkingTextureRegion(animationTime += deltaTime));
        float remainingMove = student.getSpeed() * deltaTime;
        int oldX = (int)getX();
        int oldY = (int)getY();
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
        if(oldX != (int)getX() || oldY != (int)getY() || revalidate) notifyTowers();
    }

    private void notifyTowers() {
        for (fi.tuni.tiko.gameObject.tower.TowerLocation tower : map.towerLocations) {
            if(!isValid()) {
                tower.removeTarget(this);
            }
            else tower.checkTarget(this);
        }
    }

    /**
     * Handles adding of particiaption when the student has been affected by a tower
     * @param tower the tower that is affecting the student, used to get the appropriate participation value
     */
    public void addParticipation(TowerLocation tower) {
        if(!isValid()) return;
        float toAdd = tower.getParticipation();
        if(resistance != null && resistance.tower.equals(tower.getTower())) toAdd *= 0.5;
        if(affinity != null && affinity.tower.equals(tower.getTower())) toAdd *= 1.5;
        currentParticipation += toAdd;
        if (currentParticipation >= student.getRequiredParticipation()) {
            destroy();
            new CheeringContainer(student, tower.getCheeringSpawnPosition(), map);
            tower.addStudentCount(1);
            tower.addScore();
        }
    }

    public void reachedEnd() {
        GameLogic.setLives(fi.tuni.tiko.GameLogic.getLives() - 1);
        destroy();
    }

    @Override
    public void destroy() {
        super.destroy();
        notifyTowers();
        map.getGameObjectManager().invalidate();
        map.waveManager.studentRemoved();
    }

    public float getTileCenterX() {
        return (float)Math.floor(getX()) + 0.5f;
    }

    public float getTileCenterY() {
        return (float)Math.floor(getY()) + 0.5f;
    }
}