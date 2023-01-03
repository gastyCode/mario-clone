package com.cockatielstudios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cockatielstudios.Assets;
import com.cockatielstudios.MainGame;
import com.cockatielstudios.gameObjects.entities.Player;
import com.cockatielstudios.utils.Animator;
import com.cockatielstudios.utils.CameraManager;
import com.cockatielstudios.utils.ObjectsManager;
import com.cockatielstudios.utils.MapParser;
import com.cockatielstudios.utils.CollisionListener;
import com.cockatielstudios.utils.State;
import static com.cockatielstudios.Constants.WORLD_WIDTH;
import static com.cockatielstudios.Constants.WORLD_HEIGHT;
import static com.cockatielstudios.Constants.PPM;
import static com.cockatielstudios.Constants.GRAVITY;

public class GameScreen implements Screen {
    private MainGame game;

    private OrthographicCamera camera;
    private Viewport viewport;
    private CameraManager cameraManager;
    private Hud hud;

    private ObjectsManager objectsManager;
    private MapParser mapParser;

    private World world;
    private CollisionListener collisionListener;
    private Box2DDebugRenderer b2Debug;

    private Animator animator;
    private Player player;

    private boolean isWin;

    public GameScreen(MainGame game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(WORLD_WIDTH / PPM, WORLD_HEIGHT / PPM, this.camera);
        this.hud = new Hud(this.game.spriteBatch);

        this.camera.position.set(this.viewport.getWorldWidth() / 2, this.viewport.getWorldHeight() / 2, 0);

        this.world = new World(new Vector2(0f, GRAVITY), false);
        this.collisionListener = new CollisionListener(this, this.hud);
        this.world.setContactListener(this.collisionListener);
        this.b2Debug = new Box2DDebugRenderer(true, true, true, true, true, true);

        this.objectsManager = new ObjectsManager(this);
        this.mapParser = new MapParser(Assets.MANAGER.get(Assets.MAP), this.objectsManager, this);
        this.mapParser.parseObjects();

        this.animator = new Animator();
        this.player = new Player(this, new Vector2(10f, 50f), 16, 32);
        this.cameraManager = new CameraManager(this.camera, this.player);

        this.isWin = false;
    }

    public World getWorld() {
        return this.world;
    }

    public CollisionListener getCollisionListener() {
        return this.collisionListener;
    }

    public State getPlayerState() {
        return this.player.getState();
    }

    public Vector2 getPlayerPosition() {
        return this.player.getPosition();
    }

    public ObjectsManager getObjectsManager() {
        return this.objectsManager;
    }

    public Hud getHud() {
        return this.hud;
    }

    public Animator getAnimator() {
        return this.animator;
    }

    public void setWin(boolean win) {
        this.isWin = win;
    }

    public void update(float delta) {
        this.world.step(1 / 60f, 6, 2);

        this.cameraManager.update();
        this.mapParser.update(this.camera);
        this.objectsManager.update();
        this.hud.update(delta);

        this.player.update(delta);
        this.checkPlayerDeath();
        this.checkWin();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.update(delta);

        this.game.spriteBatch.begin();
        this.mapParser.render();
        this.objectsManager.render(this.game.spriteBatch, delta);
        this.player.render(this.game.spriteBatch);
        this.game.spriteBatch.end();

        this.game.spriteBatch.setProjectionMatrix(this.hud.getStage().getCamera().combined);
        this.hud.getStage().draw();

        this.b2Debug.render(this.world, this.camera.combined);
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
        this.game.spriteBatch.dispose();
        this.world.dispose();
        this.player.dispose();
        this.b2Debug.dispose();
    }

    public void checkPlayerDeath() {
        if (this.getPlayerState() == State.DEATH) {
            this.hud.gameOver();
            this.player.dispose();
        }
    }

    public void checkWin() {
        if (this.isWin) {
            this.hud.win();
        }
    }
}
