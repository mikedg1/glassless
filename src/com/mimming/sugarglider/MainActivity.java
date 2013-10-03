package com.mimming.sugarglider;
//Code for this project is licensed under APL 2.0 and content is licensed under the Creative Commons Attribution 3.0 License.
//https://github.com/mimming/sugarglider
//Thanks to Jenny Murphy of Google

import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.mikedg.android.glass.glassless.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends Activity {

    protected static final String TAG = "sugarglider";
	private LocationManager locationManager;
	private ImageView floatingMap;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_sugar);
        
        floatingMap = (ImageView) findViewById(R.id.imageView1);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    }

	@Override
	protected void onPause() {
		super.onPause();

		locationManager.removeUpdates(locationListener);
	}

	@Override
	protected void onResume() {
		super.onResume();

	    List<String> providers = locationManager.getAllProviders();
	    for (String provider : providers) {
	        if (locationManager.isProviderEnabled(provider)) {
	            locationManager.requestLocationUpdates(provider, 5000, 10, locationListener);
	        }
	    }		
	}

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location loc) {
            Log.v(TAG, "Location manager detected a change");
            String url = "http://maps.googleapis.com/maps/api/staticmap?center=" +
                    loc.getLatitude() + "," + loc.getLongitude() +
                    "&zoom=17&size=630x350&maptype=roadmap&markers=color:green%7Clabel:G%7C" +
                    loc.getLatitude() + "," + loc.getLongitude() +
                    "&sensor=false&key=AIzaSyB86OWhdiF64GeNbugDTr_xDK3ezrHWlI8" +
                    "&style=feature:road.local%7Celement:geometry%7Ccolor:0x009900%7Cweight:1%7Cvisibility:on&style=feature:landscape%7Celement:geometry.fill%7Ccolor:0x000000%7Cvisibility:on&style=feature:administrative%7Celement:labels%7Cweight:3.9%7Cvisibility:on%7Cinvert_lightness:true&style=feature:poi%7Cvisibility:simplified";
            new ImageFetcher().execute(url);
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    private class ImageFetcher extends AsyncTask<String, Void, Drawable> {

        private Exception exception;

        @Override
        protected Drawable doInBackground(String... urls) {
            try {
                String url = urls[0];
//                InputStream is = (InputStream) new URL("http://mimming.com/index_assets/headshot_logo.jpg").getContent();
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet(url);
                HttpResponse response = httpclient.execute(request);

                InputStream is = response.getEntity().getContent();

//                HttpURLConnection connection = (HttpURLConnection)new URL(urls[0]).openConnection();
//                InputStream is = connection.getInputStream();

                return Drawable.createFromStream(is, "src name");
            } catch (Exception e) {
                Log.v(TAG, "Failed to load image: " + e.getMessage());
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            Log.v(TAG, "Got image, drawoing map");

            floatingMap.setImageDrawable(drawable);
            floatingMap.setMinimumHeight(360);
            floatingMap.setMinimumWidth(640);
        }
    }

}
