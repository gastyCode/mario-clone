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

/**
 * Trieda, ktorá slúži na načítanie všetkých potrebných assetov.
 *
 * Assety z pôvodnej hry Super Mario Bros som prebral zo stránky: https://www.mariouniverse.com/sprites-nes-smb/
 * Font som prebral zo stránky: https://www.dafont.com/joystix.font
 */
public class Assets {
    public static final AssetManager MANAGER = new AssetManager();

    public static final AssetDescriptor<TiledMap> MAP = new AssetDescriptor<TiledMap>("map/mario_map.tmx", TiledMap.class);
    public static final AssetDescriptor<Texture> PLAYER = new AssetDescriptor<Texture>("sprites/player.png", Texture.class);
    public static final AssetDescriptor<Texture> EMPTY_BLOCK = new AssetDescriptor<Texture>("sprites/empty_block.png", Texture.class);
    public static final AssetDescriptor<Texture> EMPTY_TILE = new AssetDescriptor<Texture>("sprites/empty_tile.png", Texture.class);
    public static final AssetDescriptor<TextureAtlas> ITEMS = new AssetDescriptor<>("sprites/items.atlas", TextureAtlas.class);
    public static final AssetDescriptor<Texture> GOOMBA = new AssetDescriptor<Texture>("sprites/goomba.png", Texture.class);
    public static final AssetDescriptor<BitmapFont> JOYSTIX = new AssetDescriptor<BitmapFont>("fonts/joystix.fnt", BitmapFont.class);
    public static final AssetDescriptor<Texture> FIREBALL = new AssetDescriptor<Texture>("sprites/fireball.png", Texture.class);
    public static final AssetDescriptor<Texture> BACKGOUND = new AssetDescriptor<Texture>("sprites/bg.png", Texture.class);
    public static final AssetDescriptor<Texture> LOGO = new AssetDescriptor<Texture>("sprites/logo.png", Texture.class);

    /**
     * Načítanie všetkých assetov.
     */
    public static void load() {
        MANAGER.load(PLAYER);
        MANAGER.load(EMPTY_BLOCK);
        MANAGER.load(EMPTY_TILE);
        MANAGER.load(ITEMS);
        MANAGER.load(GOOMBA);
        MANAGER.load(FIREBALL);
        MANAGER.load(BACKGOUND);
        MANAGER.load(LOGO);

        InternalFileHandleResolver resolver = new InternalFileHandleResolver();
        MANAGER.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        MANAGER.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        MANAGER.load(JOYSTIX);

        MANAGER.setLoader(TiledMap.class, new TmxMapLoader(resolver));
        MANAGER.load(MAP);

        MANAGER.finishLoading();
    }

    /**
     * Odstránenie všetkých assetov.
     */
    public static void dispose() {
        MANAGER.dispose();
    }
}
