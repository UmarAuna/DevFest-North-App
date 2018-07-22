package com.defvest.devfestnorth.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.defvest.devfestnorth.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Venue extends Fragment {
    MapView mapView;
    View rootview;

    final Double[] latitu = {9.5816055};
    final Double[] longitu = {6.5673995};
    String title ="Getting Venue.....";
    String About,Address;
    TextView Addresses, Abouts;

    public static Venue newInstance() {
        Venue fragment = new Venue();
        return fragment;
    }

    public Venue() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        rootview = inflater.inflate(R.layout.fragment_venue, container, false);

        mapView = rootview.findViewById(R.id.mapvenue);
        Addresses = rootview.findViewById(R.id.mapAddress);
        Abouts = rootview.findViewById(R.id.mapAbout);

        try{
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Details");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    About = dataSnapshot.child("About").getValue(String.class);
                    Address = dataSnapshot.child("Address").getValue(String.class);

                    Abouts.setText(About);
                    Addresses.setText(Address);
                    Log.d("Details", Address +" "+About);
                    //Toast.makeText(getContext(), Address +" "+About, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("Exception",databaseError.toException());
                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }


        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                try{
                    boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(),R.raw.style_json));
                    if(!success){
                        Toast.makeText(getContext(), "Can't Load Map", Toast.LENGTH_SHORT).show();
                    }
                }catch (Resources.NotFoundException e){
                    Toast.makeText(getContext(), " "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                int permission = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);

                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},2);
                }



                try {
                    googleMap.setMyLocationEnabled(true);
                    googleMap.setBuildingsEnabled(true);
                    googleMap.getUiSettings().setZoomControlsEnabled(true);
                    googleMap.getUiSettings().setIndoorLevelPickerEnabled(true);
                    googleMap.getUiSettings().setMapToolbarEnabled(true);
                    googleMap.setTrafficEnabled(true);
                    googleMap.getUiSettings().setMapToolbarEnabled(true);
                    googleMap.getUiSettings().setIndoorLevelPickerEnabled(true);
                }
                catch (SecurityException e) {
                    Log.d("Security exception","location not enabled");
                    Toast.makeText(getContext(),"Locaton not Enabled",Toast.LENGTH_SHORT).show();
                }

                try{
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Venue");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            latitu[0] = (Double) dataSnapshot.child("lat").getValue();
                            longitu[0] = (Double) dataSnapshot.child("lon").getValue();
                            title = dataSnapshot.child("title").getValue(String.class);

                            Log.d("LatLon", latitu[0] +" - "+longitu[0] +" "+ title);
                            //Toast.makeText(getContext(), latitu[0].toString()+" - "+ longitu[0].toString() +" - "+title, Toast.LENGTH_SHORT).show();

                            LatLng location = new LatLng(latitu[0], longitu[0]);
                            googleMap.addMarker(new MarkerOptions().position(location).title(title).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(location,17f);
                            googleMap.animateCamera(cameraUpdate);


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("Exception",databaseError.toException());
                        }
                    });
                }catch(Exception e){
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }


            }
        });
        return rootview;
    }


  @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();

    }
}
