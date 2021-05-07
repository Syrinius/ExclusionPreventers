package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.tuni.tiko.utilities.NextButtonPosition;
import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.gameObject.student.StudentType;
import fi.tuni.tiko.gameObject.tower.TowerType;

/**
 * A class for displaying info about each type of student present in the next wave on WaveClearedPopOutMenu
 * Instanciates all the elements needed for displaying that info
 */
public class StudentInfo implements HudElement {

    private final HudSprite student;
    private final HudSprite affinity;
    private final HudSprite affinitySymbol;
    private final HudSprite resistance;
    private final HudSprite resistanceSymbol;
    private final GlyphRenderer amount;

    public StudentInfo(MenuPosition position, StudentType studentType, fi.tuni.tiko.gameObject.tower.TowerType affinity, TowerType resistance, int amount) {
        position = position.clone();
        this.amount = new GlyphRenderer(position.clone(), .5f, GlyphRenderer.Type.PLAIN, amount);
        NextButtonPosition.nextMenuPosition(position, 10, 0);
        this.student = new HudSprite(studentType.type.getWalkingTextureRegion(0), position, 25);
        if (affinity == null) {
            this.affinity = null;
            this.affinitySymbol = null;
        } else {
            NextButtonPosition.nextMenuPosition(position, 30, 0);
            this.affinity = new HudSprite(affinity.tower.getTexture(affinity.tower.getNewData()), position, 25);
            this.affinitySymbol = new HudSprite(new Texture("towers/affinity.png"), position, 25);
        }
        if (resistance == null) {
            this.resistance = null;
            this.resistanceSymbol = null;
        } else {
            fi.tuni.tiko.utilities.NextButtonPosition.nextMenuPosition(position, 30, 0);
            this.resistance = new HudSprite(resistance.tower.getTexture(resistance.tower.getNewData()), position, 25);
            this.resistanceSymbol = new HudSprite(new Texture("towers/resistance.png"), position, 25);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        student.render(batch);
        if (affinity != null) {
            affinity.render(batch);
            affinitySymbol.render(batch);
        }
        if (resistance != null) {
            resistance.render(batch);
            resistanceSymbol.render(batch);
        }
        amount.render(batch);
    }

    @Override
    public void dispose() {
        student.dispose();
        if (affinity != null) {
            affinity.dispose();
            affinitySymbol.dispose();
        }
        if (resistance != null) {
            resistance.dispose();
            resistanceSymbol.dispose();
        }
        amount.dispose();
    }
}
