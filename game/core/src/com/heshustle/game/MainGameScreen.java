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

public class MainGameScreen implements Screen {
  public static final float SPEED = 120;
  private SpriteBatch batch;
  private Texture img;
  final HesHustleGame game;

  float x;
  float y;

//  OrthographicCamera camera;

  public MainGameScreen (final HesHustleGame game) {
    this.game = game;

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
  public void render(float v) {
    ScreenUtils.clear(0, 0, 0.2f, 1);

    // Uses getDeltaTime to ensure FPS changes do not affect speed
    // Otherwise more FPS would equal more speed
    if (Gdx.input.isKeyPressed(Keys.UP)) {
      y += SPEED * Gdx.graphics.getDeltaTime();
    }
    if (Gdx.input.isKeyPressed(Keys.DOWN)) {
      y -= SPEED * Gdx.graphics.getDeltaTime();
    }
    if (Gdx.input.isKeyPressed(Keys.LEFT)) {
      x -= SPEED * Gdx.graphics.getDeltaTime();
    }
    if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
      x += SPEED * Gdx.graphics.getDeltaTime();
    }

    Gdx.gl.glClearColor(1, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    game.batch.begin();

    game.batch.draw(img, x, y);

    game.batch.end();

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

  }
}
