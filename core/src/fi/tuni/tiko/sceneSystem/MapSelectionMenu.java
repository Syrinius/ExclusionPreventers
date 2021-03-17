package fi.tuni.tiko.sceneSystem;

import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.Action;
import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.MapManager;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.hud.Button;
import fi.tuni.tiko.utilities.NextButtonPosition;

/**
 * Specific implementation of the map selection screen and all the elements it contains
 */
public class MapSelectionMenu extends Scene {
    MenuPosition currentPosition = new MenuPosition(200, 50);
    MenuPosition mapButtonPosition = new MenuPosition(50, 150);
    Button backButton;
    Button map1Button;
    Button map2Button;


    public MapSelectionMenu() {
        backButton = new Button(new Texture("back_button.png"),currentPosition, 50,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.MAIN_MENU);
                    }
                });
        hudElementManager.AddHudElement(backButton);

        map1Button = new Button(new Texture("map_1_button.png"), mapButtonPosition, 50,
                new Action() {
                    @Override
                    public void run() {
                        MapManager.selectMap(0);
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(map1Button);
        mapButtonPosition = NextButtonPosition.nextMenuPosition(mapButtonPosition);
        map2Button = new Button(new Texture("map_1_button.png"), mapButtonPosition, 50,
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
