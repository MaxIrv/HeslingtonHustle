package com.heshustle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenu implements Screen {
  final HesHustleGame game;

  OrthographicCamera camera;

  public MainMenu(final HesHustleGame game) {
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 400);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(0,0,0,1);
    camera.update();
    game.batch.setProjectionMatrix(camera.combined);

    game.batch.begin();
    game.font.draw(game.batch,"Heslington Hustle",100,150);
    game.font.draw(game.batch,"Press anything to start",100,100);
    game.batch.end();

    if (Gdx.input.isTouched()) { //Will need the next screen class to call here.
      //game.setScreen(new GameScreen(game));
      dispose();
    }
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