package fi.tuni.tiko.sceneSystem;

import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.hud.GlyphRenderer;
import fi.tuni.tiko.hud.Button;
import fi.tuni.tiko.hud.HudSprite;
import fi.tuni.tiko.utilities.Action;
import fi.tuni.tiko.utilities.NextButtonPosition;

public class FinalScene extends Scene {

    private static final Texture menuBackground = new Texture("menu/menu_background.png");
    fi.tuni.tiko.hud.HudSprite background;
    private final fi.tuni.tiko.hud.Button backToMenuButton;

    Texture[] backToMenuTexture = new Texture[] {
            new Texture("menu/menu.png"),
            new Texture("menu/valikko.png")
    };

    public FinalScene() {
        MenuPosition currentPosition = new MenuPosition(200, 160);
        float gapX = 0;
        float gapY = -40;

        background = new HudSprite(menuBackground, MenuPosition.CENTER, MenuPosition.WIDTH);
        hudElementManager.AddHudElement(background);

        backToMenuButton = new Button(backToMenuTexture[GameLogic.getLanguage().ordinal()], currentPosition, 100,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.MAIN_MENU);
                    }
                });
        hudElementManager.AddHudElement(backToMenuButton);
        currentPosition = NextButtonPosition.nextMenuPosition(currentPosition, -20, -50);
        GlyphRenderer pointRenderer = new GlyphRenderer(currentPosition, 1, GlyphRenderer.Type.SCORE, fi.tuni.tiko.GameLogic.getScore());
        hudElementManager.AddHudElement(pointRenderer);
    }


}