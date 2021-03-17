package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.tuni.tiko.gameObject.tower.CookingTower;
import fi.tuni.tiko.utilities.Action;
import fi.tuni.tiko.gameObject.tower.TowerLocation;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.eventSystem.Events;
import fi.tuni.tiko.eventSystem.TouchListener;

public class TowerSelectionPopOutMenu extends HudSprite implements TouchListener {


    private static final Texture backgroundTexture = new Texture("tower_selection_background.png");
    private static final Texture cookingTowerTexture = new Texture("cooking_tower.png");
    private static final float BACKGROUND_SIZE = 100;
    HudElementManager manager;
    Button cookingTowerButton;

    public static TowerSelectionPopOutMenu GetInstance(HudElementManager manager, MenuPosition position, final TowerLocation location) {
        TowerSelectionPopOutMenu toReturn = new TowerSelectionPopOutMenu(manager, position, location);
        manager.AddHudElement(toReturn);
        return toReturn;
    }

    private TowerSelectionPopOutMenu(HudElementManager manager, MenuPosition position, final TowerLocation location) {
        super(backgroundTexture, position, BACKGROUND_SIZE);
        this.manager = manager;
        position.add(-20,40);
        cookingTowerButton = new Button(cookingTowerTexture, position, 30,
                new Action() {
                    @Override
                    public void run() {
                        location.setTower(CookingTower.getInstance());
                        dispose();
                    }
                }, Events.Priority.HIGH);
        Events.AddListener(this);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        cookingTowerButton.render(batch);
    }

    @Override
    public boolean onTouchDown(ScreenPosition position, int pointer) {
        if (!IsInside(position.ToMenuPosition())) {
            dispose();
        }
        return false;
    }

    @Override
    public boolean onTouchUp(ScreenPosition position, int pointer) {
        return false;
    }

    @Override
    public boolean onTouchDragged(ScreenPosition position, int pointer) {
        return false;
    }

    @Override
    public Events.Priority getTouchListenerPriority() {
        return Events.Priority.MEDIUM;
    }

    @Override
    public void dispose() {
        manager.RemoveHudElement(this);
        Events.RemoveListener(this);
        cookingTowerButton.dispose();
    }
}
