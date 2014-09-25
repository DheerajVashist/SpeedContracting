package com.techila.speedcontracting;

import com.techila.speedcontracting.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BudgetFragment extends Fragment {
	
	int position=0;
	public BudgetFragment(int pos){
		position = pos;
	}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_plot_view_comfirts, container, false);
         	
        ((MainActivity)getActivity()).initNewContractView(rootView, position);
        return rootView;
    }
}
