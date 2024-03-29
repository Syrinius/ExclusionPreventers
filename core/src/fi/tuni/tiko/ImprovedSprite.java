package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Moves the position of the sprite to the center
 * Adds and modifies methods to be able to do calculations based on the current position and current size of the sprite
 * Extends Sprite to have basic functionalities of libGDX Sprite such as the texture
 */
public class ImprovedSprite extends Sprite {

    public ImprovedSprite(Texture texture) {
        super(texture);
        setOrigin(texture.getWidth()/2f, texture.getHeight()/2f);
    }

    public ImprovedSprite(TextureRegion texture) {
        super(texture);
        setOrigin(texture.getRegionWidth()/2f, texture.getRegionHeight()/2f);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x - getOriginX(), y - getOriginY());
    }

    @Override
    public float getX() {
        return super.getX() + getOriginX();
    }

    @Override
    public float getY() {
        return super.getY() + getOriginY();
    }

    public float getActualWidth() {
        return getWidth() * getScaleX();
    }

    public float getActualHeight() {
        return getHeight() * getScaleY();
    }

    public float getActualOriginX() {
        return getOriginX() * getScaleX();
    }

    public float getActualOriginY() {
        return getOriginY() * getScaleY();
    }
}
