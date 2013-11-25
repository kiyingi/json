package com.news.ialert;

import java.util.ArrayList;
import java.util.List;

import uk.co.jasonfry.android.tools.ui.SwipeView;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;

public class pupdatesFragment extends SherlockListFragment implements FetchProgramListener{

	SwipeView mSwipeView;
	 
	int[] images;
	int tag=0;
	
	List<programs> myapps;
	 private ProgressDialog dialog;
	
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 
	        View rootView = inflater.inflate(R.layout.program_main, container, false);
	        
	        myapps = new ArrayList<programs>();
	        initView(); 
	        
	        if(tag==1)
	        {
	     
	        }
	      
	        
	    	return rootView;
	    }
	 
	 private void initView() {
	        // show progress dialog
	        dialog = ProgressDialog.show(getActivity(), "", "Loading please wait...");
	         
	        String url = "http://192.168.43.5/ialert/getprograms.php";
	        FetchProgramTask task = new FetchProgramTask(this);
	        task.execute(url);
	    }
	

	@Override
	public void onFetchComplete(List<programs> data) {
		// TODO Auto-generated method stub
		if(dialog != null)  dialog.dismiss();
		   tag=1;
        // create new adapter
        programAdapter adapter = new programAdapter(getActivity(), data);
        // set the adapter to list
        setListAdapter(adapter);  
        
        ListView lv = getListView();
	       
	       //////
	       
	       lv.setOnItemClickListener(new OnItemClickListener() {
	    		 
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
	                // getting values from selected ListItem
	              
	            	//Toast.makeText(MainfetchActivity.this, "its me number"+position, Toast.LENGTH_LONG).show(); 
	            	String pid = ((TextView) view.findViewById(R.id.idstxt)).getText()
	                        .toString();
	            	
		            
	            	Context context=getActivity();
                    
            	    SharedPreferences preferences =context.getSharedPreferences("updates",
            	       context. MODE_PRIVATE);
            	    SharedPreferences.Editor editor = preferences.edit();
                
            	    
            	    String myid=pid;
            	
            	    editor.putString("pid", myid);
            	    
            	    editor.commit();
            	  
	     
	            
	            	 Bundle b = new Bundle();
	            	 b.putString("pid", pid);
	           
	            	 pdetailsFragment frag= new pdetailsFragment();
				        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, frag).commit();
				    
	            	
	            }
	        });
     
	}

	@Override
	public void onFetchFailure(String msg) {
		// TODO Auto-generated method stub
		
		if(dialog != null)  dialog.dismiss();
        // show failure message
	 
	 
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show(); 
        tag=0;
		
	}
	    
	  


}
