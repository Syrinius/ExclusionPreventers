package fi.tuni.tiko.sceneSystem;


import com.badlogic.gdx.graphics.Texture;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.GameLogicListener;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.hud.Button;
import fi.tuni.tiko.hud.GlyphRenderer;
import fi.tuni.tiko.map.Map;
import fi.tuni.tiko.map.MapManager;
import fi.tuni.tiko.utilities.Action;

/**
 * Implementation for map Scenes
 */
public class GameScene extends Scene implements GameLogicListener {

    static final Texture resumeTexture = new Texture("menu/playbutton.png");
    static final Texture pauseTexture = new Texture("menu/pause.png");

    private final Button playPauseButton;
    private final GlyphRenderer funds;
    private final GlyphRenderer lives;

    public GameScene() {
        funds = new GlyphRenderer(new MenuPosition(MenuPosition.WIDTH/2, 180), .5f, GlyphRenderer.Type.FUNDS, GameLogic.getFunds());
        hudElementManager.AddHudElement(funds);
        lives = new GlyphRenderer(new MenuPosition(10, 180), .5f, GlyphRenderer.Type.LIVES, GameLogic.getLives());
        hudElementManager.AddHudElement(lives);

        GameLogic.AddListener(this);
        playPauseButton = new Button(resumeTexture, new MenuPosition(MenuPosition.WIDTH - 25, MenuPosition.HEIGHT - 25), 25, new Action() {
            @Override
            public void run() {
                Map map = MapManager.getSelectedMap();
                if (GameLogic.isPaused()) {
                    playPauseButton.setTexture(pauseTexture);
                    GameLogic.Resume();
                } else {
                    playPauseButton.setTexture(resumeTexture);
                    GameLogic.Pause();
                }
            }
        });
        hudElementManager.AddHudElement(playPauseButton);
    }

    @Override
    void render() {
        MapManager.render();
        super.render();
    }

    @Override
    public void onPause() {
        playPauseButton.setTexture(resumeTexture);
    }

    @Override
    public void onResume() {
        playPauseButton.setTexture(pauseTexture);
    }

    @Override
    public void onLivesChanged(int newValue) {
        lives.setValue(newValue);
    }

    @Override
    public void onFundsChanged(int newValue) {
        funds.setValue(newValue);
    }
}