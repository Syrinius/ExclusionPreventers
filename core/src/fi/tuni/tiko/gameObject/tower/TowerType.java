package fi.tuni.tiko.gameObject.tower;

/**
 * Allows deserialization reference for all tower in one place
 */
public enum TowerType {

    ATK(AtkTower.getInstance()),
    BOOK(BookTower.getInstance()),
    COOK(CookingTower.getInstance()),
    MUSIC(MusicTower.getInstance()),
    SPORT(SportTower.getInstance()),
    PROMO(PromoTower.getInstance()),
    SUPPORT(SupportTower.getInstance());

    public final Tower tower;

    TowerType(Tower tower) {
        this.tower = tower;
    }
}
