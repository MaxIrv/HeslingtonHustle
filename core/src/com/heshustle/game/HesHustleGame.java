package com.heshustle.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Class that encapsulates basic application window functionality.
 * Used in children of {@link com.badlogic.gdx.Screen} to switch between them.
 *
 * @see MainGameScreen
 * @see MainMenuScreen
 */
public class HesHustleGame extends Game { //Using Game instead of AppAdapter in order to use Screen as well
	//Load textures here
	SpriteBatch batch;
	BitmapFont font;
	public OrthographicCamera camera;
	//Texture img;

	/**
	 *	Initialises the {@code Camera}, {@code SpriteBatch} and {@code BitmapFont}.
	 */
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480); // Or whatever your preferred viewport size is
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this, new Hud(batch)));
	}

	/**
	 * {@inheritDoc}
	 */
	public void render () {
		super.render();
	}

	/**
	 * Disposes unneeded assets. Call when the {@code HesHustleGame} is no longer being used.
	 */
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
