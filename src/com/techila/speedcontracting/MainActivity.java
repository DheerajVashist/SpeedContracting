package com.techila.speedcontracting;

import java.util.ArrayList;

import com.techila.speedcontracting.R;
import com.techila.speedcontracting.PropertyOwnerFragment.MakeCall;
import com.techila.speedcontracting.adapter.ContractListAdapter;
import com.techila.speedcontracting.adapter.NavDrawerListAdapter;
import com.techila.speedcontracting.info.ContractInfo;
import com.techila.speedcontracting.model.NavDrawerItem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.ClipData.Item;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableLayout.LayoutParams;

public class MainActivity extends Activity implements MakeCall, ContractInfo {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// Shared Preference for Store Data
	SharedPreferences preferences;

	// View of the Distance Fragment
	private TableLayout tLayout;
	private Button btn_add_distance;

	// Trace the Fragment to change Action Bar title
	private int fSequence = 0;

	// Counter for the table row parking
	int row_count_parking = 0;

	// Counter for Add table row for building material
	int row_counter_for_bMaterial = 0;

	// property owner fragment's Next Button
	Button btn_next_to_location;

	// Next Button of the fragment Location
	Button btn_next_to_distance;

	// Button for add Playground details
	Button btn_add_playground_detail;

	// Next Button of the Distance Fragment
	Button btn_next_to_the_rooms;

	// Next Button of Room Fragment
	Button btn_next_to_facility;

	// Next Button to the Parking
	Button btn_next_to_parking;

	// Next Button to Building Material
	Button btn_next_to_building_material;

	// Button save of Owner Property
	Button btn_save_property_details;

	// Button of the Insulation Heating Activity
	ImageButton btn_add_insulation, btn_add_heating, btn_add_additional_heat;

	// ArrayList for room fragment
	ArrayList<TableRow> arr_row_of_room;
	ArrayList<TableRow> arr_row_of_parking;

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
	private boolean flag_materail_des = false;
	private int priceCount = 0;
	private int row_counter_for_vacation = 0;

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

