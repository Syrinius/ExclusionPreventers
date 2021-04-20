package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.eventSystem.Events;
import fi.tuni.tiko.eventSystem.TouchListener;
import fi.tuni.tiko.gameObject.tower.EmptyTower;
import fi.tuni.tiko.gameObject.tower.TowerData;
import fi.tuni.tiko.gameObject.tower.TowerLocation;
import fi.tuni.tiko.utilities.Action;
import fi.tuni.tiko.utilities.NextButtonPosition;

public class TowerImprovementPopOutMenu extends HudSprite implements TouchListener {

    private static final Texture backgroundTexture = new Texture("towers/tower_improvement_background.png");
    private static final Texture plusTexture = new Texture("menu/plus.png");
    private static final Texture minusTexture = new Texture("menu/minus.png");
    private static final float BACKGROUND_SIZE = 100;
    HudElementManager manager;
    private final TowerButton upgradeButton;
    private final TowerButton removeButton;
    private final Button addWorkerButton;
    private final Button removeWorkerButton;
    private final GlyphRenderer workerRenderer;

    public static TowerImprovementPopOutMenu GetInstance(HudElementManager manager, MenuPosition position, final TowerLocation location) {
        TowerImprovementPopOutMenu toReturn = new TowerImprovementPopOutMenu(manager, position, location);
        manager.AddHudElement(toReturn);
        return toReturn;
    }

    private TowerImprovementPopOutMenu(HudElementManager manager, MenuPosition towerPosition, final TowerLocation location) {
        super(backgroundTexture, towerPosition, BACKGROUND_SIZE);
        this.manager = manager;

        Action disposer = new Action() {
            @Override
            public void run() {
                dispose();
            }
        };
        towerPosition.add(0,40);

        TowerData data = location.getTower().getNewData();
        data.level = location.getLevel() + 1;
        upgradeButton = new TowerButton(location.getTower(), location, towerPosition, data, 25, disposer);
        towerPosition = NextButtonPosition.nextTowerPosition(towerPosition, 0, -50);

        removeButton = new RemoveTowerButton(location, towerPosition, 25, disposer);
        towerPosition = NextButtonPosition.nextTowerPosition(towerPosition, -30, -40);

        removeWorkerButton = new Button(minusTexture, towerPosition, 20, new Action() {
            @Override
            public void run() {
                if (location.getWorkers() > 0) {
                    location.addWorkers(-1);
                    workerRenderer.setValue(location.getWorkers());
                }
            }
        });
        towerPosition = NextButtonPosition.nextTowerPosition(towerPosition, 60, 0);

        addWorkerButton = new Button(plusTexture, towerPosition, 20, new Action() {
            @Override
            public void run() {
                if (GameLogic.getWorkers() > 0) {
                    location.addWorkers(1);
                    workerRenderer.setValue(location.getWorkers());
                }
            }
        });
        towerPosition = NextButtonPosition.nextTowerPosition(towerPosition, -30, 0);

        workerRenderer = new GlyphRenderer(towerPosition, 0.5f, GlyphRenderer.Type.WORKERS, location.getWorkers());

        Events.AddListener(this);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        upgradeButton.render(batch);
        removeButton.render(batch);
        addWorkerButton.render(batch);
        removeWorkerButton.render(batch);
        workerRenderer.render(batch);
    }

    @Override
    public boolean onTouchDown(ScreenPosition position, int pointer) {
        if (!IsInside(position.ToMenuPosition())) {
            dispose();
            return true;
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
        upgradeButton.dispose();
        removeButton.dispose();
        addWorkerButton.dispose();
        removeWorkerButton.dispose();
        workerRenderer.dispose();
    }
}
