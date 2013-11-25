package com.news.ialert;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class preference extends PreferenceActivity{

	 @Override
	     public void onCreate(Bundle savedInstanceState) {       
	         super.onCreate(savedInstanceState);       
	       addPreferencesFromResource(R.layout.main_pref);       
	     }
	
}
