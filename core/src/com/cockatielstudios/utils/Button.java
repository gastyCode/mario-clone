package com.cockatielstudios.utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class Button {
    private Stage stage;
    private TextButton button;
    private TextButtonStyle buttonStyle;

    public Button(String text, BitmapFont font, Vector2 position) {
        this.stage = new Stage();
        this.buttonStyle = new TextButtonStyle();
        this.buttonStyle.font = font;
        this.button = new TextButton(text, this.buttonStyle);
        this.button.setPosition(position.x, position.y);
        this.stage.addActor(this.button);
    }

    public void render() {
        this.stage.draw();
    }
}
