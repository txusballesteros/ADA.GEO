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


package com.example.com.mobandme.ada.examples.geo.tasks;

import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import com.google.android.gms.maps.model.LatLngBounds;
import com.example.com.mobandme.ada.examples.geo.model.context.DataBase;
import com.example.com.mobandme.ada.examples.geo.helpers.ExceptionsHelper;


public class MapMarkersTask extends AsyncTask<LatLngBounds, Integer, Boolean> {

	private final static int MARKERS_LIMIT = 200; //Limit of markers to be shown on the Map.
	public  static boolean isRunning = false;
	
	@Override
	protected void onPreExecute() {
		try {
			
			isRunning = true;
			
		} catch (Exception e) {
			ExceptionsHelper.manage(null, e);
		} finally {
			super.onPreExecute();
		}
	}
	
	@Override
	protected Boolean doInBackground(LatLngBounds... pParameters) {
		try {
			
			if (pParameters != null && pParameters.length > 0) {
				LatLngBounds currentLocation = pParameters[0];

				Location northeast = new Location(LocationManager.GPS_PROVIDER);
				northeast.setLatitude(currentLocation.northeast.latitude);
				northeast.setLongitude(currentLocation.northeast.longitude);
				
				Location southwest = new Location(LocationManager.GPS_PROVIDER);
				southwest.setLatitude(currentLocation.southwest.latitude);
				southwest.setLongitude(currentLocation.southwest.longitude);
				
				//DataBase.Context.PoisSet.fillInsideOf(northeast, southwest, MARKERS_LIMIT);
				DataBase.Context.PoisSet.fillInsideOf(northeast, southwest, "Type = ?", new String[] { "10" }, null, null, MARKERS_LIMIT);
			}
			
		} catch (Exception e) {
			ExceptionsHelper.manage(null, e);
		}
		
		return true;
	}
	
	@Override
	protected void onPostExecute(Boolean pResult) {
		try {
			
			isRunning = false;
			
		} catch (Exception e) {
			ExceptionsHelper.manage(null, e);
		} finally {
			super.onPostExecute(pResult);
		}
	}
}
