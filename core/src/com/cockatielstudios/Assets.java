package com.cockatielstudios;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets {
    public static final AssetManager manager = new AssetManager();

    public static final AssetDescriptor<TiledMap> map = new AssetDescriptor<TiledMap>("map/mario_map.tmx", TiledMap.class);
    public static final AssetDescriptor<Texture> player = new AssetDescriptor<Texture>("sprites/player.png", Texture.class);
    public static final AssetDescriptor<Texture> emptyBlock = new AssetDescriptor<Texture>("sprites/empty_block.png", Texture.class);
    public static final AssetDescriptor<Texture> emptyTile = new AssetDescriptor<Texture>("sprites/empty_tile.png", Texture.class);
    public static final AssetDescriptor<TextureAtlas> items = new AssetDescriptor<>("sprites/items.atlas", TextureAtlas.class);
    public static final AssetDescriptor<Texture> goomba = new AssetDescriptor<Texture>("sprites/goomba.png", Texture.class);
    public static final AssetDescriptor<BitmapFont> arcadeclassic = new AssetDescriptor<BitmapFont>("fonts/arcadeclassic.fnt", BitmapFont.class);
    public static final AssetDescriptor<Texture> fireball = new AssetDescriptor<Texture>("sprites/fireball.png", Texture.class);

    public static void load() {
        manager.load(player);
        manager.load(emptyBlock);
        manager.load(emptyTile);
        manager.load(items);
        manager.load(goomba);
        manager.load(fireball);

        InternalFileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        manager.load(arcadeclassic);

        manager.setLoader(TiledMap.class, new TmxMapLoader(resolver));
        manager.load(map);

        manager.finishLoading();
    }

    public static void dispose() {
        manager.dispose();
    }
}
