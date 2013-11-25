package com.news.ialert;



import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;

import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;


import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends SherlockFragmentActivity {
	 DrawerLayout mDrawerLayout;
	    ListView mDrawerList;
	    ActionMode mMode;
	    ActionBarDrawerToggle mDrawerToggle;
	    MenuListAdapter mMenuAdapter;
	    String[] title;
	    String[] subtitle;
	    int[] icon;
	   
	    int mytoken1=0;
	    int mytag=0;
	    String isdealer="no";
	  homeFragment home=new    homeFragment();
	  helpFragment help=new    helpFragment();
	  newsUpdates news=new    newsUpdates();
	  pupdatesFragment pupdates=new    pupdatesFragment();
	  reportFragment report=new    reportFragment();
	  settingsFragment setting=new    settingsFragment();
	  vistntvFtagment visit=new    vistntvFtagment();
	  aboutFragment about=new    aboutFragment();
	  
	  
	    
	    
	    Handler mHandler = new Handler();
	    Runnable mProgressRunner = new Runnable() {
	        @Override
	        public void run() {
	            mProgress += 2;

	            //Normalize our progress along the progress bar's scale
	            int progress = (Window.PROGRESS_END - Window.PROGRESS_START) / 100 * mProgress;
	            setSupportProgress(progress);

	            if (mProgress < 100) {
	                mHandler.postDelayed(mProgressRunner, 50);
	            }
	        }
	    };

	    private int mProgress = 100;
	    
	    
	    
	    
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        
	        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);


	        setContentView(R.layout.drawer_main);
	        
	        
	        Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, 10);
           
            Intent intent = new Intent(this, locationService.class);
    
            PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
           
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            
            
            
            
            
            Calendar cal2 = Calendar.getInstance();
            cal2.add(Calendar.SECOND, 10);
           
            Intent intent2 = new Intent(this, notificationService.class);
    
            PendingIntent pintent2 = PendingIntent.getService(this, 0, intent2, 0);
           
            AlarmManager alarm2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            //for 30 mint 60*60*1000
            
            
	        String fname;
	    	 String lname;
	    	 String account;
	    	 int uid;
	    	 String mylogin;
	        SharedPreferences preferences = getSharedPreferences("login",
	    			MODE_PRIVATE);
	    		    account = preferences.getString("atype", "");
	    		    uid = preferences.getInt("userid", 0);
	    		    fname=preferences.getString("fname", "");
	    		    lname=preferences.getString("lname", "");
	    		    String no="";
	    		    if(lname.equalsIgnoreCase(no) && fname.equalsIgnoreCase(no))
		   	    	 {
		   	    		
		   		    
		   		    	mylogin="Login";
		   		    	
		   		    	mytag=0;
		   		    	
		   		     stopService(new Intent(getBaseContext(), locationService.class));
		   		    	
		   		  stopService(new Intent(getBaseContext(), notificationService.class));
		   		    
		   	    	 }
		   	    	 else
		   	    	 {
		   	    		 alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
		                         1*30*1000, pintent);
		            
		   	    		 
		   	    		alarm2.setRepeating(AlarmManager.RTC_WAKEUP, cal2.getTimeInMillis(),
		                         1*30*1000, pintent2);
		            
		            
		   	    		startService(new Intent(getBaseContext(), locationService.class));
		   	    		startService(new Intent(getBaseContext(), notificationService.class));
		   	    		mylogin="Logoff";
		   	    		mytag=1;
		   	    	 }
	    		    
	    		    
	        // Generate title
	        title = new String[] {"Home","Report News","News Updates", "Visit NTV", "Help",
	                "Programe Updates" ,mylogin,"Settings","Quit"};
	 
	      //  mMode = startActionMode(new AnActionModeOfEpicProportions());
	        
	        setSupportProgressBarIndeterminateVisibility(false);

	     
	        
	        // Generate subtitle
	        subtitle = new String[] { "Home","general","Subtitle Fragment 1", "Subtitle Fragment 2",
	                "Subtitle Fragment 3","Subtitle Fragment 4" ,"Subtitle Fragment 5","payments","quit"};
	       // getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
			
	        // Generate icon
	        icon = new int[] {R.drawable.home,R.drawable.navdrawer_friends, R.drawable.home,R.drawable.via_google_wallet_c, R.drawable.via_mail_c,
	                R.drawable.listings ,R.drawable.navdrawer_friends,R.drawable.via_paypal_c,R.drawable.navdrawer_quit};
	 
	        // Locate DrawerLayout in drawer_main.xml
	        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	 
	        // Locate ListView in drawer_main.xml
	        mDrawerList = (ListView) findViewById(R.id.left_drawer);
	 
	        // Set a custom shadow that overlays the main content when the drawer
	        // opens
	        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
	                GravityCompat.START);
	 
	        // Pass results to MenuListAdapter Class
	        mMenuAdapter = new MenuListAdapter(this, title, subtitle, icon);
	 
	        // Set the MenuListAdapter to the ListView
	        mDrawerList.setAdapter(mMenuAdapter);
	 
	        // Capture button clicks on side menu
	        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	 
	        // Enable ActionBar app icon to behave as action to toggle nav drawer
	        getSupportActionBar().setHomeButtonEnabled(true);
	        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	 
	        // ActionBarDrawerToggle ties together the the proper interactions
	        // between the sliding drawer and the action bar app icon
	        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
	                R.drawable.ic_drawer, R.string.drawer_open,
	                R.string.drawer_close) {
	 
	            public void onDrawerClosed(View view) {
	                // TODO Auto-generated method stub
	                super.onDrawerClosed(view);
	            }
	 
	            public void onDrawerOpened(View drawerView) {
	                // TODO Auto-generated method stub
	                super.onDrawerOpened(drawerView);
	            }
	        };
	 
	        mDrawerLayout.setDrawerListener(mDrawerToggle);
	 
	        if (savedInstanceState == null) {
	            selectItem(0);
	        }
	    }
	 
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getSupportMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
	 
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	 
	        if (item.getItemId() == android.R.id.home) {
	 
	            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
	                mDrawerLayout.closeDrawer(mDrawerList);
	            } else {
	                mDrawerLayout.openDrawer(mDrawerList);
	            }
	        }
	 
	        return super.onOptionsItemSelected(item);
	    }
	 
	    // The click listener for ListView in the navigation drawer
	    private class DrawerItemClickListener implements
	            ListView.OnItemClickListener {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position,
	                long id) {
	            selectItem(position);
	        }
	    }
	 
	    private void selectItem(int position) {
	 
	        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	        // Locate Position
	        switch (position) {
	        
	        case 0:
	        
		          ft.replace(R.id.content_frame, home);
		          
		          ft.setTransition(
		        		  android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		       
		            break;
	        case 1:
	        	//setSupportProgressBarIndeterminateVisibility(true);
	          //ft.replace(R.id.content_frame, home);
	          
	         // Intent intent = getIntent();
	         // finish();
	         // startActivity(intent);
	        	 ft.replace(R.id.content_frame, report);
		          
		          ft.setTransition(
		        		  android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		       
	          
	         
	            break;
	        case 2:
	        	/*
	        	 ft.replace(R.id.content_frame, news);
		          
		          ft.setTransition(
		        		  android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		       
*/
	        	   Intent k=new Intent(MainActivity.this,newsUpdates.class);
					startActivity(k);
	        	

	            break;
	        case 3:
	        	 ft.replace(R.id.content_frame, visit);
		          
		          ft.setTransition(
		        		  android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		       
	        	
	            break;
	            
	        case 4:
	        	 ft.replace(R.id.content_frame, help);
		          
		          ft.setTransition(
		        		  android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		       
		            break;
	        case 5:
	        	
	        	
	        	
	        	 ft.replace(R.id.content_frame, pupdates);
		          
		          ft.setTransition(
		        		  android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		       
	        	
		            break;
		            
          case 6:
        	  
        	  if(mytag==0)
        	  {
        	  ft.replace(R.id.content_frame, about);
	          
	          ft.setTransition(
	        		  android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	       
        	  }
        	  else
        	  {
        		  logoutaleart();  
        	  }
	    
		            break;
		            
		            
		            
         
		            
	        case 7:
	        	
	        	
		          
		          Intent s=new Intent(MainActivity.this,preference.class);
					startActivity(s);
	        	
		            break;
		            
	        case 8:
	        	
	        	displayconfirm();
	        	
		            break;
		            
		            
		            
	        }
	        ft.commit();
	        mDrawerList.setItemChecked(position, true);
	        // Close drawer
	        mDrawerLayout.closeDrawer(mDrawerList);
	    }
	 
	    @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        mDrawerToggle.syncState();
	    }
	 
	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        // Pass any configuration change to the drawer toggles
	        mDrawerToggle.onConfigurationChanged(newConfig);
	    }
	    
	    
	    //***************************************************
	    

	    public void doPositiveClick() {
	        // Do stuff here.
	        Log.i("FragmentAlertDialog", "Positive click!");
	    }

	   
	    private void displayconfirm()
		{
			
				//Put up the Yes/No message box
			    	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			    	builder
			    	
			    	.setMessage("Are you sure to exit?")
			    	.setTitle("Exit")
			    	//.setIcon(android.R.drawable.ic_dialog_alert)
			    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			    	    public void onClick(DialogInterface dialog, int which) {			      	
			    	    	 android.os.Process.killProcess(android.os.Process.myPid()); 
			                              
			    	    }
			    	})
			    	.setNegativeButton("No", null)						//Do nothing on no
			    	.show();
			  
		      	
		}
	    
	    private void logoutaleart()
		{
			
				//Put up the Yes/No message box
			    	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			    	builder
			    	//.setTitle("")
			    	//calculatedvalue
			    	.setMessage("Logout ?")
			    	//.setIcon(android.R.drawable.ic_dialog_alert)
			    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			    	    public void onClick(DialogInterface dialog, int which) {			      	
			    	    	
			    	    			
			    	    	mytag=0;	
			    	    	
			    	    	SharedPreferences preferences = getSharedPreferences("login",
	                    	        MODE_PRIVATE);
	                    	    SharedPreferences.Editor editor = preferences.edit();

	                    	    editor.putString("fname", "");
	                    	    editor.putString("lname", "");
	                    	    editor.putString("atype", "");
	                    	    editor.putInt("userid", 0);
	                    	    editor.commit();
	                    	    
	                    	    
	                    	    Intent r=new Intent(MainActivity.this,MainActivity.class);
	        			        startActivity(r);   
	           		    	 
	                    	  
		
			         	
			    	    }
			    	})
			    	.setNegativeButton("No", null)						//Do nothing on no
			    	.show();
			  
		      	
		}
	    
}

