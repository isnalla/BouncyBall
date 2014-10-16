package com.nofacestudios.bouncyball;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.DrawMode;
import org.andengine.entity.primitive.Mesh;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.color.Color;

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
		mScene = new Scene();
		// Notify the callback that we're finished creating the scene, returning
		// mScene to the mEngine object (handled automatically)
		pOnCreateSceneCallback.onCreateSceneFinished(mScene);	
	}
	
	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) {
		
		// Set the raw points for our rectangle mesh
		float baseBuffer[] = {
				// Triangle one
				-200, -100, 0, // point one
				200, -100, 0, // point two
				200, 100, 0, // point three
				
				// Triangle two
				200, 100, 0, // point one
				-200, 100, 0, // point two
				-200, -100, 0 // point three
		};
		// Create the base mesh at the bottom of the screen
		Mesh meshBase = new Mesh(400, 480 - 100, baseBuffer, 6, DrawMode.TRIANGLES, mEngine.getVertexBufferObjectManager());

		// Set the meshes color to a 'brown' color
		meshBase.setColor(0.45f, 0.164f, 0.3f);
		// Attach base mesh to the scene
		mScene.attachChild(meshBase);
		
		// Create the raw points for our triangle mesh
		float roofBuffer[] = {
				// Triangle
				-300, 0, 0, // point one
				0, -200, 0, // point two
				300, 0, 0, // point three
		};
		
		// Create the roof mesh above the base mesh
		Mesh meshRoof = new Mesh(400, 480 - 200, roofBuffer, 3, DrawMode.TRIANGLES, mEngine.getVertexBufferObjectManager());
		
		meshRoof.setColor(Color.RED);
		// Attach the roof to the scene
		mScene.attachChild(meshRoof);
		
		
		// Create the raw points for our line mesh
		float doorBuffer[] = {
				-25, -100, 0, // point one
				25, -100, 0, // point two
				25, 0, 0, // point three
				-25, 0, 0, // point four
				-25, -100, 0 // point five
		};
		
		// Create the door mesh
		Mesh meshDoor = new Mesh(400, 480, doorBuffer, 5, DrawMode.LINE_STRIP, mEngine.getVertexBufferObjectManager());
		
		meshDoor.setColor(Color.BLUE);
		// Attach the door to the scene
		mScene.attachChild(meshDoor);
			
		final float positionX = CAMERA_WIDTH * 0.5f;
		final float positionY = CAMERA_HEIGHT * 0.5f;
		/* Add our marble sprite to the bottom left side of the Scene
		initially */
		Sprite mCharacterSprite = new Sprite(positionX, positionY,
				ResourceManager.getInstance().mCharacterSpriteTextureRegion, mEngine.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				ResourceManager
				.getInstance()
				.mSound
				.play();
				return super
						.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};

		/* The last step is to attach our Sprite to the Scene, as is
		necessary in order to display any type of Entity on the device's
		display: */
		/* Attach the marble to the Scene */
		mScene.attachChild(mCharacterSprite);
		mScene.registerTouchArea(mCharacterSprite);
		
		Text mSampleText = new Text(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, 
				ResourceManager.getInstance().mFont,
				"Hello World!", 
				getVertexBufferObjectManager());
		
		mScene.attachChild(mSampleText);
		
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

}
