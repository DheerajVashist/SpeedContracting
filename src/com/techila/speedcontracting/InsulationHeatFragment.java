package com.techila.speedcontracting;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InsulationHeatFragment extends Fragment {

	int position=0;
	public InsulationHeatFragment(int position) {
		// TODO Auto-generated constructor stub
		this.position = position;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View vi = inflater.inflate(
				R.layout.fragment_insulation_heating_additional_heat,
				container, false);
		
		((MainActivity)getActivity()).initNewContractView(vi, position);
		return vi;

	}

}
