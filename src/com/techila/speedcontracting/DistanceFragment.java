package com.techila.speedcontracting;

import info.androidhive.slidingmenu.R;
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
         TableLayout tl = (TableLayout)rootView.findViewById(R.id.tbl_for_distance_item);
         if(position==2){
         ((MainActivity)getActivity()).setTableLayout(rootView);
         }else if(position==3){
        	 ((MainActivity)getActivity()).setTableLayoutForRooms(rootView);
         }else if(position==4){
        	 ((MainActivity)getActivity()).setTableLayoutForFacility(rootView);
         }else if(position==5){
        	 ((MainActivity)getActivity()).setTableLayoutForPrice(rootView);
         }
        return rootView;
    }
}
