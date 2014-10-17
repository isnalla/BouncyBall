package com.nofacestudios.bouncyball;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Ball extends AnimatedSprite{
	private double ySpeed=1, gravity=0.3, bounce=-10;
	private Sprite mGroundSprite;
	private float camera_width, camera_height;
	
	
	public Ball(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager vertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion,
				vertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		
		
		
	}
	
	public void setGround(Sprite mGroundSprite){
		this.mGroundSprite = mGroundSprite;
	}
	
	public void setCameraDimensions(float camera_width, float camera_height){
		this.camera_width = camera_width;
		this.camera_height = camera_height;
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		super.onManagedUpdate(pSecondsElapsed);
		
		float y = this.getY();
		float landTop = mGroundSprite.getY() + mGroundSprite.getHeight()/2;
		float ballBottom = y-this.getHeight()/2;
		
		ySpeed += gravity;
		
		if (ballBottom <= landTop){
			ySpeed = bounce;
		}
		
		if (ballBottom-ySpeed < landTop) ySpeed -= landTop-(ballBottom-ySpeed);
		
		this.setY((float) (y-ySpeed));
	}
}
