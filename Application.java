package com.news.ialert;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Application {
	 private String title;
	    private String details;
	    private String senderlocation;
	    private String image;
	    private String face;
	    private String timendate;
	    private String video;
	    private String audio;
	    private String id;
	    
	    public String getsenderlocation() {
	        return senderlocation;
	    }
	    public void setsenderlocation(String senderlocation) {
	        this.senderlocation = senderlocation;
	    }
	    public String gettimendate() {
	        return timendate;
	    }
	    public void settimendate(String timendate) {
	        this.timendate = timendate;
	    }
	   
	     
	    public String getTitle() {
	        return title;
	    }
	    public void setTitle(String title) {
	        this.title = title;
	    }
	  
	    
	    public String getDetails() {
	        return details;
	    }
	    public void setDetails(String details) {
	        this.details = details;
	    }

	    
public String getface() {
	    	
	        return face;
	    }
	    public void setface(String face) {
	    	
	    	 byte[] mess;
	
	    	 this.face =face;
	    	 
	    }
	    	    
 public String getImage() {
	        return image;
	    }
	    public void setImage(Bitmap image) {
	    	String data="";
	        this.image = data;
	    }
	   
	    
	    public String getid() {
	        return id;
	    }
	    public void setid(String id) {
	    	String data="";
	        this.id = id;
	    }
	    
	    public String getvideo() {
	        return video;
	    }
	    public void setvideo(String video) {
	    	String data="";
	        this.video = video;
	    }
	    
	    public String getaudio() {
	        return audio;
	    }
	    public void setaudio(String audio) {
	    	String data="";
	        this.audio = audio;
	    }
	    
}
