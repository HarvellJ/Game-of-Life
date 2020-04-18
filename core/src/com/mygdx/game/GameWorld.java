package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import javafx.util.Pair;

public class GameWorld {

	public GameWorld(int startRows, int startCols) {
		this.startRows = startRows;
		this.startCols = startCols;
		this.initializeGrid();
		this.stateTime = 0f;
		this.stateTimeOfLastUpdate = 0f;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		this.selectedAbility = 0;
	}

	private ArrayList<ArrayList<Pair<Integer, Boolean>>> matrix;
	private int startRows;
	private int startCols;
	private float stateTime;
	private float stateTimeOfLastUpdate;

	private int h;
	private int w;
	
	private int selectedAbility; //0 = glider, 1 = pulsar

	public ArrayList<ArrayList<Pair<Integer, Boolean>>> getMatrix() {
		return matrix;
	}

	public void setMatrix(ArrayList<ArrayList<Pair<Integer, Boolean>>> matrix) {
		this.matrix = matrix;
	}

	public void update() {
		stateTime += Gdx.graphics.getDeltaTime();
		if (stateTimeOfLastUpdate < this.stateTime - 0.1f) {
			for (int x = 0; x < matrix.size() - 1; x++) {
				// loop through each column
				for (int i = 0; i < matrix.get(x).size() - 1; i++) {
					int neighbourCount = 0;
					// cells directly adjacent
					if (matrix.get(x).size() > i && matrix.get(x).get(i + 1).getValue()) {
						neighbourCount += 1;
					}
					if (i != 0 && matrix.get(x).get(i - 1).getValue()) {
						neighbourCount += 1;
					}
					if (matrix.size() > x && matrix.get(x + 1).get(i).getValue()) {
						neighbourCount += 1;
					}
					if (x != 0 && matrix.get(x - 1).get(i).getValue()) {
						neighbourCount += 1;
					}
					// cells on diagonals
					if (matrix.size() > x && matrix.get(x).size() > i && matrix.get(x + 1).get(i + 1).getValue()) {
						neighbourCount += 1;
					}
					if (matrix.size() > x && i != 0 && matrix.get(x + 1).get(i - 1).getValue()) {
						neighbourCount += 1;
					}
					if (x != 0 && i != 0 && matrix.get(x - 1).get(i - 1).getValue()) {
						neighbourCount += 1;
					}
					if (x != 0 && matrix.get(x).size() > i && matrix.get(x - 1).get(i + 1).getValue()) {
						neighbourCount += 1;
					}

					matrix.get(x).set(i, new Pair<Integer, Boolean>(new Integer(neighbourCount), matrix.get(x).get(i).getValue()));
				}
			}
			for (int x = 0; x < matrix.size() - 1; x++) {
				// loop through each column
				for (int i = 0; i < matrix.get(x).size() - 1; i++) {
					// if cell is alive
					if (matrix.get(x).get(i).getValue()) {

						if (matrix.get(x).get(i).getKey() < 2) {
							matrix.get(x).set(i, new Pair<Integer, Boolean>(0, new Boolean("False")));
						} else if (matrix.get(x).get(i).getKey() == 2 || matrix.get(x).get(i).getKey() == 3) {
							matrix.get(x).set(i, new Pair<Integer, Boolean>(0, new Boolean("True")));
						} else if (matrix.get(x).get(i).getKey() > 3) {
							matrix.get(x).set(i, new Pair<Integer, Boolean>(0, new Boolean("False")));
						}

						// Any live cell with fewer than two live neighbours dies
						// Any live cell with two or three live neighbours lives on to the next
						// Any live cell with more than three live neighbours dies, as if by
						// Any dead cell with exactly three live neighbours becomes a live cell, as if
					} else {
						if (matrix.get(x).get(i).getKey() == 3) {
							matrix.get(x).set(i, new Pair<Integer, Boolean>(0, new Boolean("True")));
						}
					}
				}
			}
			stateTimeOfLastUpdate = this.stateTime;
		}
	}

	public void changeAbility(int abilityNumber) {
		this.selectedAbility = abilityNumber;
	}
	
