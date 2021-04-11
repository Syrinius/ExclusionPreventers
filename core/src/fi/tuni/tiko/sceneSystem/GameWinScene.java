package fi.tuni.tiko.sceneSystem;

import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.hud.Button;
import fi.tuni.tiko.hud.HudSprite;
import fi.tuni.tiko.map.MapManager;
import fi.tuni.tiko.utilities.Action;
import fi.tuni.tiko.utilities.NextButtonPosition;

public class GameWinScene extends Scene {
    private static final Texture menuBackground = new Texture("menu/menu_background.png");
    HudSprite background;
    private final Button nextLevelButton;
    private final Button backToMenuButton;

    public GameWinScene() {
        MenuPosition currentPosition = new MenuPosition(200, 160);
        float gapX = 0;
        float gapY = -40;

        background = new HudSprite(menuBackground, MenuPosition.CENTER, MenuPosition.WIDTH);
        hudElementManager.AddHudElement(background);


        nextLevelButton = new Button(new Texture("menu/nextl.png"), currentPosition, 100,
                new Action() {
                    @Override
                    public void run() {
                        MapManager.selectMap(1);
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(nextLevelButton);
        currentPosition = NextButtonPosition.nextMenuPosition(currentPosition, gapX, gapY);

        backToMenuButton = new Button(new Texture("menu/menu.png"), currentPosition, 100,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.MAIN_MENU);
                    }
                });
        hudElementManager.AddHudElement(backToMenuButton);
        currentPosition = NextButtonPosition.nextMenuPosition(currentPosition, gapX, gapY);
    }
}
