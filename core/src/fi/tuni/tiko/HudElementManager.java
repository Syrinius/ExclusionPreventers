package fi.tuni.tiko;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import fi.tuni.tiko.coordinateSystem.MenuPosition;

public class HudElementManager {

    private static final ArrayList<HudElement> hudElements = new ArrayList<>();
    private static final ArrayList<HudElement> delayedAdd = new ArrayList<>();
    private static final ArrayList<HudElement> delayedRemove = new ArrayList<>();
    private static boolean locked;

    static BitmapFont font;
    static SpriteBatch batch;
    static OrthographicCamera camera;
    static String debugText = "";

    //static Joystick joystick;

    public static void AddHudElement(HudElement hudElement){
        if (!locked) hudElements.add(hudElement);
        else delayedAdd.add(hudElement);
    }

    public static void RemoveHudElement(HudElement hudElement){
        if (!locked) hudElements.remove(hudElement);
        else delayedRemove.add(hudElement);
    }

    public static void Initialize(){
        font = new BitmapFont();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MenuPosition.WIDTH, MenuPosition.HEIGHT);

        /*joystick = new Joystick(new MenuPosition(50,100));
		Events.AddListener(this);
		joystick.addJoystickListener(this);*/
        //GameObjectManager.AddGameObject(new TestGameObject());
    }

    public static void Render(){
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
        font.draw(batch, debugText, 10, 190);
        batch.end();
    }

    public static void SetDebugText(String text){
        debugText = text;
    }
}
