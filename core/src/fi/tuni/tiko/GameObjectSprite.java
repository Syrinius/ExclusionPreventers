package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fi.tuni.tiko.coordinateSystem.MapPosition;

public abstract class GameObjectSprite extends ImprovedSprite implements GameObject {

    MapPosition position;
    Map map;

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

    @Override
    public void render(SpriteBatch renderer) {
        draw(renderer);
    }

    @Override
    public void destroy() {
        map.getGameObjectManager().removeGameObject(this);
    }
}
