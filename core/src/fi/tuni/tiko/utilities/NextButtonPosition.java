package fi.tuni.tiko.utilities;


import com.badlogic.gdx.math.Vector2;

import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.gameObject.tower.TowerLocation;
import fi.tuni.tiko.hud.HudElementManager;
import fi.tuni.tiko.hud.TowerSelectionPopOutMenu;
import fi.tuni.tiko.sceneSystem.MainMenu;

public class NextButtonPosition {

    //use for menu elements
    public static MenuPosition nextMenuPosition(MenuPosition position, float gapX, float gapY){
        position.add(gapX, gapY);
        return position;
    }
    //use for level elements
    public static MenuPosition nextLevelPosition(MenuPosition position, float gapX, float gapY){
        if(position.x > MenuPosition.WIDTH - 100) {
            position.set(60, 100);
        }else{
            position.add(gapX, gapY);
        }
        return position;
    }

    //use for tower elements
    public static MenuPosition nextTowerPosition(MenuPosition position, float gapX, float gapY){
    if(position.x == 320 || position.x == 120) {
            position.add(-40, -35);
        }else {
        position.add(gapX, gapY);
        }
        //position.add(gapX, gapY);
        return position;
    }

}
