package fi.tuni.tiko.sceneSystem;

import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.hud.HudSprite;
import fi.tuni.tiko.utilities.Action;
import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.map.MapManager;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.hud.Button;
import fi.tuni.tiko.utilities.NextButtonPosition;

/**
 * Specific implementation of the map selection screen and all the elements it contains
 */
public class MapSelectionMenu extends Scene {

    private static final Texture menuBackground = new Texture("menu_background.png");

    HudSprite background;
    Button backButton;
    Button map1Button;
    Button map2Button;


    public MapSelectionMenu() {
        background = new HudSprite(menuBackground, MenuPosition.CENTER, MenuPosition.WIDTH);
        hudElementManager.AddHudElement(background);

        MenuPosition currentPosition = new MenuPosition(200, 50);
        backButton = new Button(new Texture("back_button.png"),currentPosition, 50,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.MAIN_MENU);
                    }
                });
        hudElementManager.AddHudElement(backButton);
        MenuPosition mapButtonPosition = new MenuPosition(50, 150);
        map1Button = new Button(new Texture("menu/levels/l1.png"), mapButtonPosition, 50,
                new Action() {
                    @Override
                    public void run() {
                        MapManager.selectMap(0);
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(map1Button);
        mapButtonPosition = NextButtonPosition.nextLevelPosition(mapButtonPosition);
        map2Button = new Button(new Texture("menu/levels/l2.png"), mapButtonPosition, 50,
                new Action() {
                    @Override
                    public void run() {
                        MapManager.selectMap(0);
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(map2Button);
    }
}
