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


package com.example.com.mobandme.ada.examples.geo.adapters;

import com.example.com.mobandme.ada.examples.geo.R;
import com.example.com.mobandme.ada.examples.geo.helpers.ExceptionsHelper;
import com.example.com.mobandme.ada.examples.geo.model.entities.Poi;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobandme.ada.geo.GeopointEntity;
import android.content.Context;
import android.widget.ArrayAdapter;

public class PoisMapAdapter extends ArrayAdapter<GeopointEntity> {

	private GoogleMap mGoogleMap;

	public PoisMapAdapter(Context pContext, GoogleMap pGoogleMap) {
		super(pContext, 0);
		mGoogleMap   = pGoogleMap;
	}

	
	@Override
	public void add(GeopointEntity pPoi) {
		try {
			
			if (mGoogleMap != null) {
				MarkerOptions marker = new MarkerOptions()
						.position(((Poi)pPoi).getLatLng())
						.visible(true)
						.draggable(true)
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));
				
				mGoogleMap.addMarker(marker);
			}
			
		} catch (Exception e) {
			ExceptionsHelper.manage(getContext(), e);
		} finally {
			super.add(pPoi);
		}
	}
	
	@Override
	public void clear() {
		try {
			
			mGoogleMap.clear();
			
		} catch (Exception e) {
			ExceptionsHelper.manage(getContext(), e);
		} finally {
			super.clear();
		}
	}
}
