package com.heshustle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.heshustle.game.GameCharacter.Direction;
import com.heshustle.game.GameCharacter.State;

public class MainMenuScreen implements Screen {
  final HesHustleGame game;
  OrthographicCamera camera;

  private final Array<GameCharacter> characters = new Array<>();
  private int selectedCharacterIndex;

  public MainMenuScreen(final HesHustleGame game) {
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 400);

    selectedCharacterIndex = 0;
    FileHandle dirHandle = Gdx.files.internal("characters");

    for (FileHandle entry: dirHandle.list()) {
      if (entry.isDirectory()) {
        String name = entry.name();
        String idlePath = "characters/" + name + "/idle.png";
        String runPath = "characters/" + name + "/run.png";

        // Capitalize first letter of the character name
        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        GameCharacter character = new GameCharacter(idlePath, runPath, name);
        character.setState(State.IDLE, Direction.DOWN);
        characters.add(character);
      }
    }
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
    game.font.draw(game.batch,"Heslington Hustle", 100, 150);
    game.font.draw(game.batch,"Press the Spacebar or Enter to start the game", 100, 100);
    game.font.draw(game.batch, "Use the left and right arrow keys to cycle between characters!", 100, 30);

//    characters.get(0).setState(State.IDLE, Direction.DOWN);
    game.font.draw(game.batch, characters.get(selectedCharacterIndex).characterName, 695, 40);

    characters.get(selectedCharacterIndex).update(Gdx.graphics.getDeltaTime());
    characters.get(selectedCharacterIndex).render(game.batch, 700, 55, 50);


    if (Gdx.input.isKeyPressed(Keys.ENTER ) ||
      Gdx.input.isKeyPressed(Keys.SPACE)) {
      try {
        game.setScreen(new MainGameScreen(game, characters.get(selectedCharacterIndex)));
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
      dispose();
    }
    // Handle character selection
    if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
      selectPreviousCharacter();
    } else if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
      selectNextCharacter();
    }


    game.batch.end();
  }

  public void selectNextCharacter() {
    selectedCharacterIndex = (selectedCharacterIndex + 1) % characters.size;
  }

  public void selectPreviousCharacter() {
    selectedCharacterIndex--;
    if (selectedCharacterIndex < 0) selectedCharacterIndex = characters.size - 1;
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