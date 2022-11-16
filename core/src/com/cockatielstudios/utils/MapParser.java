package com.cockatielstudios.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.cockatielstudios.gameObjects.tiles.Block;
import com.cockatielstudios.screens.GameScreen;

public class MapParser {
    private GameScreen screen;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public MapParser(TiledMap map, GameScreen screen) {
        this.screen = screen;
        this.map = map;
        this.renderer = new OrthogonalTiledMapRenderer(this.map);

    }

    public void render() {
        this.renderer.render();
    }

    public void update(OrthographicCamera camera) {
        this.renderer.setView(camera);
    }

    public void parseObjects() {
        MapObjects groundObjects = this.map.getLayers().get("Ground").getObjects();
        for (MapObject groundBlock : groundObjects) {
            if (groundBlock instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) groundBlock).getRectangle();
                Vector2 position = new Vector2(rect.getX(), rect.getY());

                new Block(this.screen, position, rect.getWidth(), rect.getHeight());
            }
        }
    }
}
