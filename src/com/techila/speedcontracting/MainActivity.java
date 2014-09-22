package com.techila.speedcontracting;

import info.androidhive.slidingmenu.R;

import java.util.ArrayList;

import com.techila.speedcontracting.PropertyOwnerFragment.MakeCall;
import com.techila.speedcontracting.adapter.ContractListAdapter;
import com.techila.speedcontracting.adapter.NavDrawerListAdapter;
import com.techila.speedcontracting.model.NavDrawerItem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.ClipData.Item;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableLayout.LayoutParams;

public class MainActivity extends Activity implements MakeCall {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// View of the Distance Fragment
	private TableLayout tLayout;
	private Button btn_add_distance;

	// ArrayList for room fragment
	ArrayList<TableRow> arr_row_of_room;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;
	PropertyOwnerFragment frag;
	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private LayoutInflater inflater;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	private int count = 0;
	private int roomCount = 0;
	private boolean flag = false;
	private int priceCount=0;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// array list of the Room Table
		arr_row_of_room = new ArrayList<TableRow>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1), true, "22"));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		// What's hot, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1), true, "50+"));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
				.getResourceId(6, -1), true, "50+"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons
				.getResourceId(7, -1), true, "50+"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons
				.getResourceId(8, -1), true, "50+"));

		// Recycle the typed array
		navMenuIcons.recycle();

		// layout Inflater for Table Layout
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.btn_red_bg));
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};

		// mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
		// R.drawable.ic_drawer, R.string.drawer_open,
		// R.string.drawer_close) {
		//
		// @Override
		// public boolean onOptionsItemSelected(MenuItem item) {
		// if (item != null && item.getItemId() == android.R.id.home) {
		// if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
		// mDrawerLayout.closeDrawer(Gravity.RIGHT);
		// } else {
		// mDrawerLayout.openDrawer(Gravity.RIGHT);
		// }
		// }
		// return false;
		// }
		// };
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);

	}

	private void showPopUp(final int i) {
		// TODO Auto-generated method stub

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		View vi = inflater.inflate(R.layout.dialog_view_to_add_distance, null);
		TextView tv_field_name = (TextView) vi
				.findViewById(R.id.tv_for_add_row_name);

		if (i == 2) {
			builder.setTitle("Add Distance From");
			tv_field_name.setText("Location Name");
		} else if (i == 4) {
			builder.setTitle("Add Facility");
			tv_field_name.setText("Facility");
		}

		final EditText et_loc_name = (EditText) vi
				.findViewById(R.id.et_distance_to_location_name);
		builder.setView(vi);

		builder.setPositiveButton("Submit",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (i == 2) {
							addTableRow(et_loc_name.getText().toString());
						} else if (i == 4) {
							addTableRowForFacility(et_loc_name.getText()
									.toString());
						}
					}

				});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
		builder.show();
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			if (getFragmentManager().getBackStackEntryCount() > 0) {
				getFragmentManager().popBackStack(null,
						FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
			fragment = new PropertyOwnerFragment(0, this);
			frag = (PropertyOwnerFragment) fragment;
			break;
		case 1:
			if (getFragmentManager().getBackStackEntryCount() > 0) {
				getFragmentManager().popBackStack(null,
						FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}

			fragment = new PropertyOwnerFragment(1, this);

			break;
		case 2:
			if (getFragmentManager().getBackStackEntryCount() > 0) {
				getFragmentManager().popBackStack(null,
						FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
			fragment = new PropertyOwnerFragment(2, this);

			break;
		case 3:
			if (getFragmentManager().getBackStackEntryCount() > 0) {
				getFragmentManager().popBackStack(null,
						FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
			fragment = new PropertyOwnerFragment(3, this);

			break;
		case 4:
			if (getFragmentManager().getBackStackEntryCount() > 0) {
				getFragmentManager().popBackStack(null,
						FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
			fragment = new PropertyOwnerFragment(4, this);
			break;
		case 5:
			if (getFragmentManager().getBackStackEntryCount() > 0) {
				getFragmentManager().popBackStack(null,
						FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
			fragment = new PropertyOwnerFragment(5, this);
			break;
		case 6:
			fragment = new PriceFragment();

			break;
		case 7:

			break;
		case 8:

			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment)
					.addToBackStack("tag" + position).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);

		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void callFragment(int position) {
		// TODO Auto-generated method stub
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new NewContractFragment(this);
			break;
		case 1:
			fragment = new LocationDetailFragment();
			break;
		case 2:
		case 3:
		case 4:
		case 5:
			fragment = new DistanceFragment(position);
			break;
		default:
			break;
		}
		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment)
					.addToBackStack("subtag" + position).commit();

		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}

	}

	public void setTableLayoutForRooms(View vi) {

		tLayout = (TableLayout) vi.findViewById(R.id.tbl_for_distance_item);
		btn_add_distance = (Button) vi.findViewById(R.id.btn_add_distance);
		btn_add_distance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addTableRowForRooms();
			}
		});
		addTableRowForRooms();
	}

	private void addTableRowForRooms() {

		TableRow row = new TableRow(MainActivity.this);
		row.setId(roomCount);
		TableLayout.LayoutParams rowLp = new TableLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
		TableRow.LayoutParams cellLP = new TableRow.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

		View vi = inflater.inflate(R.layout.tbl_rw_layout_rooms, null);

		final ImageButton btn_add_description = (ImageButton) vi
				.findViewById(R.id.btn_add_descripton);
		final EditText et_desc = (EditText) vi
				.findViewById(R.id.et_description);

		row.addView(vi, cellLP);
		arr_row_of_room.add(row);
		tLayout.addView(row, rowLp);
		btn_add_description.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				LinearLayout ll = (LinearLayout) btn_add_description
						.getParent();
				LinearLayout ll2 = (LinearLayout) ll.getParent();
				TableRow tr = (TableRow) ll2.getParent();
				TableRow tr2 = (TableRow) tr.getParent();
				EditText et = (EditText) tr2.findViewById(R.id.et_description);
				Log.d("Row Id ", "" + tr2.getId());
				if (flag) {
					et.setEnabled(true);
					et.setVisibility(View.VISIBLE);
					flag = false;
				} else {
					et.setEnabled(false);
					et.setVisibility(View.GONE);
					flag = true;
				}
			}
		});
		roomCount++;
	}

	public void setTableLayoutForFacility(View vi) {
		tLayout = (TableLayout) vi.findViewById(R.id.tbl_for_distance_item);
		btn_add_distance = (Button) vi.findViewById(R.id.btn_add_distance);
		btn_add_distance.setText("Add Facility");
		btn_add_distance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopUp(4);
			}
		});

		addTableRowForFacility("Basic Facility");
	}

	private void addTableRowForFacility(String fName) {
		// TODO Auto-generated method stub

		TableRow row = new TableRow(MainActivity.this);
		TableLayout.LayoutParams rowLp = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.WRAP_CONTENT,
				TableLayout.LayoutParams.WRAP_CONTENT, 1.0f);

		TableRow.LayoutParams cellLp = new TableRow.LayoutParams(
				TableRow.LayoutParams.MATCH_PARENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

		View vi = inflater.inflate(R.layout.tbl_rw_layout_facilities, null);
		TextView tv_name_of_facility = (TextView) vi
				.findViewById(R.id.tv_facility_name);
		tv_name_of_facility.setText(fName);
		row.addView(vi, cellLp);

		tLayout.addView(row, rowLp);

	}
	public void setTableLayoutForPrice(View vi){
		tLayout = (TableLayout) vi.findViewById(R.id.tbl_for_distance_item);
		btn_add_distance = (Button) vi.findViewById(R.id.btn_add_distance);
		btn_add_distance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addTableRowPrice();
			}

			
		});
		
	}
	
	
	private void addTableRowPrice() {
		// TODO Auto-generated method stub
		TableRow row = new TableRow(MainActivity.this);
		TableLayout.LayoutParams rowLp = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.WRAP_CONTENT,
				TableLayout.LayoutParams.WRAP_CONTENT, 1.0f);

		TableRow.LayoutParams cellLp = new TableRow.LayoutParams(
				TableRow.LayoutParams.MATCH_PARENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
		
		View vi = inflater.inflate(R.layout.tbl_row_for_price, null);
		TextView tv =(TextView)vi.findViewById(R.id.tv_week);
		tv.setText("Week "+priceCount);
		
		row.addView(vi,cellLp);
		
		tLayout.addView(row,rowLp);
				priceCount++;
		
	}
	
	
	public void setTableLayout(View vi) {
		tLayout = (TableLayout) vi.findViewById(R.id.tbl_for_distance_item);
		btn_add_distance = (Button) vi.findViewById(R.id.btn_add_distance);
		btn_add_distance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopUp(2);
			}
		});
		addTableRow("Closest Nighbour");
		addTableRow("Airport Distance");
	}

	private void addTableRow(String name) {
		// TODO Auto-generated method stub
		TableRow row = new TableRow(getBaseContext());
		row.setId(count);
		TableLayout.LayoutParams rowLp = new TableLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
		TableRow.LayoutParams cellLp = new TableRow.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

		View vi = inflater.inflate(R.layout.tbl_rw_layout_distance, null);
		TextView tv_location = (TextView) vi.findViewById(R.id.tv_loc_name);
		tv_location.setText(name);
		Spinner spn = (Spinner) vi.findViewById(R.id.spn_unit);
		String[] unit_distance = new String[2];
		unit_distance[0] = "Km";
		unit_distance[1] = "Mtr";
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				MainActivity.this, android.R.layout.simple_spinner_item,
				unit_distance);
		spn.setAdapter(adapter);
		row.addView(vi, cellLp);
		tLayout.addView(row, rowLp);
		count++;
	}

	public void notifyChange() {
		getFragmentManager().popBackStack();
		Toast.makeText(getBaseContext(), "" + PropertyOwnerFragment.arr.size(),
				Toast.LENGTH_SHORT).show();
		PropertyOwnerFragment.adapter.notifyDataSetChanged();

	}
}
