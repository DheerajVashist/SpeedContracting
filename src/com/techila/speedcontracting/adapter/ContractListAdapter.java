package com.techila.speedcontracting.adapter;

import info.androidhive.slidingmenu.R;

import java.util.ArrayList;

import com.techila.speedcontracting.pojo.ContractModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContractListAdapter extends ArrayAdapter {

	LayoutInflater inflater;
	Context context;
	ArrayList<ContractModel> arr;
	
	
	
	public ContractListAdapter(Context context, int resource,
			ArrayList<ContractModel> arr) {
		super(context, resource, arr);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.arr= arr;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	
	private class ViewHolder{
		
		TextView tv_projectId,tvContracterName,emailId,contact;
		
	}
	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi=convertView;
		ViewHolder holder;
		if(convertView==null){
			
			vi= inflater.inflate(R.layout.list_of_contractor, null);
			holder = new ViewHolder();
			holder.tv_projectId = (TextView)vi.findViewById(R.id.tv_project_id);
			holder.tvContracterName = (TextView)vi.findViewById(R.id.tv_contracterName);
			holder.emailId = (TextView)vi.findViewById(R.id.tv_email);
			holder.contact = (TextView)vi.findViewById(R.id.tv_contact_number);
			
			vi.setTag(holder);
		}else{
			
			holder =(ViewHolder) vi.getTag();
			
		}
		
		holder.contact.setText(arr.get(position).getContact()); 
		holder.tv_projectId .setText(arr.get(position).getProjectId());
		holder.tvContracterName.setText(arr.get(position).getContactPersoneName());
		holder.emailId.setText(arr.get(position).getEmailID());
		
		
		
		
		return vi;
	}
	
	
}
