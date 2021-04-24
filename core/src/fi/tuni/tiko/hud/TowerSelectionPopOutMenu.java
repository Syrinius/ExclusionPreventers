package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashSet;

import fi.tuni.tiko.gameObject.tower.AtkTower;
import fi.tuni.tiko.gameObject.tower.BookTower;
import fi.tuni.tiko.gameObject.tower.CookingTower;
import fi.tuni.tiko.gameObject.tower.MusicTower;
import fi.tuni.tiko.gameObject.tower.SportTower;
import fi.tuni.tiko.gameObject.tower.TowerData;
import fi.tuni.tiko.gameObject.tower.TowerType;
import fi.tuni.tiko.utilities.Action;
import fi.tuni.tiko.gameObject.tower.TowerLocation;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.eventSystem.Events;
import fi.tuni.tiko.eventSystem.TouchListener;
import fi.tuni.tiko.utilities.NextButtonPosition;

public class TowerSelectionPopOutMenu extends HudSprite implements TouchListener, PopOut {


    private static final Texture backgroundTexture = new Texture("towers/tower_selection_background.png");
    private static final float BACKGROUND_SIZE = 100;
    private final HudElementManager manager;
    private final HashSet<TowerButton> buttons = new HashSet<>();


    public static TowerSelectionPopOutMenu GetInstance(HudElementManager manager, MenuPosition position, final TowerLocation location) {
        TowerSelectionPopOutMenu toReturn = new TowerSelectionPopOutMenu(manager, position, location);
        manager.AddHudElement(toReturn);
        manager.SetPopOut(toReturn);
        return toReturn;
    }

    private TowerSelectionPopOutMenu(final HudElementManager manager, MenuPosition towerPosition, final TowerLocation location) {
        super(backgroundTexture, towerPosition, BACKGROUND_SIZE);

        float gapX = 40;
        float gapY = 0;
        Action disposer = new Action() {
            @Override
            public void run() {
                manager.SetPopOut(null);
            }
        };

        this.manager = manager;
        towerPosition.add(-20,40);

        for (TowerType towerType : TowerType.values()) {
            TowerData data = towerType.tower.getNewData();
            buttons.add(new TowerButton(towerType.tower, location, towerPosition, data, 25, disposer));
            towerPosition = NextButtonPosition.nextTowerPosition(towerPosition, gapX, gapY);
        }
        Events.AddListener(this);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        for (TowerButton button : buttons) {
            button.render(batch);
        }
    }

    @Override
    public boolean onTouchDown(ScreenPosition position, int pointer) {
        if (!IsInside(position.ToMenuPosition())) {
            manager.SetPopOut(null);
            return false;
        }
        return true;
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
        for (TowerButton button : buttons) {
            button.dispose();
        }
    }

    @Override
    public void close() {
        dispose();
    }
}
