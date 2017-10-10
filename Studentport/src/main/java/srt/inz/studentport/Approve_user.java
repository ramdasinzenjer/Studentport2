package srt.inz.studentport;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Approve_user extends Activity{
	
	ListView aprlist,rglist;	ListAdapter apr_adapter,rg_adapter;
	ArrayList<HashMap<String, String>> oslist_apr = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> oslist_rg = new ArrayList<HashMap<String, String>>();
	String response1,response2,response3;
	
	String sfname,slname,stype,sdp,sdob,semail,sunam,spass,styp; 
	String sfname1,slname1,stype1,sdp1,sdob1,semail1,styp1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approve_user);
		aprlist=(ListView)findViewById(R.id.listViewaprusr);
		rglist=(ListView)findViewById(R.id.listViewregusr);
		
		new reg_userlist().execute();
		new apr_userlist().execute();
		
	}
	
	class reg_userlist extends AsyncTask<String, String, String>
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
	                urlParameters =  "status=" + URLEncoder.encode("0", "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            response1 = Connectivity.excutePost(Constants.REGISTERLIST_URL,
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
				rg_parsingmethod();
			}
			else {
				Toast.makeText(getApplicationContext(), "No new registrations", Toast.LENGTH_SHORT).show();
			}
		}
	  }
	  
	  
	  public void rg_parsingmethod() {
			try
			{
				JSONObject jobject=new JSONObject(response1);
				JSONObject jobject1=jobject.getJSONObject("Event");
				JSONArray ja=jobject1.getJSONArray("Details");
				rglist.setAdapter(null);
				oslist_rg.clear();
				
				int length=ja.length();
				for(int i=0;i<length;i++)
				{
		          
					JSONObject c = ja.getJSONObject(i);
		            // Storing  JSON item in a Variable
		            sfname = c.getString("firstname");
		            slname=c.getString("lastname");
		            sdp=c.getString("department");
		            sdob=c.getString("dob");
		            semail=c.getString("email");
		            sunam=c.getString("username");
		            spass=c.getString("password");
		            styp=c.getString("u_typ");
		            
		            // Adding value HashMap key => value
		            HashMap<String, String> map = new HashMap<String, String>();
		            map.put("firstname", sfname);
		            map.put("lastname", slname);
		            map.put("department", sdp);
		            map.put("dob", sdob);
		            map.put("email", semail);
		            map.put("u_typ", styp);
		           
		            map.put("name", sfname+" "+slname);
		            map.put("show", "Name : "+sfname+" "+slname+"\n Department : "+sdp+"\n DOB : "+sdob+"\n Email : "+semail+"\n User Type : "+styp);
		            oslist_rg.add(map);
		            
		            rg_adapter = new SimpleAdapter(getApplicationContext(), oslist_rg,
		                R.layout.singleitem,
		                new String[] {"show"}, new int[] {R.id.textsingle});
		            rglist.setAdapter(rg_adapter);
		            rglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		               @Override
		               public void onItemClick(AdapterView<?> parent, View view,
		                                            int position, long id) {  
		            	 String  value=oslist_rg.get(+position).get("name");
		            	 
		              Toast.makeText(getApplicationContext(), ""+value+" : Long press to approve User. ", Toast.LENGTH_SHORT).show();
		             
		            	   
		               }
		                });
		            rglist.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							new user_approve().execute();
							return false;
						}
					});
		            }
			}
		        catch (JSONException e) {
		          e.printStackTrace();
		        }
		       }
	  class apr_userlist extends AsyncTask<String, String, String>
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
	                urlParameters =  "status=" + URLEncoder.encode("1", "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            response2 = Connectivity.excutePost(Constants.REGISTERLIST_URL,
	                    urlParameters);
	            Log.e("You are at", "" + response2);
	       return response2;

		}
		  @Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(response2.contains("success"))
			{
				apr_parsingmethod();
			}
			else {
				Toast.makeText(getApplicationContext(), "No users approved yet.", Toast.LENGTH_SHORT).show();
			}
		}
	  }
	  
	  
	  public void apr_parsingmethod() {
			try
			{
				JSONObject jobject=new JSONObject(response2);
				JSONObject jobject1=jobject.getJSONObject("Event");
				JSONArray ja=jobject1.getJSONArray("Details");
				aprlist.setAdapter(null);
				oslist_apr.clear();
				
				int length=ja.length();
				for(int i=0;i<length;i++)
				{
		          
					JSONObject c = ja.getJSONObject(i);
		            // Storing  JSON item in a Variable
					sfname1= c.getString("firstname");
		            slname1=c.getString("lastname");
		            sdp1=c.getString("department");
		            sdob1=c.getString("dob");
		            semail1=c.getString("email");
		            styp1=c.getString("u_typ");
		           
		            
		            // Adding value HashMap key => value
		            HashMap<String, String> map = new HashMap<String, String>();
		            map.put("firstname", sfname1);
		            map.put("lastname", slname1);
		            map.put("department", sdp1);
		            map.put("dob", sdob1);
		            map.put("email", semail1);
		            map.put("u_typ", styp1);
		           
		            map.put("name", sfname1+" "+slname1);
		            map.put("show", "Name : "+sfname1+" "+slname1+"\n Department : "+sdp1+"\n DOB : "+sdob1+"\n Email : "+semail1+"\n User Type : "+styp1);
		            oslist_apr.add(map);
		            
		            apr_adapter = new SimpleAdapter(getApplicationContext(), oslist_apr,
		                R.layout.singleitem,
		                new String[] {"show"}, new int[] {R.id.textsingle});
		            aprlist.setAdapter(apr_adapter);
		            /*aprlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		               @Override
		               public void onItemClick(AdapterView<?> parent, View view,
		                                            int position, long id) {  
		            	 String  value=oslist_apr.get(+position).get("name");
		            	 
		              Toast.makeText(getApplicationContext(), ""+value, Toast.LENGTH_SHORT).show();
		              
		            	   
		               }
		                });*/
		            }
			}
		        catch (JSONException e) {
		          e.printStackTrace();
		        }
		       }

	  class user_approve extends AsyncTask<String, String, String>
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
	                urlParameters =  "username=" + URLEncoder.encode(sunam, "UTF-8")
	                		+"&&"+"password=" +URLEncoder.encode(spass, "UTF-8")
	                		+"&&"+"type=" +URLEncoder.encode(styp, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            response3 = Connectivity.excutePost(Constants.USERSTATUP_URL,
	                    urlParameters);
	            Log.e("You are at", "" + response3);
	       return response3;
		
		}
		  @Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(response3.contains("success"))
			{
				Toast.makeText(getApplicationContext(), "User approval completed.", Toast.LENGTH_SHORT).show();
				m_refresh();
			}
			else {
				Toast.makeText(getApplicationContext(), "Error in approval.", Toast.LENGTH_SHORT).show();
			}
		}
	  }
	  
	  public void m_refresh()
	  {
		  finish();
		  startActivity(getIntent());
	  }
	  
}
