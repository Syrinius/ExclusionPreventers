package fi.tuni.tiko.sceneSystem;

import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.Action;
import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.MapManager;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.hud.Button;

public class MapSelectionMenu extends Scene {

    Button backButton;
    Button map1Button;

    public MapSelectionMenu() {
        backButton = new Button(new Texture("back_button.png"), new MenuPosition(200, 50), 50,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.MAIN_MENU);
                    }
                });
        hudElementManager.AddHudElement(backButton);

        map1Button = new Button(new Texture("map_1_button.png"), new MenuPosition(50, 150), 50,
                new Action() {
                    @Override
                    public void run() {
                        MapManager.selectMap(0);
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(map1Button);
    }
}
