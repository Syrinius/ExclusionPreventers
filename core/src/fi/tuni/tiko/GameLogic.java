package fi.tuni.tiko;

import java.util.ArrayList;

import fi.tuni.tiko.sceneSystem.SceneManager;
import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.map.MapManager;
import fi.tuni.tiko.sceneSystem.FinalScene;
import fi.tuni.tiko.sceneSystem.GameOverScene;
import fi.tuni.tiko.sceneSystem.GameWinScene;
import fi.tuni.tiko.sceneSystem.HelpScene;
import fi.tuni.tiko.eventSystem.Events;
import fi.tuni.tiko.sceneSystem.GameScene;
import fi.tuni.tiko.sceneSystem.MainMenu;
import fi.tuni.tiko.sceneSystem.MapLoadingScreen;
import fi.tuni.tiko.sceneSystem.MapSelectionMenu;
import fi.tuni.tiko.sceneSystem.SettingScene;
import fi.tuni.tiko.utilities.Action;

/**
 * Class for handling changes of game state
 * Dispatcher of StateChanged events to classes that need to listen to that
 * All elements that need to listen to game state changes should subscribe to gameLogicListeners list
 */
public class GameLogic {

    /**
     * Enum to trigger correct events based on what state the game is currently in
     * for instance clicking the settings button in main menu will change change the game state to SETTINGS_SCREEN
     * and will trigger all the events needed for the settings screen
     */
    public enum GameState {
        SPLASH_SCREEN, MAIN_MENU, MAP_SELECTION_SCREEN, SETTINGS_SCREEN,
        MAP_LOADING_SCREEN, GAME_SCREEN, LOSE_SCREEN, WIN_SCREEN, HELP_SCREEN
    }

    public enum Language {
        ENGLISH, FINNISH
    }

    private static final ArrayList<GameLogicListener> gameLogicListeners = new ArrayList<>();
    private static final ArrayList<GameLogicListener> toAdd = new ArrayList<>();
    private static final ArrayList<GameLogicListener> toRemove = new ArrayList<>();
    private static Language language = Language.ENGLISH;
    private static GameState gameState = GameState.SPLASH_SCREEN;
    private static boolean lock;
    private static boolean paused = true;
    private static int currentSpeedMultiplier = 1;
    private static int funds;
    private static int lives;
    private static int workers;
    private static int score;


    public static Language getLanguage() {
        return language;
    }

    public static void setLanguage(Language language) {
        GameLogic.language = language;
    }

    public static int getCurrentSpeedMultiplier() {
        return currentSpeedMultiplier;
    }

    public static void setCurrentSpeedMultiplier(int value) {
        currentSpeedMultiplier = value;
    }


    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        updateListeners();
        GameLogic.score = score;
        lock = true;
        for (GameLogicListener listener :
                gameLogicListeners) {
            listener.onScoreChanged(score);
        }
        lock = false;
    }

    public static void addScore(int toAdd) {
        setScore(score + toAdd);
    }

    public static int getLives() {
        return lives;
    }

    public static void addLives(int toAdd) {
        setLives(lives + toAdd);
    }

    public static void setLives(int lives) {
        updateListeners();
        GameLogic.lives = lives;
        lock = true;
        for (GameLogicListener listener :
                gameLogicListeners) {
            listener.onLivesChanged(lives);
        }
        lock = false;
        if (lives == 0) SetState(GameState.LOSE_SCREEN);
    }

    public static int getWorkers() {
        return workers;
    }

    public static void addWorkers(int toAdd) {
        setWorkers(workers + toAdd);
    }

    public static void setWorkers(int workers) {
        updateListeners();
        GameLogic.workers = workers;
        lock = true;
        for (GameLogicListener listener :
                gameLogicListeners) {
            listener.onWorkersChanged(workers);
        }
        lock = false;
    }

    public static int getFunds() {
        return funds;
    }

    public static void addFunds(int toAdd) {
        setFunds(funds + toAdd);
    }

    public static void setFunds(int funds) {
        updateListeners();
        GameLogic.funds = funds;
        lock = true;
        for (GameLogicListener listener :
                gameLogicListeners) {
            listener.onFundsChanged(funds);
        }
        lock = false;
    }

    private static void updateListeners() {
        if (!toRemove.isEmpty()) {
            gameLogicListeners.removeAll(toRemove);
            toRemove.clear();
        }
        if (!toAdd.isEmpty()) {
            gameLogicListeners.addAll(toAdd);
            toAdd.clear();
        }
    }

    public static void SetState(GameState state){
        gameState = state;
        updateListeners();

        switch (state){
            case SPLASH_SCREEN:
                Events.Initialize();
                MapPosition.Initialize();
                SetState(GameState.MAIN_MENU);
                break;
            case MAIN_MENU:
                SceneManager.SetActiveScene(new MainMenu());
                break;
            case SETTINGS_SCREEN:
                SceneManager.SetActiveScene(new SettingScene());
                break;
            case MAP_SELECTION_SCREEN:
                SceneManager.SetActiveScene(new MapSelectionMenu());
                break;
            case MAP_LOADING_SCREEN:
                SceneManager.SetActiveScene(new MapLoadingScreen());
                final fi.tuni.tiko.sceneSystem.GameScene scene = new GameScene();
                MapManager.getSelectedMap().LoadMap(scene, new Action() {
                    @Override
                    public void run() {
                        SceneManager.SetActiveScene(scene);
                        SetState(GameState.GAME_SCREEN);
                    }
                });
                break;
            case GAME_SCREEN:
                break;
            case LOSE_SCREEN:
                MapManager.getSelectedMap().dispose();
                SceneManager.SetActiveScene(new GameOverScene());
                break;
            case WIN_SCREEN:
                if (MapManager.isLastMap()) {
                    MapManager.getSelectedMap().dispose();
                    SceneManager.SetActiveScene(new FinalScene());
                } else {
                    MapManager.getSelectedMap().dispose();
                    SceneManager.SetActiveScene(new GameWinScene());
                }
                break;
            case HELP_SCREEN:
                fi.tuni.tiko.sceneSystem.SceneManager.SetActiveScene(new HelpScene());
                break;
        }
    }

    public static GameState GetState(){
        return gameState;
    }

    public static void AddListener(GameLogicListener listener){
        if (lock) toAdd.add(listener);
        else gameLogicListeners.add(listener);
    }

    public static void RemoveListener(GameLogicListener listener){
        if (lock) toRemove.add(listener);
        else gameLogicListeners.remove(listener);
    }

    public static void Pause() {
        updateListeners();
        if (paused) return;
        paused = true;
        lock = true;
        for (GameLogicListener listener :
                gameLogicListeners) {
            listener.onPause();
        }
        lock = false;
    }

    public static void Resume() {
        updateListeners();
        if (!paused) return;
        paused = false;
        lock = true;
        for (GameLogicListener listener :
                gameLogicListeners) {
            listener.onResume();
        }
        lock = false;
    }

    public static boolean isPaused() {
        return paused;
    }
}
