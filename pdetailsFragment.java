package com.news.ialert;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.jasonfry.android.tools.ui.SwipeView;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockListFragment;






public class pdetailsFragment extends SherlockListFragment implements FetchpdetailsListener{

	SwipeView mSwipeView;
	 
	int[] images;
	int mytoken=0;
	int tag=0;
	   public static final String TAG_SUCCESS = "success";
		
		 boolean isLight;
		 JSONParser jsonParser = new JSONParser();
		 private static final String TAG_PRODUCTS = "station";
	
	List<pdetails> apps;
	int succ=0;
	 private ProgressDialog dialog;
	
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 
		 
	    	
	    	
	        View rootView = inflater.inflate(R.layout.pdetails_main, container, false);
	        
	        apps = new ArrayList<pdetails>();
	        initView(); 
	        
	        if(tag==1)
	        {
	       ListView lv = getListView();
	       
	       //////
	       
	       lv.setOnItemClickListener(new OnItemClickListener() {
	    		 
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
	            	
	                // getting values from selected ListItem
	            	
	            	 /*
	            	
	            	 ft.replace(R.id.content_frame, home);
			          
			          ft.setTransition(
			        		  android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			       
	            	  Intent i = new Intent(ggetApplicationContext(),
	            		         updatedetailsActivity.class);
	            	  i.putExtras(b);
	            	  startActivity(i);
	            	  */
	            	
	            }
	        });
	       /////
	       
	       
	       
	       
	        }
	      
	        
	    	return rootView;
	    }
	 
	 private void initView() {
	        // show progress dialog
	        //dialog = ProgressDialog.show(getActivity(), "", "Loading please wait...");
		 pingtask task=new pingtask();
		 task.execute();
	    	
	    }
	

	@Override
	public void onFetchComplete(List<pdetails> data) {
		// TODO Auto-generated method stub
		if(dialog != null)  dialog.dismiss();
        // create new adapter
       // pdetailsAdapter adapter = new pdetailsAdapter(getActivity(), data);
        // set the adapter to list
       // setListAdapter(adapter);  
        tag=1;
	}

	@Override
	public void onFetchFailure(String msg) {
		// TODO Auto-generated method stub
		
		if(dialog != null)  dialog.dismiss();
        // show failure message
	 
	 
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show(); 
        tag=0;
		
	}

	 class getdetailsTask extends AsyncTask<String, Void, String> {
		 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            
	       
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
	                	 
	                	  apps = new ArrayList<pdetails>();
	                	 
	                     // looping through All messages
	                	 for (int i = 0; i < stationss.length(); i++) {
	                         JSONObject c = stationss.getJSONObject(i);

	                     
	                      
	                      pdetails app = new pdetails();
	                      
	                      app.setprogram(c.getString("program"));
	                      app.settime(c.getString("time"));
	                     
	                      apps.add(app);
	                 
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
	            	
	            	  pdetailsAdapter adapter = new pdetailsAdapter(getActivity(), apps);
	                    // set the adapter to list
	                    setListAdapter(adapter);  
	                    
	                  
	            	
	            }
	            else
	            {
	            	
	            	
	            	
	            
	            }
	        }
	 
	    }
	    ///////////////////////////////////////////////
	 
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
	            	
	            	Context context=getActivity();
	                
	        	    SharedPreferences preferences =context.getSharedPreferences("updates",
	        	       context. MODE_PRIVATE);
	        	    
	        	    
	    	    		   String myid = preferences.getString("pid", "");
	    	        String url = "http://192.168.43.5/ialert/getpdetails.php";
	    	       // FetchpdetailsTask task = new FetchpdetailsTask(this);
	    	       // Toast.makeText(getActivity(), myid, Toast.LENGTH_LONG).show(); 
	    	        //task.execute(url,myid);
	    	        
	    	        getdetailsTask task=new getdetailsTask();
	                task.execute(url,myid); 
	            	
	            		
	            	
	            	
	            }
	            else if (mytoken==2)
	            {
	            	
	            	displayconfirm();	
	            }    	
	            
	        }
	 
	    }
		   

	 private void displayconfirm()
		{
			
				//Put up the Yes/No message box
			    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			    	builder
			    	.setMessage("No internet Access please check your network settings")
			    	.setTitle("Internet Error")
			    	.setIcon(R.drawable.alert_dialog_icon)
			    	//.setIcon(android.R.drawable.ic_dialog_alert)
			    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			    	    public void onClick(DialogInterface dialog, int which) {			
			    	    	
			    	    }
			    	})
			    							//Do nothing on no
			    	.show();  
	  

		}
}
