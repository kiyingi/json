package com.news.ialert;
import java.io.ByteArrayOutputStream;
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
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.widget.CursorAdapter;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.ActionBarSherlock.OnCreateOptionsMenuListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.actionbarsherlock.widget.SearchView;




public class newsUpdates extends SherlockListActivity implements FetchDataListener{

	SwipeView mSwipeView;
	
	ActionBarSherlock mSherlock = ActionBarSherlock.wrap(this);
	 
	int[] images;
	 List<Application> myapps;
	 private ProgressDialog dialog;
	
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
		 
	     
	        
	        setContentView(R.layout.update_main);
	        myapps = new ArrayList<Application>();
	        initView(); 
	        ListView lv = getListView();
	      
	        
	        
	        
	        lv.setOnItemClickListener(new OnItemClickListener() {
	 
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
	                // getting values from selected ListItem
	              
	            	//Toast.makeText(MainfetchActivity.this, "its me number"+position, Toast.LENGTH_LONG).show(); 
	            	String title = ((TextView) view.findViewById(R.id.titleTxt)).getText()
	                        .toString();
	            	String details = ((TextView) view.findViewById(R.id.detailstxt)).getText()
	                        .toString();
	            	String location = ((TextView) view.findViewById(R.id.ulocation)).getText()
	                        .toString();
	            	String upid = ((TextView) view.findViewById(R.id.uid)).getText()
	                        .toString();
	            	 String image = ((TextView) view.findViewById(R.id.imagedata)).getText()
	                        .toString();
	            	
	            	 String video = ((TextView) view.findViewById(R.id.video)).getText()
		                        .toString();
		            	
	            	 String audio = ((TextView) view.findViewById(R.id.audio)).getText()
		                        .toString();
		            
	            	
	            	ImageView img=(ImageView)findViewById(R.id.appIcon);
	            	BitmapDrawable drawable1 = (BitmapDrawable) img.getDrawable();
	            	Bitmap bitmap = drawable1.getBitmap();
	            
	            	 Bundle b = new Bundle();
	            	 b.putString("title", title );
	            	 b.putString("video", video );
	            	 b.putString("audio", audio );
	            	 b.putString("details", details );
	            	 b.putString("location", location );
	            	 b.putString("id", upid);
	            
	            	  b.putString("image", image);
	            	 
	            	  Intent i = new Intent(getApplicationContext(),
	            		         updatedetailsActivity.class);
	            	  i.putExtras(b);
	            	  startActivity(i);
	            }
	        });
	       
	    }
	 private void initView() {
	        // show progress dialog
	        dialog = ProgressDialog.show(this, "", "Loading...");
	         
	        String url = "http://192.168.43.5/ialert/getupdates.php";
	        FetchDataTask task = new FetchDataTask(this);
	        task.execute(url);
	    }
	
	@Override
	public void onFetchComplete(List<Application> data) {
		// TODO Auto-generated method stub
		
		 if(dialog != null)  dialog.dismiss();
	        // create new adapter
	        UpdatesAdapter adapter = new UpdatesAdapter(this, data);
	        // set the adapter to list
	        setListAdapter(adapter);  
	      
	}

	@Override
	public void onFetchFailure(String msg) {
		// TODO Auto-generated method stub
		
		 if(dialog != null)  dialog.dismiss();
	        // show failure message
		 
		 
	        Toast.makeText(this, msg, Toast.LENGTH_LONG).show(); 
		}

}



