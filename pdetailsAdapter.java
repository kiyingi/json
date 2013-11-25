package com.news.ialert;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class pdetailsAdapter extends ArrayAdapter<pdetails>{
	
    private List<pdetails> items;
    pdetails app;
    int ok=0;
     
    public pdetailsAdapter(Context context, List<pdetails> items) {
        super(context, R.layout.pdetails_custom_list, items);
        this.items = items;
        
    }
     
    
    @Override
    public int getCount() {
        return items.size();
    }
     
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
         
        if(v == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.pdetails_custom_list, null);           
        }
         
      
        	
        pdetails app = items.get(position);
        
        if(app != null) {
        	
        	
        	
     
            TextView ptime = (TextView)v.findViewById(R.id.ptime);
            
           
            TextView program = (TextView)v.findViewById(R.id.pprogram);
           
           
            if(ptime != null) ptime.setText((app.gettime()).substring(0, 1).toUpperCase() + (app.gettime()).substring(1));
		       
            if(program!= null) program.setText((app.getprogram()).substring(0, 1).toUpperCase() + (app.getprogram()).substring(1));
	           
        

           
        	}
        
        return v;
    	
         
       
    }
}
