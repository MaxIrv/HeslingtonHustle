package com.heshustle.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
public class Hud {
  private Viewport viewport;
  public Stage hudStage;
  //final SpriteBatch batch;
  //final StatsActor stats;
  private BitmapFont font;
  private Label studyLabel;
  private Label studyCount;
  private Label eatLabel;
  private Label eatCount;
  private Label recreationLabel;
  private Label recreationCount;
  private Label dayLabel;
  private Label dayCount;

  public Hud(SpriteBatch batch) {
    viewport = new FitViewport(800,480,new OrthographicCamera());
    hudStage = new Stage(viewport, batch);
    font = new BitmapFont();

    Table table = new Table();
    table.top().left();
    table.setFillParent(true);
    Skin skin = new Skin();
    LabelStyle ls = new LabelStyle(font, Color.BLACK);

    studyLabel = new Label("Study",ls);
    eatLabel = new Label("Eat",ls);
    recreationLabel = new Label("Recreation",ls);
    dayLabel = new Label("Day",ls);

    studyCount = new Label("0",ls);
    eatCount = new Label("0",ls);
    recreationCount = new Label("0",ls);
    dayCount = new Label("1",ls);

    table.add(studyLabel);
    table.add(studyCount);
    table.row();
    table.add(eatLabel);
    table.add(eatCount);
    table.row();
    table.add(recreationLabel);
    table.add(recreationCount);
    table.row();
    table.add(dayLabel);
    table.add(dayCount);

    hudStage.addActor(table);
  }
}
