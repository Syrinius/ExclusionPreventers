package fi.tuni.tiko;

import java.util.ArrayList;

public class GameLogic {

    /**
     * enum to trigger correct events based on what state the game is currently in
     * for instance clicking the settings button in main menu will change change the game state to SETTINGS_SCREEN
     * and will trigger all the events needed for the settings screen
     */
    public enum GameState {
        SPLASH_SCREEN, MAIN_MENU, SETTINGS_SCREEN, BUILD_PHASE, WAVE_PHASE, END_SCREEN
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
