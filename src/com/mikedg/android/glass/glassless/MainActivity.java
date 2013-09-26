package com.mikedg.android.glass.glassless;

import com.google.glass.samples.compass.CompassActivity;
import com.google.glass.samples.level.LevelActivity;
import com.google.glass.samples.stopwatch.StopWatchActivity;
import com.google.glass.samples.waveform.WaveformActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	}

	
}
