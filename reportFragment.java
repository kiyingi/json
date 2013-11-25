package com.news.ialert;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.jasonfry.android.tools.ui.SwipeView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;


public class reportFragment extends SherlockFragment implements LocationListener,OnItemSelectedListener{
	public static String cat_url = "http://192.168.43.5/ialert/initial_data.php";
	public static String url_send = "http://192.168.43.5/ialert/save_news.php";
	
	
	
	//public static String cat_url = "http://209.217.228.75/~noisyg/ialert/initial_data.php";
	//public static String url_send = "http://209.217.228.75/~noisyg/ialert/save_news.php";
	
	
	 private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
	    public static final int MEDIA_TYPE_IMAGE = 1;
	    public static final int MEDIA_TYPE_VIDEO = 2;
	private ProgressDialog pDialog;
	SwipeView mSwipeView;
	EditText title;
	EditText descrip;
	Spinner category;
	String mycategory;
	ProgressDialog progressDialog;
  
    HttpEntity resEntity;
	int mytoken;
	private static final int SELECT_PICTURE = 1; 
	private static final int SELECT_MORE_PICTURE = 200; 
	 private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
	 private static final int SELECT_AUDIO = 2; 
	 String selectedPath = ""; 
	 private Uri fileUri;
	String filename;
	SimpleDateFormat sdf; 
	String currentDateandTime;
	String[] categoryids;
	String catid;
	String[] categories;
	String mydate;
	int sent=0;
	int len;
	String facedata;
	int succ=0;
	int succ2=0;
	int test=0;
	String desc;
	JSONParser jParser = new JSONParser();
	int ok=1;
	public static final String TAG_SUCCESS = "success";
	public static final String TAG_SUCCESS2 = "success";
	JSONParser jsonParser = new JSONParser();
	JSONParser jsonParser2 = new JSONParser();
	private static final String TAG_PRODUCTS = "stations";
	JSONArray stations = null;
	List<String> catArray =  new ArrayList<String>();
	private String selectedImagePath;
	private String moreImagePath;
	Button face;
	Button take;
	Button video;
	Button audio;
	ImageView faceimage;
	 private LocationManager locationManager;
	  private String provider;
	Button more;
	Button send;
	int[] images;
	String mytitle;
	public static double latitude;  
    public static double longitude;
    String selectedVideo;
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 
	
		
	    	
	        View rootView = inflater.inflate(R.layout.report_news, container, false);
	        
	      
	        faceimage=(ImageView)rootView.findViewById(R.id.lslogo);
	        title=(EditText)rootView.findViewById(R.id.lsname);
			descrip=(EditText)rootView.findViewById(R.id.lsdes);
			face=(Button)rootView.findViewById(R.id.btnbrowse);
			more=(Button)rootView.findViewById(R.id.btnmore);
			category = (Spinner)rootView. findViewById(R.id.lscategory);
			send=(Button)rootView.findViewById(R.id.btnsub);
			take=(Button)rootView.findViewById(R.id.btntake);
			video=(Button)rootView.findViewById(R.id.btnvideo);
			audio=(Button)rootView.findViewById(R.id.btnaudio);
			 
			
			checktask getc=new checktask();
			getc.execute();
			
				
				//getcategories task=new getcategories();
				//task.execute();
			
                Context context=getActivity();
             
     	    SharedPreferences preferences =context.getSharedPreferences("data",
     	       context. MODE_PRIVATE);
     	    
			    		   String date_time = preferences.getString("date_time", "");
			String no="";
			if(date_time.equalsIgnoreCase(no))
			{
				
				 SimpleDateFormat sdf1; 
	            	String newDateandTime;
	            	
	            	String date_;
	                sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					 newDateandTime = sdf1.format(new Date());
					date_=newDateandTime;
	                
	            
              
      	    SharedPreferences preferences2 =context.getSharedPreferences("data",
      	       context. MODE_PRIVATE);
      	    SharedPreferences.Editor editor = preferences2.edit();

      	    editor.putString("date_time", date_);
      	    
      	    editor.commit();
			}
			    		   
			    		   
			    		   
			    		   
			    		   
			    		   
			category.setOnItemSelectedListener(this);
			