	public void spawnPixels(Vector3 coordinates) {
		int xPosition = (int) coordinates.x / 5;
		int yPosition = (h - (int) coordinates.y) / 5;

		if(this.selectedAbility == 0) {
			this.spawnGlider(xPosition, yPosition);
		}
		else if(this.selectedAbility == 1) {
			this.spawnPulsar(xPosition, yPosition);
		}
		else if(this.selectedAbility == 2) {
			this.spawnRandom(xPosition, yPosition);
		}
	}

	private void spawnGlider(int xPosition, int yPosition) {
		if(this.areCoordsInBounds(xPosition, yPosition)) {
		matrix.get(yPosition).set(xPosition, new Pair<Integer, Boolean>(0, new Boolean("True")));
		matrix.get(yPosition - 1).set(xPosition + 1, new Pair<Integer, Boolean>(0, new Boolean("True")));
		matrix.get(yPosition - 2).set(xPosition + 1, new Pair<Integer, Boolean>(0, new Boolean("True")));
		matrix.get(yPosition - 2).set(xPosition, new Pair<Integer, Boolean>(0, new Boolean("True")));
		matrix.get(yPosition - 2).set(xPosition - 1, new Pair<Integer, Boolean>(0, new Boolean("True")));
		}
	}
	
	private void spawnPulsar(int xPosition, int yPosition) {
		// prevent out of bounds 
		if(this.areCoordsInBounds(xPosition, yPosition)) {
			matrix.get(yPosition).set(xPosition, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition).set(xPosition + 1, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition).set(xPosition + 2, new Pair<Integer, Boolean>(0, new Boolean("True")));

			matrix.get(yPosition).set(xPosition + 6, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition).set(xPosition + 7, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition).set(xPosition + 8, new Pair<Integer, Boolean>(0, new Boolean("True")));

			matrix.get(yPosition - 2).set(xPosition - 2, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 2).set(xPosition + 3,new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 2).set(xPosition + 5, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 2).set(xPosition + 10, new Pair<Integer, Boolean>(0, new Boolean("True")));

			matrix.get(yPosition - 3).set(xPosition - 2, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 3).set(xPosition + 3, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 3).set(xPosition + 5, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 3).set(xPosition + 10, new Pair<Integer, Boolean>(0, new Boolean("True")));

			matrix.get(yPosition - 4).set(xPosition - 2, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 4).set(xPosition + 3, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 4).set(xPosition + 5, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 4).set(xPosition + 10, new Pair<Integer, Boolean>(0, new Boolean("True")));

			matrix.get(yPosition - 5).set(xPosition, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 5).set(xPosition + 1, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 5).set(xPosition + 2, new Pair<Integer, Boolean>(0, new Boolean("True")));

			matrix.get(yPosition - 5).set(xPosition + 6, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 5).set(xPosition + 7, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 5).set(xPosition + 8, new Pair<Integer, Boolean>(0, new Boolean("True")));

			matrix.get(yPosition - 7).set(xPosition, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 7).set(xPosition + 1, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 7).set(xPosition + 2, new Pair<Integer, Boolean>(0, new Boolean("True")));

			matrix.get(yPosition - 7).set(xPosition + 6, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 7).set(xPosition + 7, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 7).set(xPosition + 8, new Pair<Integer, Boolean>(0, new Boolean("True")));

			matrix.get(yPosition - 8).set(xPosition - 2, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 8).set(xPosition + 3, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 8).set(xPosition + 5, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 8).set(xPosition + 10, new Pair<Integer, Boolean>(0, new Boolean("True")));

			matrix.get(yPosition - 9).set(xPosition - 2, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 9).set(xPosition + 3, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 9).set(xPosition + 5, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 9).set(xPosition + 10, new Pair<Integer, Boolean>(0, new Boolean("True")));

			matrix.get(yPosition - 10).set(xPosition - 2, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 10).set(xPosition + 3, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 10).set(xPosition + 5, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 10).set(xPosition + 10, new Pair<Integer, Boolean>(0, new Boolean("True")));

			matrix.get(yPosition - 12).set(xPosition, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 12).set(xPosition + 1, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 12).set(xPosition + 2, new Pair<Integer, Boolean>(0, new Boolean("True")));

			matrix.get(yPosition - 12).set(xPosition + 6, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 12).set(xPosition + 7, new Pair<Integer, Boolean>(0, new Boolean("True")));
			matrix.get(yPosition - 12).set(xPosition + 8, new Pair<Integer, Boolean>(0, new Boolean("True")));
		}
	}
	
