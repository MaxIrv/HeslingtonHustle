package core.src.com.heshustle.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class HesHustleGame extends Game { //Using Game instead of AppAdapter in order to use Screen as well
	//Load textures here
	SpriteBatch batch;
	BitmapFont font;
	//Texture img;
	
	public void create () {
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
