package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.gameObject.tower.Tower;
import fi.tuni.tiko.gameObject.tower.TowerLocation;
import fi.tuni.tiko.utilities.Action;

public class TowerButton extends Button {


    private final Tower tower;
    private final TowerLocation location;
    private final int level;
    private final GlyphRenderer glyphRenderer;

    public TowerButton(Tower tower, TowerLocation location, MenuPosition position, int level, float size, Action toExecute) {
        super(tower.getTexture(level), position, size, toExecute);
        this.tower = tower;
        this.location = location;
        this.level = level;
        glyphRenderer = new GlyphRenderer(position.clone(), 0.3f, GlyphRenderer.Type.FUNDS, tower.getCost(level));
    }

    @Override
    public boolean onTouchDown(ScreenPosition position, int pointer) {
        if (IsInside(position.ToMenuPosition()) && GameLogic.getFunds() >= tower.getCost(level)) {
            GameLogic.setFunds(GameLogic.getFunds() - tower.getCost(level));
            location.setTower(tower, level, true);
            toExecute.run();
            return true;
        }
        return false;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        glyphRenderer.render(batch);
    }
}
