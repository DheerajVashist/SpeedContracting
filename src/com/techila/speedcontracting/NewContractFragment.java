package com.techila.speedcontracting;

import com.techila.speedcontracting.pojo.ContractModel;

import info.androidhive.slidingmenu.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class NewContractFragment extends Fragment implements OnClickListener{

	Button btn_submit,btn_clear;
	MainActivity act;
 public NewContractFragment(MainActivity mainActivity) {
	// TODO Auto-generated constructor stub
	 act = mainActivity;
 }
 
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.activity_create_contract, container, false);
         
    	btn_submit = (Button)rootView.findViewById(R.id.btn_submit);
		btn_clear = (Button)rootView.findViewById(R.id.btn_clear);
		btn_submit.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
        return rootView;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_submit:
			ContractModel model = new ContractModel();
			model.setContact("New Contact");
			model.setContactPersoneName("New Name");
			model.setEmailID("New Mail ID");
			model.setProjectId("Project_ID"+ PropertyOwnerFragment.arr.size()+1);
			PropertyOwnerFragment.arr.add(model);
			act.notifyChange();
		break;
		default:
			
			break;
	}
		
  }
	
	
  }
