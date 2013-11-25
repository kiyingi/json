package com.news.ialert;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.news.ialert.pdetailsFragment.getdetailsTask;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;

public class notificationService extends Service implements LocationListener{

	 private LocationManager locationManager;
	  private String provider;
	  HttpEntity resEntity;
	public static double latitude;  
	int mytoken=0;
  public static double longitude;
  String gpsimage=null;
  public static final String TAG_SUCCESS = "success";
	String myid;
	 boolean isLight;
	 MediaPlayer  firstSound;
	 int succ=0;
	 JSONParser jsonParser = new JSONParser();
	 private static final String TAG_PRODUCTS = "station";
	 String sender;
	 Uri alert;
	 Ringtone r;
	 int id;
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
		  alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
	         if(alert == null){
	             // alert is null, using backup
	             alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	             if(alert == null){  // I can't see this ever being null (as always have a default notification) but just incase
	                 // alert backup is null, using 2nd backup
	                 alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                             
	             }
	         }
	         
	          r = RingtoneManager.getRingtone(getApplicationContext(), alert);
		
		  pingtask task=new pingtask();
	         task.execute();
 	   
	         firstSound = MediaPlayer.create(notificationService.this, R.raw.sound);   
            
            super.onCreate();
           
     }

     @Override
     public void onDestroy() {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "", 1).show();
            super.onDestroy();
     }

     @Override
     public int onStartCommand(Intent intent, int flags, int startId) {
            // TODO Auto-generated method stub
            
    	pingtask task=new pingtask();
         task.execute(); 
            
    	 
   	    
   	    
   	    
   	    
            return super.onStartCommand(intent, flags, startId);
     }
	@Override
	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void onProviderDisabled(String loc) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	class getnotificationTask extends AsyncTask<String, Void, String> {
		
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            
            succ=0;
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
        	//String login_name=args[0];
            
 
            // Building Parameters
        	// Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("myid", args[1]));
 
            // getting JSON Object
    JSONObject json = jsonParser.makeHttpRequest(args[0], "GET", params);
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                	
                	succ=1;
                	
                	JSONArray  stationss = json.getJSONArray(TAG_PRODUCTS);
                	 
                	
                	 
                     // looping through All messages
                	 for (int i = 0; i < stationss.length(); i++) {
                         JSONObject c = stationss.getJSONObject(i);

                     
                      
                     
                      
                      sender=c.getString("name");
                      id=c.getInt("nid");
                      
                      SharedPreferences preferences =getSharedPreferences("news",
                   	        MODE_PRIVATE);
                   	    SharedPreferences.Editor editor = preferences.edit();
                       
                   	    
                   	    String news_id=String.valueOf(id);
                   	
                   	    editor.putString("news_id", news_id);
                   	    
                   	    editor.commit();
                   	  
                   
                 
                   }
                	
                }
                
                
                else {
                	
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            
            if(succ==1)
            {
            	
            	showNotification();	
                    
                  
            	
            }
            else
            {
            	
            	 firstSound.stop();
            }
        }
 
    }
	
	
	
	
	
	 class pingtask extends AsyncTask<String, Void, String> {
		 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            
	          //  pDialog2.show();
	            
	            
	            
	        }       
	        protected String doInBackground(String... args) {
				
	        	
	        	InetAddress in;
		        in = null;
		        
		        try {
		            in = InetAddress.getByName("192.168.43.5");
		        } catch (UnknownHostException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		        
		       
		        try {
		            if (in.isReachable(5000)) {
		         
		            	mytoken=1;
		            } else {
		            	mytoken=2;
		               
		            }
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		            
		        	e.printStackTrace();
		        }
	        	
	        	
	        	
	        	return null;
	        	
	            
	        }
	 
	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	           // pDialog2.dismiss();
	            if(mytoken==1)
	            {
	            	
	            	
	                
	            	 SharedPreferences preferences = getSharedPreferences("login",
	     	    			MODE_PRIVATE);
	     	    		
	     	    		  int   uid = preferences.getInt("userid", 0);
	        	   
	                     myid=String.valueOf(uid); 
	                       
	     		
	                     if (uid!=0)
	                     {
	               
	                    	 String url = "http://192.168.43.5/ialert/checknotification.php";
	                     getnotificationTask task=new getnotificationTask();
	                task.execute(url,myid); 
	            	
	            		
	                     }
	            	
	            }
	            else if (mytoken==2)
	            {
	            	
	          
	            }    	
	            
	        }
	 
	    }
		   
	
	
	
	
	
	public void showNotification(){
		  
		        // define sound URI, the sound to be played when there's a notification
		         Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		 
	       // intent triggered, you can add other intent for other actions
		       /*
		         Intent intent = new Intent(notificationService.this, notificationReceiver.class);
		        PendingIntent pIntent = PendingIntent.getActivity(notificationService.this, 0, intent, 0);
		  
		         // this is it, we'll build the notification!
		       // in the addAction method, if you don't want any icon, just set the first param to 0
		        Notification mNotification = new Notification.Builder(this)
		
		            .setContentTitle("News sent by"+sender)
		             .setContentText("news updates near your area")
		             .setSmallIcon(R.drawable.ntv)
		             .setContentIntent(pIntent)
		             .setSound(soundUri)
		 
		             .addAction(R.drawable.ntv, "View", pIntent)
		             .addAction(0, "Remind", pIntent)
		
			            .build();
		
		        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		 
	        // If you want to hide the notification after it was selected, do the code below
		        // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
	 
		        notificationManager.notify(0, mNotification);
		        
		        */
		         


		                 firstSound.start();

		         
		        
		        ///////////////////////////////////////////////////////////////
		         
		        
		     
		        
		        NotificationCompat.Builder builder =
		                new NotificationCompat.Builder(this)
		                        .setSmallIcon(R.drawable.ntv)
		                        .setContentTitle("News sent by"+sender)
		                        .setContentText("news updates near your area");
		                       
		        int NOTIFICATION_ID = 12345;

		        Intent targetIntent = new Intent(this, notificationReceiver.class);
		        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		        builder.setContentIntent(contentIntent);
		        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		        nManager.notify(NOTIFICATION_ID, builder.build());
		     
		        
		    }
	
	
	
	
    private void displayconfirm()
		{
			
				//Put up the Yes/No message box
			    	AlertDialog.Builder builder = new AlertDialog.Builder(notificationService.this);
			    	builder
			    	
			    	.setMessage("News have arrived")
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
	
	
	


	      		
}