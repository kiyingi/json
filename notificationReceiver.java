package com.news.ialert;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

public class notificationReceiver extends Activity{

	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	 Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
     if(alert == null){
         // alert is null, using backup
         alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
         if(alert == null){  // I can't see this ever being null (as always have a default notification) but just incase
             // alert backup is null, using 2nd backup
             alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);               
         }
     }
     
     Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), alert);
     r.stop();
}
}
