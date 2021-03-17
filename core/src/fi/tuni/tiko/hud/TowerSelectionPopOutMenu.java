package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.Texture;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.eventSystem.Events;
import fi.tuni.tiko.eventSystem.TouchListener;

public class TowerSelectionPopOutMenu extends HudSprite implements TouchListener {

    MenuPosition currentPosition;
    private static final Texture backgroundTexture = new Texture("tower_selection_background.png");
    private static final float backgroundSize = 100;

    public TowerSelectionPopOutMenu(MenuPosition position) {
        super(backgroundTexture, position, backgroundSize);
    }

    @Override
    public boolean onTouchDown(ScreenPosition position, int pointer) {
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

    }
}
