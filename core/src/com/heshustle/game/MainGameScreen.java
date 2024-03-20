package com.heshustle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.heshustle.map.Layer;
import com.heshustle.map.Map;


public class MainGameScreen implements Screen {
  public static final float SPEED = 20;
  final HesHustleGame game;
  private final Map gameMap;
  private final GameCharacter character;
  float characterX;
  float characterY;

  public MainGameScreen (final HesHustleGame game, GameCharacter character) {
    this.game = game;

    // Initialize the game map using Gdx.files.internal passing in the path as a string
    gameMap = new Map(new OrthographicCamera(), Gdx.files.internal("HeslingtonEast.tmx").file().getAbsolutePath());
    this.character = character;
    this.characterX = gameMap.startPosition.x;
    this.characterY = gameMap.startPosition.y;
  }

  @Override
  public void show() {
//    img = new Texture(Gdx.files.internal("badlogic.jpg"));
  }

  @Override
  public void render(float delta) {
    int mapWidth = 30;
    int mapHeight = 20;
    int tileSize = 8;

    ScreenUtils.clear(0, 0, 0.2f, 1);

    game.camera.setToOrtho(false, mapWidth * tileSize, mapHeight * tileSize);
    game.camera.update(); // Ensure the camera is updated

    // Set the mapRenderer view before rendering the map
    gameMap.updateCamera(game.camera);

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen


    // Render the map layers

    // Uses getDeltaTime to ensure FPS changes does not affect speed
    // Otherwise more FPS would equal more speed
    if (Gdx.input.isKeyPressed(Keys.UP)) {
      characterY += SPEED * Gdx.graphics.getDeltaTime();
      character.setState(GameCharacter.State.RUNNING, GameCharacter.Direction.UP);
    } else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
      characterY -= SPEED * Gdx.graphics.getDeltaTime();
      character.setState(GameCharacter.State.RUNNING, GameCharacter.Direction.DOWN);
    } else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
      characterX -= SPEED * Gdx.graphics.getDeltaTime();
      character.setState(GameCharacter.State.RUNNING, GameCharacter.Direction.LEFT);
    } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
      characterX += SPEED * Gdx.graphics.getDeltaTime();
      character.setState(GameCharacter.State.RUNNING, GameCharacter.Direction.RIGHT);
    }
    // If no keys are being pressed
    if (!Gdx.input.isKeyPressed(Keys.LEFT) &&
        !Gdx.input.isKeyPressed(Keys.RIGHT) &&
        !Gdx.input.isKeyPressed(Keys.UP) &&
        !Gdx.input.isKeyPressed(Keys.DOWN)) {
      character.setState(GameCharacter.State.IDLE, character.getCurrentDirection());
    }

    Gdx.gl.glClearColor(1, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    game.batch.begin();
    gameMap.render(Layer.waterLayer);
    gameMap.render(Layer.background); // Render the background layer
    gameMap.render(Layer.foreground); // Render the foreground layer

    character.update(Gdx.graphics.getDeltaTime());
    character.render(game.batch, characterX, characterY, 10);

    if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
      // Option 1: Go back to the main menu or another screen
      game.setScreen(new MainMenuScreen(game));

      // Option 2: Exit the application
      // Gdx.app.exit();
    }

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
//    gameMap.dispose();
  }
}
