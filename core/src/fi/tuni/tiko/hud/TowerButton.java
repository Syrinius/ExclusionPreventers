package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.gameObject.tower.EmptyTower;
import fi.tuni.tiko.gameObject.tower.Tower;
import fi.tuni.tiko.gameObject.tower.TowerData;
import fi.tuni.tiko.gameObject.tower.TowerLocation;
import fi.tuni.tiko.utilities.Action;

public class TowerButton extends Button {


    private final Tower tower;
    final TowerLocation location;
    private final TowerData data;
    private final GlyphRenderer glyphRenderer;
    private static final Texture removeTexture = new Texture("menu/remove_tower.png");

    public TowerButton(Tower tower, TowerLocation location, MenuPosition position, TowerData data, float size, Action toExecute) {
        super(tower.getClass().equals(EmptyTower.class) ? removeTexture : tower.getTexture(data), position, size, toExecute);
        this.tower = tower;
        this.location = location;
        this.data = data;
        glyphRenderer = new GlyphRenderer(new MenuPosition(position.x - size / 2, position.y - size / 2.5f), 0.3f, GlyphRenderer.Type.FUNDS, tower.getCost(data));
    }

    @Override
    public boolean onTouchDown(ScreenPosition position, int pointer) {
        if (IsInside(position.ToMenuPosition())) {
            if(GameLogic.getFunds() >= tower.getCost(data)) {
                GameLogic.addFunds(-tower.getCost(data));
                location.setTower(tower, data.level, true);
                toExecute.run();
            }
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
