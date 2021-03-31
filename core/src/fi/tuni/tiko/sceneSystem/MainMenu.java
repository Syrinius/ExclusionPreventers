package fi.tuni.tiko.sceneSystem;

import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.hud.HudSprite;
import fi.tuni.tiko.utilities.Action;
import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.hud.Button;
import fi.tuni.tiko.utilities.NextButtonPosition;

/**
 * Specific implementation of the main menu screen and all the elements it contains
 */
public class MainMenu extends Scene {

    private static final Texture menuBackground = new Texture("menu/menu_background.png");

    Button playButton;
    Button helpButton;
    Button settingButton;
    Button creditButton;
    HudSprite background;
    MenuPosition currentPosition = new MenuPosition(200, 160);
    float gapX = 0;
    float gapY = -40;


    public MainMenu() {
        background = new HudSprite(menuBackground, MenuPosition.CENTER, MenuPosition.WIDTH);
        hudElementManager.AddHudElement(background);


        playButton = new Button(new Texture("menu/play.png"), currentPosition, 100,
            new Action() {
                @Override
                public void run() {
                    GameLogic.SetState(GameLogic.GameState.MAP_SELECTION_SCREEN);
                }
        });
        hudElementManager.AddHudElement(playButton);
        currentPosition = NextButtonPosition.nextMenuPosition(currentPosition, gapX, gapY);

        helpButton = new Button(new Texture("menu/help.png"), currentPosition, 100,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.MAP_SELECTION_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(helpButton);
        currentPosition = NextButtonPosition.nextMenuPosition(currentPosition, gapX, gapY);

        settingButton = new Button(new Texture("menu/settings.png"), currentPosition, 100,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.MAP_SELECTION_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(settingButton);
        currentPosition = NextButtonPosition.nextMenuPosition(currentPosition, gapX, gapY);

        creditButton = new Button(new Texture("menu/credits.png"), currentPosition, 100,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.MAP_SELECTION_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(creditButton);
    }
}