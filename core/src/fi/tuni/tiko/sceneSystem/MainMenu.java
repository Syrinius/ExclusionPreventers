package fi.tuni.tiko.sceneSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.hud.HudSprite;
import fi.tuni.tiko.utilities.Action;
import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.hud.Button;
import fi.tuni.tiko.utilities.NextButtonPosition;

/**
 * Specific implementation of the main menu screen and all the elements it contains
 */
public class MainMenu extends Scene {

    private static final Texture menuBackground = new Texture("menu/menu_background.png");

    Button playButton;
    Texture[] playTexture = new Texture[] {
            new Texture("menu/play.png"),
            new Texture("menu/pelaa.png")
    };
    Button helpButton;
    Texture[] helpTexture = new Texture[] {
            new Texture("menu/help.png"),
            new Texture("menu/ohjeet.png")
    };
    Button settingButton;
    Texture[] settingTexture = new Texture[] {
            new Texture("menu/settings.png"),
            new Texture("menu/asetukset.png")
    };
    Button creditButton;
    Texture[] creditTexture = new Texture[] {
            new Texture("menu/credits.png"),
            new Texture("menu/credits.png")
    };
    Button englishButton;
    Button finnishButton;
    HudSprite background;
    MenuPosition currentPosition = new MenuPosition(200, 160);
    float gapX = 0;
    float gapY = -40;
    static final Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menusong.wav"));


    public MainMenu() {
        background = new HudSprite(menuBackground, MenuPosition.CENTER, MenuPosition.WIDTH);
        hudElementManager.AddHudElement(background);
        menuMusic.play();
        menuMusic.setLooping(true);
        //menuMusic.pause();

        playButton = new Button(playTexture[GameLogic.getLanguage().ordinal()], currentPosition, 100,
            new Action() {
                @Override
                public void run() {
                    GameLogic.SetState(GameLogic.GameState.MAP_SELECTION_SCREEN);
                }
        });
        hudElementManager.AddHudElement(playButton);
        currentPosition = NextButtonPosition.nextMenuPosition(currentPosition, gapX, gapY);

        helpButton = new Button(helpTexture[GameLogic.getLanguage().ordinal()], currentPosition, 100,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.MAP_SELECTION_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(helpButton);
        currentPosition = NextButtonPosition.nextMenuPosition(currentPosition, gapX, gapY);

        settingButton = new Button(settingTexture[GameLogic.getLanguage().ordinal()], currentPosition, 100,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.SETTINGS_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(settingButton);
        currentPosition = NextButtonPosition.nextMenuPosition(currentPosition, gapX, gapY);

        creditButton = new Button(creditTexture[GameLogic.getLanguage().ordinal()], currentPosition, 100,
                new Action() {
                    @Override
                    public void run() {
                        GameLogic.SetState(GameLogic.GameState.MAP_SELECTION_SCREEN);
                    }
                });
        hudElementManager.AddHudElement(creditButton);
        englishButton = new Button(new Texture("menu/ukflag.png"), new MenuPosition(MenuPosition.WIDTH - 25, 25), 25, new Action() {
            @Override
            public void run() {
                GameLogic.setLanguage(GameLogic.Language.ENGLISH);
                GameLogic.SetState(GameLogic.GameState.MAIN_MENU);
            }
        });
        hudElementManager.AddHudElement(englishButton);
        finnishButton = new Button(new Texture("menu/fin.png"), new MenuPosition(MenuPosition.WIDTH - 60, 25), 25, new Action() {
            @Override
            public void run() {
                GameLogic.setLanguage(GameLogic.Language.FINNISH);
                GameLogic.SetState(GameLogic.GameState.MAIN_MENU);
            }
        });
        hudElementManager.AddHudElement(finnishButton);
    }
}