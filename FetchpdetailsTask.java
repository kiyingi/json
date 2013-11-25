package com.news.ialert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.Toast;

public class FetchpdetailsTask extends AsyncTask<String, Void, String>{
    private final FetchpdetailsListener listener;
    private String msg;
     
    public FetchpdetailsTask(FetchpdetailsListener listener) {
        this.listener = listener;
    }
     
    @Override
    protected String doInBackground(String... params2) {
        if(params2 == null) return null;
         
        // get url from params
        String url = params2[0];
        String myid = params2[1];
         
        try {
            // create http connection
            HttpClient client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            
            params.add(new BasicNameValuePair("myid", myid));            
            
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("myid", new StringBody(myid)); 
     
         
            HttpResponse response = client.execute(httpget);
             
          
            HttpEntity entity = response.getEntity();
             
            if(entity == null) {
                msg = "No response from server";
                return null;       
            }
          
            // get response content and convert it to json string
            InputStream is = entity.getContent();
            return streamToString(is);
        }
        catch(IOException e){
            msg = "No Network Connection";
        }
         
        return null;
    }
     
    @Override
    protected void onPostExecute(String sJson) {
        if(sJson == null) {
            if(listener != null) listener.onFetchFailure(msg);
            return;
        }       
         
        try {
            // convert json string to json array
            JSONArray aJson = new JSONArray(sJson);
            // create apps list
            List<pdetails> apps = new ArrayList<pdetails>();
             
            for(int i=0; i<aJson.length(); i++) {
                JSONObject json = aJson.getJSONObject(i);
                pdetails app = new pdetails();
                app.settime(json.getString("time"));
                app.setprogram(json.getString("program"));
               
                
               
                
          
               
               
                 
                // add the app to apps list
                apps.add(app);
            }
             
            //notify the activity that fetch data has been complete
            if(listener != null) listener.onFetchComplete(apps);
        } catch (JSONException e) {
            msg = "Invalid response";
            if(listener != null) listener.onFetchFailure(msg);
            return;
        }       
    }
     
    /**
     * This function will convert response stream into json string
     * @param is respons string
     * @return json string
     * @throws IOException
     */
    public String streamToString(final InputStream is) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
         
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        }
        catch (IOException e) {
            throw e;
        }
        finally {          
            try {
                is.close();
            }
            catch (IOException e) {
                throw e;
            }
        }
         
        return sb.toString();
    }
}
