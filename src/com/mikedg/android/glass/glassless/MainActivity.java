package com.mikedg.android.glass.glassless;

import java.util.List;

import com.google.glass.samples.compass.CompassActivity;
import com.google.glass.samples.level.LevelActivity;
import com.google.glass.samples.stopwatch.StopWatchActivity;
import com.google.glass.samples.waveform.WaveformActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements OnInitListener {

	private TextToSpeech mTts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        mTts = new TextToSpeech(this, this);
        
        readAllSensors();
	}

	private void readAllSensors() {
		SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
		for (Sensor sensor: deviceSensors) {
			Log.d("Glassless", sensor.getName());
		}
	}

	@Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        //First spot we can open the options menu on phones, works differently on Glass
        //But this is the best spot anyway
        openOptionsMenu();
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//There's no easy way to change the characteristics of the menu
		//The text color should be white
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				openOptionsMenu();
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.waveform_menu_item:
				startActivity(new Intent(this, WaveformActivity.class));
				break;
			case R.id.stop_wtch_menu_item:
				startActivity(new Intent(this, StopWatchActivity.class));
				break;
			case R.id.level_menu_item:
				startActivity(new Intent(this, LevelActivity.class));
				break;
			case R.id.compass_menu_item:
				startActivity(new Intent(this, CompassActivity.class));
				break;
			case R.id.sugar_menu_item:
				startActivity(new Intent(this, com.mimming.sugarglider.MainActivity.class));
				break;
			case R.id.tts_menu_item:
				testTts();
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	}

	public void testTts() {
		mTts.speak("Hi there", TextToSpeech.QUEUE_FLUSH, null);
		mTts.speak("Testing TTS", TextToSpeech.QUEUE_ADD, null);
	}
	
	@Override
	public void onInit(int status) {
	}
}
