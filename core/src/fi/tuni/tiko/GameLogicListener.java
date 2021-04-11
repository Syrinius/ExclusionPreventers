package fi.tuni.tiko;

/**
 * Listener for events about game state changes
 * All classes that need information about game state changes should implement this
 */
public interface GameLogicListener {
    void onPause();
    void onResume();
    void onLivesChanged(int newValue);
    void onFundsChanged(int newValue);
    void onWorkersChanged(int newValue);
}
