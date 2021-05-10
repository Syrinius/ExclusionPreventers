package fi.tuni.tiko.utilities;


import fi.tuni.tiko.coordinateSystem.MenuPosition;

/**
 * automaticly gets next position using given gap values
 */

public class NextButtonPosition {

    /**
     * Gets last used postion, gapx and gapY.
     *
     * use for menu elements.
     *
     * @return next position
     */
    //use for menu elements
    public static MenuPosition nextMenuPosition(MenuPosition position, float gapX, float gapY){
        position.add(gapX, gapY);
        return position;
    }
    /**
     * Gets last used postion, gapx and gapY.
     *
     * use for level elements.
     *
     * @return next position
     */
    //use for level elements
    public static MenuPosition nextLevelPosition(MenuPosition position, float gapX, float gapY){
        if(position.x > MenuPosition.WIDTH - 100) {
            position.set(60, 100);
        }else{
            position.add(gapX, gapY);
        }
        return position;
    }
    /**
     * Gets last used postion, gapx and gapY
     *
     * checks if there is room to add on right if not adds it to left column.
     *
     * use for tower elements
     *
     * @return next position
     */
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
