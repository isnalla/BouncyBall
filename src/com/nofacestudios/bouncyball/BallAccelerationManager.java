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
		
//		pAccelerationData.getX()
	}
	
}
