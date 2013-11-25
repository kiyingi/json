package com.news.ialert;

import uk.co.jasonfry.android.tools.ui.PageControl;
import uk.co.jasonfry.android.tools.ui.SwipeView;
import uk.co.jasonfry.android.tools.ui.SwipeView.OnPageChangedListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;


public class helpFragment extends SherlockFragment{

	SwipeView mSwipeView;
	 
	int[] images;
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 
		 
	    	
	    	
	        View rootView = inflater.inflate(R.layout.help, container, false);
	        
	       TextView helptxt=(TextView)rootView.findViewById(R.id.uhelp);
	       Animation anim = new AlphaAnimation(0.0f, 1.0f);
	       anim.setDuration(250); //You can manage the time of the blink with this parameter
	       anim.setStartOffset(50);
	       anim.setRepeatMode(Animation.REVERSE);
	       anim.setRepeatCount(Animation.INFINITE);
	       helptxt.startAnimation(anim);
	    	return rootView;
	    }
	    
	  


}
