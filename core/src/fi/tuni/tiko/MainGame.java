package fi.tuni.tiko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import fi.tuni.tiko.coordinateSystem.MapPosition;
import fi.tuni.tiko.coordinateSystem.ScreenPosition;
import fi.tuni.tiko.eventSystem.Events;

public class MainGame extends ApplicationAdapter {

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		ScreenPosition.WIDTH = width;
		ScreenPosition.HEIGHT = height;
	}

	@Override
	public void create () {
		Events.Initialize();
		GameObjectManager.Initialize();
		MapPosition.Initialize();
		HudElementManager.Initialize();
		MapManager.selectMap(0).LoadMap();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		MapManager.render();
		GameObjectManager.Render();
		HudElementManager.Render();
	}
	
	@Override
	public void dispose () {
		GameObjectManager.Dispose();
	}
}
