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

    private static final Texture menuBackground = new Texture("menu/menu_background.png");

    HudSprite background;
    Button backButton;
    Button map1Button, map2Button, map3Button, map4Button, map5Button, map6Button, map7Button, map8Button, map9Button, map10Button;
    MenuPosition mapButtonPosition = new MenuPosition(60, 150);
    float gapX = 70;
    float gapY = 0;


    public MapSelectionMenu() {
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

        map1Button = new Button(new Texture("menu/levels/l1.png"), mapButtonPosition, 50,
                new Action() {
                    @Override
                    public void run() {
                        MapManager.selectMap(0);
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(map1Button);

        mapButtonPosition = NextButtonPosition.nextLevelPosition(mapButtonPosition, gapX, gapY);
        map2Button = new Button(new Texture("menu/levels/l2.png"), mapButtonPosition, 50,
                new Action() {
                    @Override
                    public void run() {
                        MapManager.selectMap(0);
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(map2Button);

        mapButtonPosition = NextButtonPosition.nextLevelPosition(mapButtonPosition, gapX, gapY);
        map3Button = new Button(new Texture("menu/levels/l3.png"), mapButtonPosition, 50,
                new Action() {
                    @Override
                    public void run() {
                        MapManager.selectMap(0);
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(map3Button);

        mapButtonPosition = NextButtonPosition.nextLevelPosition(mapButtonPosition, gapX, gapY);
        map4Button = new Button(new Texture("menu/levels/l4.png"), mapButtonPosition, 50,
                new Action() {
                    @Override
                    public void run() {
                        MapManager.selectMap(0);
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(map4Button);

        mapButtonPosition = NextButtonPosition.nextLevelPosition(mapButtonPosition, gapX, gapY);
        map5Button = new Button(new Texture("menu/levels/l5.png"), mapButtonPosition, 50,
                new Action() {
                    @Override
                    public void run() {
                        MapManager.selectMap(0);
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(map5Button);

        mapButtonPosition = NextButtonPosition.nextLevelPosition(mapButtonPosition, gapX, gapY);
        map6Button = new Button(new Texture("menu/levels/l6.png"), mapButtonPosition, 50,
                new Action() {
                    @Override
                    public void run() {
                        MapManager.selectMap(0);
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(map6Button);

        mapButtonPosition = NextButtonPosition.nextLevelPosition(mapButtonPosition, gapX, gapY);
        map7Button = new Button(new Texture("menu/levels/l7.png"), mapButtonPosition, 50,
                new Action() {
                    @Override
                    public void run() {
                        MapManager.selectMap(0);
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(map7Button);

        mapButtonPosition = NextButtonPosition.nextLevelPosition(mapButtonPosition, gapX, gapY);
        map8Button = new Button(new Texture("menu/levels/l8.png"), mapButtonPosition, 50,
                new Action() {
                    @Override
                    public void run() {
                        MapManager.selectMap(0);
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(map8Button);

        mapButtonPosition = NextButtonPosition.nextLevelPosition(mapButtonPosition, gapX, gapY);
        map9Button = new Button(new Texture("menu/levels/l9.png"), mapButtonPosition, 50,
                new Action() {
                    @Override
                    public void run() {
                        MapManager.selectMap(0);
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(map9Button);

        mapButtonPosition = NextButtonPosition.nextLevelPosition(mapButtonPosition, gapX, gapY);
        map10Button = new Button(new Texture("menu/levels/l10.png"), mapButtonPosition, 50,
                new Action() {
                    @Override
                    public void run() {
                        MapManager.selectMap(0);
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(map10Button);
    }
}
