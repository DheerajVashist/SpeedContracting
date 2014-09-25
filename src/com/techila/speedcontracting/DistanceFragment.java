package com.techila.speedcontracting;

import com.techila.speedcontracting.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

public class DistanceFragment extends Fragment {
	int position=0;
	public DistanceFragment(int position){
		this.position = position;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_distance, container, false);
             	((MainActivity)getActivity()).initNewContractView(rootView, position);
         
        return rootView;
    }
}
