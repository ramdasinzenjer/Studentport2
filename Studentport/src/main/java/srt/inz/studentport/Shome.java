package srt.inz.studentport;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Shome extends Activity{
	TextView tusr; EditText etp; ListView mplist;
	Button bp;  String stopic,sdatetime,stid;
	
	ListAdapter adapter;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	String response,response1,response3;
	
	String sh_sid,sh_nam,sh_dp,spost,sdate;
	
	String p_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shome);
		tusr=(TextView)findViewById(R.id.textwelcomeuser);
		etp=(EditText)findViewById(R.id.editpost);
		mplist=(ListView)findViewById(R.id.listViewpost);
		bp=(Button)findViewById(R.id.button_post);
		
		SharedPreferences share=getSharedPreferences("skey", MODE_WORLD_READABLE);
		sh_sid=share.getString("suid", "");
		sh_nam=share.getString("snam", "");
		sh_dp=share.getString("sdp", "");
		
		tusr.setText("Hello "+sh_nam);
		Toast.makeText(getApplicationContext(), ""+sh_dp, Toast.LENGTH_SHORT).show();
		
		bp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
				Calendar calobj = Calendar.getInstance();
				sdate=df.format(calobj.getTime());
				spost=etp.getText().toString();
				new st_post().execute();
				
			}
		});
		
		new ret_posts().execute();
		
	}
	
	public class st_post extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlParameters = null;
            try {
                urlParameters =  "st_id=" + URLEncoder.encode(sh_sid, "UTF-8") + "&&"
                        + "topic=" + URLEncoder.encode(spost, "UTF-8") + "&&"
                        +"datetime=" + URLEncoder.encode(sdate, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            response = Connectivity.excutePost(Constants.POSTTOPIC_URL,
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
					Toast.makeText(getApplicationContext(), "Topic successfully posted", Toast.LENGTH_SHORT).show();
					new ret_posts().execute();
				}
				else {
					Toast.makeText(getApplicationContext(), "Error in posting", Toast.LENGTH_SHORT).show();
				}
			}
	}
	
	public class ret_posts extends AsyncTask<String, String, String>
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
	                urlParameters =  "dept=" + URLEncoder.encode(sh_dp, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            response1 = Connectivity.excutePost(Constants.POSTRETTOPIC_URL,
	                    urlParameters);
	            Log.e("You are at", "" + response1);
	       return response1;
			 
		}
		  @Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(response1.contains("success"))
			{
			parsingmethod();
			}
			else {
				Toast.makeText(getApplicationContext(), "No posts available.", Toast.LENGTH_SHORT).show();
			}
		}
	  }  
	  public void parsingmethod() {
			try
			{
				JSONObject jobject=new JSONObject(response1);
				JSONObject jobject1=jobject.getJSONObject("Event");
				JSONArray ja=jobject1.getJSONArray("Details");
				int length=ja.length();
				 oslist.clear();
				mplist.setAdapter(null);
				for(int i=0;i<length;i++)
				{
		           // oslist.clear();
					//listv.setAdapter(null);
					JSONObject c = ja.getJSONObject(i);
		            // Storing  JSON item in a Variable
		            stid = c.getString("st_id");
		           stopic=c.getString("topic");
		           sdatetime=c.getString("datetime");
		           p_id=c.getString("id");
		           
		            // Adding value HashMap key => value
		            HashMap<String, String> map = new HashMap<String, String>();
		            map.put("st_id", stid);
		            map.put("topic", stopic);
		            map.put("datetime", sdatetime);
		            map.put("id", p_id);
		            
		            map.put("show", stid+"\n "+stopic+".\n Posted on : "+sdatetime);
		            //Toast.makeText(getApplicationContext(), ""+sname,Toast.LENGTH_SHORT).show();
		            oslist.add(map);
		            
		            adapter = new SimpleAdapter(getApplicationContext(), oslist,
		                R.layout.singleitem,
		                new String[] {"show"}, new int[] {R.id.textsingle});
		            mplist.setAdapter(adapter);
		            mplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		               @Override
		               public void onItemClick(AdapterView<?> parent, View view,
		                                            int position, long id) {               
		              // Toast.makeText(getApplicationContext(), " "+oslist.get(+position).get("topic"), Toast.LENGTH_SHORT).show();
		               
		               String value=oslist.get(+position).get("topic");
		               String value2=oslist.get(+position).get("id");
		              
		               SharedPreferences shar=getSharedPreferences("post", MODE_WORLD_READABLE);
		                 SharedPreferences.Editor ed=shar.edit();
		                 ed.putString("pid", value2);
		                 ed.putString("ptp", value);
		                 ed.commit();
		                 openDialog(value);
		                 /*  Intent i=new Intent(getApplicationContext(),REQ_employee.class);
	                    startActivity(i);*/             
		               }
		                });
		            }
			}
		        catch (JSONException e) {
		          e.printStackTrace();
		        }
		       }
	  public void openDialog(String stp){
	      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	      alertDialogBuilder.setMessage(""+stp);
	      
	      alertDialogBuilder.setNegativeButton("Comment",new DialogInterface.OnClickListener() {
	         @Override
	         public void onClick(DialogInterface dialog, int which) {
	        	 
	        	 Toast.makeText(getApplicationContext(),"Comment",Toast.LENGTH_SHORT).show();
	        	 Intent i=new Intent(getApplicationContext(),SComments.class);
                 startActivity(i);
	        	 //finish();
	         }
	      });
	      
	      AlertDialog alertDialog = alertDialogBuilder.create();
	      alertDialog.show();
	   }
}
