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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
public class aboutFragment extends SherlockFragment{

	SwipeView mSwipeView;
	 
	int[] images;EditText fname;
	EditText lname;
	EditText email1;
	int on=0;
	EditText tele;
	EditText address;
	EditText country;
	
	EditText companyname;
	EditText companyaddress;
	EditText password1;
	Button register;
	Button done;
	int userid;
	 
	 private ProgressDialog pDialog;

		TextView toregister;
		EditText email;
		EditText password;
		Button login;
		Button tablogin;
		Button cancel;
		FrameLayout flayout;
		int succ=0;
		
       String ffname;
       String llname;
		private ProgressDialog pDialog1;
		Button payment_btn;
		int mytoken=0;
		String useremail;
		String pword;
		String myaccount;
		boolean login_token=false;
		 public static final String TAG_SUCCESS = "success";
		 public static String url_login = "http://192.168.43.5/ialert/login_file.php";
		 
		 JSONParser jsonParser = new JSONParser();
		// private static final String TAG_SUCCESS1 = "success";
			private static final String TAG_PRODUCTS = "station";
			private static final String TAG_id = "id";
			private static final String TAG_accountType = "accountType";
			private static final String TAG_firstName = "firstName";
			private static final String TAG_lastName = "lastName";
			JSONArray stationss = null;
		    JSONObject mymessage;
		    @Override
		    public View onCreateView(LayoutInflater inflater, ViewGroup container,
		            Bundle savedInstanceState) {

		        View rootView = inflater.inflate(R.layout.about_us, container, false);
	
			on=0;
			email=(EditText)rootView.findViewById(R.id.email);
			password=(EditText)rootView.findViewById(R.id.passw);
	
			login=(Button)rootView.findViewById(R.id.btnLogin);
		
			login.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String test="";
					
					useremail=email.getText().toString();
					pword=password.getText().toString();
					
					if(useremail.equalsIgnoreCase(test) || pword.equalsIgnoreCase(test))
					{
						Toast.makeText(getActivity(), "Please enter the missing fields", Toast.LENGTH_LONG).show();
					}
					else
					{
						
						checktask tk=new checktask();
						tk.execute();

					}
					
					
					
				}
			});
			return rootView;
			
		}
		
		
		 class checktask extends AsyncTask<String, Void, String> {
			 
		        /**
		         * Before starting background thread Show Progress Dialog
		         * */
		        @Override
		        protected void onPreExecute() {
		            super.onPreExecute();
		            pDialog1 = new ProgressDialog(getActivity());
		            pDialog1.setMessage("Checking server connectivity");
		            pDialog1.setIndeterminate(false);
		            pDialog1.setCancelable(true);
		            pDialog1.show();
		            
		        }       
		        protected String doInBackground(String... args) {
					
		        	
		        	InetAddress in;
			        in = null;
			        
			        try {
			        	// in = InetAddress.getByName("198.38.88.33");
			            in = InetAddress.getByName("192.168.43.1");
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
			            mytoken=2;
			        	e.printStackTrace();
			        }
		        	
		        	
		        	
		        	return null;
		        	
		            
		        }
		 
		        /**
		         * After completing background task Dismiss the progress dialog
		         * **/
		        protected void onPostExecute(String file_url) {
		            pDialog1.dismiss();
		            if(mytoken==1)
		            {
		            	
		            	
		            	mylogin task=new mylogin();
						task.execute(useremail);
		            	
		            	
		            	
		            }
		            else if (mytoken==2)
		            {
		            	displayerror();
		            }
		            
		        }
		 
		    }
			
		
		
		
		
		
		 private void displayerror()
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
				    	    	   
				    	    	homeFragment frag= new homeFragment();
						        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, frag).commit();
						    
				    	    	
				    	    	
				    	    }
				    	})
				    							//Do nothing on no
				    	.show();
				  
			      	
			}  	
		
		
		
		
		
		
		
		class mylogin extends AsyncTask<String, Void, String> {
			 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(getActivity());
	            pDialog.setMessage("user Loging in ..");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	            
	        }
	 
	        /**
	         * Creating product
	         * */
	        protected String doInBackground(String... args) {
	        	//String login_name=args[0];
	            String login_email=useremail;
	            String password=pword;
	           
	 
	            // Building Parameters
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("login_name", login_email));
	            params.add(new BasicNameValuePair("password", password));
	            
	 
	            // getting JSON Object
	    JSONObject json = jsonParser.makeHttpRequest(url_login, "GET", params);
	            Log.d("Create Response", json.toString());
	 
	            // check for success tag
	            try {
	                int success = json.getInt(TAG_SUCCESS);
	 
	                if (success == 1) {
	                	
	                	succ=1;
	                	
	                	JSONArray  stationss = json.getJSONArray(TAG_PRODUCTS);
	                	 
	                	 
	                	
	                     // looping through All Products
	                   for (int i = 0; i < stationss.length(); i++) {
	                         JSONObject c = stationss.getJSONObject(i);
	  
	                         userid=c.getInt(TAG_id); 
	                        myaccount=c.getString(TAG_accountType); 
	                       ffname=c.getString(TAG_firstName);
	                      llname=c.getString(TAG_lastName);
	                      
	                      
	                    
	                      
	                      
	                      Context context=getActivity();
	                     
	                    	    SharedPreferences preferences =context.getSharedPreferences("login",
	                    	       context. MODE_PRIVATE);
	                    	    SharedPreferences.Editor editor = preferences.edit();

	                    	    editor.putString("fname", ffname);
	                    	    editor.putString("lname", llname);
	                    	    editor.putString("atype", myaccount);
	                    	    editor.putInt("userid", userid);
	                    	    
	                    	    editor.commit();
	                    	  
	                      
	                      
	                      
	                      
	                         
	                   }
	                	
	                } else {
	                	
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
	            pDialog.dismiss();
	            if(succ==1)
	            {
	            	Toast.makeText(getActivity(), "Login succes", Toast.LENGTH_SHORT).show();
	            	 Intent r=new Intent(getActivity(),MainActivity.class);
 			        startActivity(r); 
	            }
	            else
	            {
	            	Toast.makeText(getActivity(), "Login failled please try again", Toast.LENGTH_SHORT).show();
	            	
	            }
	        }
	 
	    }


		
}
