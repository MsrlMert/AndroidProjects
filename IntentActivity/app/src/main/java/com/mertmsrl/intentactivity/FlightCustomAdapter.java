package com.mertmsrl.intentactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FlightCustomAdapter extends BaseAdapter {

    List<Flights> flightsList;
    LayoutInflater inflater;
    Context context;
    ImageView imageViewAirportPicture;
    TextView textViewAirportName, textViewAirportNameAbbr, textViewFlightStartPoint, textViewFlightEndPoint;

    public FlightCustomAdapter(List<Flights> flightsList, Context context) {
        this.flightsList = flightsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return flightsList.size();
    }

    @Override
    public Object getItem(int position) {
        return flightsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_flights_design,null);

        imageViewAirportPicture = view.findViewById(R.id.imageViewAirportPicture);

        textViewAirportName = view.findViewById(R.id.textViewAirportName);
        textViewAirportNameAbbr = view.findViewById(R.id.textViewAirportNameAbbr);
        textViewFlightStartPoint = view.findViewById(R.id.textViewFlightStartPoint);
        textViewFlightEndPoint = view.findViewById(R.id.textViewFlightEndPoint);


        imageViewAirportPicture.setImageResource(flightsList.get(position).getAirportPictureId());

        textViewAirportName.setText(flightsList.get(position).getAirportName());
        textViewAirportNameAbbr.setText(flightsList.get(position).getAirportNameAbbr());
        textViewFlightStartPoint.setText(flightsList.get(position).getFlightStartPoint()+"  --- ");
        textViewFlightEndPoint.setText(flightsList.get(position).getFlightEndPoint());


        return view;
    }
}
