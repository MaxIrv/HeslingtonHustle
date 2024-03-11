package com.heshustle.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenu implements Screen {
  final HesHustleGame game;

  OrthographicCamera camera;

  public MainMenu(final HesHustleGame game) {
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 400);
  }
  public void render(float delta) {
    camera.update();
  }
}
