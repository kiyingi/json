package com.news.ialert;

import uk.co.jasonfry.android.tools.ui.SwipeView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.actionbarsherlock.app.SherlockFragment;

public class vistntvFtagment extends SherlockFragment{

	SwipeView mSwipeView;
	 
	int[] images;
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 
		 
	    	
	    	
	        View rootView = inflater.inflate(R.layout.visit_ntv, container, false);
	       
	        
	        WebView myWebView = (WebView)rootView. findViewById(R.id.webview);
	        WebSettings webSettings = myWebView.getSettings();
	        webSettings.setJavaScriptEnabled(true);
	        myWebView.loadUrl("http://www.ntv.co.ug");
	      
	        
	    	return rootView;
	    }
	    
	  


}
