package srt.inz.studentport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import srt.inz.studentport.Shome.ret_posts;
//import srt.inz.studentport.Shome.st_likes;
import srt.inz.studentport.Shome.st_post;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class Testclass extends Activity{
	TextView tusr; EditText etp; ListView mplist;
	Button bp;  String stopic,sdatetime,stid;
	
	ListAdapter adapter;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	String response,response1,response3;
	
	String sh_sid,spost,sdate;
	
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
		
		
		tusr.setText("Hello "+sh_sid);
		
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
	
	public class st_post extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try 
			{
				String serv_ip=getString(R.string.server_add);
				DefaultHttpClient hc=new DefaultHttpClient();
			ResponseHandler<String> resp=new BasicResponseHandler();
			HttpPost postMethod=new HttpPost(serv_ip+"post_topic.php?");
			List<NameValuePair>nameValuePairs=new ArrayList<NameValuePair>(3);
			nameValuePairs.add(new BasicNameValuePair("st_id", sh_sid ));
			nameValuePairs.add(new BasicNameValuePair("topic", spost));
			nameValuePairs.add(new BasicNameValuePair("datetime", sdate));
			postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			 response= hc.execute(postMethod,resp);
			 System.out.println(response);
				
			}
			catch(Exception e)
			{
				System.out.println("Error"+e);
			}
			
			return null;
			}
			  @Override
			protected void onPostExecute(Void result) {
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
	
	public class ret_posts extends AsyncTask<Void, Void, Void>
	  {
		  @Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		  @Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
		try 
		{
			String serv_ip=getString(R.string.server_add);
			DefaultHttpClient hc=new DefaultHttpClient();
		ResponseHandler<String> resp=new BasicResponseHandler();
		HttpPost postMethod=new HttpPost(serv_ip+"ret_post.php?");
		 response1= hc.execute(postMethod,resp);
		 System.out.println(response1);
			
		}
		catch(Exception e)
		{
			System.out.println("Error"+e);
		}
		
		return null;
		}
		  @Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(response1.contains("success"))
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
		           
		            // Adding value HashMap key => value
		            HashMap<String, String> map = new HashMap<String, String>();
		            map.put("st_id", stid);
		            map.put("topic", stopic);
		            map.put("datetime", sdatetime);
		            
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
		               openDialog(value);
		               /*SharedPreferences shar=getSharedPreferences("emp_det", MODE_WORLD_READABLE);
		                 SharedPreferences.Editor ed=shar.edit();
		                 ed.putString("e_nam", ssssid);
		                 ed.commit();
		                Intent i=new Intent(getApplicationContext(),REQ_employee.class);
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
	      
	      alertDialogBuilder.setPositiveButton("Like", new DialogInterface.OnClickListener() {
	         @Override
	         public void onClick(DialogInterface arg0, int arg1) {
	            
	            Toast.makeText(getApplicationContext(),"Like",Toast.LENGTH_SHORT).show();
	           
	            new st_likes().execute();
	         }
	      });
	      
	      alertDialogBuilder.setNegativeButton("Comment",new DialogInterface.OnClickListener() {
	         @Override
	         public void onClick(DialogInterface dialog, int which) {
	        	 
	        	 Toast.makeText(getApplicationContext(),"Comment",Toast.LENGTH_SHORT).show();
	        	
	        	 //finish();
	         }
	      });
	      
	      AlertDialog alertDialog = alertDialogBuilder.create();
	      alertDialog.show();
	   }
	  
	  public class st_likes extends AsyncTask<Void, Void, Void>
		{

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try 
				{
					String serv_ip=getString(R.string.server_add);
					DefaultHttpClient hc=new DefaultHttpClient();
				ResponseHandler<String> resp=new BasicResponseHandler();
				HttpPost postMethod=new HttpPost(serv_ip+"likeupdate2.php?");
				List<NameValuePair>nameValuePairs=new ArrayList<NameValuePair>(3);
				nameValuePairs.add(new BasicNameValuePair("st_id", sh_sid));
				nameValuePairs.add(new BasicNameValuePair("topic", stopic));
				nameValuePairs.add(new BasicNameValuePair("l_u", "like"));
				postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				 response3= hc.execute(postMethod,resp);
				 System.out.println(response3);
					
				}
				catch(Exception e)
				{
					System.out.println("Error"+e);
				}
				
				return null;
				}
				  @Override
				protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					
					if(response3.contains("success"))
					{
						Toast.makeText(getApplicationContext(), "Liked", Toast.LENGTH_SHORT).show();
					}
					else {
						Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
					}
				}
		}

}
