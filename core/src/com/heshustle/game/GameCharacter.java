package com.heshustle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * <p>Class that deals with all character functions, specifically:</p>
 * <ul>
 * <li>Keeping track of the character's position and the direction they're facing.</li>
 * <li>Rendering and animating the character.</li>
 * </ul>
 * <p>Also implements helper enums and methods for these functions</p>
 */
public class GameCharacter {

  /**
   * Valid character states, consisting of:<br>
   * {@link #IDLE}
   * {@link #RUNNING}
   */
  public enum State {
    IDLE, RUNNING
  }

  /**
   * Valid character directions, consisting of:<br>
   * {@link #UP}
   * {@link #DOWN}
   * {@link #LEFT}
   * {@link #RIGHT}
   */
  public enum Direction {
    UP, DOWN, LEFT, RIGHT
  }

  public String characterName;

  private Animation<TextureRegion>[] runAnimations;
  private Animation<TextureRegion>[] idleAnimations;
  private TextureRegion currentFrame;
  private State currentState;
  private Direction currentDirection;
  private float stateTime;
  public float characterHeight, characterWidth;

  /**
   * <p>Constructor for GameCharacter.</p>
   *
   * @param idleSheetPath Relative file path from assets to the idle animation file sheet (.png)
   * @param runSheetPath Relative file path from assets to the run animation file sheet (.png)
   * @param characterName String that names the character. Used in character selection menu.
   */
  public GameCharacter(String idleSheetPath, String runSheetPath, String characterName) {
    this.characterName = characterName;
    Texture idleSheet = new Texture(Gdx.files.internal(idleSheetPath));
    Texture runSheet = new Texture(Gdx.files.internal(runSheetPath));

    runAnimations = new Animation[4];
    idleAnimations = new Animation[4];
    currentState = State.IDLE; // Default state
    currentDirection = Direction.DOWN; // Default direction
    stateTime = 0f;

    // Create idle animation
    TextureRegion[][] idleFrames = TextureRegion.split(idleSheet, 16, 32);
    int frameLength = 6; // Number of frames per direction for idle animations

    // Extract animations for each direction
    idleAnimations[Direction.RIGHT.ordinal()] = extractAnimation(idleFrames[0], 0, frameLength);
    idleAnimations[Direction.UP.ordinal()] = extractAnimation(idleFrames[0], frameLength, frameLength);
    idleAnimations[Direction.LEFT.ordinal()] = extractAnimation(idleFrames[0], 2 * frameLength, frameLength);
    idleAnimations[Direction.DOWN.ordinal()] = extractAnimation(idleFrames[0], 3 * frameLength, frameLength);

    TextureRegion[][] runFrames = TextureRegion.split(runSheet, 16, 32);
//    frameLength = 6; // Number of frames per direction for run animations

    // Extract animations for each direction
    runAnimations[Direction.RIGHT.ordinal()] = extractAnimation(runFrames[0], 0, frameLength);
    runAnimations[Direction.UP.ordinal()] = extractAnimation(runFrames[0], frameLength, frameLength);
    runAnimations[Direction.LEFT.ordinal()] = extractAnimation(runFrames[0], 2 * frameLength, frameLength);
    runAnimations[Direction.DOWN.ordinal()] = extractAnimation(runFrames[0], 3 * frameLength, frameLength);
  }

  // Helper method to extract animation frames

  /**
   * <p>Helper method to extract animation frames from a texture array</p>
   *
   * @param spriteSheetRow Texture array of animation frames.
   * @param startFrame Index of starting pixel of the target animation frame.
   * @param frameCount Number of frames in the animation.
   * @return New Animation<> that has a 0.1f frameDuration and contains the animation frames
   * @see Animation
   */
  private Animation<TextureRegion> extractAnimation(TextureRegion[] spriteSheetRow, int startFrame, int frameCount) {
    TextureRegion[] animationFrames = new TextureRegion[frameCount];
    for (int i = 0; i < frameCount; i++) {
      animationFrames[i] = spriteSheetRow[startFrame + i];
    }
    return new Animation<>(0.1f, animationFrames);
  }

  /**
   * <p>Updates the GameCharacter's sprite based on how much time has elapsed since the last update.
   * </p>
   * @param delta Amount of time in seconds that has passed since the last update.
   */
  public void update(float delta) {
    stateTime += delta;

    // Get current frame of animation for the current state
    switch (currentState) {
      case IDLE:
        currentFrame = idleAnimations[currentDirection.ordinal()].getKeyFrame(stateTime, true);
        break;
      case RUNNING:
        currentFrame = runAnimations[currentDirection.ordinal()].getKeyFrame(stateTime, true);
        break;
    }
  }

  /**
   * Renders the sprite via {@code batch}
   *
   * @param batch Batch to render the sprite to.
   * @param x X coordinate of bottom left of the sprite.
   * @param y Y coordinate of bottom left of the sprite.
   * @param desiredHeight Desired height of the sprite in pixels. (Used for scaling)
   * @see SpriteBatch
   */
  public void render(SpriteBatch batch, float x, float y, float desiredHeight) {
    float originalWidth = currentFrame.getRegionWidth();
    float originalHeight = currentFrame.getRegionHeight();
    float aspectRatio = originalWidth / originalHeight;

    float scaledWidth = desiredHeight * aspectRatio;

    this.characterWidth = scaledWidth;
    // Dividing by 2 to give more allowance on collisions with height
    this.characterHeight = desiredHeight / 2;


    // Draw the current frame at the character's position
    if (currentFrame != null) { // Add this null check
      batch.draw(currentFrame, x, y, scaledWidth, desiredHeight);
    } else {
      switch (currentState) {
        case IDLE:
          currentFrame = idleAnimations[currentDirection.ordinal()].getKeyFrame(stateTime, true);
          break;
        case RUNNING:
          currentFrame = runAnimations[currentDirection.ordinal()].getKeyFrame(stateTime, true);
          break;
      }
      batch.draw(currentFrame, x, y, scaledWidth, desiredHeight);
    }
  }

  /**
   * Sets the state and/or direction of the character.
   * @param newState New state for the character to be in.
   * @param newDirection New direction for the character to face.
   */
  public void setState(State newState, Direction newDirection) {
    if(this.currentState != newState || this.currentDirection != newDirection) {
      this.currentState = newState;
      this.currentDirection = newDirection;
      stateTime = 0f; // Only reset the state time if the state or direction has changed
    }
  }

  /**
   * Gets the current direction of the {@code GameCharacter}.
   * @return The current direction of the {@code GameCharacter}.
   */
  public Direction getCurrentDirection() {
    return currentDirection;
  }

  /**
   * Disposes unneeded assets. Call when the {@code GameCharacter} is no longer being used.
   */
  public void dispose() {
    // Dispose textures when done
    // This should be called when the character is no longer needed
  }
}
