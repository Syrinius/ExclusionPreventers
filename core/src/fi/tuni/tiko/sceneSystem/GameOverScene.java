package fi.tuni.tiko.sceneSystem;

import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.hud.Button;
import fi.tuni.tiko.hud.GlyphRenderer;
import fi.tuni.tiko.hud.HudSprite;
import fi.tuni.tiko.utilities.Action;
import fi.tuni.tiko.utilities.NextButtonPosition;

public class GameOverScene extends Scene {

    private static final Texture menuBackground = new Texture("menu/menu_background.png");
    HudSprite background;
    private final Button playAgainButton;
    Texture[] playAgainTexture = new Texture[] {
            new Texture("menu/playagain.png"),
            new Texture("menu/pelaauud.png")
    };
    private final Button backToMenuButton;
    Texture[] backToMenuTexture = new Texture[] {
            new Texture("menu/menu.png"),
            new Texture("menu/valikko.png")
    };

    public GameOverScene() {
        MenuPosition currentPosition = new MenuPosition(200, 160);
        float gapX = 0;
        float gapY = -40;

        background = new HudSprite(menuBackground, MenuPosition.CENTER, MenuPosition.WIDTH);
        hudElementManager.AddHudElement(background);


        playAgainButton = new Button(playAgainTexture[GameLogic.getLanguage().ordinal()], currentPosition, 100,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.MAP_LOADING_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(playAgainButton);
        currentPosition = NextButtonPosition.nextMenuPosition(currentPosition, gapX, gapY);

        backToMenuButton = new Button(backToMenuTexture[GameLogic.getLanguage().ordinal()], currentPosition, 100,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.MAIN_MENU);
                    }
                });
        hudElementManager.AddHudElement(backToMenuButton);
        currentPosition = NextButtonPosition.nextMenuPosition(currentPosition, -20, -50);
        GlyphRenderer pointRenderer = new GlyphRenderer(currentPosition, 1, GlyphRenderer.Type.SCORE, GameLogic.getScore());
        hudElementManager.AddHudElement(pointRenderer);
    }
}
