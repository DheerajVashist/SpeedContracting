package com.techila.speedcontracting;

import com.techila.speedcontracting.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BuildingMaterialFragment extends Fragment {
	
	public BuildingMaterialFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
         
        return rootView;
    }
}
