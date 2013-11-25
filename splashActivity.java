package com.news.ialert;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;



public class splashActivity extends SherlockFragmentActivity{
		
		
		
		
		
		


		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			

			  getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>i-Alert</font>"));
				 
			
			
			setContentView(R.layout.activity_main);
			String no="alert";
			

	       

			 
			 
			
			Thread timer=new Thread() {

				@Override
				public void run() {
					
					
					try{
						
						sleep(3000);
						
						
						
					}
					catch(InterruptedException e)
					{
					e.printStackTrace();	
					}
					
					finally
					{
						
						
						
						//Intent i=new Intent(Splash.this,indexTabActivity.class);
						Intent i=new Intent(splashActivity.this,MainActivity.class);
						
						
						
				        startActivity(i);
				        
				        
				        finish();
					}
					
				}
				
				
				
				
			};
			
			timer.start();
				
				
				
			}
			
			

}
