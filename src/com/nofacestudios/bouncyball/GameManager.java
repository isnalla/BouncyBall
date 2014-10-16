package com.nofacestudios.bouncyball;

public class GameManager {
	private static final int INITIAL_SCORE = 0;
	private static final int INITIAL_BIRD_COUNT = 0;
	private static final int INITIAL_ENEMY_COUNT = 0;
	private static GameManager INSTANCE;
	private int mCurrentScore;
	private int mBirdCount;
	private int mEnemyCount;
	
	// The constructor does not do anything for this singleton
	GameManager(){
	}
	
	public static GameManager getInstance(){
		if(INSTANCE == null){
			INSTANCE = new GameManager();
		}
		return INSTANCE;
	}
	
	// get the current score
	public int getCurrentScore(){
		return this.mCurrentScore;
	}
	// get the bird count
	public int getBirdCount(){
		return this.mBirdCount;
	}
	// increase the current score, most likely when an enemy is destroyed
	public void incrementScore(int pIncrementBy){
		mCurrentScore += pIncrementBy;
	}
	// Any time a bird is launched, we decrement our bird count
	public void decrementBirdCount(){
		mBirdCount -= 1;
	}
	
	public void startGame(){
		this.resetGame();
		TerrainManager.getInstance().setTerrainGenerationEnabled(true);
	}
	
	// Resetting the game simply means we must revert back to initial values.
	public void resetGame(){
		this.mCurrentScore = GameManager.INITIAL_SCORE;
		this.mBirdCount = GameManager.INITIAL_BIRD_COUNT;
		this.mEnemyCount = GameManager.INITIAL_ENEMY_COUNT;
	}
}
