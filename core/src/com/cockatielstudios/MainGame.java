package com.cockatielstudios;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.cockatielstudios.screens.GameScreen;

public class MainGame extends Game {
	public SpriteBatch spriteBatch;

	@Override
	public void create () {
		Assets.load();
		Assets.manager.finishLoading();
		Box2D.init();

		spriteBatch = new SpriteBatch();
//		setScreen(new MenuScreen(this));
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		Assets.dispose();
		spriteBatch.dispose();
	}
}
