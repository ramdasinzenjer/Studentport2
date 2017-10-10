package srt.inz.studentport;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Sprofile extends Activity{
	
	EditText etfn,etln,etem,etun,etpwd;
	Button bup;	Spinner sp_dpt; ArrayAdapter<String> s1;
	String sfn,sln,sem,sun,spwd,hos,sh_un,response;
	TextView tv_click;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sprofile);
		
		etfn=(EditText)findViewById(R.id.editText_fname1);
		etln=(EditText)findViewById(R.id.editText_lname1);
		etem=(EditText)findViewById(R.id.editText_email1);
		etun=(EditText)findViewById(R.id.editText_username1);
		etpwd=(EditText)findViewById(R.id.editText_password1);
		tv_click=(TextView)findViewById(R.id.textViewhead1);
		bup=(Button)findViewById(R.id.button_create1);

		SharedPreferences share=getSharedPreferences("skey", MODE_WORLD_READABLE);
		sh_un=share.getString("suid", "");
		  etun.setText(sh_un);
		  
		  etfn.setEnabled(false);
			etln.setEnabled(false);
			etun.setEnabled(false);
			etpwd.setEnabled(false);
			etem.setEnabled(false);
			bup.setEnabled(false);
			tv_click.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					etfn.setEnabled(true);
					etln.setEnabled(true);
					etun.setEnabled(true);
					etpwd.setEnabled(true);
					etem.setEnabled(true);
					bup.setEnabled(true);
					
				
				}
			});
		  
		  new usr_det().execute();
		    bup.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					sfn=etfn.getText().toString();
					sln=etln.getText().toString();
					sem=etem.getText().toString();
					spwd=etpwd.getText().toString();
					sun=etun.getText().toString();
					
					new update_usr().execute();				
					
				}
			});
		
	}
	public class update_usr extends AsyncTask<String, String, String>
    {
    	

    		@Override
    		protected String doInBackground(String... arg0) {
    			// TODO Auto-generated method stub
    			String urlParameters = null;
	            try {
	                urlParameters =  "firstname=" + URLEncoder.encode(sfn, "UTF-8") + "&&"
	                        + "email=" + URLEncoder.encode(sem, "UTF-8") + "&&"
	                        +"lastname=" + URLEncoder.encode(sln, "UTF-8") + "&&"	                        
	                        + "username=" + URLEncoder.encode(sun, "UTF-8") + "&&"
	                        +"password=" + URLEncoder.encode(spwd, "UTF-8") + "&&"
	                        +"shid=" + URLEncoder.encode(sh_un, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            hos = Connectivity.excutePost(Constants.PROFUP_URL,
	                    urlParameters);
	            Log.e("You are at", "" + hos);
	       return hos;
    		}
    		@Override
    		protected void onPostExecute(String result) {
    			// TODO Auto-generated method stub
    			if(hos.contains("success"))
    			{
    				Toast.makeText(getApplicationContext(), "Successfully updated.", Toast.LENGTH_SHORT).show();	
    				
    				Intent i=new Intent(getApplicationContext(),Student_home.class);
    				startActivity(i);
    				finish();
    			}
    			else
    			{
    				Toast.makeText(getApplicationContext(), "Updation error. ", Toast.LENGTH_LONG).show();
    			}
    			super.onPostExecute(result);
    			
    	
    }
    	}
	
	public class usr_det extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			 String urlParameters = null;
	            try {
	                urlParameters =  "username=" + URLEncoder.encode(sh_un, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            response = Connectivity.excutePost(Constants.STUDENTDETAILS_URL,
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
				for(int i=0;i<length;i++)
				{
		           
					JSONObject c = ja.getJSONObject(i);
		            // Storing  JSON item in a Variable
		            sfn=c.getString("firstname");
		            sln=c.getString("lastname");
		            sun=c.getString("username");
		            spwd=c.getString("password");
		            sem=c.getString("email");
		            
		            etfn.setText(sfn);
		            etln.setText(sln);
		            etun.setText(sun);
		            etpwd.setText(spwd);
		            etem.setText(sem);
		         
		            }
			}
		        catch (JSONException e) {
		          e.printStackTrace();
		        }
		       }

}
