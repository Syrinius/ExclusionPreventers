package fi.tuni.tiko.gameObject.student;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface Student {


    void tick(StudentContainer container);
    TextureRegion getTextureRegion();
}
