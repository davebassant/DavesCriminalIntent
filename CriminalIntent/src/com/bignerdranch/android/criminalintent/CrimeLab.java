package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class CrimeLab {
	
	private ArrayList<Crime> mCrimes;

	private static CrimeLab sCrimeLab;
	private Context mAppContext;
	
	private CrimeLab(Context appContext) {
		mAppContext = appContext;
		mCrimes = new ArrayList<Crime>();
		
		for (int i = 0; i < 100; i++) {
			Crime c = new Crime();
			c.setTitle("Crime #" + i);
			c.setSolved(i % 2 == 0);
			mCrimes.add(c);
		}
	}
	
	/**
	 * Notice in get(Context) that you do not directly pass in the Context parameter to the constructor. This Context could be an Activity or 
	 * another Context object, like Service. You cannot be sure that just any Context will exist as long as CrimeLab needs it, which is for 
	 * the life of the application.
	 * 
	 * To ensure that your singleton has a long-term Context to work with, you call getApplicationContext() and trade the passed-in Context 
	 * for the application context. The application context is a Context that is global to your application. Whenever you have an application-wide 
	 * singleton, it should always use the application context.
	 */
	
	public static CrimeLab get(Context c) {
		if (sCrimeLab == null) {
			sCrimeLab = new CrimeLab(c.getApplicationContext());
		}
		
		return sCrimeLab;
	}
	
	public ArrayList<Crime> getCrimes() {
		return mCrimes;
	}
	
	public Crime getCrime(UUID id) {
		for (Crime crime : mCrimes) {
			if (crime.getId().equals(id)) {
				return crime;
			}
		}
		
		return null;
	}
}
