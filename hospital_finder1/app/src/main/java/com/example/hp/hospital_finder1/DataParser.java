package com.example.hp.hospital_finder1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    public List<HashMap<String,String>> parse(String jsonData)
    {
        JSONArray jsonArray=null;
        JSONObject jsonObject;
        try {
            jsonObject=new JSONObject((String)jsonData);
            jsonArray =jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getAllNearByHospitals(jsonArray);
    }
    private List<HashMap<String,String>> getAllNearByHospitals(JSONArray jsonArray)
    {
        int counter=jsonArray.length();
        List<HashMap<String,String>> nearByHospitalsList=new ArrayList<>();
        HashMap<String,String> NearByPlaceMap=null;
        for(int i=0;i<counter;i++)
        {
            try {
                NearByPlaceMap=getSingleNearByHospital((JSONObject)jsonArray.get(i));
                nearByHospitalsList.add(NearByPlaceMap);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return nearByHospitalsList;
    }
    private HashMap<String,String> getSingleNearByHospital(JSONObject googlePlaceJSON)
    {
     HashMap<String,String> googlePlaceMap=new HashMap<>();
     String NameOfPlace ="NA";
        String vicinity ="NA";
        String latitude =""; String longitude =""; String reference ="";
        try {
           if(!googlePlaceJSON.isNull("name"))
           {
               NameOfPlace =googlePlaceJSON.getString("name");
           }
            if(!googlePlaceJSON.isNull("vicinity"))
            {
                NameOfPlace =googlePlaceJSON.getString("vicinity");
            }
            latitude=googlePlaceJSON.getJSONObject("geometry").getJSONObject("Location").getString("lat");
            longitude=googlePlaceJSON.getJSONObject("geometry").getJSONObject("Location").getString("lng");
            reference =googlePlaceJSON.getString("reference");
              googlePlaceMap.put("place_name",NameOfPlace);
            googlePlaceMap.put("vicinity",vicinity);
            googlePlaceMap.put("lat",latitude);
            googlePlaceMap.put("lng",longitude);
            googlePlaceMap.put("reference",reference);
        } catch (JSONException e) {
            e.printStackTrace();
        }
     return googlePlaceMap;
    }



}
