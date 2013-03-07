package com.example.com.mobandme.ada.examples.geo.helpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.example.com.mobandme.ada.examples.geo.model.entities.Poi;

public class ImportHelper {

	private final static String POIS_ASSET_NAME = "pois.dat";
	
	@SuppressWarnings("finally")
	public static List<Poi> importPois(Context pContext) {
		List<Poi> returnedValue = new ArrayList<Poi>();
		
		try {
			
			if (pContext != null) {
				InputStream input = pContext.getAssets().open(POIS_ASSET_NAME);
				if (input != null) {
	
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(input));
					if (reader != null) {
						String fileLine = null;
						while ((fileLine = reader.readLine()) != null) {
							if (fileLine != null && !fileLine.trim().equals("")) {
								String[] poiData = fileLine.split(",");
								
								if (poiData != null && poiData.length >= 3) {
									returnedValue.add(new Poi(Double.parseDouble(poiData[1]), Double.parseDouble(poiData[0]), Integer.parseInt(poiData[2])));
								}
							}
						}
						reader.close();
					}
	
					input.close();
				}
			}
			
		} catch (Exception e) {
			ExceptionsHelper.manage(pContext, e);
		} finally {
			return returnedValue;
		}
	}
}
