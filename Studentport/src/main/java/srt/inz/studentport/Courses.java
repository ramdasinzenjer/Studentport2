package srt.inz.studentport;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Courses extends Activity{
	
	Spinner spd,spc;	ArrayAdapter<String> s1,s2;
	
	TextView tt,td,tdu,tf;	String sd,sdu,sf;
	String sh_sid,sdep,scourse,response,response1;
	String scr;
	
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courses);
		spd=(Spinner)findViewById(R.id.spinner_dep);
		spc=(Spinner)findViewById(R.id.spinner_course);
		tt=(TextView)findViewById(R.id.textView_ctitle);
		td=(TextView)findViewById(R.id.textView_cdescr);
		tdu=(TextView)findViewById(R.id.textView_cduration);
		tf=(TextView)findViewById(R.id.textView_cfee);
		
		SharedPreferences share=getSharedPreferences("skey", MODE_WORLD_READABLE);
		sh_sid=share.getString("suid", "");
		
		
		String[] dpm = getResources().getStringArray(R.array.department);
        
        s1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dpm);
	    s1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spd.setAdapter(s1);
	    spd.setOnItemSelectedListener(new OnItemSelectedListener()
        {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				sdep=arg0.getItemAtPosition(arg2).toString();
				((TextView) arg0.getChildAt(0)).setTextColor(Color.BLUE);
						
				new crs_det().execute();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub	
			}
        	
        });
	}
	
	public class crs_det extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			
			  String urlParameters = null;
	            try {
	                urlParameters =  "dept=" + URLEncoder.encode(sdep, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            response = Connectivity.excutePost(Constants.COURSERET_URL,
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
					Toast.makeText(getApplicationContext(), "Error details.", Toast.LENGTH_SHORT).show();
					spc.setAdapter(null);
					tt.setText("Course Title");
		            td.setText("Description : ");
		            tdu.setText("Course duration : ");
		            tf.setText("Fee details : ");
		         
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
				List<String> lables = new ArrayList<String>();
				for(int i=0;i<length;i++)
				{
		           
					JSONObject c = ja.getJSONObject(i);
		            // Storing  JSON item in a Variable
		            scr=c.getString("c_name");
		            
		            HashMap<String, String> map = new HashMap<String, String>();
		            map.put("c_name", scr);
		            oslist.add(map);	            
		            lables.add(oslist.get(i).get("c_name"));
		            
		            s2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lables);
				    s2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				    spc.setAdapter(s2);
				    spc.setOnItemSelectedListener(new OnItemSelectedListener()
			        {

						@Override
						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							scourse=arg0.getItemAtPosition(arg2).toString();
							((TextView) arg0.getChildAt(0)).setTextColor(Color.BLUE);
							new course_det().execute();
				
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub	
						}
			        	
			        });
         
		            }
			}
		        catch (JSONException e) {
		          e.printStackTrace();
		        }
		       }
	  
	  public class course_det extends AsyncTask<String, String, String>
		{

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				String urlParameters = null;
	            try {
	                urlParameters =  "c_name=" + URLEncoder.encode(scourse, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            response1 = Connectivity.excutePost(Constants.COURSEDET_URL,
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
					parsingmethod1();
					}
					else {
						Toast.makeText(getApplicationContext(), "Error details.", Toast.LENGTH_SHORT).show();
					}
				}
			
		}

		  public void parsingmethod1() {
				try
				{
					JSONObject jobject=new JSONObject(response1);
					JSONObject jobject1=jobject.getJSONObject("Event");
					JSONArray ja=jobject1.getJSONArray("Details");
					int length=ja.length();
					for(int i=0;i<length;i++)
					{
			           
						JSONObject c = ja.getJSONObject(i);
			            // Storing  JSON item in a Variable
			            
			            sd=c.getString("c_descr");
			            sdu=c.getString("duration");
			            sf=c.getString("fee");
			            
			            tt.setText(scourse);
			            td.setText("Description : \n"+sd);
			            tdu.setText("Course duration : "+sdu);
			            tf.setText("Fee details : "+sf);
			         
			            }
				}
			        catch (JSONException e) {
			          e.printStackTrace();
			        }
			       }

}
