package com.techila.speedcontracting;

import java.util.ArrayList;

import com.techila.speedcontracting.R;
import com.techila.speedcontracting.adapter.ContractListAdapter;
import com.techila.speedcontracting.pojo.ContractModel;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class PropertyOwnerFragment extends Fragment {
	
	public static ArrayList<ContractModel> arr;
	public static ContractListAdapter adapter;
	int position;
	MakeCall call;
	public PropertyOwnerFragment(int i, MainActivity mainActivity){
		
		position =i;
		call = (MakeCall) mainActivity;
		arr = new ArrayList<ContractModel>();
		for(int j=0;j<5;j++){
			ContractModel con = new ContractModel();
			
			con.setContact("12344343");
			con.setContactPersoneName("XYZ");
			con.setEmailID("XYZ@gmail.com");
			con.setProjectId("prj"+(j+1));
			arr.add(con);
		}
	}
	
	
	


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_contract_list, container, false);
       
        
        ListView lv = (ListView)rootView.findViewById(R.id.lv_of_contract);
		Button btn = (Button)rootView.findViewById(R.id.btn_add_contract);
		
		if(position==1){
			btn.setText("Add Location Info");
		}
		
		adapter = new ContractListAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, arr);
		lv.setAdapter(adapter);
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					call.callFragment(position); 
					break;
				case 1:
					call.callFragment(position); 
					break;
				case 2:
				case 3:
				case 4:
				case 5:
					call.callFragment(position);
					break;
				default:
					break;
				}
			 
			}
		});
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2,
					long pos) {
				// TODO Auto-generated method stub
				call.callFragment(position);
				
			}
		});
        
        return rootView;
    }
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		adapter.notifyDataSetChanged();
		
	}
	
	interface MakeCall{
		void callFragment(int position);
	}
	
}
