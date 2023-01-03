package com.cockatielstudios.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.cockatielstudios.gameObjects.tiles.Ground;
import com.cockatielstudios.gameObjects.tiles.Pipe;
import com.cockatielstudios.gameObjects.tiles.Block;
import com.cockatielstudios.gameObjects.tiles.MysteryBlock;
import com.cockatielstudios.gameObjects.tiles.Flag;
import com.cockatielstudios.screens.GameScreen;
import static com.cockatielstudios.Constants.PPM;
import static com.cockatielstudios.Constants.FALL_SENSOR_HEIGHT;
import static com.cockatielstudios.Constants.WORLD_WIDTH;

public class MapParser {
    private GameScreen screen;
    private ObjectsManager objectsManager;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Body fallSensorBody;



    public MapParser(TiledMap map, ObjectsManager objectsManager, GameScreen screen) {
        this.screen = screen;
        this.objectsManager = objectsManager;
        this.map = map;
        this.renderer = new OrthogonalTiledMapRenderer(this.map, 1 / PPM);
        this.createFallSensor(new Vector2(0f, -FALL_SENSOR_HEIGHT), WORLD_WIDTH, FALL_SENSOR_HEIGHT);
    }

    private World getWorld() {
        return this.screen.getWorld();
    }

    public void update(OrthographicCamera camera) {
        this.renderer.setView(camera);
    }

    public void render() {
        this.renderer.render();
    }

    public void parseObjects() {
        MapObjects groundObjects = this.map.getLayers().get("Ground").getObjects();
        MapObjects pipeObjects = this.map.getLayers().get("Pipes").getObjects();
        MapObjects blockObjects = this.map.getLayers().get("Blocks").getObjects();
        MapObjects mysteryBlockObjects = this.map.getLayers().get("MysteryBlocks").getObjects();
        MapObjects goombaObjects = this.map.getLayers().get("Goombas").getObjects();
        MapObjects flagObjects = this.map.getLayers().get("Flag").getObjects();

        for (MapObject groundBlock : groundObjects) {
            if (groundBlock instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject)groundBlock).getRectangle();
                Vector2 position = new Vector2(rect.getX(), rect.getY());

                new Ground(this.screen, position, rect.getWidth(), rect.getHeight());
            }
        }
        for (MapObject pipe : pipeObjects) {
            if (pipe instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject)pipe).getRectangle();
                Vector2 position = new Vector2(rect.getX(), rect.getY());

                new Pipe(this.screen, position, rect.getWidth(), rect.getHeight());
            }
        }
        for (MapObject block : blockObjects) {
            if (block instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject)block).getRectangle();
                Vector2 position = new Vector2(rect.getX(), rect.getY());
                MapProperties props = block.getProperties();

                this.objectsManager.addBlock(new Block(this.screen, position, rect.getWidth(), rect.getHeight(), props.get("id", Integer.class)));
            }
        }
        for (MapObject mysteryBlock : mysteryBlockObjects) {
            if (mysteryBlock instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject)mysteryBlock).getRectangle();
                Vector2 position = new Vector2(rect.getX(), rect.getY());
                MapProperties props = mysteryBlock.getProperties();

                this.objectsManager.addMysteryBlock(new MysteryBlock(this.screen, position, rect.getWidth(), rect.getHeight(), props.get("id", Integer.class), props.get("isSpecial", Boolean.class)));
            }
        }

        for (MapObject goomba : goombaObjects) {
            if (goomba instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject)goomba).getRectangle();
                Vector2 position = new Vector2(rect.getX(), rect.getY());

                this.objectsManager.addGoombaData(position);
            }
        }

        for (MapObject flag : flagObjects) {
            if (flag instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject)flag).getRectangle();
                Vector2 position = new Vector2(rect.getX(), rect.getY());

                new Flag(this.screen, position, rect.getWidth(), rect.getHeight());
            }
        }
    }

    public void createFallSensor(Vector2 position, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.x = position.x;
        bodyDef.position.y = position.y;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width / 2, height / 2);

        this.fallSensorBody = this.getWorld().createBody(bodyDef);
        this.fallSensorBody.createFixture(polygonShape, 0f).setUserData(ObjectName.FALL_DETECTOR);

        polygonShape.dispose();
    }
}
