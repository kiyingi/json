package com.news.ialert;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class playActivity extends Activity 

{




       private static ProgressDialog progressDialog;

       String videourl; 

       VideoView videoView ; 
String url;



       @Override

       protected void onCreate(Bundle savedInstanceState) 

       {

              super.onCreate(savedInstanceState);

              setContentView(R.layout.video);

              Bundle bundle = getIntent().getExtras();
		      url = bundle.getString("video");

		      videourl=url;
              videoView = (VideoView) findViewById(R.id.video_View);




              progressDialog = ProgressDialog.show(playActivity.this, "", "Buffering video...",true);

              progressDialog.setCancelable(false); 







              PlayVideo();

       }




       private void PlayVideo()

       {

              try

              {    

                     getWindow().setFormat(PixelFormat.TRANSLUCENT);

                     MediaController mediaController = new MediaController(playActivity.this);

                     mediaController.setAnchorView(videoView);         




                     Uri video = Uri.parse(videourl);           

                     videoView.setMediaController(mediaController);

                     videoView.setVideoURI(video);

                     videoView.requestFocus();            

                     videoView.setOnPreparedListener(new OnPreparedListener()

                     {




                           public void onPrepared(MediaPlayer mp)

                           {                 

                                  progressDialog.dismiss();   

                                  videoView.start();

                           }

						
                     }); 




              }

              catch(Exception e)

              {

                     progressDialog.dismiss();

                     System.out.println("Video Play Error :"+e.toString());

                     finish();

              } 




       }


}
