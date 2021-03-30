package fi.tuni.tiko.utilities;


import com.badlogic.gdx.math.Vector2;

import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.sceneSystem.MainMenu;

public class NextButtonPosition {


    public static MenuPosition nextMenuPosition(MenuPosition position){
        return new MenuPosition(200,100);
    }
    public static MenuPosition nextLevelPosition(MenuPosition position){
        return new MenuPosition(150, 150);
    }

}
