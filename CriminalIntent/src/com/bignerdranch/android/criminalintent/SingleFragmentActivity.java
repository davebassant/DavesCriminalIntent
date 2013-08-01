package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class SingleFragmentActivity extends FragmentActivity {
	
	public abstract Fragment createFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		
		FragmentManager fm = super.getSupportFragmentManager();
		/**
		 * You call getSupportFragmentManager() because you are using the support library and the 
		 * FragmentActivity class. If you were not interested in compatibility with pre-Honeycomb 
		 * devices, then you would subclass Activity and call getFragmentManager(). 
		 */
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer); //fragmentContainer defined in activity_fragment.xml (frame layout)
		
		/**
		 * In this code, you set the activity’s view to be inflated from activity_fragment.xml (id=fragmentContainer). Then you look for 
		 * the fragment in the FragmentManager in that container, creating and adding it if it does not exist. 
		 */
		
		if (fragment == null) {
			fragment = createFragment();
			fm.beginTransaction()
				.add(R.id.fragmentContainer, fragment)
				.commit();
			
			/**
			 * Remember Transactions from Database studies. This transaction is saying "Create a new 
			 * fragment transaction, include one add operation on it and commit"
			 * 
			 * Fragment transactions are used to add, remove, attach, detach, or replace fragments in the 
			 * fragment list.
			 */
		}
	}
}

/**
* One critical difference between the fragment life-cycle and the activity life-cycle is that 
* fragment life-cycle methods are called by the hosting activity, not the OS. The OS knows nothing 
* about the fragments that an activity is using to manage things. 
*/
