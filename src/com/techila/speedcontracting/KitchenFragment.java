package com.techila.speedcontracting;

import com.techila.speedcontracting.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class KitchenFragment extends Fragment {
	
	public KitchenFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.tbl_rw_layout_parking, container, false);
         
        return rootView;
    }
}
