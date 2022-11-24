package com.cockatielstudios.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.cockatielstudios.gameObjects.items.Mushroom;
import com.cockatielstudios.gameObjects.tiles.Block;
import com.cockatielstudios.gameObjects.tiles.Ground;
import com.cockatielstudios.gameObjects.tiles.MysteryBlock;
import com.cockatielstudios.gameObjects.tiles.Pipe;
import com.cockatielstudios.screens.GameScreen;

import java.util.ArrayList;

import static com.cockatielstudios.Constants.*;

public class MapParser {
    private GameScreen screen;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Body fallSensorBody;

    private ArrayList<Block> blocks;
    private ArrayList<MysteryBlock> mysteryBlocks;
    private ArrayList<Mushroom> mushrooms;

    public MapParser(TiledMap map, GameScreen screen) {
        this.screen = screen;
        this.map = map;
        this.renderer = new OrthogonalTiledMapRenderer(this.map, 1 / PPM);
        this.createFallSensor(new Vector2(0f, -FALL_SENSOR_HEIGHT), WORLD_WIDTH, FALL_SENSOR_HEIGHT);

        this.blocks = new ArrayList<Block>();
        this.mysteryBlocks = new ArrayList<MysteryBlock>();

        this.mushrooms = new ArrayList<Mushroom>();
        this.mushrooms.add(new Mushroom(this.screen, new Vector2(32f, 32f), 16, 16, 1));

    }

    private World getWorld() {
        return this.screen.world;
    }

    private CollisionListener getCollisions() {
        return this.screen.collisionListener;
    }

    public void render(SpriteBatch spriteBatch) {
        this.renderer.render();

        for (Block block : this.blocks) {
            block.render(spriteBatch);
        }

        for (MysteryBlock mysteryBlock : this.mysteryBlocks) {
            mysteryBlock.render(spriteBatch);
        }

        for (Mushroom mushroom : this.mushrooms) {
            mushroom.render(spriteBatch);
            mushroom.update(0f);
        }
    }

    public void update(OrthographicCamera camera) {
        this.renderer.setView(camera);
        this.checkObjects();
    }

    public void parseObjects() {
        MapObjects groundObjects = this.map.getLayers().get("Ground").getObjects();
        MapObjects pipeObjects = this.map.getLayers().get("Pipes").getObjects();
        MapObjects blockObjects = this.map.getLayers().get("Blocks").getObjects();
        MapObjects mysteryBlockObjects = this.map.getLayers().get("MysteryBlocks").getObjects();

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

                new Pipe(this.screen, position, rect.getWidth(), rect.getHeight());
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
        for (MapObject mysteryBlock : mysteryBlockObjects) {
            if (mysteryBlock instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) mysteryBlock).getRectangle();
                Vector2 position = new Vector2(rect.getX(), rect.getY());
                MapProperties props = mysteryBlock.getProperties();

                this.mysteryBlocks.add(new MysteryBlock(this.screen, position, rect.getWidth(), rect.getHeight(), props.get("id", Integer.class)));
            }
        }
    }

    private void checkObjects() {
        if (this.screen.player.getState() != State.SMALL) {
            for (Block block : this.blocks) {
                if (block.getID() == this.getCollisions().getCollidedBlockID()) {
                    block.onCollision();
                }
            }
        }

        for (MysteryBlock mysteryBlock : this.mysteryBlocks) {
            if (mysteryBlock.getID() == this.getCollisions().getCollidedMysteryBlockID()) {
                mysteryBlock.onCollision();
            }
        }

        for (Mushroom mushroom : this.mushrooms) {
            if (mushroom.getID() == this.getCollisions().getCollidedMushroomID()) {
                mushroom.onCollision();
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
