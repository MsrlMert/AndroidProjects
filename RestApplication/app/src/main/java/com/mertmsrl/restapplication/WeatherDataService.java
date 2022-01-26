package com.mertmsrl.restapplication;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {

    public static final String QUERY_FOR_NAME = "https://www.metaweather.com/api/location/search/?query=";
    public static final String QUERY_FOR_WEATHER_BY_ID = "https://www.metaweather.com/api/location/";
    Context context;
    String cityId;


    // Constructor
    public WeatherDataService(Context context) {
        this.context = context;
    }


    public interface VolleyResponseListener {
        void onResponse(String cityId);


        void onError(String message);

    }

    public String getCityId(String cityName, VolleyResponseListener volleyResponseListener) {
        String url = QUERY_FOR_NAME + cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                cityId = "";
                String cityNameJson = "";

                try {
                    JSONObject jsonObject = response.getJSONObject(0);

                    cityNameJson = jsonObject.getString("title");
                    cityId = jsonObject.getString("woeid");
//                    Toast.makeText(context, "External Class:"+cityId, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();

                }
//                Toast.makeText(context, "City Name : "+cityNameJson+" \nCity Id: "+ cityId, Toast.LENGTH_LONG).show();
                volleyResponseListener.onResponse(cityId);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Not Worked", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Something wrong");

            }

        });
        Singleton.getInstance(context).addToRequestQueue(request);
        return cityId;


    }


    public void getWeatherById(String cityId) {

        List<WeatherReportModel> reportModel = new ArrayList<>();


        String url = QUERY_FOR_WEATHER_BY_ID + cityId;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Singleton.getInstance(context).addToRequestQueue(request);

    }
}


//        public List<WeatherReportModel> getWeatherByName(){
//
//    }
