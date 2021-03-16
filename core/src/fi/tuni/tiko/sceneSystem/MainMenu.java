package fi.tuni.tiko.sceneSystem;

import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.Action;
import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.MainGame;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.eventSystem.Events;
import fi.tuni.tiko.hud.Button;
import fi.tuni.tiko.sceneSystem.Scene;
import fi.tuni.tiko.utilities.NextButtonPosition;

/**
 * Specific implementation of the main menu screen and all the elements it contains
 */
public class MainMenu extends Scene {
    MenuPosition currentPosition = new MenuPosition(200, 150);
    Button playButton;
    Button SettingButton;

    public MainMenu() {
        playButton = new Button(new Texture("play_button.png"), currentPosition, 100,
            new Action() {
                @Override
                public void run() {
                    GameLogic.SetState(GameLogic.GameState.MAP_SELECTION_SCREEN);
                }
        });
        hudElementManager.AddHudElement(playButton);
        currentPosition = NextButtonPosition.nextMenuPosition(currentPosition);
        SettingButton = new Button(new Texture("play_button.png"), currentPosition, 100,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.MAP_SELECTION_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(SettingButton);
    }
}