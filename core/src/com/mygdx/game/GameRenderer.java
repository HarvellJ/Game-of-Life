package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import javafx.util.Pair;

public class GameRenderer {

	private ShapeRenderer shapeRendererHUD; // renders shapes on the player's HUD
	private ShapeRenderer shapeRendererGameObjects; // renders shapes on the map

	private OrthographicCamera camera; // the camera (orthographic renders everything on one pane, regardless of
										// distance)
	private SpriteBatch spriteBatch; // the sprite batch to use for the game

	private GameWorld gameWorld;

	float w;
	float h;

	Texture img;

	public GameRenderer(GameWorld gameWorld) {
		img = new Texture("badlogic.jpg");

		// game world
		this.gameWorld = gameWorld;
		this.spriteBatch = new SpriteBatch();

		// get the width/height of the window
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		// sort out the camera
		camera = new OrthographicCamera(100, 100 * (h / w));

		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		camera.setToOrtho(false, w, h);
		camera.update();
		camera.position.set(0f, 0f, 0f);

		this.shapeRendererHUD = new ShapeRenderer();
		this.shapeRendererGameObjects = new ShapeRenderer();

	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// set camera
		// camera.position.set(0f, 0f, 0f);
		camera.update();

		// create the sprite batch
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();

		float rowPosition = 0;
		float colPosition = 0;
		float boxWidth = 5;
		float boxHeight = 5;

		for (ArrayList<Pair<Integer, Boolean>> row : gameWorld.getMatrix()) {
			// loop through each column
			for (Pair<Integer, Boolean> i : row) {
				if (i.getValue()) {
					shapeRendererHUD.begin(ShapeType.Filled);
					shapeRendererHUD.setColor(new Color(0, 0.5f, 0.5f, 0.5f));
					shapeRendererHUD.rect(colPosition, rowPosition, boxWidth, boxHeight);
					shapeRendererHUD.end();
					colPosition += boxWidth;
					// spriteBatch.draw(img, 0, 0);
				} else {
					colPosition += boxWidth;
				}
			}
			rowPosition += boxHeight;
			colPosition = 0f;
		}
		spriteBatch.end();
	}

}
