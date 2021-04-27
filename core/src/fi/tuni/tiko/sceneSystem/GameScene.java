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

import static fi.tuni.tiko.sceneSystem.MainMenu.menuMusic;

/**
 * Implementation for map Scenes
 */
public class GameScene extends Scene implements GameLogicListener {

    static final Texture resumeTexture = new Texture("menu/playbutton.png");
    static final Texture pauseTexture = new Texture("menu/pause.png");

    static final Texture normalSpeedTexture = new Texture("menu/1x.png");
    static final Texture fastSpeedTexture = new Texture("menu/2x.png");

    private final Button playPauseButton;
    private final Button speedChangeButton;
    private final GlyphRenderer funds;
    private final GlyphRenderer lives;
    private final GlyphRenderer workers;
    private final GlyphRenderer score;

    public GameScene() {
        funds = new GlyphRenderer(new MenuPosition(MenuPosition.WIDTH/2, 180), .5f, GlyphRenderer.Type.FUNDS, GameLogic.getFunds());
        hudElementManager.AddHudElement(funds);
        lives = new GlyphRenderer(new MenuPosition(10, 180), .5f, GlyphRenderer.Type.LIVES, GameLogic.getLives());
        hudElementManager.AddHudElement(lives);
        workers = new GlyphRenderer(new MenuPosition(MenuPosition.WIDTH/3, 180), .5f, GlyphRenderer.Type.WORKERS, GameLogic.getWorkers());
        hudElementManager.AddHudElement(workers);
        score = new GlyphRenderer(new MenuPosition(MenuPosition.WIDTH/6, 180), .5f, GlyphRenderer.Type.SCORE, GameLogic.getScore());
        hudElementManager.AddHudElement(score);
        menuMusic.pause();

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

        speedChangeButton = new Button(fastSpeedTexture, new MenuPosition(MenuPosition.WIDTH - 60, MenuPosition.HEIGHT - 25), 25, new Action() {
            @Override
            public void run() {
                Map map = MapManager.getSelectedMap();
                if (GameLogic.getCurrentSpeedMultiplier() != 1) {
                    speedChangeButton.setTexture(fastSpeedTexture);
                    GameLogic.setCurrentSpeedMultiplier(1);
                } else {
                    speedChangeButton.setTexture(normalSpeedTexture);
                    GameLogic.setCurrentSpeedMultiplier(12);
                }
            }
        });
        hudElementManager.AddHudElement(speedChangeButton);
    }

    @Override
    void render() {
        MapManager.render();
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        GameLogic.RemoveListener(this);
        playPauseButton.dispose();
        speedChangeButton.dispose();
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

    @Override
    public void onWorkersChanged(int newValue) {
        workers.setValue(newValue);
    }

    @Override
    public void onScoreChanged(int newValue) {
        score.setValue(newValue);
    }
}