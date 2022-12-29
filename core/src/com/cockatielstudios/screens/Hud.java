package com.cockatielstudios.screens;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cockatielstudios.Assets;
import static com.cockatielstudios.Constants.*;

public class Hud {
    private Stage stage;
    private Viewport viewport;

    private int timer;
    private int score;
    private float time;

    private Label timeLabel;
    private Label timerLabel;
    private Label worldLabel;
    private Label levelLabel;
    private Label marioLabel;
    private Label scoreLabel;

    public Hud(SpriteBatch spriteBatch) {
        this.timer = 300;
        this.score = 0;
        this.time = 0f;

        this.viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, new OrthographicCamera());
        this.stage = new Stage(viewport, spriteBatch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        this.timeLabel = new Label("TIME", new Label.LabelStyle(Assets.manager.get(Assets.arcadeclassic), Color.WHITE));
        this.timerLabel = new Label(String.format("%03d", this.timer), new Label.LabelStyle(Assets.manager.get(Assets.arcadeclassic), Color.WHITE));
        this.worldLabel = new Label("WORLD", new Label.LabelStyle(Assets.manager.get(Assets.arcadeclassic), Color.WHITE));
        this.levelLabel = new Label("1 1", new Label.LabelStyle(Assets.manager.get(Assets.arcadeclassic), Color.WHITE));
        this.marioLabel = new Label("MARIO", new Label.LabelStyle(Assets.manager.get(Assets.arcadeclassic), Color.WHITE));
        this.scoreLabel = new Label(String.format("%06d", this.score), new Label.LabelStyle(Assets.manager.get(Assets.arcadeclassic), Color.WHITE));

        table.add(this.marioLabel).expandX().padTop(10);
        table.add(this.worldLabel).expandX().padTop(10);
        table.add(this.timeLabel).expandX().padTop(10);
        table.row();
        table.add(this.scoreLabel).expandX();
        table.add(this.levelLabel).expandX();
        table.add(this.timerLabel).expandX();

        this.stage.addActor(table);
    }

    public void update(float delta) {
        this.time += delta;
        if (this.time >= 1f) {
            this.time = 0f;
            this.timer -= 1;
        }
        this.timerLabel.setText(String.format("%03d", this.timer));
        this.scoreLabel.setText(String.format("%06d", this.score));
    }

    public Stage getStage() {
        return stage;
    }

    public void addScore(int score) {
        this.score += score;
    }
}
