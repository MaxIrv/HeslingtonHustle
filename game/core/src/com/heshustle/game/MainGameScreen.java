package core.src.com.heshustle.game;

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
  final HesHustleGame game;

  private GameCharacter character;

  float characterX;
  float characterY;

  public MainGameScreen (final HesHustleGame game, GameCharacter character) {
    this.game = game;
    this.character = character;
  }

  @Override
  public void show() {
  }

  @Override
  public void render(float v) {
    ScreenUtils.clear(0, 0, 0.2f, 1);

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

    character.update(Gdx.graphics.getDeltaTime());
    character.render(game.batch, characterX, characterY);

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

  }
}
