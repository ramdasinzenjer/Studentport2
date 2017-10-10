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

public class SComments extends Activity{
	
	ListView mlist; TextView tvpost; EditText ecmnt; Button pcmnt;
	
	ArrayList<HashMap<String, String>> oslist= new ArrayList<HashMap<String,String>>();
	ListAdapter adapter;
	
	String sh_sid,sh_pid,sh_post,scmnt,scid,response,response1,scomment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scomments);
		tvpost=(TextView)findViewById(R.id.tvpost);
		ecmnt=(EditText)findViewById(R.id.et_cmnt);
		pcmnt=(Button)findViewById(R.id.b_cmnt);
		mlist=(ListView)findViewById(R.id.listView_comment);
		
		SharedPreferences share=getSharedPreferences("post", MODE_WORLD_READABLE);
		sh_pid=share.getString("pid", "");
		sh_post=share.getString("ptp", "");
		
		SharedPreferences share1=getSharedPreferences("skey", MODE_WORLD_READABLE);
		sh_sid=share1.getString("suid", "");
		
		tvpost.setText(sh_post);
		
		pcmnt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				scomment=ecmnt.getText().toString();
				new st_comment().execute();
			}
		});
		
		new ret_commentst().execute();
		
	}
	
	public class ret_commentst extends AsyncTask<String, String, String>
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
	                urlParameters =  "post_id=" + URLEncoder.encode(sh_pid, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            response = Connectivity.excutePost(Constants.RETCOMMENTS_URL,
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
				mlist.setAdapter(null);
				for(int i=0;i<length;i++)
				{
		          
					JSONObject c = ja.getJSONObject(i);
		            // Storing  JSON item in a Variable
		            scmnt = c.getString("cmnt");
		           scid=c.getString("std_id");
	           
		            // Adding value HashMap key => value
		            HashMap<String, String> map = new HashMap<String, String>();
		            map.put("cmnt", scmnt);
		            map.put("std_id", scid);

		            map.put("show", scmnt+"\n By : "+scid);
		            //Toast.makeText(getApplicationContext(), ""+sname,Toast.LENGTH_SHORT).show();
		            oslist.add(map);
		            
		            adapter = new SimpleAdapter(getApplicationContext(), oslist,
		                R.layout.singleitem,
		                new String[] {"show"}, new int[] {R.id.textsingle});
		            mlist.setAdapter(adapter);
		            mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		               @Override
		               public void onItemClick(AdapterView<?> parent, View view,
		                                            int position, long id) {               
		              // Toast.makeText(getApplicationContext(), " "+oslist.get(+position).get("topic"), Toast.LENGTH_SHORT).show();
		               
		               String value=oslist.get(+position).get("e_descr");
		               String value2=oslist.get(+position).get("e_title");
		             //  openDialog(value,value2);
		                          
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
	  public class st_comment extends AsyncTask<String, String, String>
		{

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				  String urlParameters = null;
		            try {
		                urlParameters =  "std_id=" + URLEncoder.encode(sh_sid, "UTF-8")
		                		+"&&" + "post_id=" + URLEncoder.encode(sh_pid, "UTF-8")
		                		+"&&" + "cmnt=" + URLEncoder.encode(scomment, "UTF-8");
		            } catch (UnsupportedEncodingException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }

		            response1 = Connectivity.excutePost(Constants.RETCOMMENTS_URL,
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
						Toast.makeText(getApplicationContext(), "Successfully posted", Toast.LENGTH_SHORT).show();
						new ret_commentst().execute();
					}
					else {
						Toast.makeText(getApplicationContext(), "Error in posting", Toast.LENGTH_SHORT).show();
					}
				}
		}

}
