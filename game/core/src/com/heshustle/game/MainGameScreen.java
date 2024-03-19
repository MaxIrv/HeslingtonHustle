package com.heshustle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.heshustle.game.map.Map;
import com.heshustle.game.map.Layer;


public class MainGameScreen implements Screen {
  public static final float SPEED = 120;
  private SpriteBatch batch;
  private Texture img;
  final HesHustleGame game;

  private Map gameMap;

  float x;
  float y;

//  OrthographicCamera camera;

  public MainGameScreen (final HesHustleGame game) {
    this.game = game;

    // Initialize the game map using Gdx.files.internal passing in the path as a string
    gameMap = new Map(new OrthographicCamera(), Gdx.files.internal("HeslingtonEast.tmx").file().getAbsolutePath());

    // load images
//    img = new Texture(Gdx.files.internal("badlogic.jpg"));

    // load sound effects
    // effect = Gdx.audio.newSound(Gdx.files.internal("example.wav");

//    camera = new OrthographicCamera();
//    camera.setToOrtho(false, 800, 400);
  }

  @Override
  public void show() {

    img = new Texture(Gdx.files.internal("badlogic.jpg"));
  }

  @Override
  public void render(float delta) {
    int mapWidth = 30;
    int mapHeight = 20;
    int tileSize = 8;

    ScreenUtils.clear(0, 0, 0.2f, 1);

    handleInput(delta);
    game.camera.setToOrtho(false, mapWidth * tileSize, mapHeight * tileSize);
    game.camera.update(); // Ensure the camera is updated

    // Set the mapRenderer view before rendering the map
    gameMap.updateCamera(game.camera);

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen


    // Render the map layers

    game.batch.begin();
    gameMap.render(Layer.background); // Render the background layer
//    game.batch.draw(img, x, y); // Draw your sprite
    gameMap.render(Layer.foreground); // Render the foreground layer
    game.batch.end();
  }

  private void handleInput(float delta) {
    if (Gdx.input.isKeyPressed(Keys.UP)) y += SPEED * delta;
    if (Gdx.input.isKeyPressed(Keys.DOWN)) y -= SPEED * delta;
    if (Gdx.input.isKeyPressed(Keys.LEFT)) x -= SPEED * delta;
    if (Gdx.input.isKeyPressed(Keys.RIGHT)) x += SPEED * delta;
  }

  @Override
  public void resize(int i, int i1) {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
    img.dispose();
//    gameMap.dispose();
  }
}
