package com.cockatielstudios;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets {
    public static final AssetManager manager = new AssetManager();

    public static final AssetDescriptor<TiledMap> map = new AssetDescriptor<TiledMap>("map/mario_map.tmx", TiledMap.class);
    public static final AssetDescriptor<Texture> player = new AssetDescriptor<Texture>("sprites/mario.png", Texture.class);
    public static final AssetDescriptor<Texture> emptyBlock = new AssetDescriptor<Texture>("sprites/empty_block.png", Texture.class);
    public static final AssetDescriptor<Texture> emptyTile = new AssetDescriptor<Texture>("sprites/empty_tile.png", Texture.class);
    public static final AssetDescriptor<TextureAtlas> items = new AssetDescriptor<>("sprites/items.atlas", TextureAtlas.class);
    public static final AssetDescriptor<Texture> goomba = new AssetDescriptor<Texture>("sprites/goomba.png", Texture.class);

    public static void load() {
        manager.load(player);
        manager.load(emptyBlock);
        manager.load(emptyTile);
        manager.load(items);
        manager.load(goomba);

        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load(map);

        manager.finishLoading();
    }

    public static void dispose() {
        manager.dispose();
    }
}
