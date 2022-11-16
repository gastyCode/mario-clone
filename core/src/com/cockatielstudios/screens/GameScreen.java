package com.cockatielstudios.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cockatielstudios.Assets;
import com.cockatielstudios.MainGame;
import com.cockatielstudios.gameObjects.entities.Player;
import com.cockatielstudios.utils.MapParser;

import static com.cockatielstudios.Constants.*;

public class GameScreen implements Screen {
    private MainGame game;

    private OrthographicCamera camera;
    private Viewport viewport;

    private MapParser mapParser;

    public World world;
    public Box2DDebugRenderer b2Debug;

    public Player player;

    public GameScreen(MainGame game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, this.camera);

        this.camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        this.world = new World(GRAVITY, false);
        this.b2Debug = new Box2DDebugRenderer(true, true, true, true, true, true);

        this.mapParser = new MapParser(Assets.manager.get(Assets.map), this);
        this.mapParser.parseObjects();

        this.player = new Player(this, new Vector2(10f, 50f), 16, 32);
    }

    public void update(float delta) {
        this.world.step(delta, 3, 3);

        this.camera.update();
        this.mapParser.update(this.camera);

        this.player.update(delta);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        this.mapParser.render();

        this.game.spriteBatch.setProjectionMatrix(this.camera.combined);
        this.game.spriteBatch.begin();
        this.player.render(this.game.spriteBatch);
        this.game.spriteBatch.end();

        this.b2Debug.render(this.world, this.camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        this.game.spriteBatch.dispose();
        this.world.dispose();
        this.b2Debug.dispose();
    }
}
