package com.techila.speedcontracting;

import com.techila.speedcontracting.pojo.ContractModel;

import info.androidhive.slidingmenu.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewContractActivity extends Activity implements OnClickListener{

	Button btn_submit,btn_clear;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_contract);
		
		btn_submit = (Button)findViewById(R.id.btn_submit);
		btn_clear = (Button)findViewById(R.id.btn_clear);
		
		
		btn_submit.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
		switch (v.getId()) {
		case R.id.btn_submit:
			ContractModel model = new ContractModel();
			model.setContact("New Contact");
			model.setContactPersoneName("New Name");
			model.setEmailID("New Mail ID");
			model.setProjectId("Project_ID"+ PropertyOwnerFragment.arr.size()+1);
			PropertyOwnerFragment.arr.add(model);
			Toast.makeText(getApplicationContext(), "New Record Is Added", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case R.id.btn_clear:
			
			break;
		default:
			
			break;
		}
		
		
	}
		
	
	
		
		
		
	
	
	
}
