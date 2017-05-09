package com.timeset.codechallenge.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.timeset.codechallenge.R;
import com.timeset.codechallenge.entity.FlickerProfile;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsViewFragment extends Fragment {

    MapView mMapView;

    private static List<FlickerProfile> profiles;
    public MapsViewFragment() {
        // Required empty public constructor
    }

    public static MapsViewFragment newInstance(List<FlickerProfile> list) {
        MapsViewFragment mapsViewFragment = new MapsViewFragment();
        profiles = list;
        return mapsViewFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_maps_view, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

            initializeMap();



        return rootView;
    }



    protected Marker createMarker(double latitude, double longitude, String title,GoogleMap googleMap,int i) {

       Marker marker = googleMap.addMarker(new MarkerOptions()
               .position(new LatLng(latitude, longitude))
               .anchor(0.5f, 0.5f)
               .title(title)
               .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        marker.setTag(i);
        return marker;

    }

    public void initializeMap(){
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // For showing a move to my location button
                for(int i = 0 ; i < profiles.size() ; i++ ) {
                    createMarker(profiles.get(i).getLatitude(), profiles.get(i).getLongitude(), profiles.get(i).getOwnerName(),googleMap,i);
                }

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String title = profiles.get((int)marker.getTag()).getTitle();
                        Toast.makeText(getContext(),title,Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
