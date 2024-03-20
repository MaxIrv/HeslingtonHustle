package core.src.com.heshustle.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;

public class GameCharacter {
  public enum State {
    IDLE, RUNNING
  }

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
  private Animation<TextureRegion> extractAnimation(TextureRegion[] spriteSheetRow, int startFrame, int frameCount) {
    TextureRegion[] animationFrames = new TextureRegion[frameCount];
    for (int i = 0; i < frameCount; i++) {
      animationFrames[i] = spriteSheetRow[startFrame + i];
    }
    return new Animation<>(0.1f, animationFrames);
  }

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

  public void render(SpriteBatch batch, float x, float y, float desiredHeight) {
    float originalWidth = currentFrame.getRegionWidth();
    float originalHeight = currentFrame.getRegionHeight();
    float aspectRatio = originalWidth / originalHeight;

//    float desiredHeight = 15; // Your desired height
    float scaledWidth = desiredHeight * aspectRatio;


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

  public void setState(State newState, Direction newDirection) {
    if(this.currentState != newState || this.currentDirection != newDirection) {
      this.currentState = newState;
      this.currentDirection = newDirection;
      stateTime = 0f; // Only reset the state time if the state or direction has changed
    }
  }

  public Direction getCurrentDirection() {
    return currentDirection;
  }

  public void dispose() {
    // Dispose textures when done
    // This should be called when the character is no longer needed
  }
}
