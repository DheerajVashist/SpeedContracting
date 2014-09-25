package com.techila.speedcontracting;
import com.techila.speedcontracting.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class LocationDetailFragment extends Fragment {

		int position=0;

		public LocationDetailFragment(int i) {
			
			position = i;
			
		}
		
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
	         
	        ((MainActivity)getActivity()).initNewContractView(rootView, position);
	        return rootView;
	    }
		
	
	
}
