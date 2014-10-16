package com.nofacestudios.bouncyball;

import java.util.LinkedList;
import java.util.List;

import org.andengine.engine.Engine;
import org.andengine.entity.sprite.Sprite;

public class Terrain {
	private List<Obstacle> mObstacles;
	private Sprite mTerrainSprite;
	
	Terrain(float xPos, float height, Engine mEngine){
		mObstacles = new LinkedList<Obstacle>();
		mTerrainSprite = new Sprite(
				xPos, height/2, 
				mEngine.getCamera().getWidth()*2,
				height,
				ResourceManager.getInstance().mGroundTextureRegion,
				mEngine.getVertexBufferObjectManager()
			);
	}
	
	public void addObstacle(Obstacle pObstacle){
		mObstacles.add(pObstacle);
	}
	
	public Sprite getSprite(){
		return mTerrainSprite;
	}
}
