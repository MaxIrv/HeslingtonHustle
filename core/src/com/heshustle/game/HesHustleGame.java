package com.heshustle.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class HesHustleGame extends Game { //Using Game instead of AppAdapter in order to use Screen as well
	//Load textures here
	SpriteBatch batch;
	BitmapFont font;
	public OrthographicCamera camera;
	//Texture img;
	
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480); // Or whatever your preferred viewport size is
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	public void render () {
		super.render();
	}
	
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
