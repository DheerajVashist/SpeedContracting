package com.techila.speedcontracting;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.techila.speedcontracting.adapter.ContractListAdapter;
import com.techila.speedcontracting.pojo.ContractModel;


public class CreateContractActivity extends Activity {

	public static ArrayList<ContractModel> arr;
	public static ContractListAdapter adapter;
	private ListView lv_of_contract;
	private Button btn_add_contract;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contract_activity);
		
		lv_of_contract = (ListView)findViewById(R.id.lv_of_contract);
		
		btn_add_contract = (Button)findViewById(R.id.btn_add_contract);
		
		final Intent it =new Intent(CreateContractActivity.this,MainActivity.class);
		
		
		btn_add_contract.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(it);
			}
		});
	
		lv_of_contract.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long pos) {
				// TODO Auto-generated method stub
				startActivity(it);
			}
		});
	}
	
	
	
	
}
