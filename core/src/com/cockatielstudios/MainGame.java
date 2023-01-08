package com.cockatielstudios;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.cockatielstudios.screens.MenuScreen;

/**
 * Trieda, ktorá dedí z triedy Game, potrebná pre spúšťanie a následný chod celého programu.
 *
 * Táto trieda je predvytvorená frameworkom LibGDX.
 */
public class MainGame extends Game {
	private SpriteBatch spriteBatch;

	/**
	 * Načíta všetky assety, inicializuje fyziku a vytvorí menu.
	 */
	@Override
	public void create () {
		Assets.load();
		Assets.MANAGER.finishLoading();
		Box2D.init();

		this.spriteBatch = new SpriteBatch();

		setScreen(new MenuScreen(this, this.spriteBatch));
	}

	/**
	 * Vykreslí program.
	 */
	@Override
	public void render () {
		super.render();
	}

	/**
	 * Vyčistí všetky nepotrebné dáta, ktoré vytvoril program z pamäte.
	 */
	@Override
	public void dispose () {
		super.dispose();
		Assets.dispose();
		this.spriteBatch.dispose();
	}
}
