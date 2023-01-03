package com.cockatielstudios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cockatielstudios.Assets;
import com.cockatielstudios.MainGame;
import com.cockatielstudios.utils.Button;
import static com.cockatielstudios.Constants.PPM;
import static com.cockatielstudios.Constants.WORLD_WIDTH;
import static com.cockatielstudios.Constants.WORLD_HEIGHT;

public class MenuScreen implements Screen {
    private MainGame game;

    private OrthographicCamera camera;
    private Viewport viewport;

    private Button startButton;
    private Button quitButton;

    public MenuScreen(MainGame game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(WORLD_WIDTH / PPM, WORLD_HEIGHT / PPM, this.camera);
        this.camera.position.set(this.viewport.getWorldWidth() / 2, this.viewport.getWorldHeight() / 2, 0);

        this.startButton = new Button("Start", Assets.MANAGER.get(Assets.ARCADE_CLASSIC), new Vector2(0, 0));
        this.quitButton = new Button("Quit", Assets.MANAGER.get(Assets.ARCADE_CLASSIC), new Vector2(this.camera.position.x, 20));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.camera.update();
        this.startButton.render();
        this.quitButton.render();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height);
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
