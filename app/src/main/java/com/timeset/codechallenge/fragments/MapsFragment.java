package com.timeset.codechallenge.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.timeset.codechallenge.R;
import com.timeset.codechallenge.Util.MyUtility;
import com.timeset.codechallenge.activities.MainActivity;
import com.timeset.codechallenge.entity.ConnectionDetector;
import com.timeset.codechallenge.entity.FlickerProfile;
import com.timeset.codechallenge.interfaces.HandleNavigationListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment{


    private ConnectionDetector connectionDetector;
    private TextView errorText;
    private Button mapsButton;
    private HandleNavigationListener handleNavigationListener = null;
    private RelativeLayout progressLayout;

    public MapsFragment() {
        // Required empty public constructor
    }

    public static MapsFragment newInstance() {
        MapsFragment mapsFragment = new MapsFragment();
        return mapsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_maps, container, false);
        try{
            handleNavigationListener = (HandleNavigationListener)rootView.getContext();
        }catch(ClassCastException e){
            throw new ClassCastException(getString(R.string.classcast_exception_listener));
        }
        errorText = (TextView)rootView.findViewById(R.id.errorTextInternet);
        mapsButton = (Button) rootView.findViewById(R.id.mapsButton);
        progressLayout = (RelativeLayout) rootView.findViewById(R.id.progressLayout);
        progressLayout.setVisibility(View.GONE);
        connectionDetector = new ConnectionDetector(getContext());
        errorText.setVisibility(View.GONE);
        mapsButton.setVisibility(View.VISIBLE);

        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!connectionDetector.isNetworkConnectedPP()){
                    errorText.setText(getString(R.string.no_network));
                    errorText.setBackgroundColor(getResources().getColor(R.color.errorInternet, null));
                    errorText.setVisibility(View.VISIBLE);
                }else{
                    errorText.setVisibility(View.GONE);
                    mapsButton.setVisibility(View.GONE);
                    progressLayout.setVisibility(View.VISIBLE);
                    MyDownloadProfileTask myDownloadProfileTask = new MyDownloadProfileTask();
                    String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&license=1,2,4,7&has_geo=1&extras=original_format,description,geo,date_upload,owner_name&format=json&nojsoncallback=1&api_key=ccc0bafafb2e761c02f4b2a5feb5ba88";
                    myDownloadProfileTask.execute(url);
                }
            }
        });
        return rootView;
    }


    public class MyDownloadProfileTask extends AsyncTask<String,Void,List<FlickerProfile>> {

        public MyDownloadProfileTask() {

        }

        @Override
        protected List<FlickerProfile> doInBackground(String... params) {
            List<FlickerProfile> list = new ArrayList<>();
            String json = "";
            for (String url : params) {
                json = MyUtility.downloadJSONusingHTTPGetRequest(url);
                list = MyUtility.parseJSON(json, list);
            }
            ((MainActivity)getActivity()).setFlickerProfiles(list);
            return list;
        }

        @Override
        protected void onPostExecute(List<FlickerProfile> listProfile) {
            // super.onPostExecute(listProfile);
            progressLayout.setVisibility(View.GONE);
            handleNavigationListener.navigateToItemSelected(R.id.mapsButton);
        }

    }


}
