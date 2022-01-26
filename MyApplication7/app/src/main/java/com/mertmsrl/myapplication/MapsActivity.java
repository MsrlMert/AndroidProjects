package com.mertmsrl.myapplication;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private ZoomControls zoomControls;

    private ArrayList<Modal> arrayList;
    private EditText editTextSearch;
    private Button btnSearch;


    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList = new ArrayList<>();
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        zoomControls = findViewById(R.id.activity_map_zoom);
        editTextSearch = findViewById(R.id.edit_text);
        btnSearch = findViewById(R.id.btn_search);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

         */

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                arrayList.add(new Modal(lat, lon));
                Toast.makeText(MapsActivity.this, String.valueOf(lat)+" "+String.valueOf(lon), Toast.LENGTH_SHORT).show();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 13f));
                mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title("Position"));
                Toast.makeText(MapsActivity.this, String.valueOf(arrayList.size()), Toast.LENGTH_SHORT).show();
                if (arrayList.size() > 1){
                    PolylineOptions polylineOptions = new PolylineOptions();
                    polylineOptions.add(new LatLng(arrayList.get(0).getLat(),arrayList.get(0).getLon())).add(new LatLng(arrayList.get(1).getLat(), arrayList.get(1).getLon()))
                            .width(5).color(Color.BLUE).visible(true).geodesic(true);

                    mMap.addPolyline(polylineOptions);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());

                try {
                    List<Address> address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    Toast.makeText(MapsActivity.this, address.get(0).getThoroughfare(), Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });





        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else {

            // permission is given
            if (locationManager != null){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 3f, locationListener);

            }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults.length >0 && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 3f, locationListener);
            }
        }
    }

    public void btnListener(View view) {
        String address = editTextSearch.getText().toString();

        if (!TextUtils.isEmpty(address)){
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            try {
                List<Address> addressList = geocoder.getFromLocationName(address,1);

                double lat = addressList.get(0).getLatitude();
                double lon = addressList.get(0).getLongitude();
                mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon))).setTitle("Address");
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 5f));
                mMap.addPolyline(new PolylineOptions().add(new LatLng(lat, lon)));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}