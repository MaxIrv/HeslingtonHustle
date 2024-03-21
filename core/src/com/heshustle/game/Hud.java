package com.heshustle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.heshustle.interaction.Interaction;
import com.heshustle.interaction.Interaction.Type;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Class that renders a HUD show the player's current stats. Also keeps track of the player's
 * current stats.
 */
public class Hud {
  private final Viewport viewport;
  public Stage hudStage;
  //final SpriteBatch batch;
  //final StatsActor stats;
  private final BitmapFont font;
  private String directory;
  private final Label studyLabel;
  private int studyCount;
  private Label studyCountLabel;
  private final Label eatLabel;
  private int eatCount;
  private Label eatCountLabel;
  private final Label recreationLabel;
  private int recreationCount;
  private Label recreationCountLabel;
  private final Label dayLabel;
  private int dayCount;
  private Label dayCountLabel;

  /**
   * Constructor for Hud.
   * @param batch Batch to render the HUD to.
   */
  public Hud(SpriteBatch batch) {
    viewport = new FitViewport(800,480,new OrthographicCamera());
    hudStage = new Stage(viewport, batch);
    font = new BitmapFont();

    studyCount = 0;
    eatCount = 0;
    recreationCount = 0;
    dayCount = 1;

    Table table = new Table();
    table.top().left();
    table.setFillParent(true);
    LabelStyle ls = new LabelStyle(font, Color.BLACK);

    studyLabel = new Label("Study",ls);
    eatLabel = new Label("Eat",ls);
    recreationLabel = new Label("Recreation",ls);
    dayLabel = new Label("Day",ls);

    studyCountLabel = new Label(Integer.toString(studyCount),ls);
    eatCountLabel = new Label(Integer.toString(eatCount),ls);
    recreationCountLabel = new Label(Integer.toString(recreationCount),ls);
    dayCountLabel = new Label(Integer.toString(dayCount),ls);

    table.add(studyLabel);
    table.add(studyCountLabel);
    table.row();
    table.add(eatLabel);
    table.add(eatCountLabel);
    table.row();
    table.add(recreationLabel);
    table.add(recreationCountLabel);
    table.row();
    table.add(dayLabel);
    table.add(dayCountLabel);

    hudStage.addActor(table);
  }

  /**
   * Updates the stats and display based on the interaction passed.
   * @param interaction Interaction to increase by one.
   */
  public void update(Interaction interaction) {
    if(interaction.getType() == Type.STUDY) {
      studyCount++;
      studyCountLabel.setText(studyCount);
    } else if(interaction.getType() == Type.EAT) {
      eatCount++;
      eatCountLabel.setText(eatCount);
    } else if (interaction.getType() == Type.RECREATION) {
      recreationCount++;
      recreationCountLabel.setText(recreationCount);
    } else if (interaction.getType() == Type.SLEEP) {
      dayCount++;
      dayCountLabel.setText(dayCount);
    }

  }

  /**
   * Gets the number of times the player has studied.
   * @return The number of times the player has studied.
   */
  public int getStudyCount() {
    return studyCount;
  }
  /**
   * Gets the number of times the player has eaten.
   * @return The number of times the player has eaten.
   */
  public int getEatCount() {
    return eatCount;
  }
  /**
   * Gets the number of times the player has recreated.
   * @return The number of times the player has recreated.
   */
  public int getRecreationCount() {
    return recreationCount;
  }
  /**
   * Gets the number of days that have elapsed since the start of the game (in game time).
   * @return The number of days that have elapsed since the start of the game.
   */
  public int getDayCount() {
    return dayCount;
  }
}
