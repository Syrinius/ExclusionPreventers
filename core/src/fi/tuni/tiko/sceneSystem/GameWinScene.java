package fi.tuni.tiko.sceneSystem;

import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.hud.Button;
import fi.tuni.tiko.hud.GlyphRenderer;
import fi.tuni.tiko.hud.HudSprite;
import fi.tuni.tiko.map.MapManager;
import fi.tuni.tiko.utilities.Action;
import fi.tuni.tiko.utilities.NextButtonPosition;

public class GameWinScene extends Scene {
    private static final Texture menuBackground = new Texture("menu/menu_background.png");
    HudSprite background;
    private final Button nextLevelButton;
    Texture[] nextLevelTexture = new Texture[] {
            new Texture("menu/nextl.png"),
            new Texture("menu/seuraavat.png")
    };
    private final Button backToMenuButton;
    Texture[] backToMenuTexture = new Texture[] {
            new Texture("menu/menu.png"),
            new Texture("menu/valikko.png")
    };

    public GameWinScene() {
        MenuPosition currentPosition = new MenuPosition(200, 160);
        float gapX = 0;
        float gapY = -40;

        background = new HudSprite(menuBackground, MenuPosition.CENTER, MenuPosition.WIDTH);
        hudElementManager.AddHudElement(background);


        nextLevelButton = new Button(nextLevelTexture[GameLogic.getLanguage().ordinal()], currentPosition, 100,
                new Action() {
                    @Override
                    public void run() {
                        MapManager.selectMap(MapManager.getIndex(MapManager.getSelectedMap()) + 1);
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(nextLevelButton);
        currentPosition = NextButtonPosition.nextMenuPosition(currentPosition, gapX, gapY);

        backToMenuButton = new Button(backToMenuTexture[GameLogic.getLanguage().ordinal()], currentPosition, 100,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.MAIN_MENU);
                    }
                });
        hudElementManager.AddHudElement(backToMenuButton);
        currentPosition = NextButtonPosition.nextMenuPosition(currentPosition, gapX, gapY);
        GlyphRenderer pointRenderer = new GlyphRenderer(currentPosition, 1, GlyphRenderer.Type.SCORE, GameLogic.getScore());
        hudElementManager.AddHudElement(pointRenderer);
    }
}
