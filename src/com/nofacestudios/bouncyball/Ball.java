package com.nofacestudios.bouncyball;

import java.util.List;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Ball extends AnimatedSprite{
	private double ySpeed=1, gravity=0.3, bounce=-10;
	private Object collidedObject;
	private float mBallBottom = 0;
	
	public Ball(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager vertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion,
				vertexBufferObjectManager);
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {

		float y = this.getY();
		
		ySpeed += gravity;

	
		y = (float) (y-ySpeed);
		this.setY(y);
		
		collidedObject = checkForCollision();
		
		if(collidedObject instanceof Terrain){
			ySpeed = bounce;

			Sprite collidedObjectSprite = ((Terrain) collidedObject).getSprite();
			float landTop = collidedObjectSprite.getY() + collidedObjectSprite.getHeight()/2;

			y = (landTop + getHeight()/2 + 2);
		}
		
		this.setY(y);
		
		super.onManagedUpdate(pSecondsElapsed);

	}
	
	@Override
	protected void onManagedDraw(GLState pGLState, Camera pCamera) {
		// TODO Auto-generated method stub
		super.onManagedDraw(pGLState, pCamera);
	}
	
	private Object checkForCollision(){
		List<Terrain> terrains = TerrainManager.getInstance().getTerrains();
		
		for(Terrain t : terrains){
			if(t.getSprite().collidesWith(this)){
				return t;
			}
		}
		return null;
	}
}
