package srt.inz.studentport;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainPage extends Activity {
	
	EditText euid,epwd;
	Button lgb,rgb;
	
	Spinner sp_typ; ArrayAdapter<String> s1;
	String suid,spwd,styp,sh,sreg,sdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        
        euid=(EditText)findViewById(R.id.editText_lgusr);
        epwd=(EditText)findViewById(R.id.editText_lgpwd);
        lgb=(Button)findViewById(R.id.button_mlog);
        rgb=(Button)findViewById(R.id.button_mreg);
        sp_typ=(Spinner)findViewById(R.id.spinner_typ);
        
        String[] typ = getResources().getStringArray(R.array.usertype);
        
        s1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,typ);
	    s1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    sp_typ.setAdapter(s1);
	    sp_typ.setOnItemSelectedListener(new OnItemSelectedListener()
        {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				styp=arg0.getItemAtPosition(arg2).toString();
				((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
			
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub	
			}
        	
        });
        
        lgb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				suid=euid.getText().toString();
				spwd=epwd.getText().toString();
				
			
				
				new user_loginn().execute();
				
			}
		});
        
        rgb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Intent i=new Intent(getApplicationContext(),Student_reg.class);
				startActivity(i);*/
				openDialog();
			}
		});
    }
    public void openDialog(){
	      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	      alertDialogBuilder.setMessage("Select mode of registration");
	      
	      alertDialogBuilder.setPositiveButton("Student", new DialogInterface.OnClickListener() {
	         @Override
	         public void onClick(DialogInterface arg0, int arg1) {
	           
	            Toast.makeText(getApplicationContext(),"Student",Toast.LENGTH_SHORT).show();
	            Intent i=new Intent(getApplicationContext(),Student_reg.class);
				startActivity(i);
	            
	         }
	      });
	      
	      alertDialogBuilder.setNegativeButton("Faculty",new DialogInterface.OnClickListener() {
	         @Override
	         public void onClick(DialogInterface dialog, int which) {
	        	 
	        	 Toast.makeText(getApplicationContext(),"Faculty",Toast.LENGTH_SHORT).show();
	        	 Intent i=new Intent(getApplicationContext(),Staffreg.class);
					startActivity(i);
	        	 //finish();
	         }
	      });
	      
	      AlertDialog alertDialog = alertDialogBuilder.create();
	      alertDialog.show();
	   }
    public class user_loginn extends AsyncTask<String, String, String>
    {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
    		 String urlParameters = null;
	            try {
	                urlParameters =  "username=" + URLEncoder.encode(suid, "UTF-8") + "&&"
	                        + "password=" + URLEncoder.encode(spwd, "UTF-8") + "&&"
	    	                + "type=" + URLEncoder.encode(styp, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            sh = Connectivity.excutePost(Constants.LOGIN_URL,
	                    urlParameters);
	            Log.e("You are at", "" + sh);
	           
			return sh;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			if(sh.contains("success"))
			{
				
				if(styp.contains("Admin"))
				{
					Intent i=new Intent(getApplicationContext(), Admin_home.class);
					startActivity(i);
					Toast.makeText(getApplicationContext(), "Welcome Admin ...", Toast.LENGTH_SHORT).show();
				}
				else
				{
					if(styp.contains("Faculty"))
					{
						
						Intent i=new Intent(getApplicationContext(), Staff_home.class);
						startActivity(i);
					}
					else
					{
						
						Intent i=new Intent(getApplicationContext(), Student_home.class);
						startActivity(i);
					}
					parsingmethod();
					
					Toast.makeText(getApplicationContext(), "Successfully Logged in."+sh, Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Usernmae or Password error.", Toast.LENGTH_SHORT).show();							
			}
			super.onPostExecute(result);
		
		}
		
	}
    
    public void parsingmethod() {
		try
		{
			JSONObject jobject=new JSONObject(sh);
			JSONObject jobject1=jobject.getJSONObject("Event");
			JSONArray ja=jobject1.getJSONArray("Details");
			int length=ja.length();
			for(int i=0;i<length;i++)
			{
	          
				JSONObject c = ja.getJSONObject(i);
	            // Storing  JSON item in a Variable
	            sreg = c.getString("reg_no");
	            sdp = c.getString("department");
	            String fname=c.getString("firstname");
	            String lname=c.getString("lastname");
	            String snam=fname+" "+lname;
	            
	            SharedPreferences sh=getSharedPreferences("skey", MODE_WORLD_READABLE);
				SharedPreferences.Editor ed=sh.edit();
				ed.putString("suid", suid);
				ed.putString("sreg", sreg);
				ed.putString("sdp", sdp);
				ed.putString("snam", snam);
				ed.commit();
        
	       	               }
		}
	        catch (JSONException e) {
	          e.printStackTrace();
	        }
	       }
}

