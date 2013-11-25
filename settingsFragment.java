package com.news.ialert;

import uk.co.jasonfry.android.tools.ui.SwipeView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class settingsFragment extends SherlockFragment{

	SwipeView mSwipeView;
	 
	int[] images;
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 
		 
	    	
	    	
	        View rootView = inflater.inflate(R.layout.settings, container, false);
	        
	      
	        
	    	return rootView;
	    }
	    
	  


}
