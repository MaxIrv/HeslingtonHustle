package com.heshustle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.heshustle.interaction.Interaction;
import com.heshustle.map.GameMap;
import com.heshustle.map.Layer;


public class MainGameScreen implements Screen {
  private BitmapFont font;
  private float fontScale = 0.3f;
  public static final float SPEED = 20;
  final HesHustleGame game;
  private final GameMap gameMap;
  private final GameCharacter character;
  final Hud hud;
  // Character Position
  private float characterX, characterY;

  public MainGameScreen (final HesHustleGame game, GameCharacter character, Hud hud)
      throws ClassNotFoundException {
    this.game = game;
    this.hud = hud;
    this.font = new BitmapFont();
    this.font.getData().setScale(fontScale);
    this.font.setColor(Color.BLACK);
    // Initialize the game map using Gdx.files.internal passing in the path as a string
    try {
      gameMap = new GameMap(new OrthographicCamera(),
          Gdx.files.internal("HeslingtonEast.tmx").file().getAbsolutePath());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Failed to load game map");
    }
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

    float deltaTime = Gdx.graphics.getDeltaTime();

    // Uses getDeltaTime to ensure FPS changes does not affect speed
    // Otherwise more FPS would equal more speed
    if (Gdx.input.isKeyPressed(Keys.UP)) {
      updatePlayerPosition(0, SPEED * deltaTime);
      character.setState(GameCharacter.State.RUNNING, GameCharacter.Direction.UP);
    } else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
      updatePlayerPosition(0, -SPEED * deltaTime);
      character.setState(GameCharacter.State.RUNNING, GameCharacter.Direction.DOWN);
    } else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
      updatePlayerPosition(-SPEED * deltaTime, 0);
      character.setState(GameCharacter.State.RUNNING, GameCharacter.Direction.LEFT);
    } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
      updatePlayerPosition(SPEED * deltaTime, 0);
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

    // Create a rectangle representing the player's new position
    Rectangle characterRect = new Rectangle(characterX, characterY, character.characterWidth, character.characterHeight);

    // Handle interactions proximity
    Interaction nearbyInteraction = null;
    float interactionRange = 100.0f; // Set this to the distance within which you can interact

    for (Interaction interaction : gameMap.interactions) {
      if (characterRect.overlaps(interaction.getBounds())) {
        nearbyInteraction = interaction;
        break; // Assume only one interaction at a time
      }
    }

    // In your render method
    if (nearbyInteraction != null) {
      font.draw(game.batch, "Press E to interact with " + nearbyInteraction.getName(), characterX, characterY + 20); // Adjust the coordinates as necessary
    }

    // In your update method or input handler
    if (Gdx.input.isKeyJustPressed(Keys.E) && nearbyInteraction != null) {
      // Call the interaction logic
      performInteraction(nearbyInteraction);
    }


    if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
      // Option 1: Go back to the main menu or another screen
      game.setScreen(new MainMenuScreen(game, hud));

      // Option 2: Exit the application
      // Gdx.app.exit();
    }
    game.batch.end();
    game.batch.setProjectionMatrix(hud.hudStage.getCamera().combined);
    hud.hudStage.draw();
  }

  public void performInteraction(Interaction nearbyInteraction) {

  }

  public void updatePlayerPosition(float deltaX, float deltaY) {
    float newX = characterX + deltaX;
    float newY = characterY + deltaY;



    // Create a rectangle representing the player's new position
    Rectangle newCharacterRect = new Rectangle(newX, newY, character.characterWidth, character.characterHeight);

    // Check for collisions with blocked tiles in the foreground layer
    // and collidable water tiles
    boolean collision = checkCollision(newCharacterRect, gameMap.collidableTiles);

    // Update position only if there's no collision
    if (!collision) {
      // Check horizontal boundaries
      if (newX < 0) {
        characterX = 0; // Prevent moving beyond the left edge
      } else if (newX + character.characterWidth > gameMap.mapPixelWidth) {
        characterX = gameMap.mapPixelWidth - character.characterWidth; // Prevent moving beyond the right edge
      } else {
        characterX = newX;
      }

      // Check vertical boundaries
      if (newY < 0) {
        characterY = 0; // Prevent moving beyond the bottom edge
      } else if (newY + character.characterHeight > gameMap.mapPixelHeight) {
        characterY = gameMap.mapPixelHeight - character.characterHeight; // Prevent moving beyond the top edge
      } else {
        characterY = newY;
      }
    }
  }

  private boolean checkCollision(Rectangle playerRect, Array<Rectangle> collidableTiles) {
    for (Rectangle tileRect : collidableTiles) {
      if (playerRect.overlaps(tileRect)) {
        return true; // Collision detected
      }
    }
    return false; // No collision detected
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
