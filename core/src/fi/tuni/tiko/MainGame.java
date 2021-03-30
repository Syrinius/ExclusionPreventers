package fi.tuni.tiko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import fi.tuni.tiko.coordinateSystem.MenuPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.sceneSystem.SceneManager;
import fi.tuni.tiko.wave.MapData;

public class MainGame extends ApplicationAdapter {

	BitmapFont font;
	SpriteBatch debugBatch;
	OrthographicCamera debugCamera;
	private static String debugText = "";
	private static MainGame instance;

	public static MainGame GetInstance() {
		return instance;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		ScreenPosition.WIDTH = width;
		ScreenPosition.HEIGHT = height;
	}

	@Override
	public void create() {
		instance = this;
		font = new BitmapFont();
		debugBatch = new SpriteBatch();
		debugCamera = new OrthographicCamera();
		debugCamera.setToOrtho(false, MenuPosition.WIDTH, MenuPosition.HEIGHT);
		GameLogic.SetState(GameLogic.GameState.SPLASH_SCREEN);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		SceneManager.Render();
		debugCamera.update();
		debugBatch.setProjectionMatrix(debugCamera.combined);
		debugBatch.begin();
		font.draw(debugBatch, debugText, 10, 190);
		debugBatch.end();
	}

	public static void SetDebugText(String text){
		debugText = text;
	}
}
