package fi.tuni.tiko.gameObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fi.tuni.tiko.ImprovedSprite;
import fi.tuni.tiko.map.Map;
import fi.tuni.tiko.coordinateSystem.MapPosition;

/**
 * Adds functionalities to ImprovedSprite that are specific to GameObjects
 * Implements GameObject to add generic implementations for GameObject methods
 */
public abstract class GameObjectSprite extends ImprovedSprite implements GameObject {

    MapPosition position;
    public Map map;

    public GameObjectSprite(TextureRegion texture, MapPosition position, Map map) {
        super(texture);
        initialize(position, map);
    }

    public GameObjectSprite(Texture texture, MapPosition position, Map map) {
        super(texture);
        initialize(position, map);
    }

    private void initialize(MapPosition position, Map map) {
        this.position = position;
        this.map = map;
        setScale(MapPosition.SCALE);
        setPosition(position.x, position.y);
        map.getGameObjectManager().addGameObject(this);
    }

    public boolean IsInside(MapPosition position) {
        return (position.x > getX() - getActualOriginX() && position.x < getX() - getActualOriginX() + getActualWidth() &&
                position.y > getY() - getActualOriginY() && position.y < getY() - getActualOriginY() + getActualHeight());
    }

    @Override
    public void render(SpriteBatch renderer) {
        draw(renderer);
    }

    @Override
    public void destroy() {
        map.getGameObjectManager().removeGameObject(this);
    }
}
