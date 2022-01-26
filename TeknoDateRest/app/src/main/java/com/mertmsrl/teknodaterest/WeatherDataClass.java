package com.mertmsrl.teknodaterest;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

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

public class WeatherDataClass {


    Context context;

    final String URL_LONDON = "https://www.metaweather.com/api/location/search/?query=london";
    final String URL_SAN = "https://www.metaweather.com/api/location/search/?query=san";

    ArrayList<Cities> citiesArrayListSan = new ArrayList<>();
    ArrayList<Cities> citiesArrayListLondon = new ArrayList<>();

    public interface VolleyResponseListener {
        void onResponse(ArrayList<Cities> citiesArrayList1);

        void onError();
    }

    public interface WeatherReportByIdListener {
        void onResponse(List<ListViewDataCity> listViewDataCities);

        void onError();
    }




    public WeatherDataClass(Context context) {
        this.context = context;
    }

//    public void addCitiesToArrayList(, VolleyResponseListener volleyResponseListener){
//
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//
//    }

    public void addCitiesToArrayListSan(VolleyResponseListener volleyResponseListener) {

        // Defining a Request
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Request.Method.GET, URL_SAN, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String cityName = jsonObject.getString("title");
                        String cityId = jsonObject.getString("woeid");


                        citiesArrayListSan.add(new Cities(cityName, cityId));
                    }
                    volleyResponseListener.onResponse(citiesArrayListSan);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Singleton.getInstance(context).addToRequestQueue(jsonArrayRequest1);

    }


    public void addCitiesToArrayListLondon(VolleyResponseListener volleyResponseListener) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL_LONDON, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);
                        String cityName = jsonObject.getString("title");
                        String cityId = jsonObject.getString("woeid");
                        citiesArrayListLondon.add(new Cities(cityName, cityId));
                    }

                    volleyResponseListener.onResponse(citiesArrayListLondon);
                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Singleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void getCityWeatherBySpinner(String cityId, WeatherReportByIdListener weatherReportByIdListener){
        getCityWeatherTask(cityId, weatherReportByIdListener);

    }

    public void getCityWeatherById(String cityId, WeatherReportByIdListener weatherReportByIdListener){

        getCityWeatherTask(cityId, weatherReportByIdListener);

    }

    public void getCityWeatherTask(String cityId, WeatherReportByIdListener weatherReportByIdListener){
        List<ListViewDataCity> listViewDataCities = new ArrayList<>();
        String url = "https://www.metaweather.com/api/location/"+cityId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("consolidated_weather");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        String weatherStateName = jsonArray.getJSONObject(i).getString("weather_state_name");
                        String maxTemp = jsonArray.getJSONObject(i).getString("max_temp");
                        String minTemp = jsonArray.getJSONObject(i).getString("min_temp");
                        String applicableDate = jsonArray.getJSONObject(i).getString("applicable_date");
                        listViewDataCities.add(new ListViewDataCity(weatherStateName, maxTemp, minTemp, applicableDate));
                    }

                    weatherReportByIdListener.onResponse(listViewDataCities);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


}
