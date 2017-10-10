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
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

public class Add_events extends Activity{
	
	EditText etitle,edesc,eplac,edat,etim; Button bup;
	String stitl,sdesc,splac,sdate,stime;
	
	ListView meventlist;
	ListAdapter adapter;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	String response;	
	String sh_sid,stitle,sdescr,splace,sdat,stim;
	
	EditText et,ed,ep,edt,etm; String st,sd,sp,sdt,stm,hos,hos1,sid,vid; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_events);
		meventlist=(ListView)findViewById(R.id.listVieweve);
		et=(EditText)findViewById(R.id.editText_title);
		ed=(EditText)findViewById(R.id.editText_decrip);
		ep=(EditText)findViewById(R.id.editText_place);
		edt=(EditText)findViewById(R.id.editText_edat);
		etm=(EditText)findViewById(R.id.editText_etim);
		bup=(Button)findViewById(R.id.button_upeve);
		
		bup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				st=et.getText().toString();
				sd=ed.getText().toString();
				sp=ep.getText().toString();
				sdt=edt.getText().toString();
				stm=etm.getText().toString();
				
				new Up_events().execute();			
			}
		});
		
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
	                urlParameters =  "";
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
		           sid=c.getString("id");
		            // Adding value HashMap key => value
		            HashMap<String, String> map = new HashMap<String, String>();
		            map.put("e_title", stitle);
		            map.put("e_descr", sdescr);
		            map.put("e_place", splace);
		            map.put("e_time", stim);
		            map.put("e_date", sdat);
		            map.put("id", sid);
		            
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
		               vid=oslist.get(+position).get("id");
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
	      alertDialogBuilder.setPositiveButton("Remove",new DialogInterface.OnClickListener() {
		         @Override
		         public void onClick(DialogInterface dialog, int which) {
		        	 
		        	new remove_event().execute();
		        	 //finish();
		         }
		      });
	      
	      AlertDialog alertDialog = alertDialogBuilder.create();
	      alertDialog.show();
	   }
	  
	  public class Up_events extends AsyncTask<String, String, String>
		{
		
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				String urlParameters = null;
	            try {
	                urlParameters =  "e_title=" + URLEncoder.encode(st, "UTF-8") + "&&"
	                        + "e_descr=" + URLEncoder.encode(sd, "UTF-8") + "&&"
	                        +"e_place=" + URLEncoder.encode(sp, "UTF-8") + "&&"	                        
	                        + "e_date=" + URLEncoder.encode(sdt, "UTF-8") + "&&"
	                        +"e_time=" + URLEncoder.encode(stm, "UTF-8") ;
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            hos = Connectivity.excutePost(Constants.EVENTUPDATE_URL,
	                    urlParameters);
	            Log.e("You are at", "" + hos);
	       return hos;
				
			}
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if(hos.contains("success"))
				{
					Toast.makeText(getApplicationContext(), "Events updated.", Toast.LENGTH_SHORT).show();	
					m_refresh();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
					Log.e("Database error :", "Update event error");					
				}	
			}				
		}
		public class remove_event extends AsyncTask<String, String, String>
		{	
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				String urlParameters = null;
	            try {
	                urlParameters =  "id=" + URLEncoder.encode(vid, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            hos1 = Connectivity.excutePost(Constants.EVENTDROP_URL,
	                    urlParameters);
	            Log.e("You are at", "" + hos1);
	       return hos1;
				
			}
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if(hos1.contains("success"))
				{
					Toast.makeText(getApplicationContext(), "Event removed.", Toast.LENGTH_SHORT).show();	
					m_refresh();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
					Log.e("Database error :", "Event removal process error");					
				}	
			}		
		}		
		public void m_refresh()
		  {
			  finish();
			  startActivity(getIntent());
		  }		
}
