package com.news.ialert;


	import java.text.NumberFormat;
	import java.util.List;
	 
	import android.content.Context;
	import android.content.res.Resources;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;
	import android.widget.ArrayAdapter;
	import android.widget.ImageView;
	import android.widget.LinearLayout;
	import android.widget.TextView;
	 
	public class UpdatesAdapter extends ArrayAdapter<Application>{
		
	    private List<Application> items;
	    Application app;
	    int ok=0;
	     
	    public UpdatesAdapter(Context context, List<Application> items) {
	        super(context, R.layout.news_custom_list, items);
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
	            v = li.inflate(R.layout.news_custom_list, null);           
	        }
	         
	      
	        	
	        Application app = items.get(position);
	        
	        if(app != null) {
	        	
	        	
	        	
	            ImageView icon = (ImageView)v.findViewById(R.id.appIcon);
	            TextView titleText = (TextView)v.findViewById(R.id.titleTxt);
	            TextView videoText = (TextView)v.findViewById(R.id.video);
	            TextView audioText = (TextView)v.findViewById(R.id.audio);
	            TextView detailsText = (TextView)v.findViewById(R.id.detailstxt);
	            TextView senderlocationText = (TextView)v.findViewById(R.id.ulocation);
	            TextView idText = (TextView)v.findViewById(R.id.uid);
	           
	            TextView imgdata = (TextView)v.findViewById(R.id.imagedata);
	            
	            if(audioText != null) audioText.setText((app.getaudio()).substring(0, 1).toUpperCase() + (app.getaudio()).substring(1));
			       
	            if(videoText != null) videoText.setText((app.getvideo()).substring(0, 1).toUpperCase() + (app.getvideo()).substring(1));
		           
	            if(titleText != null) titleText.setText((app.getTitle()).substring(0, 1).toUpperCase() + (app.getTitle()).substring(1));
	            if(detailsText != null) detailsText.setText((app.getDetails()).substring(0, 1).toUpperCase() + (app.getDetails()).substring(1));
	            if(senderlocationText != null) senderlocationText.setText((app.getsenderlocation()).substring(0, 1).toUpperCase() + (app.getsenderlocation()).substring(1));
	            if(idText != null) idText.setText((app.getid()).substring(0, 1).toUpperCase() + (app.getid()).substring(1));

	            
	            
	             
	            if(icon != null) {
	                Resources res = getContext().getResources();
	               // String sIcon = "com.sj.jsondemo:drawable/" + app.getIcon();
	               // icon.setImageBitmap(app.getIcon());
	                
	               // ImageView imgAvatar = (ImageView)findViewById(R.id.appIcon);
	                

	        		ImageDownloader imageManager = new ImageDownloader();
	        		
	        		String url_news_updates = "http://192.168.43.5/ialert/image_uploads/"+app.getface();
	        		String imaged=imageManager.download(url_news_updates, icon);
	        		imgdata.setText(imaged);

	            }
	            
	             
	            
	            

	           
	        	}
	        
	        return v;
	    	
	         
	       
	    }
	}
