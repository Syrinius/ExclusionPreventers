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
import fi.tuni.tiko.utilities.NextButtonPosition;

public class TowerSelectionPopOutMenu extends HudSprite implements TouchListener {


    private static final Texture backgroundTexture = new Texture("tower_selection_background.png");
    private static final Texture cookingTowerTexture = new Texture("cooking_tower.png");
    private static final Texture sportTowerTexture = new Texture("towers/sport.png");
    private static final Texture atkTowerTexture = new Texture("towers/atk.png");
    private static final Texture bookTowerTexture = new Texture("towers/book.png");
    private static final Texture musicTowerTexture = new Texture("note_tower.png");
    private static final float BACKGROUND_SIZE = 100;
    HudElementManager manager;
    Button cookingTowerButton, sportTowerButton, atkTowerButton, bookTowerButton, musicTowerButton;
    MenuPosition towerPosition;
    float gapX = 40;
    float gapY = 0;

    public static TowerSelectionPopOutMenu GetInstance(HudElementManager manager, MenuPosition position, final TowerLocation location) {
        TowerSelectionPopOutMenu toReturn = new TowerSelectionPopOutMenu(manager, position, location);
        manager.AddHudElement(toReturn);
        return toReturn;
    }

    private TowerSelectionPopOutMenu(HudElementManager manager, MenuPosition towerPosition, final TowerLocation location) {
        super(backgroundTexture, towerPosition, BACKGROUND_SIZE);
        this.manager = manager;
        towerPosition.add(-20,40);
        cookingTowerButton = new Button(cookingTowerTexture, towerPosition, 30,
                new Action() {
                    @Override
                    public void run() {
                        location.setTower(CookingTower.getInstance(), true);
                        dispose();
                    }
                }, Events.Priority.HIGH);

        towerPosition = NextButtonPosition.nextTowerPosition(towerPosition, gapX, gapY);
        sportTowerButton = new Button(sportTowerTexture, towerPosition, 30,
                new Action() {
                    @Override
                    public void run() {
                        location.setTower(CookingTower.getInstance(), true);
                        dispose();
                    }
                }, Events.Priority.HIGH);

        towerPosition = NextButtonPosition.nextTowerPosition(towerPosition, gapX, gapY);
        atkTowerButton = new Button(atkTowerTexture, towerPosition, 30,
                new Action() {
                    @Override
                    public void run() {
                        location.setTower(CookingTower.getInstance(), true);
                        dispose();
                    }
                }, Events.Priority.HIGH);

        towerPosition = NextButtonPosition.nextTowerPosition(towerPosition, gapX, gapY);
        bookTowerButton = new Button(bookTowerTexture, towerPosition, 30,
                new Action() {
                    @Override
                    public void run() {
                        location.setTower(CookingTower.getInstance(), true);
                        dispose();
                    }
                }, Events.Priority.HIGH);

        towerPosition = NextButtonPosition.nextTowerPosition(towerPosition, gapX, gapY);
        musicTowerButton = new Button(musicTowerTexture, towerPosition, 30,
                new Action() {
                    @Override
                    public void run() {
                        location.setTower(CookingTower.getInstance(), true);
                        dispose();
                    }
                }, Events.Priority.HIGH);

        Events.AddListener(this);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        cookingTowerButton.render(batch);
        sportTowerButton.render(batch);
        atkTowerButton.render(batch);
        bookTowerButton.render(batch);
        musicTowerButton.render(batch);
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
        sportTowerButton.dispose();
        atkTowerButton.dispose();
        bookTowerButton.dispose();
        musicTowerButton.dispose();
    }
}
