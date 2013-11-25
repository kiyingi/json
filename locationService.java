package com.news.ialert;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class locationService extends Service implements LocationListener{

	 private LocationManager locationManager;
	  private String provider;
	  HttpEntity resEntity;
	public static double latitude;  
   public static double longitude;
   String gpsimage=null;
	@Override
	public IBinder onBind(Intent arg0) {
		//TODO Auto-generated method stub
		return null;
	}
	  @Override
      public void onCreate() {
             // TODO Auto-generated method stub
		  locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE); 
		    
	      // Define the criteria how to select the locatioin provider -> use
	    // default

		  Criteria criteria = new Criteria();
  	    provider = locationManager.getBestProvider(criteria, false);
  	    Location location = locationManager.getLastKnownLocation(provider);
  	    
  	    if (location != null) {
  	        System.out.println("Provider " + provider + " has been selected.");
  	        onLocationChanged(location);
  	      } else {
  	    	 // Toast.makeText(getActivity(), "Location not available at the moment", Toast.LENGTH_LONG).show();
  	    		
  	      }
  	    
  	   
                
                  Thread thread=new Thread(new Runnable(){
                         public void run() {
                         	
                         	
                             doFileUpload5();
                             
                           
                         
                             }
                         });
                 
			 
			 
                 thread.start();
             
             super.onCreate();
      }

      @Override
      public void onDestroy() {
             // TODO Auto-generated method stub
             Toast.makeText(getApplicationContext(), "GPS Service Destroy", 1).show();
             super.onDestroy();
      }

      @Override
      public int onStartCommand(Intent intent, int flags, int startId) {
             // TODO Auto-generated method stub
             
             
             
     	    Criteria criteria = new Criteria();
    	    provider = locationManager.getBestProvider(criteria, false);
    	    Location location = locationManager.getLastKnownLocation(provider);
    	    
    	    if (location != null) {
    	        System.out.println("Provider " + provider + " has been selected.");
    	        onLocationChanged(location);
    	      } else {
    	    	 // Toast.makeText(getActivity(), "Location not available at the moment", Toast.LENGTH_LONG).show();
    	    		
    	      }
    	    
    	   
                  
                    Thread thread=new Thread(new Runnable(){
                           public void run() {
                           	
                           	
                               doFileUpload5();
                               
                             
                           
                               }
                           });
                   
			 
			 
                   thread.start();
               
		
		
           
    	    
    	    
    	    
    	    
             return super.onStartCommand(intent, flags, startId);
      }
	@Override
	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		
		 loc.getLatitude();  
	        loc.getLongitude();  
	        latitude=loc.getLatitude();  
	        longitude=loc.getLongitude(); 
	        
	        Toast.makeText(this, "lat="+latitude+"  longitude="+longitude, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onProviderDisabled(String loc) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Please enable your Gps to report News", Toast.LENGTH_LONG).show();
		
	}
	
	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	 private void doFileUpload5(){
		 String path = getResources().getString(R.drawable.bluebg);


	        File file1 = new File(path);
	       
	        String urlString = "http://192.168.43.5/ialert/update_db.php";
	        try
	        {
	             HttpClient client = new DefaultHttpClient();
	             HttpPost post = new HttpPost(urlString);
	           
	           
	             MultipartEntity reqEntity = new MultipartEntity();
	            
	             //////
	             
	             
	        
	              String lat=Double.toString(latitude);
	              String longt=Double.toString(longitude);
	             
	          
	           
	            
	             
	            
	             //////
	          
	             SharedPreferences preferences =getSharedPreferences("data",
	           	       this. MODE_PRIVATE);
	           	    
	      			    		   String date_time = preferences.getString("date_time", "");   
	            
	      			    		 SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
	      			           
	      			           String sender=sharedPrefs.getString("names", "not defined"); 
	      			           
	      			         String contact=sharedPrefs.getString("number", "not defined"); 
	           
	      			          
	      			         
	      			       String fname;
	      		    	 String lname;
	      		    	 String account;
	      		    	 int uid;
	      		  	 String  id="0"; ;
	      		    	 String mylogin;
	      		        SharedPreferences preferences3 = getSharedPreferences("login",
	      		    			MODE_PRIVATE);
	      		    		    account = preferences3.getString("atype", "");
	      		    		    uid = preferences3.getInt("userid", 0);
	      		    		    fname=preferences3.getString("fname", "");
	      		    		    lname=preferences3.getString("lname", "");
	      		    		    if(uid!=0)
	      		    		    {
	      		    		    	
	      		    		    	id=String.valueOf(uid);
	      		    		    }
	      		    		    
	      		    		    String no="";
		      			 
		      			 	  
	      			     
	      			       reqEntity.addPart("lat", new StringBody(lat));
	      			       reqEntity.addPart("longt", new StringBody(longt)); 
	      			     
	      		             
	      		            reqEntity.addPart("id", new StringBody(id));
	      		      
	      		             post.setEntity(reqEntity);
	      		             HttpResponse response = client.execute(post);
	      		             resEntity = response.getEntity();
	      		             final String response_str = EntityUtils.toString(resEntity);
	      		             if (resEntity != null) {
	      		                 Log.i("RESPONSE",response_str);
	      		                 
	      		                 
	      		                             try {
	      		                               
	      		                                Toast.makeText(getApplicationContext(),"Your Submission has sucessfully sent.", Toast.LENGTH_LONG).show();
	      		                              
	      		                               
	      		                                
	      		                             } catch (Exception e) {
	      		                                e.printStackTrace();
	      		                            }
	      		                  
	      		             }
	      		        }
	      		        catch (Exception ex){
	      		             Log.e("Debug", "error: " + ex.getMessage(), ex);
	      		        }
	      		      }
	      		
}
	 