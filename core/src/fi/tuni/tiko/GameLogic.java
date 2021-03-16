package fi.tuni.tiko;

import java.util.ArrayList;

import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.eventSystem.Events;
import fi.tuni.tiko.sceneSystem.GameScene;
import fi.tuni.tiko.sceneSystem.MainMenu;
import fi.tuni.tiko.sceneSystem.MapLoadingScreen;
import fi.tuni.tiko.sceneSystem.MapSelectionMenu;
import fi.tuni.tiko.sceneSystem.SceneManager;

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
        SPLASH_SCREEN, MAIN_MENU, MAP_SELECTION_SCREEN, MAP_CONFIRM_SCREEN, SETTINGS_SCREEN, MAP_LOADING_SCREEN, BUILD_PHASE, WAVE_PHASE, END_SCREEN
    }

    private static final ArrayList<GameLogicListener> gameLogicListeners = new ArrayList<>();
    private static final ArrayList<GameLogicListener> toAdd = new ArrayList<>();
    private static final ArrayList<GameLogicListener> toRemove = new ArrayList<>();

    private static GameState gameState = GameState.SPLASH_SCREEN;
    private static boolean lock;

    public static void SetState(GameState state){
        gameState = state;
        lock = true;
        for (GameLogicListener listener :
                gameLogicListeners) {
            listener.StateChanged(state);
        }
        lock = false;
        if (!toRemove.isEmpty()) {
            gameLogicListeners.removeAll(toRemove);
            toRemove.clear();
        }
        if (!toAdd.isEmpty()) {
            gameLogicListeners.addAll(toAdd);
            toAdd.clear();
        }

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
                final GameScene scene = new GameScene();
                MapManager.getSelectedMap().LoadMap(scene, new Action() {
                    @Override
                    public void run() {
                        SceneManager.SetActiveScene(scene);
                    }
                });
                break;
            case BUILD_PHASE:
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
}
