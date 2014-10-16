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
	
	TerrainManager(){
		mTerrainGenerationEnabled = false;
		mInitiated = false;
		mTerrains = new ArrayList<Terrain>();
	}
	
	public static TerrainManager getInstance(){
		if(INSTANCE == null){
			INSTANCE = new TerrainManager();
		}
		return INSTANCE;
	}
	
	public void initTerrainGeneration(Engine mEngine){
		//TODO set terrain generation Conditions
		this.generateTerrain(mEngine);
		this.generateTerrain(mEngine);
		
		mInitiated = true;
	}
	
	public void generateTerrain(Engine mEngine){
		float height = 100;
		float terrainX = 0;
		
		if(mTerrains.size() > 0){
			Sprite lastTerrainSprite = mTerrains.get(mTerrains.size() - 1).getSprite();
			terrainX = lastTerrainSprite.getX()
					+ lastTerrainSprite.getWidth();
		}
		
		Terrain terrain = new Terrain(terrainX, height, mEngine);
		/*
		 * Add obstacles here
		 */
		mTerrains.add(terrain);
		mEngine.getScene().attachChild(terrain.getSprite());
	}
	
	public void moveTerrains(Engine mEngine){
		for(Terrain t : mTerrains){
			t.getSprite().setX(t.getSprite().getX()-3);
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
