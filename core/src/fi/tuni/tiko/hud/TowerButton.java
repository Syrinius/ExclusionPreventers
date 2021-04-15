package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.gameObject.tower.EmptyTower;
import fi.tuni.tiko.gameObject.tower.Tower;
import fi.tuni.tiko.gameObject.tower.TowerLocation;
import fi.tuni.tiko.utilities.Action;

public class TowerButton extends Button {


    private final Tower tower;
    final TowerLocation location;
    private final int level;
    private final GlyphRenderer glyphRenderer;
    private static final Texture removeTexture = new Texture("menu/remove_tower.png");

    public TowerButton(Tower tower, TowerLocation location, MenuPosition position, int level, float size, Action toExecute) {
        super(tower.getClass().equals(EmptyTower.class) ? removeTexture : tower.getTexture(level), position, size, toExecute);
        this.tower = tower;
        this.location = location;
        this.level = level;
        glyphRenderer = new GlyphRenderer(new MenuPosition(position.x, position.y + size/2), 0.3f, GlyphRenderer.Type.FUNDS, tower.getCost(level));
    }

    @Override
    public boolean onTouchDown(ScreenPosition position, int pointer) {
        if (IsInside(position.ToMenuPosition()) && GameLogic.getFunds() >= tower.getCost(level)) {
            GameLogic.addFunds(- tower.getCost(level));
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
