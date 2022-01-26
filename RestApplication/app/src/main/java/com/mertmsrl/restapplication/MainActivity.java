package com.mertmsrl.restapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btnGetCityById, btnWeatherById, btnWeatherByName;
    EditText editTextEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGetCityById = findViewById(R.id.btnGetCityId);
        btnWeatherById = findViewById(R.id.btnWeatherById);
        btnWeatherByName = findViewById(R.id.btnWeatherByName);
        editTextEnter = findViewById(R.id.editTextEnter);
        buttonListener();
    }

    public void buttonListener() {

        btnGetCityById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                // Instantiate the RequestQueue.
//                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//                String url = "https://www.metaweather.com/api/location/search/?query=london";
//
//                // Request a string response from the provided URL.
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // Display the first 500 characters of the response string.
//                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        editTextEnter.setText("That didn't work!");
//                    }
//                });
//
//                // Add the request to the RequestQueue.
//                queue.add(stringRequest);




                String cityName =editTextEnter.getText().toString().toLowerCase();


                WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);
                weatherDataService.getCityId(cityName, new WeatherDataService.VolleyResponseListener() {
                    @Override
                    public void onResponse(String cityId) {
                        Toast.makeText(MainActivity.this, "Main Class:"+cityId, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "something WRONG", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        btnWeatherById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cityName = editTextEnter.getText().toString();
                WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);
                String cityId = "44418";

                weatherDataService.getWeatherById(cityId);


            }
        });

        btnWeatherByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}