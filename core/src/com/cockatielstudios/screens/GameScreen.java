package com.cockatielstudios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

/**
 * Trieda reprezentujúca samostatnú hru, ktorá implementuje rozhranie Screen
 */
public class GameScreen implements Screen {
    private MainGame game;
    private SpriteBatch spriteBatch;

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

    /**
     * Konštruktor, ktorý sa stará o správne nastavenie hry a teda aj generáciu samotného levelu.
     *
     * Použitie triedy Viewport je inšpirované video tutoriálom: https://youtu.be/D7u5B2Oh9r0
     * Použitie triedy World je inšpirované tutoriálom na webe:
     * https://box2d.org/documentation/md__d_1__git_hub_box2d_docs_hello.html
     *
     * @param game Inštancia triedy MainGame, ktorý sa stará o chod celej hry.
     * @param spriteBatch Pomocník pri vykresľovaní textúr.
     */
    public GameScreen(MainGame game, SpriteBatch spriteBatch) {
        this.game = game;
        this.spriteBatch = spriteBatch;
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(WORLD_WIDTH / PPM, WORLD_HEIGHT / PPM, this.camera);
        this.hud = new Hud(this.spriteBatch);

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

    /**
     * Dochádza k aktualizovaní sveta, kamery, hráča, objektov a HUDu. Zároveň sa kontroluje smrť hráča, časovač a či
     * došlo k výhre.
     *
     * @param delta Čas v sekundách od posledného rámca.
     */
    public void update(float delta) {
        this.world.step(1 / 60f, 6, 2);

        this.cameraManager.update();
        this.mapParser.update(this.camera);
        this.objectsManager.update();
        this.hud.update(delta);

        this.player.update(delta);
        this.checkPlayerDeath();
        this.chceckTimer();
        this.checkWin();
    }

    @Override
    public void show() {

    }

    /**
     * Vykreslenie herného sveta, objektov, hráča a HUDu.
     *
     * @param delta Čas v sekundách od posledného rámca.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.update(delta);

        this.spriteBatch.begin();
        this.mapParser.render();
        this.objectsManager.render(this.spriteBatch, delta);
        this.player.render(this.spriteBatch);
        this.spriteBatch.end();

        this.spriteBatch.setProjectionMatrix(this.hud.getStage().getCamera().combined);
        this.hud.getStage().draw();

//        this.b2Debug.render(this.world, this.camera.combined);
    }

    /**
     * Pri zmene veľkosti okna zmení veľkosť hry, aby mala rovnaké rozmery ako okno.
     *
     * @param width Nová šírka okna.
     * @param height Nová výška okna.
     */
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

    /**
     * Uvoľní pamať zničením objektov, ktoré boli vytvorené v metóde create.
     */
    @Override
    public void dispose() {
        this.game.dispose();
        this.world.dispose();
        this.player.dispose();
        this.b2Debug.dispose();
    }

    /**
     * Pri smrti hráča sa zobrazí text "GAME OVER!"
     */
    public void checkPlayerDeath() {
        if (this.getPlayerState() == State.DEATH) {
            this.hud.gameOver();
            this.player.dispose();
        }
    }

    /**
     * Pri vypršaní časovača hráč zomrie a zobrazí sa text "GAME OVER!"
     */
    public void chceckTimer() {
        if (this.hud.getTimer() <= 0) {
            this.hud.gameOver();
            this.player.setState(State.DEATH);
            this.player.dispose();
        }
    }

    /**
     * Pri výhre sa zobrazí text "YOU WIN!"
     */
    public void checkWin() {
        if (this.isWin) {
            this.hud.win();
        }
    }
}
