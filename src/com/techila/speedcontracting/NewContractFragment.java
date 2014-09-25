package com.techila.speedcontracting;

import com.techila.speedcontracting.R;
import com.techila.speedcontracting.pojo.ContractModel;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class NewContractFragment extends Fragment {

	Button btn_submit,btn_clear;
	MainActivity act;
	int position=0 ;
	 public NewContractFragment(int i) {
		// TODO Auto-generated constructor stub
		position =i;
	}
	
	 
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.activity_create_contract, container, false);
         ((MainActivity)getActivity()).initNewContractView(rootView,position);
    	
        return rootView;
    }

	
	
  }