	private void spawnRandom(int xPosition, int yPosition) {
		if(this.areCoordsInBounds(xPosition, yPosition)) {
		matrix.get(yPosition).set(xPosition, new Pair<Integer, Boolean>(0, new Boolean("True")));
		matrix.get(yPosition + 1).set(xPosition, new Pair<Integer, Boolean>(0, new Boolean("True")));
		matrix.get(yPosition + 1).set(xPosition + 2, new Pair<Integer, Boolean>(0, new Boolean("True")));
		matrix.get(yPosition + 1).set(xPosition + 3, new Pair<Integer, Boolean>(0, new Boolean("True")));
		matrix.get(yPosition + 1).set(xPosition + 4, new Pair<Integer, Boolean>(0, new Boolean("True")));
		matrix.get(yPosition + 2).set(xPosition, new Pair<Integer, Boolean>(0, new Boolean("True")));
		matrix.get(yPosition + 2).set(xPosition + 2, new Pair<Integer, Boolean>(0, new Boolean("True")));
		matrix.get(yPosition + 2).set(xPosition + 5, new Pair<Integer, Boolean>(0, new Boolean("True")));
		matrix.get(yPosition + 3).set(xPosition + 3, new Pair<Integer, Boolean>(0, new Boolean("True")));
		matrix.get(yPosition + 3).set(xPosition + 4, new Pair<Integer, Boolean>(0, new Boolean("True")));
		}
	}
	
	private void initializeGrid() {
		matrix = new ArrayList<ArrayList<Pair<Integer, Boolean>>>();
		for (int i = 0; i < startRows; i++) {
			matrix.add(new ArrayList<Pair<Integer, Boolean>>());
		}

		for (ArrayList<Pair<Integer, Boolean>> row : matrix) {
			for (int i = 0; i < startCols; i++) {
				row.add(new Pair<Integer, Boolean>(0, new Boolean("False")));
			}
		}

		// set some starting params
		// Random randomColNum = new Random();
		// Random randomRowNum = new Random();
		// Random randomLoopCount = new Random();
		// Random chanceOfSpawn = new Random();
		//
		// int loopCount = randomLoopCount.nextInt(100);
//		 for (int i = 0; i < 400; i++) {
//		 matrix.get(i).set(i, new Pair<Integer, Boolean>(0, new Boolean("True")));
//		 }
		// matrix.get(1).set(6, new Boolean("True"));
		// matrix.get(1).set(5, new Boolean("True"));
		// matrix.get(1).set(4, new Boolean("True"));
		// matrix.get(2).set(5, new Boolean("True"));
		// matrix.get(3).set(6, new Boolean("True"));
		// matrix.get(0).set(2, new Boolean("True"));
		// matrix.get(0).set(1, new Boolean("True"));

//		matrix.get(50).set(50, new Pair<Integer, Boolean>(0, new Boolean("True")));
//		matrix.get(50 - 1).set(50 + 1, new Pair<Integer, Boolean>(0, new Boolean("True")));
//		matrix.get(50 - 2).set(50 + 1, new Pair<Integer, Boolean>(0, new Boolean("True")));
//		matrix.get(50 - 2).set(50, new Pair<Integer, Boolean>(0, new Boolean("True")));
//		matrix.get(50 - 2).set(50 - 1, new Pair<Integer, Boolean>(0, new Boolean("True")));
	}
	
	private boolean areCoordsInBounds(int x, int y) {
		return (x + 15 < this.startCols) && ((x - 15) >= 0) && (y < startRows) && ((y - 15) >= 0) ;
	}

}
