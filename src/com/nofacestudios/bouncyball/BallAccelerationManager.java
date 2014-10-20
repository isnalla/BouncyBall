package com.nofacestudios.bouncyball;

import org.andengine.engine.options.EngineOptions;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;

import android.util.Log;

public class BallAccelerationManager implements IAccelerationListener {
	MainActivity mainActivity;
	
	
	public BallAccelerationManager(MainActivity mainActivity){
		this.mainActivity = mainActivity;
	}
	
	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub
		
		TerrainManager tm =TerrainManager.getInstance(); 
		long accel = (long) pAccelerationData.getX();
		long normalSpeed = TerrainManager.INITIAL_MOVE_SPEED;
		long minSpeed = (long) (normalSpeed * 0.5);
		long maxSpeed = (long) (normalSpeed * 2.0);

		long speed = normalSpeed + accel;
		
		
		if (speed < minSpeed) speed = minSpeed;
		if (speed > maxSpeed) speed = maxSpeed;
		
		tm.setMoveSpeed(speed );
	}
	
}
