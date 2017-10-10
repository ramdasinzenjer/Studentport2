package srt.inz.studentport;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class S_Events extends Activity{
	
	ListView meventlist;
	ListAdapter adapter;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	String response;
	
	String sh_sid,stitle,sdescr,splace,sdat,stim;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sevents);
		meventlist=(ListView)findViewById(R.id.listView_events);
		
		SharedPreferences share=getSharedPreferences("skey", MODE_WORLD_READABLE);
		sh_sid=share.getString("suid", "");
		
		new ret_clgevent().execute();
	}
	
	
	public class ret_clgevent extends AsyncTask<String, String, String>
	  {
		  @Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		  @Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
		
			  String urlParameters = null;
	            try {
	                urlParameters = "";
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            response = Connectivity.excutePost(Constants.EVENTSRET_URL,
	                    urlParameters);
	            Log.e("You are at", "" + response);
	       return response;
			 
		}
		  @Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(response.contains("success"))
			{
			parsingmethod();
			}
			else {
				Toast.makeText(getApplicationContext(), "Error in parsing.", Toast.LENGTH_SHORT).show();
			}
		}
	  }
	  
	  
	  public void parsingmethod() {
			try
			{
				JSONObject jobject=new JSONObject(response);
				JSONObject jobject1=jobject.getJSONObject("Event");
				JSONArray ja=jobject1.getJSONArray("Details");
				int length=ja.length();
				 oslist.clear();
				meventlist.setAdapter(null);
				for(int i=0;i<length;i++)
				{
		          
					JSONObject c = ja.getJSONObject(i);
		            // Storing  JSON item in a Variable
		            stitle = c.getString("e_title");
		           sdescr=c.getString("e_descr");
		           splace=c.getString("e_place");
		           stim=c.getString("e_time");
		           sdat=c.getString("e_date");
		           
		            // Adding value HashMap key => value
		            HashMap<String, String> map = new HashMap<String, String>();
		            map.put("e_title", stitle);
		            map.put("e_descr", sdescr);
		            map.put("e_place", splace);
		            map.put("e_time", stim);
		            map.put("e_date", sdat);
		            
		            map.put("show", stitle+"\n Date : "+sdat+"\t Time : "+stim);
		            //Toast.makeText(getApplicationContext(), ""+sname,Toast.LENGTH_SHORT).show();
		            oslist.add(map);
		            
		            adapter = new SimpleAdapter(getApplicationContext(), oslist,
		                R.layout.singleitem,
		                new String[] {"show"}, new int[] {R.id.textsingle});
		            meventlist.setAdapter(adapter);
		            meventlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		               @Override
		               public void onItemClick(AdapterView<?> parent, View view,
		                                            int position, long id) {               
		              // Toast.makeText(getApplicationContext(), " "+oslist.get(+position).get("topic"), Toast.LENGTH_SHORT).show();
		               
		               String value=oslist.get(+position).get("e_descr");
		               String value2=oslist.get(+position).get("e_title");
		               openDialog(value,value2);
		                          
		               }
		                });
		            }
			}
		        catch (JSONException e) {
		          e.printStackTrace();
		        }
		       }
	  public void openDialog(String sdet, String stl){
	      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	      alertDialogBuilder.setMessage(""+sdet);
	      alertDialogBuilder.setTitle(""+stl);
	      alertDialogBuilder.setNegativeButton("OK",new DialogInterface.OnClickListener() {
	         @Override
	         public void onClick(DialogInterface dialog, int which) {
	        	 	        	
	        	 //finish();
	         }
	      });
	      
	      AlertDialog alertDialog = alertDialogBuilder.create();
	      alertDialog.show();
	   }

}
