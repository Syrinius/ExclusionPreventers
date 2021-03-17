package fi.tuni.tiko.sceneSystem;

import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.hud.HudSprite;
import fi.tuni.tiko.utilities.Action;
import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.hud.Button;

/**
 * Specific implementation of the main menu screen and all the elements it contains
 */
public class MainMenu extends Scene {

    private static final Texture menuBackground = new Texture("menu_background.png");

    Button playButton;
    Button SettingButton;
    HudSprite background;


    public MainMenu() {
        background = new HudSprite(menuBackground, MenuPosition.CENTER, MenuPosition.WIDTH);
        hudElementManager.AddHudElement(background);

        MenuPosition currentPosition = new MenuPosition(200, 150);
        playButton = new Button(new Texture("play_button.png"), currentPosition, 100,
            new Action() {
                @Override
                public void run() {
                    GameLogic.SetState(GameLogic.GameState.MAP_SELECTION_SCREEN);
                }
        });
        hudElementManager.AddHudElement(playButton);
        /*currentPosition = NextButtonPosition.nextMenuPosition(currentPosition);

        SettingButton = new Button(new Texture("play_button.png"), currentPosition, 100,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.MAP_SELECTION_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(SettingButton);*/
    }
}