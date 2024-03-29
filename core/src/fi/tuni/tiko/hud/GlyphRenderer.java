package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.utilities.FancyMath;

/**
 * A class for drawing numbers displaying ingame resources
 */
public class GlyphRenderer implements HudElement {

    public enum Type { FUNDS, LIVES, WORKERS, SCORE, PLAIN }

    private static final int GLYPH_SIZE = 26;
    private static final Array<TextureRegion> glyphs = FancyMath.getTextureRegionArray(new Texture("glyphs.png"), GLYPH_SIZE, GLYPH_SIZE, 14);
    private MenuPosition position;
    private final float size;
    private final Type type;
    private int value;

    public GlyphRenderer(MenuPosition position, float size, Type type, int value) {
        this.position = position;
        this.size = size;
        this.type = type;
        this.value = value;
    }

    public void setPosition(MenuPosition position) {
        this.position = position;
    }

    public void setValue(int value) {
        this.value = Math.abs(value);
    }

    /**
     * First part is drawing the glyph representing the type of resource, if any
     * Second part is drawing each individual digit of the number
     * @param batch the spritebatch
     */
    @Override
    public void render(SpriteBatch batch) {
        float positionX = position.x;
        switch (type) {
            case FUNDS:
                batch.draw(glyphs.get(11), positionX, position.y, GLYPH_SIZE * size, GLYPH_SIZE * size);
                positionX += GLYPH_SIZE * size;
                break;
            case LIVES:
                batch.draw(glyphs.get(10), positionX, position.y, GLYPH_SIZE * size, GLYPH_SIZE * size);
                positionX += GLYPH_SIZE * size;
                break;
            case WORKERS:
                batch.draw(glyphs.get(12), positionX, position.y, GLYPH_SIZE * size, GLYPH_SIZE * size);
                positionX += GLYPH_SIZE * size;
                break;
            case SCORE:
                batch.draw(glyphs.get(13), positionX, position.y, GLYPH_SIZE * size, GLYPH_SIZE * size);
                positionX += GLYPH_SIZE * size;
                break;
        }

        int iteratedValue = value;
        for (int digit = value == 0 ? 0 : (int)Math.log10(value); digit >= 0; digit--) {
            int currentDigit = iteratedValue / (int)Math.pow(10, digit);
            iteratedValue -= (int)Math.pow(10, digit) * currentDigit;
            batch.draw(glyphs.get(currentDigit), positionX, position.y, GLYPH_SIZE * size, GLYPH_SIZE * size);
            positionX += GLYPH_SIZE * size;
        }
    }

    @Override
    public void dispose() {

    }
}
