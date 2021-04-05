package fi.tuni.tiko.gameObject.student;

import com.badlogic.gdx.math.Vector2;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.gameObject.GameObjectSprite;
import fi.tuni.tiko.gameObject.tower.TowerLocation;
import fi.tuni.tiko.map.Map;
import fi.tuni.tiko.map.Path;
import fi.tuni.tiko.utilities.FancyMath;

public class StudentContainer extends GameObjectSprite {

    private Student student;
    private float animationTime = 0;
    private final Path path;
    private int nextCheckpoint = 1;
    private int currentParticipation = 0;

    public StudentContainer(Student student, Map map, Path path) {
        super(student.getWalkingTextureRegion(0), path.checkPoints.get(0), map);
        this.student = student;
        this.path = path;
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

    @Override
    public void onTick(float deltaTime, boolean revalidate) {
        if(!isValid()) return;
        student.tick(this);
        setRegion(student.getWalkingTextureRegion(animationTime += deltaTime));
        float remainingMove = student.getSpeed();
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
        for (TowerLocation tower : map.towerLocations) {
            if(!isValid() || Math.pow(tower.getX() - (Math.floor(getX()) + 0.5), 2) + Math.pow(tower.getY() - (Math.floor(getY()) + 0.5), 2) > Math.pow(tower.getRange(), 2)) {
                tower.removeTarget(this);
            } else tower.addTarget(this);
        }
    }

    public void addParticipation(TowerLocation tower) {
        if(!isValid()) return;
        currentParticipation += tower.getParticipation();
        if (currentParticipation >= student.getRequiredParticipation()) {
            destroy();
            new CheeringContainer(student, tower.getCheeringSpawnPosition(), map);
            //TODO
        }
    }

    public void reachedEnd() {
        GameLogic.setLives(GameLogic.getLives() - 1);
        destroy();
        //TODO
    }

    @Override
    public void destroy() {
        super.destroy();
        notifyTowers();
        map.getGameObjectManager().invalidate();
        map.waveManager.studentRemoved();
    }
}