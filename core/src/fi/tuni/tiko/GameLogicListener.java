package fi.tuni.tiko;

/**
 * Listener for events about game state changes
 * All classes that need information about game state changes should implement this
 */
public interface GameLogicListener {
    void StateChanged(GameLogic.GameState state);
}
