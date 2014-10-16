package com.nofacestudios.bouncyball;

import java.io.IOException;
import java.util.List;

import org.andengine.audio.music.Music;
import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;

public class MainActivity extends BaseGameActivity {

	private static final float CAMERA_WIDTH = 800;
	private static final float CAMERA_HEIGHT = 480;
	private Scene mScene;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		// Define our mCamera object
		Camera mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		// Declare & Define our engine options to be applied to our Engine object
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, 
				new FillResolutionPolicy(), 
				mCamera);
		// It is necessary in a lot of applications to define the following
		// wake lock options in order to disable the device's display
		// from turning off during gameplay due to inactivity Chapter 19 
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		// Return the engineOptions object, passing it to the engine
		return engineOptions;
	}
	
	@Override
	public synchronized void onResumeGame() {
		Music mMusic = ResourceManager.getInstance().mMusic;
		if(mMusic != null && !mMusic.isPlaying()){
			mMusic.play();
		}
		super.onResumeGame();
	}
	
	/* Music objects which loop continuously should be paused in
	* onPauseGame() of the activity life cycle
	*/
	@Override
	public synchronized void onPauseGame() {
		Music mMusic = ResourceManager.getInstance().mMusic;
		if(mMusic != null && mMusic.isPlaying()){
			mMusic.pause();
		}
		super.onPauseGame();
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		// Create a fixed step engine updating at 60 steps per second
		return new FixedStepEngine(pEngineOptions, 60);
	}
	
	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws IOException {
		/* We should notify the pOnCreateResourcesCallback that we've
		finished
		* loading all of the necessary resources in our game AFTER
		they are loaded.
		* onCreateResourcesFinished() should be the last method
		called. */
		ResourceManager rm = ResourceManager.getInstance();
		// Load the game texture resources
		rm.loadGameTextures(mEngine, this);
		// Load the font resources
		rm.loadFonts(mEngine);
		// Load the sound resources
		rm.loadSounds(mEngine, this);

		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}
	
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws IOException {
		// Create the Scene object
		mScene = new Scene(){
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				TerrainManager tm = TerrainManager.getInstance();
				if(tm.isTerrainGenerationEnabled()){
					if(!tm.hasInitiated()){
						tm.initTerrainGeneration(mEngine);
					}
					TerrainManager.getInstance().moveTerrains(mEngine);
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
		};
		// Notify the callback that we're finished creating the scene, returning
		// mScene to the mEngine object (handled automatically)
		pOnCreateSceneCallback.onCreateSceneFinished(mScene);	
	}
	
	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) {
		
		int x = 5;
		AnimatedSprite mBallAnimatedSprite = new AnimatedSprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/3, 
				ResourceManager.getInstance().mBallTiledTextureRegion,
				getVertexBufferObjectManager()){
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				GameManager.getInstance().startGame();
				Log.d("helo","hi");
				return super
						.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
			}
		};
		Log.d("hi","hello");
		mScene.attachChild(mBallAnimatedSprite);
		mScene.registerTouchArea(mBallAnimatedSprite);
		
		mBallAnimatedSprite.animate(100, true);
		
		ResourceManager.getInstance().mGroundTextureRegion.setTextureSize(CAMERA_WIDTH, 100);
		Sprite mGroundSprite = new Sprite(CAMERA_WIDTH/2, 
				ResourceManager.getInstance().mGroundTextureRegion.getHeight()/2
				, ResourceManager.getInstance().mGroundTextureRegion, getVertexBufferObjectManager());		

		mScene.attachChild(mGroundSprite);
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

}
