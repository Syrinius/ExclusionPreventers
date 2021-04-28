package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.eventSystem.Events;
import fi.tuni.tiko.eventSystem.TouchListener;
import fi.tuni.tiko.gameObject.tower.TowerLocation;
import fi.tuni.tiko.sceneSystem.HelpScene;
import fi.tuni.tiko.utilities.Action;

public class HelpPopOutMenu extends HudSprite implements TouchListener, PopOut{

    private static final Texture backgroundTexture = new Texture("menu/wave_cleared_background.png");
    private static final Texture backTexture = new Texture("menu/back.png");
    private static final Texture leftTexture = new Texture("menu/left.png");
    private static final Texture rightTexture = new Texture("menu/right.png");
    private static final float BACKGROUND_SIZE = 250;
    private final HudElementManager manager;
    private final Button backButton;
    private final Button leftButton;
    private final Button rightButton;
    private final HudSprite helpSprite;
    private int page = 0;

    public static HelpPopOutMenu GetInstance(HudElementManager manager) {
        HelpPopOutMenu toReturn = new HelpPopOutMenu(manager);
        manager.AddHudElement(toReturn);
        manager.SetPopOut(toReturn);
        return toReturn;
    }

    private HelpPopOutMenu(final HudElementManager manager) {
        super(backgroundTexture, new MenuPosition(200, 100), BACKGROUND_SIZE);
        Events.AddListener(this);
        this.manager = manager;
        helpSprite = new HudSprite(HelpScene.getPage(page), MenuPosition.CENTER, 200);

        backButton = new Button(backTexture, new MenuPosition(200, 25), 30, new Action() {
            @Override
            public void run() {
                manager.SetPopOut(null);
            }
        }, Events.Priority.HIGH);

        leftButton = new Button(leftTexture, new MenuPosition(100, 25), 20, new Action() {
            @Override
            public void run() {
                if (page != 0) helpSprite.setTexture(HelpScene.getPage(--page));
            }
        }, Events.Priority.HIGH);

        rightButton = new Button(rightTexture, new MenuPosition(300, 25), 20, new Action() {
            @Override
            public void run() {
                if (page != HelpScene.getHelpImagesLastIndex()) helpSprite.setTexture(HelpScene.getPage(++page));
            }
        }, Events.Priority.HIGH);
    }

    @Override
    public boolean onTouchDown(ScreenPosition position, int pointer) {
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
    public void render(SpriteBatch batch) {
        super.render(batch);
        helpSprite.render(batch);
        backButton.render(batch);
        leftButton.render(batch);
        rightButton.render(batch);
    }

    @Override
    public void dispose() {
        manager.RemoveHudElement(this);
        Events.RemoveListener(this);
        backButton.dispose();
        leftButton.dispose();
        rightButton.dispose();
        helpSprite.dispose();
    }

    @Override
    public void close() {
        dispose();
    }
}
