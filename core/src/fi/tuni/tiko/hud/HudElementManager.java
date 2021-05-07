package fi.tuni.tiko.hud;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import fi.tuni.tiko.coordinateSystem.MenuPosition;

/**
 * Dispatches render events to HudElements
 * All HudElements should subscribe to hudElements list
 * As with other observer patterns the lock exists to avoid concurrent modification exception
 */
public class HudElementManager {

    private final ArrayList<fi.tuni.tiko.hud.HudElement> hudElements = new ArrayList<>();
    private final ArrayList<fi.tuni.tiko.hud.HudElement> delayedAdd = new ArrayList<>();
    private final ArrayList<fi.tuni.tiko.hud.HudElement> delayedRemove = new ArrayList<>();
    private boolean locked;
    private fi.tuni.tiko.hud.PopOut currentPopOut;

    public void SetPopOut(PopOut toSet) {
        if (currentPopOut != null) currentPopOut.close();
        currentPopOut = toSet;
    }
    SpriteBatch batch;
    OrthographicCamera camera;

    public void Dispose() {
        if (locked) return;
        locked = true;
        for (fi.tuni.tiko.hud.HudElement hudElement : hudElements) {
            hudElement.dispose();
        }
        hudElements.clear();
        delayedAdd.clear();
        delayedRemove.clear();
    }

    public void AddHudElement(fi.tuni.tiko.hud.HudElement hudElement){
        if (!locked) hudElements.add(hudElement);
        else delayedAdd.add(hudElement);
    }

    public void RemoveHudElement(fi.tuni.tiko.hud.HudElement hudElement){
        if (!locked) hudElements.remove(hudElement);
        else delayedRemove.add(hudElement);
    }

    public HudElementManager() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MenuPosition.WIDTH, MenuPosition.HEIGHT);
    }

    public void Render(){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        locked = true;
        for (HudElement hudElement :
                hudElements) {
            hudElement.render(batch);
        }
        locked = false;
        if (!delayedAdd.isEmpty()) {
            hudElements.addAll(delayedAdd);
            delayedAdd.clear();
        }
        if (!delayedRemove.isEmpty()) {
            hudElements.removeAll(delayedRemove);
            delayedRemove.clear();
        }
        batch.end();
    }

}
