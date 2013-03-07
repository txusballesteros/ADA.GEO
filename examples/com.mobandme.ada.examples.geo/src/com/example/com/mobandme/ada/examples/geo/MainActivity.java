/**
   Copyright Mob&Me 2013 (@MobAndMe)

   Licensed under the GPL General Public License, Version 3.0 (the "License"),  
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.gnu.org/licenses/gpl.html

   Unless required by applicable law or agreed to in writing, software 
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   
   Website: http://adaframework.com
   Contact: Txus Ballesteros <txus.ballesteros@mobandme.com>
*/

package com.example.com.mobandme.ada.examples.geo;

import com.example.com.mobandme.ada.examples.geo.adapters.PoisMapAdapter;
import com.example.com.mobandme.ada.examples.geo.helpers.DistancesHelper;
import com.example.com.mobandme.ada.examples.geo.helpers.ExceptionsHelper;
import com.example.com.mobandme.ada.examples.geo.model.context.DataBase;
import com.example.com.mobandme.ada.examples.geo.tasks.MapMarkersTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;

public class MainActivity extends Activity implements OnCameraChangeListener {

	private static final int DEFAULT_ZOOM = 14;
	private static final int DEFAULT_BEARING = 0;
	private static final int DEFAULT_TILT = 45;
	
	private MapFragment    mMapFragment;
	private GoogleMap      mGoogleMap;
	private PoisMapAdapter mMapAdapter;
	private LatLng         mCurrentCameraTarget;
	
	@Override
	public void onCameraChange(CameraPosition pCamera) {
		try {
			
			if (mCurrentCameraTarget != null && !mCurrentCameraTarget.equals(pCamera.target)) {
				fillMapMarkers();				
			}
			mCurrentCameraTarget = pCamera.target;
			
		} catch (Exception e) {
			ExceptionsHelper.manage(this, e);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initializeActivity();
	}
	
	private void fillMapMarkers() {
		if (mGoogleMap != null) {
			LatLngBounds mapBounds = mGoogleMap.getProjection().getVisibleRegion().latLngBounds;
			
			if (!MapMarkersTask.isRunning) {
				new MapMarkersTask().execute(mapBounds);
			}
		}
	}

	private void initializeActivity() {
		try {
			
			DataBase.initializeContext(this);

			setContentView(R.layout.activity_map);
			
			initializeActionBar();
			initializeMap();
			
		} catch (Exception e) {
			ExceptionsHelper.manage(this, e);
		}
	}
	
	private void initializeActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
	}
	
	private void initializeMap() {
		mMapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.poisMap);
		
		if (mMapFragment != null) {
			mGoogleMap = mMapFragment.getMap();
			
			if (mGoogleMap != null) {
				mMapAdapter = new PoisMapAdapter(this, mGoogleMap);
				DataBase.Context.PoisSet.setAdapter(mMapAdapter);
				
				mGoogleMap.setMyLocationEnabled(false);
				mGoogleMap.getUiSettings().setCompassEnabled(false);
				mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
				mGoogleMap.getUiSettings().setTiltGesturesEnabled(true);				
				mGoogleMap.setOnCameraChangeListener(this);

				initializeCamera();
			}
		}
	}
	
	private void initializeCamera() {
		if (mGoogleMap != null) {
			
			mCurrentCameraTarget = new LatLng(DistancesHelper.CURRENT_LATITUDE, DistancesHelper.CURRENT_LONGITUDE);
			mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder()
					.target(mCurrentCameraTarget)
					.zoom(DEFAULT_ZOOM)
				    .bearing(DEFAULT_BEARING)
				    .tilt(DEFAULT_TILT)
				    .build()));
		}
	}
}
