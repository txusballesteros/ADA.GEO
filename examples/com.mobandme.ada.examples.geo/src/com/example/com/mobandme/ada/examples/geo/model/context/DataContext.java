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

package com.example.com.mobandme.ada.examples.geo.model.context;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mobandme.ada.ObjectContext;
import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.mobandme.ada.geo.GeolocationObjectSet;
import com.example.com.mobandme.ada.examples.geo.helpers.ExceptionsHelper;
import com.example.com.mobandme.ada.examples.geo.helpers.ImportHelper;
import com.example.com.mobandme.ada.examples.geo.model.entities.Poi;

public class DataContext extends ObjectContext {

	public GeolocationObjectSet<Poi> PoisSet;
	
	public DataContext(Context pContext) { 
		super(pContext); 
		
		try {
			
			initializeContext();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initializeContext() throws AdaFrameworkException {
		PoisSet = new GeolocationObjectSet<Poi>(Poi.class, this);
	}
	
	@Override
	protected void onPopulate(SQLiteDatabase pDatabase, int pAction) {
		try {
			
			if (pAction == ACTION_CREATE) {
				PoisSet.addAll(ImportHelper.importPois(getContext()));
				PoisSet.save(pDatabase);
			}
			
		} catch (Exception e) {
			ExceptionsHelper.manage(getContext(), e);
		}
	}
}
