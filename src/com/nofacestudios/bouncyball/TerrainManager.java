package com.nofacestudios.bouncyball;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.Engine;
import org.andengine.entity.sprite.Sprite;

public class TerrainManager {
	private static TerrainManager INSTANCE;
	private boolean mTerrainGenerationEnabled;
	private boolean mInitiated;
	private List<Terrain> mTerrains;
	private long mMoveSpeed; 
	public static final long INITIAL_MOVE_SPEED = 7;
	
	TerrainManager(){
		mTerrainGenerationEnabled = false;
		mInitiated = false;
		mMoveSpeed = INITIAL_MOVE_SPEED;
		mTerrains = new ArrayList<Terrain>();
	}
	
	public static TerrainManager getInstance(){
		if(INSTANCE == null){
			INSTANCE = new TerrainManager();
		}
		return INSTANCE;
	}
	
	public List<Terrain> getTerrains(){
		return this.mTerrains;
	}
	
	public void initTerrainGeneration(Engine mEngine){		
		//add one more terrain
		this.generateTerrain(mEngine);
		
		mInitiated = true;
	}
	
	public void generateTerrain(Engine mEngine){
		float height = 100;
		float terrainX = 0;
		Terrain terrain = null;
		if(mTerrains.size() > 0){
			Sprite lastTerrainSprite = mTerrains.get(mTerrains.size() - 1).getSprite();
			terrainX = lastTerrainSprite.getX()
					+ lastTerrainSprite.getWidth();
			
			terrain = new Terrain(terrainX, height, mEngine);
			/*
			 * Add obstacles here
			 */
		}else{
			terrain = new Terrain(terrainX, height, mEngine);
		}
		
		mTerrains.add(terrain);
		mEngine.getScene().attachChild(terrain.getSprite());
	}
	
	public long getMoveSpeed(){
		return this.mMoveSpeed;
	}
	
	public void setMoveSpeed(long pMoveSpeed){
		this.mMoveSpeed = pMoveSpeed;
	}
	
	public void moveTerrains(Engine mEngine){
		for(Terrain t : mTerrains){
			t.getSprite().setX(t.getSprite().getX() - mMoveSpeed);
		}
		if(mTerrains.size() > 0){
			Terrain firstTerrain = mTerrains.get(0);
			if(firstTerrain.getSprite().getX()
			+  firstTerrain.getSprite().getWidth()
				< 0){
				mTerrains.remove(0);
				generateTerrain(mEngine);
			}
		}
	}
	
	public void setTerrainGenerationEnabled(boolean pTerrainGenerationEnabled){
		mTerrainGenerationEnabled = pTerrainGenerationEnabled;
	}
	
	public boolean isTerrainGenerationEnabled(){
		return mTerrainGenerationEnabled;
	}
	
	public boolean hasInitiated(){
		return mInitiated;
	}
}