		preferences = getApplicationContext().getSharedPreferences(
				"Contract_info", Context.MODE_PRIVATE);

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
				.getResourceId(5, -1)));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
				.getResourceId(6, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons
				.getResourceId(7, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons
				.getResourceId(8, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons
				.getResourceId(9, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], navMenuIcons
				.getResourceId(10, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[11], navMenuIcons
				.getResourceId(11, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[12], navMenuIcons
				.getResourceId(12, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[13], navMenuIcons
				.getResourceId(13, -1)));
		// Recycle the typed array
		navMenuIcons.recycle();

		// layout Inflater for Table Layout
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		arr_row_of_parking = new ArrayList<TableRow>();

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
			tv_field_name.setText("Location Name:");
		} else if (i == 4) {
			builder.setTitle("Add Facility");
			tv_field_name.setText("Facility:");
		} else if (i == 9) {
			builder.setTitle("Add Material");
			tv_field_name.setText("Material Name:");
		} else if (i == 10) {
			builder.setTitle("Add Vacation Detail");
			tv_field_name.setText("Vacation Option:");
		} else if (i == 12) {
			builder.setTitle("Add Kitchen Detail");
			tv_field_name.setText("Kitchen Option:");
		} else if (i == 13) {
			builder.setTitle("Add Furniture Detail");
			tv_field_name.setText("Furniture Option:");
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
						} else if (i == 9 || i == 10 || i == 12 || i == 13) {
							addTableRowForBuildingMaterial(et_loc_name
									.getText().toString(), i);
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
			fragment = new NewContractFragment(0);
			fSequence = 0;
			break;
		case 1:
			if (getFragmentManager().getBackStackEntryCount() > 0) {
				getFragmentManager().popBackStack(null,
						FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}

			fragment = new LocationDetailFragment(1);
			fSequence = 0;
			break;
		case 2:
		case 3:
		case 4:
		case 5:
		case 7:
			if (getFragmentManager().getBackStackEntryCount() > 0) {
				getFragmentManager().popBackStack(null,
						FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
			fragment = new DistanceFragment(position);
			fSequence = 0;
			break;
		case 6:
			if (getFragmentManager().getBackStackEntryCount() > 0) {
				getFragmentManager().popBackStack(null,
						FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
			fragment = new BudgetFragment(6);
			fSequence = 0;
			break;

		case 8:
			if (getFragmentManager().getBackStackEntryCount() > 0) {
				getFragmentManager().popBackStack(null,
						FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
			fragment = new PlaygroundFragment(8);
			fSequence = 0;
			break;
		case 9:
		case 10:
		case 12:
		case 13:
			if (getFragmentManager().getBackStackEntryCount() > 0) {
				getFragmentManager().popBackStack(null,
						FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
			fragment = new DistanceFragment(position);
			fSequence = 0;
			break;
		case 11:
			if (getFragmentManager().getBackStackEntryCount() > 0) {
				getFragmentManager().popBackStack(null,
						FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
			fragment = new InsulationHeatFragment(position);
			fSequence = 0;
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
			fragment = new NewContractFragment(0);
			break;
		case 1:
			fragment = new LocationDetailFragment(1);
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
		btn_add_distance.setText("Add Room");

		btn_add_distance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addTableRowForRooms();
			}
		});
		addTableRowForRooms();
	}

	private void setTableLayoutForParking(View vi) {
		tLayout = (TableLayout) vi.findViewById(R.id.tbl_for_distance_item);
		btn_add_distance = (Button) vi.findViewById(R.id.btn_add_distance);
		btn_add_distance.setText("Add Parking");
		btn_add_distance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addTableRowForParking();
			}

		});
	}

	private void addTableRowForParking() {
		// TODO Auto-generated method stub

		TableRow row = new TableRow(MainActivity.this);
		row.setId(row_count_parking);
		TableLayout.LayoutParams rowLp = new TableLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
		TableRow.LayoutParams cellLP = new TableRow.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

		View vi = inflater.inflate(R.layout.tbl_rw_layout_parking, null);

		final Button btn_add_parking_detail = (Button) vi
				.findViewById(R.id.btn_add_parking_detail);

		btn_add_parking_detail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				LinearLayout ll1 = (LinearLayout) btn_add_parking_detail
						.getParent();
				LinearLayout ll2 = (LinearLayout) ll1.getParent();
				RelativeLayout rl = (RelativeLayout) ll2.getParent();
				TableRow tr = (TableRow) rl.getParent();

				ll2 = (LinearLayout) tr.findViewById(R.id.ll_of_ch_box);

				showDialogForParkingDetail(ll2, 1);

			}

		});

		row.addView(vi, cellLP);

		arr_row_of_parking.add(row);

		tLayout.addView(row, rowLp);

		row_count_parking++;
	}

	private void showDialogForParkingDetail(final LinearLayout lLayout, int pos) {

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		View vi = inflater.inflate(R.layout.dialog_view_to_add_distance, null);
		TextView tv_field_name = (TextView) vi
				.findViewById(R.id.tv_for_add_row_name);

		if (pos == 1) {
			builder.setTitle("Enter Parking Detail");
			tv_field_name.setText("Detail Desc.:");
		} else if (pos == 2) {
			builder.setTitle("Enter Playground Detail");
			tv_field_name.setText("Play Desc.:");
		} else if (pos == 11) {
			builder.setTitle("Enter Detail");
			tv_field_name.setText("Field Name.:");
		}
		final EditText et_loc_name = (EditText) vi
				.findViewById(R.id.et_distance_to_location_name);
		builder.setView(vi);

		builder.setPositiveButton("Submit",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						CheckBox chBox = new CheckBox(MainActivity.this);
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								LinearLayout.LayoutParams.MATCH_PARENT,
								LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
						chBox.setText(et_loc_name.getText().toString());
						lLayout.addView(chBox, params);
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
		TextView tv_room_count = (TextView) vi.findViewById(R.id.tv_room_count);
		tv_room_count.setText("Room " + (roomCount + 1));
		final Spinner spn = (Spinner) vi.findViewById(R.id.spn_rm_type);

		String[] rm_type = new String[8];

		rm_type[0] = "Bedroom";
		rm_type[1] = "Livingroom";
		rm_type[2] = "Bathroom";
		rm_type[3] = "Toilet";
		rm_type[4] = "Mezzanine";
		rm_type[5] = "Annex";
		rm_type[6] = "Kitchen";
		rm_type[7] = "Wintergarden";

		spn.setAdapter(new ArrayAdapter<String>(MainActivity.this,
				android.R.layout.simple_spinner_item, rm_type));

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

	public void setTableLayoutForPrice(View vi) {
		tLayout = (TableLayout) vi.findViewById(R.id.tbl_for_distance_item);
		btn_add_distance = (Button) vi.findViewById(R.id.btn_add_distance);
		btn_add_distance.setText("Add Price");
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
		TextView tv = (TextView) vi.findViewById(R.id.tv_week);
		tv.setText("Week " + priceCount);

		row.addView(vi, cellLp);

		tLayout.addView(row, rowLp);
		priceCount++;

	}

	public void setTableLayout(View vi) {
		tLayout = (TableLayout) vi.findViewById(R.id.tbl_for_distance_item);
		btn_add_distance = (Button) vi.findViewById(R.id.btn_add_distance);
		btn_add_distance.setText("Add Distance");
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

	public void initNewContractView(View rootView, final int position) {
		// TODO Auto-generated method stub
		final View vi = rootView;

		switch (position) {
		case 0:
			btn_next_to_location = (Button) vi
					.findViewById(R.id.btn_next_to_location);
			btn_save_property_details = (Button) vi.findViewById(R.id.btn_save);
			loadPreferenceOfPropertyOwner(vi);
			btn_save_property_details.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					storeOwnerInfo(vi);
					Toast.makeText(MainActivity.this, "Form Saved Successfully", Toast.LENGTH_SHORT).show();
				}
			});
			btn_next_to_location.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					LocationDetailFragment fragment = new LocationDetailFragment(
							position + 1);
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager
							.beginTransaction()
							.setCustomAnimations(R.anim.card_flip_right_in,
									R.anim.card_flip_right_out,
									R.anim.card_flip_left_in,
									R.anim.card_flip_left_out)
							.replace(R.id.frame_container, fragment)
							.addToBackStack("tag" + (position + 1)).commit();

					// update selected item and title, then close the drawer
					mDrawerList.setItemChecked(position + 1, true);
					mDrawerList.setSelection(position + 1);
					setTitle(navMenuTitles[position + 1]);
					mDrawerLayout.closeDrawer(mDrawerList);
					fSequence = 10;
				}
			});

			break;
		case 1:
			btn_next_to_distance = (Button) vi
					.findViewById(R.id.btn_next_to_distance);

			btn_next_to_distance.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DistanceFragment fragment = new DistanceFragment(
							position + 1);

					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager
							.beginTransaction()
							.setCustomAnimations(R.anim.card_flip_right_in,
									R.anim.card_flip_right_out,
									R.anim.card_flip_left_in,
									R.anim.card_flip_left_out)
							.replace(R.id.frame_container, fragment)
							.addToBackStack("tag" + (position + 1)).commit();

					// update selected item and title, then close the drawer
					mDrawerList.setItemChecked(position + 1, true);
					mDrawerList.setSelection(position + 1);
					setTitle(navMenuTitles[position + 1]);
					mDrawerLayout.closeDrawer(mDrawerList);
					fSequence = 20;
				}
			});
			break;
		case 2:
		case 3:
		case 4:
		case 5:
			btn_next_to_the_rooms = (Button) rootView
					.findViewById(R.id.btn_next_to_room);

			switch (position) {
			case 2:
				setTableLayout(rootView);

				break;
			case 3:
				setTableLayoutForRooms(rootView);
				break;
			case 4:
				setTableLayoutForFacility(rootView);
				break;
			case 5:
				setTableLayoutForPrice(rootView);
				break;
			default:
				break;
			}
			btn_next_to_the_rooms.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (position < 5) {
						DistanceFragment fragment = new DistanceFragment(
								position + 1);
						switch (position) {
						case 2:
							fSequence = 30;
							break;
						case 3:
							fSequence = 40;
							break;
						case 4:
							fSequence = 50;
							break;
						default:
							break;
						}
						FragmentManager fragmentManager = getFragmentManager();
						fragmentManager
								.beginTransaction()
								.setCustomAnimations(R.anim.card_flip_right_in,
										R.anim.card_flip_right_out,
										R.anim.card_flip_left_in,
										R.anim.card_flip_left_out)
								.replace(R.id.frame_container, fragment)
								.addToBackStack("tag" + (position + 1))
								.commit();
						Log.d("fSequence", "" + fSequence);

					} else {
						fSequence = 60;
						BudgetFragment fragment = new BudgetFragment(
								position + 1);
						FragmentManager fragmentManager = getFragmentManager();
						fragmentManager
								.beginTransaction()
								.setCustomAnimations(R.anim.card_flip_right_in,
										R.anim.card_flip_right_out,
										R.anim.card_flip_left_in,
										R.anim.card_flip_left_out)
								.replace(R.id.frame_container, fragment)
								.addToBackStack("tag" + (position + 1))
								.commit();

						Log.d("fSequence", "" + fSequence);
					}
					// update selected item and title, then close the drawer
					mDrawerList.setItemChecked(position + 1, true);
					mDrawerList.setSelection(position + 1);
					setTitle(navMenuTitles[position + 1]);
					mDrawerLayout.closeDrawer(mDrawerList);

				}
			});

			break;
		case 6:
			btn_next_to_parking = (Button) vi
					.findViewById(R.id.btn_next_to_parking);
			btn_next_to_parking.setTextColor(Color.parseColor("#FFFFFF"));
			btn_next_to_parking.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DistanceFragment fragment = new DistanceFragment(
							position + 1);

					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager
							.beginTransaction()
							.setCustomAnimations(R.anim.card_flip_right_in,
									R.anim.card_flip_right_out,
									R.anim.card_flip_left_in,
									R.anim.card_flip_left_out)
							.replace(R.id.frame_container, fragment)
							.addToBackStack("tag" + (position + 1)).commit();
					fSequence = 70;
					mDrawerList.setItemChecked(position + 1, true);
					mDrawerList.setSelection(position + 1);
					setTitle(navMenuTitles[position + 1]);
					mDrawerLayout.closeDrawer(mDrawerList);

				}
			});
			break;
		case 7:
			btn_next_to_the_rooms = (Button) rootView
					.findViewById(R.id.btn_next_to_room);
			btn_next_to_the_rooms.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					PlaygroundFragment fragment = new PlaygroundFragment(8);
					getFragmentManager()
							.beginTransaction()
							.setCustomAnimations(R.anim.card_flip_right_in,
									R.anim.card_flip_right_out,
									R.anim.card_flip_left_in,
									R.anim.card_flip_left_out)
							.replace(R.id.frame_container, fragment)
							.addToBackStack("tag" + (position + 1)).commit();

					mDrawerList.setItemChecked(position + 1, true);
					mDrawerList.setSelection(position + 1);
					setTitle(navMenuTitles[position + 1]);
					mDrawerLayout.closeDrawer(mDrawerList);
					fSequence = 80;
				}
			});
			setTableLayoutForParking(rootView);

			break;
		case 8:
			btn_add_playground_detail = (Button) vi
					.findViewById(R.id.btn_add_playground_detail);
			btn_next_to_building_material = (Button) vi
					.findViewById(R.id.btn_next_to_buiding_material);
			final LinearLayout lLayout = (LinearLayout) rootView
					.findViewById(R.id.ll_for_add_playground_detail);

			btn_next_to_building_material
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							DistanceFragment fragment = new DistanceFragment(
									position + 1);

							getFragmentManager()
									.beginTransaction()
									.setCustomAnimations(
											R.anim.card_flip_right_in,
											R.anim.card_flip_right_out,
											R.anim.card_flip_left_in,
											R.anim.card_flip_left_out)
									.replace(R.id.frame_container, fragment)
									.addToBackStack("tag" + (position + 1))
									.commit();

							mDrawerList.setItemChecked(position + 1, true);
							mDrawerList.setSelection(position + 1);
							setTitle(navMenuTitles[position + 1]);
							mDrawerLayout.closeDrawer(mDrawerList);
							fSequence = 90;
						}
					});

			btn_add_playground_detail.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showDialogForParkingDetail(lLayout, 2);
				}
			});
			break;
		case 9:
		case 10:
		case 12:
		case 13:
			setTableLayoutForBuildingMaterial(rootView, position);
			btn_next_to_the_rooms = (Button) rootView
					.findViewById(R.id.btn_next_to_room);
			if (position == 13) {
				btn_next_to_the_rooms.setText("Save Contract");
			}

			btn_next_to_the_rooms.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (position == 9 || position == 12) {
						DistanceFragment fragment = new DistanceFragment(
								position + 1);

						FragmentManager fragmentManager = getFragmentManager();
						fragmentManager
								.beginTransaction()
								.setCustomAnimations(R.anim.card_flip_right_in,
										R.anim.card_flip_right_out,
										R.anim.card_flip_left_in,
										R.anim.card_flip_left_out)
								.replace(R.id.frame_container, fragment)
								.addToBackStack("tag" + (position + 1))
								.commit();
						mDrawerList.setItemChecked(position + 1, true);
						mDrawerList.setSelection(position + 1);
						setTitle(navMenuTitles[position + 1]);
						mDrawerLayout.closeDrawer(mDrawerList);
						if (position == 9) {
							fSequence = 100;
						} else if (position == 12) {
							fSequence = 130;
						}
					} else if (position == 10) {

						InsulationHeatFragment fragment = new InsulationHeatFragment(
								position + 1);

						FragmentManager fragmentManager = getFragmentManager();
						fragmentManager
								.beginTransaction()
								.setCustomAnimations(R.anim.card_flip_right_in,
										R.anim.card_flip_right_out,
										R.anim.card_flip_left_in,
										R.anim.card_flip_left_out)
								.replace(R.id.frame_container, fragment)
								.addToBackStack("tag" + (position + 1))
								.commit();
						mDrawerList.setItemChecked(position + 1, true);
						mDrawerList.setSelection(position + 1);
						setTitle(navMenuTitles[position + 1]);
						mDrawerLayout.closeDrawer(mDrawerList);
						fSequence = 110;
					} else if (position == 13) {

					}
				}
			});
			break;
		case 11:
			btn_add_additional_heat = (ImageButton) vi
					.findViewById(R.id.btn_add_additional_heating);
			btn_add_insulation = (ImageButton) vi
					.findViewById(R.id.btn_add_insulation);
			btn_add_heating = (ImageButton) vi
					.findViewById(R.id.btn_add_heating_source);
			final LinearLayout ll_additional_heat = (LinearLayout) vi
					.findViewById(R.id.ll_for_additional_heat);
			final LinearLayout ll_heat = (LinearLayout) vi
					.findViewById(R.id.ll_heating);
			final LinearLayout ll_insulation = (LinearLayout) vi
					.findViewById(R.id.ll_insulation);
			Button btn_next = (Button) vi
					.findViewById(R.id.btn_submit_contract);

			btn_next.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DistanceFragment fragment = new DistanceFragment(
							position + 1);

					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager
							.beginTransaction()
							.setCustomAnimations(R.anim.card_flip_right_in,
									R.anim.card_flip_right_out,
									R.anim.card_flip_left_in,
									R.anim.card_flip_left_out)
							.replace(R.id.frame_container, fragment)
							.addToBackStack("tag" + (position + 1)).commit();
					mDrawerList.setItemChecked(position + 1, true);
					mDrawerList.setSelection(position + 1);
					setTitle(navMenuTitles[position + 1]);
					mDrawerLayout.closeDrawer(mDrawerList);
					fSequence = 120;
				}
			});

			btn_add_additional_heat.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showDialogForParkingDetail(ll_additional_heat, position);
				}
			});
			btn_add_heating.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showDialogForParkingDetail(ll_heat, position);
				}
			});
			btn_add_insulation.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showDialogForParkingDetail(ll_insulation, position);
				}
			});

			break;
		default:
			break;
		}

	}

	private void loadPreferenceOfPropertyOwner(View vi) {
		// TODO Auto-generated method stub

		if (preferences.getBoolean("cFlag", false)) {
			

			EditText et = (EditText) vi.findViewById(R.id.et_address_owner);
			et.setText(preferences.getString(OWNER_ADDRESSS, ""));
			((EditText) vi.findViewById(R.id.et_name_owner))
					.setText(preferences.getString(OWNER_NAME, ""));
			
			((EditText) vi.findViewById(R.id.et_town_owner))
			.setText(preferences.getString(OWNER_TOWN, ""));
			
			((EditText) vi.findViewById(R.id.et_postal_code_owner))
			.setText(preferences.getString(OWNER_POSTAL_CODE, ""));
			
			((EditText) vi.findViewById(R.id.et_tel_owner))
			.setText(preferences.getString(OWNER_TELEPHONE, ""));
			
			((EditText) vi.findViewById(R.id.et_mobile_owner))
			.setText(preferences.getString(OWNER_MOBILE, ""));
			
			((EditText) vi.findViewById(R.id.et_mail_id_owner))
			.setText(preferences.getString(OWNER_EMAIL, ""));
			
			((EditText) vi.findViewById(R.id.et_address_contractor))
			.setText(preferences.getString(CP_ADDRESS, ""));
			
			((EditText) vi.findViewById(R.id.et_name_contracter))
			.setText(preferences.getString(CP_NAME, ""));
			
			((EditText) vi.findViewById(R.id.et_town_contractor))
			.setText(preferences.getString(CP_TOWN, ""));
			
			((EditText) vi.findViewById(R.id.et_postal_contractor))
			.setText(preferences.getString(CP_POSTAL_CODE, ""));
			
			((EditText) vi.findViewById(R.id.et_tel_contractor))
			.setText(preferences.getString(CP_TELEPHONE, ""));
			
			((EditText) vi.findViewById(R.id.et_mobile_contractor))
			.setText(preferences.getString(CP_MOBILE, ""));
			
			((EditText) vi.findViewById(R.id.et_mail_id_contractor))
			.setText(preferences.getString(CP_EMAIL, ""));
			
			((EditText) vi.findViewById(R.id.et_iban_payment))
			.setText(preferences.getString(IBAN, ""));
			
			((EditText) vi.findViewById(R.id.et_swift_payment))
			.setText(preferences.getString(SWIFT, ""));
			
			
		}

	}

	private void storeOwnerInfo(View vi) {
		// TODO Auto-generated method stub

		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("cFlag", true);
		editor.putString(OWNER_ADDRESSS, ((EditText) vi.findViewById(R.id.et_address_owner))
								.getText().toString());
		editor.putString(OWNER_NAME, ((EditText) vi.findViewById(R.id.et_name_owner))
								.getText().toString());
		editor.putString(OWNER_TOWN, ((EditText) vi.findViewById(R.id.et_town_owner))
								.getText().toString());
		editor.putString(OWNER_POSTAL_CODE,
				((EditText) vi.findViewById(R.id.et_postal_code_owner))
								.getText().toString());
		editor.putString(OWNER_TELEPHONE,
				((EditText) vi.findViewById(R.id.et_tel_owner)).getText().toString());
		Log.d("TelePhone Value",""+((EditText) vi.findViewById(R.id.et_tel_owner))
				.getText().toString());
		editor.putString(OWNER_MOBILE,
					((EditText) vi.findViewById(R.id.et_mobile_owner))
								.getText().toString());
		editor.putString(OWNER_EMAIL, ((EditText) vi.findViewById(R.id.et_mail_id_owner))
								.getText().toString());
		editor.putString(
				CP_ADDRESS, ((EditText) vi
								.findViewById(R.id.et_address_contractor))
								.getText().toString());
		editor.putString(CP_NAME, ((EditText) vi.findViewById(R.id.et_name_contracter))
								.getText().toString());
		editor.putString(CP_TOWN, ((EditText) vi.findViewById(R.id.et_town_contractor))
								.getText().toString());
		editor.putString(
				CP_POSTAL_CODE, ((EditText) vi
								.findViewById(R.id.et_postal_contractor))
								.getText().toString());
		editor.putString(CP_TELEPHONE,((EditText) vi.findViewById(R.id.et_tel_contractor))
								.getText().toString());
		editor.putString(
				CP_MOBILE, ((EditText) vi
								.findViewById(R.id.et_mobile_contractor))
								.getText().toString());
		editor.putString(
				CP_EMAIL,
				""
						+ ((EditText) vi
								.findViewById(R.id.et_mail_id_contractor))
								.getText().toString());
		editor.putString(IBAN, ((EditText) vi
				.findViewById(R.id.et_iban_payment)).getText().toString());
		editor.putString(SWIFT, ((EditText) vi
				.findViewById(R.id.et_swift_payment)).getText().toString());
		editor.commit();
		
	}

	private void setTableLayoutForBuildingMaterial(View vi, final int pos) {
		// TODO Auto-generated method stub

		tLayout = (TableLayout) vi.findViewById(R.id.tbl_for_distance_item);
		btn_add_distance = (Button) vi.findViewById(R.id.btn_add_distance);
		if (pos == 9) {
			btn_add_distance.setText("Add Material");
		} else if (pos == 10) {
			btn_add_distance.setText("Add Vacation Detail");
		} else if (pos == 12) {
			btn_add_distance.setText("Add Kitchen Detail");
		} else if (pos == 13) {
			btn_add_distance.setText("Add Furniture Detail");
		}
		btn_add_distance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showPopUp(pos);
			}

		});

	}

	private void addTableRowForBuildingMaterial(String mName, int pos) {
		// TODO Auto-generated method stub
		TableRow row = new TableRow(getBaseContext());
		if (pos == 9) {
			row.setId(row_counter_for_bMaterial);
		} else if (pos == 10) {
			row.setId(row_counter_for_vacation);
		}

		TableLayout.LayoutParams rowLp = new TableLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
		TableRow.LayoutParams cellLp = new TableRow.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

		View vi = inflater.inflate(R.layout.tbl_rw_layout_building_material,
				null);
		CheckBox cbMaterialName = (CheckBox) vi
				.findViewById(R.id.ch_bx_brick_house);
		if (pos == 9) {
			cbMaterialName.setId(row_counter_for_bMaterial * 10);
		} else if (pos == 10) {
			cbMaterialName.setId(row_counter_for_vacation * 10);
		}
		cbMaterialName.setText(mName);
		final ImageButton btn_add_des = (ImageButton) vi
				.findViewById(R.id.btn_add_material_description);
		EditText et_add_description = (EditText) vi
				.findViewById(R.id.et_material_description);
		btn_add_des.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LinearLayout ll = (LinearLayout) btn_add_des.getParent();
				LinearLayout ll2 = (LinearLayout) ll.getParent();
				LinearLayout ll3 = (LinearLayout) ll2.getParent();
				TableRow tr = (TableRow) ll3.getParent();

				EditText et = (EditText) tr
						.findViewById(R.id.et_material_description);

				if (flag_materail_des) {
					et.setVisibility(View.GONE);
					flag_materail_des = false;
				} else {
					et.setVisibility(View.VISIBLE);
					flag_materail_des = true;
				}

			}
		});

		row.addView(vi, cellLp);

		tLayout.addView(row, rowLp);
		if (pos == 9) {
			row_counter_for_bMaterial++;
		} else if (pos == 10) {
			row_counter_for_vacation++;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		switch (fSequence) {
		case 10:
			setTitle(navMenuTitles[0]);
			fSequence = 0;
			break;
		case 20:
			setTitle(navMenuTitles[1]);
			fSequence = 10;
			break;
		case 30:
			setTitle(navMenuTitles[2]);
			fSequence = 20;
			break;
		case 40:
			setTitle(navMenuTitles[3]);
			fSequence = 30;
			break;
		case 50:
			setTitle(navMenuTitles[4]);
			fSequence = 40;
			break;
		case 60:
			setTitle(navMenuTitles[5]);
			fSequence = 50;
			Log.d("fSequence", "" + fSequence);
			break;
		case 70:
			setTitle(navMenuTitles[6]);
			fSequence = 60;
			break;
		case 80:
			setTitle(navMenuTitles[7]);
			fSequence = 70;
			break;
		case 90:
			setTitle(navMenuTitles[8]);
			fSequence = 80;
			break;
		case 100:
			setTitle(navMenuTitles[9]);
			fSequence = 90;
			break;
		case 110:
			setTitle(navMenuTitles[10]);
			fSequence = 100;
			break;
		case 120:
			setTitle(navMenuTitles[11]);
			fSequence = 110;
			break;
		default:

			break;
		}
	}

}
