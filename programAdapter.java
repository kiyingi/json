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

public class programAdapter extends ArrayAdapter<programs>{
	
    private List<programs> items;
    programs app;
    int ok=0;
     
    public programAdapter(Context context, List<programs> items) {
        super(context, R.layout.program_custom_list, items);
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
            v = li.inflate(R.layout.program_custom_list, null);           
        }
         
      
        	
        programs app = items.get(position);
        
        if(app != null) {
        	
        	
        	
            ImageView icon = (ImageView)v.findViewById(R.id.picon);
            TextView nameText = (TextView)v.findViewById(R.id.dayTxt);
            
           
            TextView idText = (TextView)v.findViewById(R.id.idstxt);
           
           
            if(nameText != null) nameText.setText((app.getname()).substring(0, 1).toUpperCase() + (app.getname()).substring(1));
		       
            if(idText!= null) idText.setText((app.getid()).substring(0, 1).toUpperCase() + (app.getid()).substring(1));
	           
             
            
             
            if(icon != null) {
                Resources res = getContext().getResources();
               // String sIcon = "com.sj.jsondemo:drawable/" + app.getIcon();
               // icon.setImageBitmap(app.getIcon());
                
               // ImageView imgAvatar = (ImageView)findViewById(R.id.appIcon);
                

        		ImageDownloader imageManager = new ImageDownloader();
        		
        		String url_news_updates = "http://192.168.43.5/ialert/image_uploads/"+app.getface();
        		String imaged=imageManager.download(url_news_updates, icon);
        		

            }
            
             
            
            

           
        	}
        
        return v;
    	
         
       
    }
}
