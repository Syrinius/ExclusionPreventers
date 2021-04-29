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
        helpImages.add(new Texture("menu/help/eng/general.png"));
        helpImages.add(new Texture("menu/help/eng/help1.png"));
        helpImages.add(new Texture("menu/help/eng/help2.png"));
        helpImages.add(new Texture("menu/help/eng/help3.png"));
        helpImages.add(new Texture("menu/help/eng/help4.png"));
        helpImages.add(new Texture("menu/help/eng/student_guide.png"));
    }

    static {
        helpImagesFin.add(new Texture("menu/help/suomi/yleiset_ohjeet.png"));
        helpImagesFin.add(new Texture("menu/help/suomi/torni_ohje1.png"));
        helpImagesFin.add(new Texture("menu/help/suomi/torni_ohje2.png"));
        helpImagesFin.add(new Texture("menu/help/suomi/torni_ohje3.png"));
        helpImagesFin.add(new Texture("menu/help/suomi/ohje4.png"));
        helpImagesFin.add(new Texture("menu/help/suomi/oppilas_ohjeet.png"));
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

        final HudSprite helpSprite = new HudSprite(getPage(page), MenuPosition.CENTER, 250);
        hudElementManager.AddHudElement(helpSprite);

        Button backButton = new Button(new Texture("menu/back.png"), new MenuPosition(MenuPosition.WIDTH / 2, 20), 50, new Action() {
            @Override
            public void run() {
                GameLogic.SetState(GameLogic.GameState.MAIN_MENU);
            }
        });
        hudElementManager.AddHudElement(backButton);

        Button leftButton = new Button(new Texture("menu/left.png"), new MenuPosition(40, 20), 30, new Action() {
            @Override
            public void run() {
                if (page != 0) helpSprite.setTexture(getPage(--page));
            }
        });
        hudElementManager.AddHudElement(leftButton);

        Button rightButton = new Button(new Texture("menu/right.png"), new MenuPosition(MenuPosition.WIDTH - 40, 20), 30, new Action() {
            @Override
            public void run() {
                if (page != getHelpImagesLastIndex()) helpSprite.setTexture(getPage(++page));
            }
        });
        hudElementManager.AddHudElement(rightButton);
    }
}
