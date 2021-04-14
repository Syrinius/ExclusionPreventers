package fi.tuni.tiko.hud;

import fi.tuni.tiko.GameLogic;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.gameObject.tower.EmptyTower;
import fi.tuni.tiko.gameObject.tower.TowerLocation;
import fi.tuni.tiko.utilities.Action;

public class RemoveTowerButton extends TowerButton {

    public RemoveTowerButton(TowerLocation location, MenuPosition position, int level, float size, Action toExecute) {
        super(EmptyTower.getInstance(), location, position, level, size, toExecute);
    }

    @Override
    public boolean onTouchDown(ScreenPosition position, int pointer) {
        if (IsInside(position.ToMenuPosition())) {
            GameLogic.addFunds(location.getRefund());
            location.refundWorkers();
            location.setTower(EmptyTower.getInstance(), 0, true);
            toExecute.run();
            return true;
        }
        return false;
    }
}
