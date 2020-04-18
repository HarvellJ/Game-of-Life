package com.mygdx.game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

/**
 * Player Controller Class Input information
 */
public class GameController implements InputProcessor {

	private GameWorld gameWorld;
	private GameRenderer gameRenderer;

	public GameController(GameWorld gameWorld, GameRenderer gameRenderer) {
		this.gameWorld = gameWorld;
		this.gameRenderer = gameRenderer;
	}

	@Override
	public boolean keyDown(int keycode) {

		if (keycode == Input.Keys.NUM_1) {
			gameWorld.changeAbility(0);
		}
		if (keycode == Input.Keys.NUM_2) {
			gameWorld.changeAbility(1);
		}
		if (keycode == Input.Keys.NUM_3) {
			gameWorld.changeAbility(2);
		}
		if (keycode == Input.Keys.NUM_4) {

		}
		if (keycode == Input.Keys.SPACE) {

		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Buttons.RIGHT) {
			Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
			//Vector3 position = gameRenderer.getCamera().unproject(clickCoordinates);
			this.gameWorld.spawnPixels(clickCoordinates);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {		
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
