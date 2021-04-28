package fi.tuni.tiko.sceneSystem;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.hud.Button;
import fi.tuni.tiko.hud.HudSprite;
import fi.tuni.tiko.utilities.Action;

public class HelpScene extends Scene {

    private static final Texture menuBackground = new Texture("menu/menu_background.png");
    public static final ArrayList<Texture> helpImages = new ArrayList<>();
    public static final ArrayList<Texture> helpImagesFin = new ArrayList<>();
    static {
        helpImages.add(new Texture("menu/wave_cleared_background.png"));
        helpImages.add(new Texture("menu/menu_background.png"));
    }

    static {
        helpImagesFin.add(new Texture("menu/wave_cleared_background.png"));
        helpImagesFin.add(new Texture("menu/menu_background.png"));
    }

    private int page = 0;

    public static int getHelpImagesLastIndex() {
        return helpImages.size() - 1;
    }

    public static Texture getPage(int pageNumber) {
        return GameLogic.getLanguage() == GameLogic.Language.ENGLISH ? helpImages.get(pageNumber) : helpImagesFin.get(pageNumber);
    }

    public HelpScene() {
        HudSprite background = new HudSprite(menuBackground, MenuPosition.CENTER, MenuPosition.WIDTH);
        hudElementManager.AddHudElement(background);

        final HudSprite helpSprite = new HudSprite(getPage(page), MenuPosition.CENTER, 300);
        hudElementManager.AddHudElement(helpSprite);

        Button backButton = new Button(new Texture("menu/back.png"), new MenuPosition(MenuPosition.WIDTH / 2, 30), 50, new Action() {
            @Override
            public void run() {
                GameLogic.SetState(GameLogic.GameState.MAIN_MENU);
            }
        });
        hudElementManager.AddHudElement(backButton);

        Button leftButton = new Button(new Texture("menu/left.png"), new MenuPosition(40, 30), 30, new Action() {
            @Override
            public void run() {
                if (page != 0) helpSprite.setTexture(getPage(--page));
            }
        });
        hudElementManager.AddHudElement(leftButton);

        Button rightButton = new Button(new Texture("menu/right.png"), new MenuPosition(MenuPosition.WIDTH - 40, 30), 30, new Action() {
            @Override
            public void run() {
                if (page != getHelpImagesLastIndex()) helpSprite.setTexture(getPage(++page));
            }
        });
        hudElementManager.AddHudElement(rightButton);
    }
}
