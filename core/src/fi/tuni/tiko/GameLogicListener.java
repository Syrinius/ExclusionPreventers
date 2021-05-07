package fi.tuni.tiko;

/**
 * Listener for events about game state and resource changes
 * All classes that need information about game state or resource changes should implement this
 */
public interface GameLogicListener {
    void onPause();
    void onResume();
    void onLivesChanged(int newValue);
    void onFundsChanged(int newValue);
    void onWorkersChanged(int newValue);
    void onScoreChanged(int newValue);
}
