package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends ListFragment {
	
	private static final String TAG = "CrimeListFragment";
	
	private ArrayList<Crime> mCrimes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLab.get(getActivity()).getCrimes();
		
		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		
		super.setListAdapter(adapter);
	}
	
	/**
	 * Why override onResume() to update the list view and not onStart() ? You cannot assume that your activity will be stopped when another activity is 
	 * in front of it. If the other activity is transparent, your activity may just be paused. If your activity is paused and your update code is in onStart(), 
	 * then the list will not be reloaded. In general, onResume() is the safest place to take action to update a fragment�s view. 
	 */
	
	@Override 
	public void onResume() { 
		super.onResume(); 
		((CrimeAdapter)getListAdapter()).notifyDataSetChanged(); 
	} 
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);
//		Log.d(TAG, c.getTitle() + " was clicked");
		
		//Start CrimeActivity
//		Intent i = new Intent(getActivity(), CrimeActivity.class);
		//Start CrimePagerActivity with this crime
		Intent i = new Intent(getActivity(), CrimePagerActivity.class);
		i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
		startActivity(i);
	}
	
	private class CrimeAdapter extends ArrayAdapter<Crime> {
		
		public CrimeAdapter(ArrayList<Crime> crimes) {
			super(getActivity(), 0, crimes);
		}
		
		//The convertView parameter is an existing list item that the adapter can reconfigure and return instead of creating a brand new object. 
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//If we weren't given a view, inflate one 
			if (convertView == null) { 
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null); 
			} 
			
			//Configure the view for this Crime 
			Crime c = getItem(position);
			
			TextView titleTextView = (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView); 
			titleTextView.setText(c.getTitle()); 
			
			TextView dateTextView = (TextView)convertView.findViewById(R.id.crime_list_item_dateTextView); 
			dateTextView.setText(c.getDate().toString()); 
			
			CheckBox solvedCheckBox = (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
			solvedCheckBox.setChecked(c.isSolved());
			
			return convertView;
		}
	}
}
