package com.mertmsrl.teknodaterest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnGetCityId, btnGetWeatherById, btnGetWeatherByName, btnSearch;
    EditText editTextEnter;
    Spinner spinnerCities;
    TextView textViewCityId;
    Context context = MainActivity.this;
    String selectedCityId = "";
    ListView listViewCities ;
    static int index = 0;

    WeatherDataClass weatherDataClassObject = new WeatherDataClass(context);

    List<ListViewDataCity> listViewDataCityList = new ArrayList<>();

    ArrayList<Cities> citiesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        citiesArrayList = new ArrayList<>();


        // EditText
        editTextEnter = findViewById(R.id.editTextEnter);


        // Buttons
        btnGetCityId = findViewById(R.id.btnGetCityId);
        btnGetWeatherById = findViewById(R.id.btnGetCityWeatherById);
        btnGetWeatherByName = findViewById(R.id.btnGetCityWeatherByName);
        btnSearch = findViewById(R.id.btnSearch);

        // Spinner Defining
        spinnerCities = findViewById(R.id.spinnerCities);

        // TextView Defining
        textViewCityId = findViewById(R.id.textViewCityId);

        // ListView Defining
        listViewCities = findViewById(R.id.listViewCitiesWeather);


//        addCitiesToArrayList();
        addCitiesToArrayList();
        spinnerListener();
        buttonSearchListener();



    }

    public void addCitiesToArrayList() {

        while (index < 1) {
            List<String> stringList = new ArrayList<>();
            weatherDataClassObject.addCitiesToArrayListSan(new WeatherDataClass.VolleyResponseListener() {
                @Override
                public void onResponse(ArrayList<Cities> citiesArrayList1) {


                    for (Cities city : citiesArrayList1) {
                        stringList.add(city.getCityName());
                        citiesArrayList.add(city);
                    }

                }

                @Override
                public void onError() {

                }
            });

            weatherDataClassObject.addCitiesToArrayListLondon(new WeatherDataClass.VolleyResponseListener() {
                @Override
                public void onResponse(ArrayList<Cities> citiesArrayList1) {

                    for (Cities city : citiesArrayList1) {
                        stringList.add(city.getCityName());
                        citiesArrayList.add(city);

                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, stringList);
                    spinnerCities.setAdapter(arrayAdapter);

                }

                @Override
                public void onError() {

                }
            });
            index++;
        }
    }





    public void spinnerListener() {
        spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);

                int indexOfObject = findObjectIndex(citiesArrayList, item.toString());
                selectedCityId = citiesArrayList.get(indexOfObject).getCityId();
                textViewCityId.setText(selectedCityId);
                setListViewData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public int findObjectIndex(ArrayList<Cities> citiesArrayList, String cityName) {
        int index = 0;
        for (Cities city : citiesArrayList) {
            if (city.getCityName().equals(cityName)) {
                return index;
            }
            index++;
        }
        return index;
    }

    public void setListViewData(){
        weatherDataClassObject.getCityWeatherBySpinner(selectedCityId, new WeatherDataClass.WeatherReportByIdListener() {
            @Override
            public void onResponse(List<ListViewDataCity> listViewDataCities) {
                ListViewAdapter adapter = new ListViewAdapter(listViewDataCities,context);
                listViewDataCityList = listViewDataCities;
                listViewCities.setAdapter(adapter);
            }

            @Override
            public void onError() {

            }
        });
    }

    public void buttonSearchListener(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weatherDataClassObject.getCityWeatherById(selectedCityId, new WeatherDataClass.WeatherReportByIdListener() {
                    @Override
                    public void onResponse(List<ListViewDataCity> listViewDataCities) {
                        ListViewAdapter adapter = new ListViewAdapter(listViewDataCities, context);
                        listViewCities.setAdapter(adapter);
                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        });
    }







}