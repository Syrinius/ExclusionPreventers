package fi.tuni.tiko.sceneSystem;

import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.hud.Button;
import fi.tuni.tiko.hud.HudSprite;
import fi.tuni.tiko.utilities.Action;

/**
 * Specific implementation of the map selection screen and all the elements it contains
 */
public class SettingScene extends Scene {

    private static final Texture menuBackground = new Texture("menu/menu_background.png");

    fi.tuni.tiko.hud.HudSprite background;
    fi.tuni.tiko.hud.Button backButton;


    public SettingScene() {
        background = new HudSprite(menuBackground, MenuPosition.CENTER, MenuPosition.WIDTH);
        hudElementManager.AddHudElement(background);

        MenuPosition currentPosition = new MenuPosition(200, 50);
        backButton = new Button(new Texture("menu/back.png"),currentPosition, 50,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.MAIN_MENU);
                    }
                });
        hudElementManager.AddHudElement(backButton);


    }
}
