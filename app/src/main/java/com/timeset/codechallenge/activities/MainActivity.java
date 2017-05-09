package com.timeset.codechallenge.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.timeset.codechallenge.R;
import com.timeset.codechallenge.entity.FlickerProfile;
import com.timeset.codechallenge.fragments.AdderFragment;
import com.timeset.codechallenge.fragments.MapsFragment;
import com.timeset.codechallenge.fragments.MapsViewFragment;
import com.timeset.codechallenge.interfaces.HandleNavigationListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements HandleNavigationListener{

    public static List<FlickerProfile> getFlickerProfiles() {
        return flickerProfiles;
    }

    public void setFlickerProfiles(List<FlickerProfile> flickerProfiles) {
        MainActivity.flickerProfiles = flickerProfiles;
    }

    private static List<FlickerProfile> flickerProfiles;
    private TextView errorText;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_add:
                    replaceFragment(AdderFragment.newInstance(),getString(R.string.title_home));
                    return true;
                case R.id.navigation_maps:
                    replaceFragment(MapsFragment.newInstance(),getString(R.string.title_dashboard));
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        replaceFragment(AdderFragment.newInstance(),getString(R.string.title_home));

    }

    public void replaceFragment(Fragment frag,String toolBarTitle){
        setTitle(toolBarTitle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content,frag).commit();
    }

    public void updateResultToText(View v){
        int num = Integer.parseInt(v.getTag().toString());
        double result = num + 12.61;
        AdderFragment.resultText.setText(getString(R.string.result_label)+" "+result);
    }

    @Override
    public void navigateToItemSelected(int buttonId) {
        switch (buttonId){
            case R.id.mapsButton:
                if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)){

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},
                            100);
                }else{
                    replaceFragment(MapsViewFragment.newInstance(getFlickerProfiles()),getString(R.string.title_dashboard));
                }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)  {
                    replaceFragment(MapsViewFragment.newInstance(getFlickerProfiles()),getString(R.string.title_dashboard));
                } else {
                    Toast.makeText(getApplicationContext(),getString(R.string.no_permission),Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
