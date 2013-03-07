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

package com.example.com.mobandme.ada.examples.geo.model.entities;

import com.google.android.gms.maps.model.LatLng;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;
import com.mobandme.ada.annotations.TableIndex;
import com.mobandme.ada.geo.GeopointEntity;

@Table(name = "Poi")
public class Poi extends GeopointEntity {

	@TableField(name = "Name", datatype = DATATYPE_STRING, getterSuffix = "Name", setterSuffix = "Name")
	private String mName;

	@TableField(name = "Type", datatype = DATATYPE_INTEGER, getterSuffix = "Type", setterSuffix = "Type")
	@TableIndex(name = LOCATION_INDEX) 
	private int mType;
	
	public String getName() { return mName; }
	public void setName(String pName) { mName = pName; }
	
	public int getType() { return mType; }
	public void setType(int pType) { mType = pType; }
	
	public Poi() { }
	public Poi(double pLatitude, double pLongitude, int pType) { 
		super(pLatitude, pLongitude);
		setType(pType);
	}
	
	public LatLng getLatLng() {
		return new LatLng(getLatitude(), getLongitude());
	}
}
