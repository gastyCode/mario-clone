package com.cockatielstudios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cockatielstudios.Assets;
import com.cockatielstudios.MainGame;

import static com.cockatielstudios.Constants.WORLD_WIDTH;
import static com.cockatielstudios.Constants.WORLD_HEIGHT;

public class MenuScreen implements Screen {
    private MainGame game;
    private SpriteBatch spriteBatch;
    private Stage stage;

    private OrthographicCamera camera;
    private Viewport viewport;

    private TextButton startButton;
    private TextButton quitButton;

    public MenuScreen(MainGame game, SpriteBatch spriteBatch) {
        this.game = game;
        this.spriteBatch = spriteBatch;
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, this.camera);
        this.camera.position.set(this.viewport.getWorldWidth() / 2, this.viewport.getWorldHeight() / 2, 0);
        this.stage = new Stage(this.viewport, this.spriteBatch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Texture bgTexture = Assets.MANAGER.get(Assets.BACKGOUND);
        Drawable bg = new TextureRegionDrawable(new TextureRegion(bgTexture));

        Texture logoTexture = Assets.MANAGER.get(Assets.LOGO);
        Image logo = new Image(logoTexture);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = Assets.MANAGER.get(Assets.JOYSTIX);

        this.startButton = new TextButton("START", style);
        this.quitButton = new TextButton("QUIT", style);

        this.startButton.addListener(this.startButton.getClickListener());
        this.quitButton.addListener(this.quitButton.getClickListener());

        table.setBackground(bg);
        table.add(logo).size(120, 60).padTop(20);
        table.row();
        table.add(this.startButton).expandX().padTop(40);
        table.row();
        table.add(this.quitButton).expandX().padTop(10);

        this.stage.addActor(table);
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.camera.update();

        if (this.startButton.getClickListener().isPressed()) {
            this.game.setScreen(new GameScreen(this.game, this.spriteBatch));
        }
        if (this.quitButton.getClickListener().isPressed()) {
            Gdx.app.exit();
        }

        this.spriteBatch.setProjectionMatrix(this.stage.getCamera().combined);
        this.stage.draw();
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
        this.game.dispose();
    }
}
