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
import com.badlogic.gdx.utils.Array;
import com.cockatielstudios.gameObjects.tiles.Block;
import com.cockatielstudios.gameObjects.tiles.Ground;
import com.cockatielstudios.screens.GameScreen;

import javax.naming.PartialResultException;
import java.util.ArrayList;

import static com.cockatielstudios.Constants.*;

public class MapParser {
    private GameScreen screen;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Body fallSensorBody;

    private ArrayList<Block> blocks;

    public MapParser(TiledMap map, GameScreen screen) {
        this.screen = screen;
        this.map = map;
        this.renderer = new OrthogonalTiledMapRenderer(this.map, 1 / PPM);
        this.createFallSensor(new Vector2(0f, -FALL_SENSOR_HEIGHT), WORLD_WIDTH, FALL_SENSOR_HEIGHT);

        this.blocks = new ArrayList<Block>();

    }

    private World getWorld() {
        return this.screen.world;
    }

    private CollisionListener getCollisions() {
        return this.screen.collisionListener;
    }

    public void render() {
        this.renderer.render();
    }

    public void update(OrthographicCamera camera) {
        this.renderer.setView(camera);
        this.checkObjects();
    }

    public void parseObjects() {
        MapObjects groundObjects = this.map.getLayers().get("Ground").getObjects();
        MapObjects pipeObjects = this.map.getLayers().get("Pipes").getObjects();
        MapObjects blockObjects = this.map.getLayers().get("Blocks").getObjects();

        for (MapObject groundBlock : groundObjects) {
            if (groundBlock instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) groundBlock).getRectangle();
                Vector2 position = new Vector2(rect.getX(), rect.getY());

                new Ground(this.screen, position, rect.getWidth(), rect.getHeight());
            }
        }
        for (MapObject pipe : pipeObjects) {
            if (pipe instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) pipe).getRectangle();
                Vector2 position = new Vector2(rect.getX(), rect.getY());

                new Ground(this.screen, position, rect.getWidth(), rect.getHeight());
            }
        }
        for (MapObject block : blockObjects) {
            if (block instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) block).getRectangle();
                Vector2 position = new Vector2(rect.getX(), rect.getY());
                MapProperties props = block.getProperties();

                this.blocks.add(new Block(this.screen, position, rect.getWidth(), rect.getHeight(), props.get("id", Integer.class)));
            }
        }
    }

    private void checkObjects() {
        for (Block block : this.blocks) {
            if (block.getId() == this.getCollisions().getCollidedBlockID()) {
                block.body.setActive(false);
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
        this.fallSensorBody.createFixture(polygonShape, 0f).setUserData("fall");

        polygonShape.dispose();
    }
}
