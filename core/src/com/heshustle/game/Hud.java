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
  public void update(Interaction interaction) {
    LabelStyle ls = new LabelStyle(new BitmapFont(), Color.BLACK);
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
}
