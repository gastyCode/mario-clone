package com.cockatielstudios;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.cockatielstudios.screens.MenuScreen;

public class MainGame extends Game {
	private SpriteBatch spriteBatch;

	@Override
	public void create () {

		Assets.load();
		Assets.MANAGER.finishLoading();
		Box2D.init();

		this.spriteBatch = new SpriteBatch();

		setScreen(new MenuScreen(this, this.spriteBatch));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		Assets.dispose();
		this.spriteBatch.dispose();
	}
}
