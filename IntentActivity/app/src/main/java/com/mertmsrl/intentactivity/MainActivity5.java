package com.mertmsrl.intentactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity5 extends AppCompatActivity {

    ListView listViewFlights;
    EditText editTextSearchFlight;
    Context context = this;
    List<Flights> flights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        init();
        setFlightsData();
        setFlightAdapter();
    }


    public void init() {
        listViewFlights = findViewById(R.id.listViewFlights);

        editTextSearchFlight = findViewById(R.id.editTextSearchFlight);
    }

    public void setFlightAdapter() {

        FlightCustomAdapter flightCustomAdapter = new FlightCustomAdapter(flights,context);
        listViewFlights.setAdapter(flightCustomAdapter);
    }

    public void setFlightsData() {

        flights = new ArrayList<>();
        flights.add(new Flights("Sabiha Gökçen Airport", "SGH", "Istanbul", "Ankara", R.drawable.sabiha_gokcen_airport));
        flights.add(new Flights("Esenboğa Airport", "ESB", "Ankara", "Istanbul", R.drawable.esenboga_airport));
        flights.add(new Flights("Adnan Menderes Airport", "AMA", "Istanbul", "Ankara", R.drawable.adnan_menderes_airport));
        flights.add(new Flights("Sabiha Gökçen Airport", "SGH", "Istanbul", "Izmir", R.drawable.sabiha_gokcen_airport));
        flights.add(new Flights("Atatürk Airport", "AHL", "Istanbul", "Izmir", R.drawable.ataturk_airport));
        flights.add(new Flights("Sabiha Gökçen Airport", "SGH", "Istanbul", "Ankara", R.drawable.sabiha_gokcen_airport));
        flights.add(new Flights("Esenboğa Airport", "ESB", "Ankara", "Istanbul", R.drawable.esenboga_airport));
        flights.add(new Flights("Adnan Menderes Airport", "AMA", "Istanbul", "Ankara", R.drawable.adnan_menderes_airport));
        flights.add(new Flights("Sabiha Gökçen Airport", "SGH", "Istanbul", "Izmir", R.drawable.sabiha_gokcen_airport));
        flights.add(new Flights("Atatürk Airport", "AHL", "Istanbul", "Izmir", R.drawable.ataturk_airport));
        flights.add(new Flights("Sabiha Gökçen Airport", "SGH", "Istanbul", "Ankara", R.drawable.sabiha_gokcen_airport));
        flights.add(new Flights("Esenboğa Airport", "ESB", "Ankara", "Istanbul", R.drawable.esenboga_airport));
        flights.add(new Flights("Adnan Menderes Airport", "AMA", "Istanbul", "Ankara", R.drawable.adnan_menderes_airport));
        flights.add(new Flights("Sabiha Gökçen Airport", "SGH", "Istanbul", "Izmir", R.drawable.sabiha_gokcen_airport));
        flights.add(new Flights("Atatürk Airport", "AHL", "Istanbul", "Izmir", R.drawable.ataturk_airport));
        flights.add(new Flights("Sabiha Gökçen Airport", "SGH", "Istanbul", "Ankara", R.drawable.sabiha_gokcen_airport));
        flights.add(new Flights("Esenboğa Airport", "ESB", "Ankara", "Istanbul", R.drawable.esenboga_airport));
        flights.add(new Flights("Adnan Menderes Airport", "AMA", "Istanbul", "Ankara", R.drawable.adnan_menderes_airport));
        flights.add(new Flights("Sabiha Gökçen Airport", "SGH", "Istanbul", "Izmir", R.drawable.sabiha_gokcen_airport));
        flights.add(new Flights("Atatürk Airport", "AHL", "Istanbul", "Izmir", R.drawable.ataturk_airport));


    }


}