package com.nofacestudios.bouncyball;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.debug.Debug;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

public class ResourceManager {

	// ResourceManager Singleton instance
	private static ResourceManager INSTANCE;
	private static final int TEXTURE_ATLAS_SOURCE_PADDING = 2;
	/* The variables listed should be kept public, allowing us easy access
	   to them when creating new Sprites, Text objects and to play sound files */
	public ITextureRegion mGameBackgroundTextureRegion;
	public ITextureRegion mMenuBackgroundTextureRegion;
	public ITextureRegion mCharacterSpriteTextureRegion;
	public ITextureRegion mBallTextureRegion;
	public ITextureRegion mGroundTextureRegion;
	public ITiledTextureRegion mBallTiledTextureRegion;
	
	public Sound mSound;

	public Font	mFont;

	public Music mMusic;

	ResourceManager(){
		// The constructor is of no use to us
	}

	public synchronized static ResourceManager getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ResourceManager();
		}
		return INSTANCE;
	}

	/* Each scene within a game should have a loadTextures method as well
	 * as an accompanying unloadTextures method. This way, we can display
	 * a loading image during scene swapping, unload the first scene's textures
	 * then load the next scenes textures.
	 */
	public synchronized void loadGameTextures(Engine pEngine, Context pContext){
		// Set our game assets folder in "assets/gfx/game/"
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");

		BuildableBitmapTextureAtlas mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(pEngine.getTextureManager(), 800, 480 + TEXTURE_ATLAS_SOURCE_PADDING*2);
		
		mGameBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, pContext, "background.png");
		mCharacterSpriteTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, pContext, "character.png");
		mBallTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, pContext, "ball.png");
	
		BuildableBitmapTextureAtlas mBitmapTextureAtlasRepeating = new BuildableBitmapTextureAtlas(
				pEngine.getTextureManager(),
				128, 32,
				TextureOptions.REPEATING_BILINEAR);
		mGroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlasRepeating, pContext, "ground.png");

		BuildableBitmapTextureAtlas mBallTiledBitmapTextureAtlas = 
				new BuildableBitmapTextureAtlas(
						pEngine.getTextureManager(), 
						520, 269);
		
		mBallTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				mBallTiledBitmapTextureAtlas, 
				pContext, 
				"ball_tiled.png", 
				8, 
				4);
		try {
			mBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource,BitmapTextureAtlas>(0, 1, TEXTURE_ATLAS_SOURCE_PADDING));
			mBitmapTextureAtlas.load();
			mBitmapTextureAtlasRepeating.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource,BitmapTextureAtlas>(0, 0, 0));
			mBitmapTextureAtlasRepeating.load();
			mBallTiledBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource,BitmapTextureAtlas>(0, 0, 0));
			mBallTiledBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}
	
	/* All textures should have a method call for unloading once
	 * they're no longer needed; ie. a level transition. */
	public synchronized void unloadGameTextures(){
		// call unload to remove the corresponding texture atlas from memory
		BuildableBitmapTextureAtlas mBitmapTextureAtlas = (BuildableBitmapTextureAtlas) mGameBackgroundTextureRegion.getTexture();
		mBitmapTextureAtlas.unload();
		
		// ... Continue to unload all textures related to the 'Game' scene
		
		// Once all textures have been unloaded, attempt to invoke the Garbage Collector
		System.gc();
	}
	
	/* Similar to the loadGameTextures(...) method, except this method will be
	 * used to load a different scene's textures
	 */
	public synchronized void loadMenuTextures(Engine pEngine, Context pContext){
		// Set our menu assets folder in "assets/gfx/menu/"
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		
		BuildableBitmapTextureAtlas mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(pEngine.getTextureManager() ,800 , 480 + TEXTURE_ATLAS_SOURCE_PADDING*2);
		
		mMenuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, pContext, "background.png");
		
		try {
			mBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, TEXTURE_ATLAS_SOURCE_PADDING));
			mBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		
	}
	
	// Once again, this method is similar to the 'Game' scene's for unloading
	public synchronized void unloadMenuTextures(){
		// call unload to remove the corresponding texture atlas from memory
		BuildableBitmapTextureAtlas mBitmapTextureAtlas = (BuildableBitmapTextureAtlas) mMenuBackgroundTextureRegion.getTexture();
		mBitmapTextureAtlas.unload();
		
		// ... Continue to unload all textures related to the 'Game' scene
		
		// Once all textures have been unloaded, attempt to invoke the Garbage Collector
		System.gc();
	}
	
	/* As with textures, we can create methods to load sound/music objects
	 * for different scene's within our games.
	 */
	public synchronized void loadSounds(Engine pEngine, Context pContext){
		// Set the SoundFactory's base path
		SoundFactory.setAssetBasePath("sounds/");
		MusicFactory.setAssetBasePath("sounds/");
		 try {
			 // Create mSound object via SoundFactory class
			 mSound	= SoundFactory.createSoundFromAsset(pEngine.getSoundManager(), pContext, "click.mp3");
			 Log.v("SOUND:",mSound.toString());
		 } catch (final IOException e) {
             Log.v("Sounds Load","Exception:" + e.getMessage());
		 }
		 
/*		// Load our "music.mp3" file into a music object
		 try {
			 mMusic = MusicFactory.createMusicFromAsset(pEngine.getMusicManager(), pContext, "music.mp3");
		 } catch (IOException e) {
			 e.printStackTrace();
		 }*/
	}	
	
	/* In some cases, we may only load one set of sounds throughout
	 * our entire game's life-cycle. If that's the case, we may not
	 * need to include an unloadSounds() method. Of course, this all
	 * depends on how much variance we have in terms of sound
	 */
	public synchronized void unloadSounds(){
		// we call the release() method on sounds to remove them from memory
		if(!mSound.isReleased()) mSound.release();
	}
	
	/* Lastly, we've got the loadFonts method which, once again,
	 * tends to only need to be loaded once as Font's are generally 
	 * used across an entire game, from menu to shop to game-play.
	 */
	public synchronized void loadFonts(Engine pEngine){
		FontFactory.setAssetBasePath("fonts/");
		
		// Create mFont object via FontFactory class
		mFont = FontFactory.create(pEngine.getFontManager(), pEngine.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL),  32f, true, org.andengine.util.adt.color.Color.WHITE_ABGR_PACKED_INT);

		mFont.load();
	}
	
	/* If an unloadFonts() method is necessary, we can provide one
	 */
	public synchronized void unloadFonts(){
		// Similar to textures, we can call unload() to destroy font resources
		if(!(mFont == null)) mFont.unload();
	}
}
