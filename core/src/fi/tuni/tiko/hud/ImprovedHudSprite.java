package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fi.tuni.tiko.ImprovedSprite;
import fi.tuni.tiko.coordinateSystem.MenuPosition;

public class ImprovedHudSprite extends ImprovedSprite {

    public ImprovedHudSprite(Texture texture, MenuPosition position, float size) {
        super(texture);
        setScale(size/texture.getWidth());
        setPosition(position.x, position.y);
    }

    public ImprovedHudSprite(TextureRegion texture, MenuPosition position, float size) {
        super(texture);
        setScale(size/texture.getRegionWidth());
        setPosition(position.x, position.y);
    }

    public boolean IsInside(MenuPosition position) {
        return (position.x > getX() - getActualOriginX() && position.x < getX() - getActualOriginX() + getActualWidth() &&
            position.y > getY() - getActualOriginY() && position.y < getY() - getActualOriginY() + getActualHeight());
    }
}
