package com.heshustle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Class that implements {@link Screen} to create a game over screen based on certain stats.
 */
public class GameOverScreen implements Screen {
  final HesHustleGame game;
  OrthographicCamera camera;
  final Hud hud;
  public String studyScore;
  public String eatScore;
  public String recreationScore;

  /**
   * Constructor for GameOverScreen.
   * @param game Game to attach this screen to.
   * @param hud HUD to obtain stats from.
   */
  public GameOverScreen(final HesHustleGame game, Hud hud) {
    this.game = game;
    this.hud = hud;

    camera = new OrthographicCamera();
    camera.setToOrtho(false,800,400);

    this.studyScore = "You Studied " + Integer.toString(hud.getStudyCount()) + " times";
    this.eatScore = "You Ate " + Integer.toString(hud.getEatCount()) + " times";
    this.recreationScore = "You relaxed " + Integer.toString(hud.getRecreationCount()) + " times";

  }

  /**
   * Not yet implemented.
   * {@inheritDoc}
   */
  @Override
  public void show() {

  }

  /**
   * Renders the {@link Screen} to the game.
   * @param delta Time in seconds since the last frame, not currently used.
   */
  @Override
  public void render(float delta) {
    ScreenUtils.clear(0,0,0,1);
    camera.update();
    game.batch.setProjectionMatrix(camera.combined);

    if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
      Gdx.app.exit();
    }

    game.batch.begin();
    game.font.draw(game.batch,"Game Over!",100,300);
    game.font.draw(game.batch,studyScore,100,250);
    game.font.draw(game.batch,eatScore,100,200);
    game.font.draw(game.batch,recreationScore,100,150);
    game.font.draw(game.batch,"Press Esc to exit",100,100);
    game.batch.end();
  }
  /**
   * Not yet implemented.
   * {@inheritDoc}
   */
  @Override
  public void resize(int i, int i1) {

  }
  /**
   * Not yet implemented.
   * {@inheritDoc}
   */
  @Override
  public void pause() {

  }
  /**
   * Not yet implemented.
   * {@inheritDoc}
   */
  @Override
  public void resume() {

  }
  /**
   * Not yet implemented.
   * {@inheritDoc}
   */
  @Override
  public void hide() {

  }
  /**
   * Not yet implemented.
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

  }
}
