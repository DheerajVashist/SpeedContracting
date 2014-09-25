package com.techila.speedcontracting;

import com.techila.speedcontracting.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlaygroundFragment extends Fragment {

	int position=0;
	public PlaygroundFragment(int i) {
		// TODO Auto-generated constructor stub
		
		position =i;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View vi = inflater.inflate(R.layout.fragment_playground, container,false);
			((MainActivity)getActivity()).initNewContractView(vi, position);
			
		return vi;
	}
	
}