			video.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent pickMedia = new Intent(Intent.ACTION_GET_CONTENT);
					pickMedia.setType("video/*");
					startActivityForResult(pickMedia,12345);
				}
			});
			
			if (!isDeviceSupportCamera()) {
	            Toast.makeText(getActivity().getApplicationContext(),
	                    "Sorry! Your device doesn't support camera",
	                    Toast.LENGTH_LONG).show();
	            // will close the app if the device does't have camera
	           
	        }
			audio.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					/*
					Intent recordIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
					startActivityForResult(recordIntent, 1111);
				///////////////
					Intent recordIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
					startActivityForResult(recordIntent, 1111);
						*/
					
					openGalleryAudio(); 
					
				
					
				}
					
				
			});

			take.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					  captureImage();
				}
			});
	        face.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
					selectedImagePath=null;
				    Intent intent = new Intent(); 
				    intent.setType("image/*"); 
				    intent.setAction(Intent.ACTION_GET_CONTENT); 
				    startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE); 
					
					
					
				}
			});
	        
	        
	        more.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					moreImagePath=null;
				    Intent intent = new Intent(); 
				    intent.setType("image/*"); 
				    intent.setAction(Intent.ACTION_GET_CONTENT); 
				    startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_MORE_PICTURE); 
					
				   
				    
		            
				}
	
			});
	        
	        
	        send.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				
					
					 locationManager = (LocationManager) getActivity().getSystemService(getActivity().getApplicationContext().LOCATION_SERVICE); 
					    
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
					mytitle=title.getText().toString();
					desc=descrip.getText().toString();
					mycategory=String.valueOf(category.getSelectedItem());
					
					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					 currentDateandTime = sdf.format(new Date());
					mydate=currentDateandTime;
                     String no="";
                    if(mytitle.equalsIgnoreCase(no) || desc.equalsIgnoreCase(no))
                    {
                    	Toast.makeText(getActivity(), "Please Enter news title and description and try again", Toast.LENGTH_LONG).show();
							
                    }
                    else
                    {

					if(selectedImagePath==null)
					{
						Toast.makeText(getActivity(), "Please Browse Photo for the News", Toast.LENGTH_LONG).show();
						//Toast.makeText(registerlistingsActivity.this, "is"+catArray.size(), Toast.LENGTH_LONG).show();
						
						
					}
					
					else
					{
						InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(getActivity().getApplicationContext().INPUT_METHOD_SERVICE);
						mgr.hideSoftInputFromWindow(title.getWindowToken(), 0);
						
						
						
						
						
						//checktask check=new checktask();
					//check.execute();

					
					//sendnews task=new sendnews();
					//task.execute();
						
						
						
						
						  if(!(selectedImagePath.trim().equalsIgnoreCase("NONE")) )
							 {
				                    progressDialog = ProgressDialog.show(getActivity(), "", "sending news details.....", false);
				                     Thread thread=new Thread(new Runnable(){
				                            public void run() {
				                            	
				                            	
				                                doFileUpload4();
				                                
				                                 getActivity().runOnUiThread(new Runnable(){
				                                    public void run() {
				                                        if(progressDialog.isShowing())
				                                        {
				                                            progressDialog.dismiss();
				                                        }
				                                    }
				                                 });
				                            
				                                }
				                            });
				                    
							 
							 
				                    thread.start();
				                }
						
						else{
				                            Toast.makeText(getActivity().getApplicationContext(),"Please select two files to upload.", Toast.LENGTH_SHORT).show();
				                }
			                
			                
			                
						
						
					}
					
					
                    }
					
					/*
					for(int i=0;i<=catArray.size();i++)
					{
						if(category.equalsIgnoreCase(categories[i]))
						{
							catid=categoryids[i];
							
							
						}
					}  
					
					
					*/
					
					
				}
			});
	        
	        
	        
	    	return rootView;
	    }
	    

		public void onActivityResult(int requestCode, int resultCode, Intent data) { 
			if (requestCode == 12345) {
	            if (resultCode == Activity.RESULT_OK) {
	                Uri selectedVideoLocation = data.getData();
	                selectedVideo=getPath(selectedVideoLocation);
	                //Toast.makeText(getActivity().getApplicationContext(),selectedVideo , Toast.LENGTH_LONG).show();
		            
	                ////////upload/////////////////////
	                
	                 if(!(selectedVideo.trim().equalsIgnoreCase("NONE")) )
					 {
		                    progressDialog = ProgressDialog.show(getActivity(), "", "Attaching Video.....", false);
		                     Thread thread=new Thread(new Runnable(){
		                            public void run() {
		                            	
		                            	
		                                doFileUpload2();
		                                
		                                 getActivity().runOnUiThread(new Runnable(){
		                                    public void run() {
		                                        if(progressDialog.isShowing())
		                                        {
		                                            progressDialog.dismiss();
		                                        }
		                                    }
		                                 });
		                            
		                                }
		                            });
		                    
					 
					 
		                    thread.start();
		                }
				
				else{
		                            Toast.makeText(getActivity().getApplicationContext(),"Please select two files to upload.", Toast.LENGTH_SHORT).show();
		                }
	                
	                
	                
	                
	                
	                //////////end upload///////
	            } 
	            else
	            {
	            	
	            	Toast.makeText(getActivity().getApplicationContext(),"Selection cancelled by user" , Toast.LENGTH_LONG).show();
		 		     	
	            	
	            }

	        }
			else if (requestCode == SELECT_PICTURE) { 
	        if (resultCode == getActivity().RESULT_OK) { 
	            if (requestCode == SELECT_PICTURE) { 
	                Uri selectedImageUri = data.getData(); 
	                selectedImagePath = getPath(selectedImageUri); 
	                
	             
	             filename=    new File(selectedImagePath).getName();


	                //String fileName= FilenameUtils.getName(stringNameWithPath);
	                Toast.makeText(getActivity().getApplicationContext(),selectedImageUri.getPath() , Toast.LENGTH_LONG).show();
	                System.out.println("Image Path : " + selectedImagePath); 
	           //faceimage.setImageURI(selectedImageUri);
	                
	              
	            
	            }
	        }
	        
	        
	        }
			
			/////////////////
			else if (requestCode == SELECT_MORE_PICTURE) { 
		        if (resultCode == getActivity().RESULT_OK) { 
		            if (requestCode == SELECT_MORE_PICTURE) { 
		                Uri moreImageUri = data.getData(); 
		                moreImagePath = getPath(moreImageUri); 
		                
		             
		            


		                //String fileName= FilenameUtils.getName(stringNameWithPath);
		                //Toast.makeText(getActivity().getApplicationContext(),moreImageUri.getPath() , Toast.LENGTH_LONG).show();
		                System.out.println("Image Path : " + moreImagePath); 
		                
		                if(!(moreImagePath.trim().equalsIgnoreCase("NONE")) )
						 {
			                    progressDialog = ProgressDialog.show(getActivity(), "", "Attaching more photos.....", false);
			                     Thread thread=new Thread(new Runnable(){
			                            public void run() {
			                            	
			                            	
			                                doFileUpload3();
			                                
			                                 getActivity().runOnUiThread(new Runnable(){
			                                    public void run() {
			                                        if(progressDialog.isShowing())
			                                        {
			                                            progressDialog.dismiss();
			                                        }
			                                    }
			                                 });
			                            
			                                }
			                            });
			                    
						 
						 
			                    thread.start();
			                }
					
					else{
			                            Toast.makeText(getActivity().getApplicationContext(),"Please select two files to upload.", Toast.LENGTH_SHORT).show();
			                }
		                
		                
		            
		            }
		        }
		        
		        
		        }
			
			else if (requestCode == SELECT_AUDIO) 
	            { 
				 if (resultCode == getActivity().RESULT_OK) { 
	                System.out.println("SELECT_AUDIO"); 
	                Uri selectedImageUri = data.getData(); 
	                selectedPath = getPath(selectedImageUri); 
	                System.out.println("SELECT_AUDIO Path : " + selectedPath); 
	               // Toast.makeText(getActivity().getApplicationContext(),selectedPath , Toast.LENGTH_LONG).show();
	 		       /////////////////////////////////////
	                
	                
	                
	                
	                
	                
	                
	                
	                
	                ////////upload/////////////////////
	                
	                 if(!(selectedPath.trim().equalsIgnoreCase("NONE")) )
					 {
		                    progressDialog = ProgressDialog.show(getActivity(), "", "Uploading files to server.....", false);
		                     Thread thread=new Thread(new Runnable(){
		                            public void run() {
		                            	
		                            	
		                                doFileUpload();
		                                
		                                 getActivity().runOnUiThread(new Runnable(){
		                                    public void run() {
		                                        if(progressDialog.isShowing())
		                                        {
		                                            progressDialog.dismiss();
		                                        }
		                                    }
		                                 });
		                            
		                                }
		                            });
		                    
					 
					 
		                    thread.start();
		                }
				
				else{
		                            Toast.makeText(getActivity().getApplicationContext(),"Please select two files to upload.", Toast.LENGTH_SHORT).show();
		                }
	                
	                
	                
	                
	                
	                //////////end upload///////
				 }
				 else
				 {
					 Toast.makeText(getActivity().getApplicationContext(),"Selection cancelled by user" , Toast.LENGTH_LONG).show();
		 		       	 
				 }
	            } 
			else if(requestCode==CAMERA_CAPTURE_VIDEO_REQUEST_CODE)
			{
				if (resultCode == getActivity().RESULT_OK) { 
					
					
					
				}
				else if (resultCode == getActivity().RESULT_CANCELED) {
	                // user cancelled recording
	                Toast.makeText(getActivity().getApplicationContext(),
	                        "User cancelled video recording", Toast.LENGTH_SHORT)
	                        .show();
	            }
			 else {
                // failed to record video
                Toast.makeText(getActivity().getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
				
				
			}

			/////////////////
			else if(requestCode == 1111)
		    {
				
		         File folder = new File(Environment.getExternalStorageDirectory(), "/Sounds");
		         long folderModi = folder.lastModified();

		    FilenameFilter filter = new FilenameFilter() 
		    {
		        public boolean accept(File dir, String name) 
		        {
		            return (name.endsWith("3ga"));
		        }
		    };

		    File[] folderList = folder.listFiles(filter);

		    String recentName = "";

		    for(int i=0; i<folderList.length;i++)
		    {
		        long fileModi = folderList[i].lastModified();

		        if(folderModi == fileModi)
		        {
		            recentName = folderList[i].getName();
		            String fullname="/Sounds/"+recentName;
		            Toast.makeText(getActivity().getApplicationContext(),fullname , Toast.LENGTH_LONG).show();
		        }
		    }
		   
		}
			
			
			
		}
		
		
		//////////////////////////////////////////////////////////////////
		
		/*
		  public void onActivityResult(int requestCode, int resultCode, Intent data) {
		        // if the result is capturing Image
		        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
		            // code to check capture image response
		        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
		            if (resultCode == getActivity().RESULT_OK) {
		                // video successfully recorded
		                // preview the recorded video
		                //previewVideo();
		            } else if (resultCode == getActivity().RESULT_CANCELED) {
		                // user cancelled recording
		                Toast.makeText(getActivity().getApplicationContext(),
		                        "User cancelled video recording", Toast.LENGTH_SHORT)
		                        .show();
		            } else {
		                // failed to record video
		                Toast.makeText(getActivity().getApplicationContext(),
		                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
		                        .show();
		            }
		        }
		    }
*/
		//////////////////////////////////////////////////////////////////
	 public String getPath(Uri uri) { 
	        String[] projection = { MediaStore.Images.Media.DATA }; 
	        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null); 
	        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); 
	        cursor.moveToFirst(); 
	        return cursor.getString(column_index); 
	         
	         }
	  

	@Override
	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		 loc.getLatitude();  
	        loc.getLongitude();  
	        latitude=loc.getLatitude();  
	        longitude=loc.getLongitude(); 
	        
	        Toast.makeText(getActivity(), "lat="+latitude+"  longitude="+longitude, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onProviderDisabled(String loc) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), "Please enable your Gps to report News", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	} 
	
	
	
	class getcategories extends AsyncTask<String, String, String> {
		 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           
        }
 
        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(cat_url, "GET", params);
            
         
            	
          
 
            // Check your log cat for JSON reponse
            Log.d("database initial data: ", json.toString());
 
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // products found
                    // Getting Array of Products
                	ok=1;
                     stations = json.getJSONArray(TAG_PRODUCTS);
                     categoryids=new String[500];
                     categories=new String[500];
                      len=stations.length();
                    
                       String no="";
                    for (int i = 0; i < stations.length(); i++) {
                        JSONObject c = stations.getJSONObject(i);
 
                        // Storing each json item in variable
                        
                        	 categoryids[i] = c.getString("categoryId");
	                         categories[i] = c.getString("subcategory");	
	                         catArray.add(categories[i]) ; 
	                         
                        
                      
                        
                        
                        
                        
                    }
                    
                    
                }
                
                
                else {
                    // no products found
                    
                }
            }
            
            
            catch (JSONException e) {
            	
            	
                e.printStackTrace();
            }
            
            
            return null;
            
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
        	
          // pDialog3.dismiss();
            
            
            if(ok==1)
            {
            	//Toast.makeText(registerlistingsActivity.this, cagetid.toString(), Toast.LENGTH_SHORT).show();
            	
            	
                
              	  
                
                
                 ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                         android.R.layout.simple_spinner_item, catArray);
           
                
                  dataAdapter
                         .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           
                 
                 category.setAdapter(dataAdapter);
                 
                
            	
            }
            
           
        }
 
    }

	private Bitmap decodeToLowResImage(byte [] b, int width, int height) {
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new ByteArrayInputStream(b), null, o);

            //The new size we want to scale to
            final int REQUIRED_SIZE_WIDTH=(int)(width*0.7);
            final int REQUIRED_SIZE_HEIGHT=(int)(height*0.7);

            //Find the correct scale value. It should be the power of 2.
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE_WIDTH || height_tmp/2<REQUIRED_SIZE_HEIGHT)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new ByteArrayInputStream(b), null, o2);
        } catch (OutOfMemoryError e) {
        }
        return null;
    }
 
    //////********************************************//////////////////////
	
	
	
	
	
	class sendnews extends AsyncTask<String, Void, String> {
		 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Sending Your Updates please wait ..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

            
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
        	//String login_name=args[0];
        	
        	
        	
        	FileInputStream in;
            BufferedInputStream buf;
            try {
           	    in = new FileInputStream(selectedImagePath);
                buf = new BufferedInputStream(in);
                Bitmap bMap = BitmapFactory.decodeStream(buf);
               //image.setImageBitmap(bMap);
                
                
                ByteArrayOutputStream baos = new ByteArrayOutputStream();  
                bMap.compress(Bitmap.CompressFormat.JPEG, 80, baos); // bm is the bitmap object   
                byte[] b = baos.toByteArray();  

               
                
             // mystring = Base64.encodeToString(b,Base64.DEFAULT);  
              
              
              Bitmap bMap2= decodeToLowResImage(b,  50, 50);
              ByteArrayOutputStream baos2 = new ByteArrayOutputStream();  
              bMap2.compress(Bitmap.CompressFormat.JPEG, 80, baos2); // bm is the bitmap object   
              byte[] b2 = baos2.toByteArray(); 
              facedata = Base64.encodeToString(b2,Base64.DEFAULT); 
              
              //Toast.makeText(getApplicationContext(), mystring, Toast.LENGTH_LONG).show();

            //  byte[] decryptedData = decrypt(key,encryptedData);
                
                //Toast.makeText(getApplicationContext(), encryptedData.length, Toast.LENGTH_LONG).show();
               // byte[] bMapArray= new byte[buf.available()];
              // Bitmap image2 = BitmapFactory.decodeByteArray(decryptedData, 0, decryptedData.length);
               
                //img.setImageBitmap(image2);
              
              String not="";
              if(facedata.equalsIgnoreCase(not))
              {
            	test=0;  
              }
              else
              {
            	  test=1;
              }
                
         
                }
             catch (Exception e) {
                Log.e("Error reading file", e.toString());
                test=2;
            }
            
	
        	
   
    
    String catid = null;
	     
	  for(int i=0;i<=catArray.size();i++)
		{
			if(mycategory.equalsIgnoreCase(categories[i]))
			{
				catid=categoryids[i];
				
				
			}
		}  
    
	  
	 
    
    
        
	  
	  
	  
	  
	  
       
        String myimage=facedata;
        String mydate1=mydate; 
        String title=mytitle;
       
        String decrip1=desc;
        
        String filename1=filename;
         String lat=Double.toString(latitude);
         String longt=Double.toString(longitude);
         String sender="dakam";
         String contact="0798565656";
      
 
       
		
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("title", title));            
            params.add(new BasicNameValuePair("description",decrip1 ));            
            params.add(new BasicNameValuePair("date1", mydate1));         
            params.add(new BasicNameValuePair("imagename", filename1));
            params.add(new BasicNameValuePair("lat", lat));
            params.add(new BasicNameValuePair("longt", longt));
            params.add(new BasicNameValuePair("sender", sender));
            params.add(new BasicNameValuePair("categoryid", catid));
            params.add(new BasicNameValuePair("contact", contact));
            params.add(new BasicNameValuePair("image", myimage));
           
           
           // uploadFile(selectedImagePath);
            
            
 
            // getting JSON Object
            
            
            JSONObject json2 = jsonParser2.makeHttpRequest(url_send,
                    "POST", params);
            Log.d("Create Response", json2.toString());
 
            // check for success tag
            try {
                int success2 = json2.getInt(TAG_SUCCESS2);
 
                if (success2 == 1) {
                	
                	succ2=1;
                	
                	
                } else if(success2==2){
                	
                	succ2=2;
                }
                else
                {
                	succ2=0;
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
        	 //setSupportProgressBarIndeterminateVisibility(false);
        	  pDialog.dismiss();
            if(succ2==1)
            {
            	Toast.makeText(getActivity(), "Your News Updates added successfully", Toast.LENGTH_LONG).show();
            	
            	Fragment home= new homeFragment();
		        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, home).commit();
		    
          
            	     
            	/*
            	Intent i=new Intent(Main.this,firstActivity.class);
            	startActivity(i);
            	finish();
            	*/
            }
            else if(succ2==2)
            {
            	Toast.makeText(getActivity(), "Please provide more images for the listing", Toast.LENGTH_SHORT).show();
            	sent=1;
            }
            else 
            {
            	Toast.makeText(getActivity(), "An error occured please try again", Toast.LENGTH_SHORT).show();
            	sent=1;
            }
        }
       
        
       
    }
	
	

	
	
	/////////////////////////******************************//////////
	
	 class checktask extends AsyncTask<String, Void, String> {
		 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	           
	            
	        }       
	        protected String doInBackground(String... args) {
				
	        	
	        	InetAddress in;
		        in = null;
		        
		        try {
		        	// in = InetAddress.getByName("198.38.88.33");
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
		            mytoken=2;
		        	e.printStackTrace();
		        }
	        	
	        	
	        	
	        	return null;
	        	
	            
	        }
	 
	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	           
	            if(mytoken==1)
	            {
	            	getcategories task=new getcategories();
					task.execute();
	            	
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
			    	    	   
			    	    	
			    	    	
			    	    }
			    	})
			    							//Do nothing on no
			    	.show();
			  
		      	
		}  	
	 private void captureImage() {
	        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	 
	        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
	 
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	 
	        // start the image capture Intent
	        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	    }
	 
	 
	 
	 @Override
	public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	 
	        // save file url in bundle as it will be null on scren orientation
	        // changes
	        outState.putParcelable("file_uri", fileUri);
	    }
	 
	
	 
	 
	 
	 public Uri getOutputMediaFileUri(int type) {
	        return Uri.fromFile(getOutputMediaFile(type));
	    }
	 private static File getOutputMediaFile(int type) {
		 
	        // External sdcard location
	        File mediaStorageDir = new File(
	                Environment
	                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
	                IMAGE_DIRECTORY_NAME);
	 
	        // Create the storage directory if it does not exist
	        if (!mediaStorageDir.exists()) {
	            if (!mediaStorageDir.mkdirs()) {
	                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
	                        + IMAGE_DIRECTORY_NAME + " directory");
	                return null;
	            }
	        }
	        
	        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
	                Locale.getDefault()).format(new Date());
	        File mediaFile;
	        if (type == MEDIA_TYPE_IMAGE) {
	            mediaFile = new File(mediaStorageDir.getPath() + File.separator
	                    + "IMG_" + timeStamp + ".jpg");
	        } else if (type == MEDIA_TYPE_VIDEO) {
	            mediaFile = new File(mediaStorageDir.getPath() + File.separator
	                    + "VID_" + timeStamp + ".mp3");
	        } else {
	            return null;
	        }
	 
	        return mediaFile;
	 }
	 private void recordVideo() {
	        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
	 
	        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
	 
	        // set video quality
	        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
	 
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
	                                                            // name
	 
	        // start the video capture Intent
	        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
	    }
	 
	 ////////////////////
	 private boolean isDeviceSupportCamera() {
	        if (getActivity().getApplicationContext().getPackageManager().hasSystemFeature(
	                PackageManager.FEATURE_CAMERA)) {
	            // this device has a camera
	            return true;
	        } else {
	            // no camera on this device
	            return false;
	        }
	    }
	 
	 //////////////
	 
	 
	 
	 public void openGalleryAudio(){ 
		  
		    Intent intent = new Intent(); 
		        intent.setType("audio/*"); 
		        intent.setAction(Intent.ACTION_GET_CONTENT); 
		        startActivityForResult(Intent.createChooser(intent,"Select Audio "), SELECT_AUDIO); 
		   } 
	 
	 
	 //////file upload method///////
	 
	 
	 private void doFileUpload(){
		 
	        File file1 = new File(selectedPath);
	       
	        String urlString = "http://192.168.43.5/ialert/upload_audio.php";
	        try
	        {
	             HttpClient client = new DefaultHttpClient();
	             HttpPost post = new HttpPost(urlString);
	             FileBody bin1 = new FileBody(file1);
	           
	             MultipartEntity reqEntity = new MultipartEntity();
	             reqEntity.addPart("uploadedfile1", bin1);
	             //////
	         	
	            
	       	  
	            
	             //////
	             Context context=getActivity();
	             SharedPreferences preferences =context.getSharedPreferences("data",
	           	       context. MODE_PRIVATE);
	           	    
	      			    		   String date_time = preferences.getString("date_time", "");   
	            
	      			    		 SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
	      			           
	      			           String sender=sharedPrefs.getString("names", "not defined"); 
	           
	             
	             
	             
	            reqEntity.addPart("sender", new StringBody(sender));
	            reqEntity.addPart("date_time", new StringBody(date_time));
	             post.setEntity(reqEntity);
	             HttpResponse response = client.execute(post);
	             resEntity = response.getEntity();
	             final String response_str = EntityUtils.toString(resEntity);
	             if (resEntity != null) {
	                 Log.i("RESPONSE",response_str);
	                 getActivity().runOnUiThread(new Runnable(){
	                        public void run() {
	                             try {
	                               
	                                Toast.makeText(getActivity().getApplicationContext(),"Upload Complete. Check the server uploads directory.", Toast.LENGTH_LONG).show();
	                            } catch (Exception e) {
	                                e.printStackTrace();
	                            }
	                           }
	                    });
	             }
	        }
	        catch (Exception ex){
	             Log.e("Debug", "error: " + ex.getMessage(), ex);
	        }
	      }
	 
	 
	 //////end of file upload///////
	 
	 private void doFileUpload2(){
		 
	        File file1 = new File(selectedVideo);
	       
	        String urlString = "http://192.168.43.5/ialert/upload_video.php";
	        try
	        {
	             HttpClient client = new DefaultHttpClient();
	             HttpPost post = new HttpPost(urlString);
	             FileBody bin1 = new FileBody(file1);
	           
	             MultipartEntity reqEntity = new MultipartEntity();
	             reqEntity.addPart("uploadedfile1", bin1);
	             //////
	         	
	            
	       	  
	            
	             //////
	             Context context=getActivity();
	             SharedPreferences preferences =context.getSharedPreferences("data",
	           	       context. MODE_PRIVATE);
	           	    
	      			    		   String date_time = preferences.getString("date_time", "");   
	            
	      			    		 SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
	      			           
	      			           String sender=sharedPrefs.getString("names", "not defined"); 
	           
	             
	             
	             
	            reqEntity.addPart("sender", new StringBody(sender));
	            reqEntity.addPart("date_time", new StringBody(date_time));
	             post.setEntity(reqEntity);
	             HttpResponse response = client.execute(post);
	             resEntity = response.getEntity();
	             final String response_str = EntityUtils.toString(resEntity);
	             if (resEntity != null) {
	                 Log.i("RESPONSE",response_str);
	                 getActivity().runOnUiThread(new Runnable(){
	                        public void run() {
	                             try {
	                               
	                                Toast.makeText(getActivity().getApplicationContext(),"Upload Complete. Check the server uploads directory.", Toast.LENGTH_LONG).show();
	                            } catch (Exception e) {
	                                e.printStackTrace();
	                            }
	                           }
	                    });
	             }
	        }
	        catch (Exception ex){
	             Log.e("Debug", "error: " + ex.getMessage(), ex);
	        }
	      }
	 
	 
	 
	 /////
	 private void doFileUpload4(){
		 
	        File file1 = new File(selectedImagePath);
	       
	        String urlString = "http://192.168.43.5/ialert/news_save.php";
	        try
	        {
	             HttpClient client = new DefaultHttpClient();
	             HttpPost post = new HttpPost(urlString);
	             FileBody bin1 = new FileBody(file1);
	           
	             MultipartEntity reqEntity = new MultipartEntity();
	             reqEntity.addPart("uploadedfile1", bin1);
	             //////
	             
	             
	             String mydate1=mydate; 
	             String title=mytitle;
	            
	             String decrip1=desc;
	             
	             String filename1=filename;
	              String lat=Double.toString(latitude);
	              String longt=Double.toString(longitude);
	             
	          
	           
	            
	             
	            
	             //////
	             Context context=getActivity();
	             SharedPreferences preferences =context.getSharedPreferences("data",
	           	       context. MODE_PRIVATE);
	           	    
	      			    		   String date_time = preferences.getString("date_time", "");   
	            
	      			    		 SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
	      			           
	      			           String sender=sharedPrefs.getString("names", "not defined"); 
	      			           
	      			         String contact=sharedPrefs.getString("number", "not defined"); 
	           
	      			         String catid = null;
	      				     
		      			 	  for(int i=0;i<=catArray.size();i++)
		      			 		{
		      			 			if(mycategory.equalsIgnoreCase(categories[i]))
		      			 			{
		      			 				catid=categoryids[i];
		      			 				
		      			 			}
		      			 		}  
		    
		       reqEntity.addPart("categoryid", new StringBody(catid)); 
		       reqEntity.addPart("title", new StringBody(title)); 
		       reqEntity.addPart("description", new StringBody(decrip1));
		       reqEntity.addPart("lat", new StringBody(lat));
		       reqEntity.addPart("longt", new StringBody(longt)); 
		       reqEntity.addPart("imagename", new StringBody(filename1)); 
		       reqEntity.addPart("description", new StringBody(decrip1)); 
		       reqEntity.addPart("contact", new StringBody(contact));
		       reqEntity.addPart("date1", new StringBody(mydate1)); 
	             
	            reqEntity.addPart("sender", new StringBody(sender));
	            reqEntity.addPart("date_time", new StringBody(date_time));
	             post.setEntity(reqEntity);
	             HttpResponse response = client.execute(post);
	             resEntity = response.getEntity();
	             final String response_str = EntityUtils.toString(resEntity);
	             if (resEntity != null) {
	                 Log.i("RESPONSE",response_str);
	                 getActivity().runOnUiThread(new Runnable(){
	                        public void run() {
	                             try {
	                               
	                                Toast.makeText(getActivity().getApplicationContext(),"Your Submission has sucessfully sent.", Toast.LENGTH_LONG).show();
	                                Context context=getActivity();
	                                SharedPreferences preferences2 =context.getSharedPreferences("data",
	                               	       context. MODE_PRIVATE);
	                               	    SharedPreferences.Editor editor = preferences2.edit();
                                        String no="";
	                               	    editor.putString("date_time", no);
	                               	    
	                               	    editor.commit();
	                               	 Fragment home= new homeFragment();
	                 		        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, home).commit();
	                 		      
	                                
	                             } catch (Exception e) {
	                                e.printStackTrace();
	                            }
	                           }
	                    });
	             }
	        }
	        catch (Exception ex){
	             Log.e("Debug", "error: " + ex.getMessage(), ex);
	        }
	      }
	 
	 
	 
	 private void doFileUpload3(){
		 
	        File file1 = new File(moreImagePath);
	       
	        String urlString = "http://192.168.43.5/ialert/upload_image.php";
	        try
	        {
	             HttpClient client = new DefaultHttpClient();
	             HttpPost post = new HttpPost(urlString);
	             FileBody bin1 = new FileBody(file1);
	           
	             MultipartEntity reqEntity = new MultipartEntity();
	             reqEntity.addPart("uploadedfile1", bin1);
	             //////
	         	
	            
	       	  
	            
	             //////
	             Context context=getActivity();
	             SharedPreferences preferences =context.getSharedPreferences("data",
	           	       context. MODE_PRIVATE);
	           	    
	      			    		   String date_time = preferences.getString("date_time", "");   
	            
	      			    		 SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
	      			           
	      			           String sender=sharedPrefs.getString("names", "not defined"); 
	           
	             
	      			           
	      			        
	            reqEntity.addPart("sender", new StringBody(sender));
	            reqEntity.addPart("date_time", new StringBody(date_time));
	             post.setEntity(reqEntity);
	             HttpResponse response = client.execute(post);
	             resEntity = response.getEntity();
	             final String response_str = EntityUtils.toString(resEntity);
	             if (resEntity != null) {
	                 Log.i("RESPONSE",response_str);
	                 getActivity().runOnUiThread(new Runnable(){
	                        public void run() {
	                             try {
	                               
	                                Toast.makeText(getActivity().getApplicationContext(),"Upload Complete. Check the server uploads directory.", Toast.LENGTH_LONG).show();
	                            } catch (Exception e) {
	                                e.printStackTrace();
	                            }
	                           }
	                    });
	             }
	        }
	        catch (Exception ex){
	             Log.e("Debug", "error: " + ex.getMessage(), ex);
	        }
	      }
	 
	 

}
