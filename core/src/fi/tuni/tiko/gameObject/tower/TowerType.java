package fi.tuni.tiko.gameObject.tower;

public enum TowerType {

    ATK(AtkTower.getInstance()),
    BOOK(BookTower.getInstance()),
    COOKING(CookingTower.getInstance()),
    MUSIC(MusicTower.getInstance()),
    SPORT(SportTower.getInstance());

    public final Tower tower;

    TowerType(Tower tower) {
        this.tower = tower;
    }
}
