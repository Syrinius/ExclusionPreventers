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
 * Class for handling changes of game state and ingame resources
 * Dispatcher of events to classes that need to listen to that
 * All elements that need to listen to game state or resource changes should subscribe to gameLogicListeners list
 */
public class GameLogic {

    /**
     * Enum to trigger correct events based on what state the game is currently in
     * for instance clicking the play button in main menu will change change the game state to MAP_SELECTION
     * and will trigger all the events needed for the settings screen
     */
    public enum GameState {
        SPLASH_SCREEN, MAIN_MENU, MAP_SELECTION_SCREEN, SETTINGS_SCREEN,
        MAP_LOADING_SCREEN, GAME_SCREEN, LOSE_SCREEN, WIN_SCREEN, HELP_SCREEN
    }

    /**
     * Enum for triggering correct events based on language setting
     */
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

    /**
     * Calls update listeners to have listeners from delayed add included
     * locks to have attempted changes to the iterated list be added to the delayed change lists instead
     * dispatches the event to all listeners
     * @param score score to be set
     */
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

    /**
     * Calls update listeners to have listeners from delayed add included
     * locks to have attempted changes to the iterated list be added to the delayed change lists instead
     * dispatches the event to all listeners
     * if lives reach 0 calls SetState to end the game
     * @param lives lives to be set
     */
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

    /**
     * Calls update listeners to have listeners from delayed add included
     * locks to have attempted changes to the iterated list be added to the delayed change lists instead
     * dispatches the event to all listeners
     * @param workers workers to be set
     */
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

    /**
     * Calls update listeners to have listeners from delayed add included
     * locks to have attempted changes to the iterated list be added to the delayed change lists instead
     * dispatches the event to all listeners
     * @param funds funds to be set
     */
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

    /**
     * Exists so that cahnges to the listeners list attempted while it's being iterated, will be done later instead
     */
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

    /**
     * the main function for moving the game from one state to another
     * Initializes necessary things and calls SceneManager to have currently displayed Scene changes appropriately for the state change
     * @param state gamestate to be set
     */
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

    /**
     * exists to ensure the listeners list doesn't get modified while it's being iterated
     * @param listener listener to be added
     */
    public static void AddListener(GameLogicListener listener){
        if (lock) toAdd.add(listener);
        else gameLogicListeners.add(listener);
    }

    /**
     * exists to ensure the listeners list doesn't get modified while it's being iterated
     * @param listener listener to be removed
     */
    public static void RemoveListener(GameLogicListener listener){
        if (lock) toRemove.add(listener);
        else gameLogicListeners.remove(listener);
    }

    /**
     * Handled pausing of the game
     * locks to have attempted changes to the iterated list be added to the delayed change lists instead
     * dispatches the event to all listeners
     */
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

    /**
     * Handles resuming the game
     * locks to have attempted changes to the iterated list be added to the delayed change lists instead
     * dispatches the event to all listeners
     */
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
