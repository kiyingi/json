package com.news.ialert;

import com.actionbarsherlock.app.SherlockActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class updatedetailsActivity  extends SherlockActivity{
	Button close;
	Button pvideo;
	Button paudio;
	String mylocation;
	String mydetails ;
	String mytitle;
	String myid;
	String myvideo;
	String myaudio;
	;
	String myimage;

	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			
			
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			 setContentView(R.layout.updatesdetail);
			 getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
			 getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Ialert</font>"));
			 Bundle bundle = getIntent().getExtras();
		      mytitle = bundle.getString("title");
		        mydetails = bundle.getString("details");
		       mylocation = bundle.getString("location");
		        myid = bundle.getString("id");
		        myimage = bundle.getString("image");
		        myvideo = bundle.getString("video");
		        myaudio = bundle.getString("audio");
		       
		        
					  TextView titletv=(TextView)findViewById(R.id.title);
				       TextView detailstv=(TextView)findViewById(R.id.details);
				       TextView locationtv=(TextView)findViewById(R.id.location);
				      String no1="no";
				  
				      
				       
				       ImageView imagetv=(ImageView)findViewById(R.id.image);
				  
				       Button ok=(Button)findViewById(R.id.btnok);
				       pvideo=(Button)findViewById(R.id.videos);
				       paudio=(Button)findViewById(R.id.audios);
				       
				       
				       
					      if(myvideo.equalsIgnoreCase(no1))
					      {
					    	  
					    	pvideo.setVisibility(View.INVISIBLE);  
					    	  
					      }
					      if(myaudio.equalsIgnoreCase(no1))
					      {
					    	  
					    	paudio.setVisibility(View.INVISIBLE);  
					    	  
					      }
				       titletv.setText(mytitle);
				       detailstv.setText(mydetails);
				       locationtv.setText(mylocation);
				     
				      
				       byte[] mess;
				        
				       if(myimage!=null)
				       {
				        mess=Base64.decode(myimage, Base64.DEFAULT);
				   	
				   	Bitmap image2 = BitmapFactory.decodeByteArray(mess, 0, mess.length);
				   	
				         
				        imagetv.setImageBitmap(image2);
				       }
				      
					 ok.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
						finish();
							
							
						}
					});
					
					 pvideo.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								 Bundle b = new Bundle();
								 String  videourl="http://192.168.43.5/ialert/video_uploads/"+myvideo;
				            	 b.putString("video", videourl );
				            	String no="no";
				            	 if(myvideo.equalsIgnoreCase(no))
				            	 {
				            		Toast.makeText(getApplicationContext(), "no video attached", Toast.LENGTH_LONG).show() ;
				            	 }
				            	 else
				            	 {
				            	 
				            	  Intent i = new Intent(getApplicationContext(),
				            		         playActivity.class);
				            	  i.putExtras(b);
				            	  startActivity(i);
						
								
				            	 }
							}
						});
					 
					 paudio.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								 Bundle b = new Bundle();
								 
								 
								 String  videourl="http://192.168.43.5/ialert/audio_uploads/"+myaudio;
				            	 b.putString("video", videourl );
				            	 
				            	String no="no";
				            	 if(myaudio.equalsIgnoreCase(no))
				            	 {
				            		 Toast.makeText(getApplicationContext(), "no Audio attached", Toast.LENGTH_LONG).show() ;	 
				            	 }
				            	 else
				            	 {
				            	  Intent i = new Intent(getApplicationContext(),
				            		         playActivity.class);
				            	  i.putExtras(b);
				            	  startActivity(i);
						
				            	 }
								
							}
						});
						
						
		}

		@Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			
		
			super.onBackPressed();
		}
		
		/*
		private void callsupplier() {
		    try {
		        Intent callIntent = new Intent(Intent.ACTION_CALL);
		        callIntent.setData(Uri.parse("tel:"+myphonenumber));
		        startActivity(callIntent);
		    } catch (ActivityNotFoundException activityException) {
		         Log.e("Contacting Supplier", "Call failed");
		    }
		}
		*/
}

