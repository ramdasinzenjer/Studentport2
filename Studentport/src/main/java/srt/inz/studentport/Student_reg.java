package srt.inz.studentport;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;


public class Student_reg extends Activity{
	
	EditText etfn,etln,etem,etdob,etun,etpwd,ereg;
	Button bcn,bcr;	Spinner sp_dpt; ArrayAdapter<String> s1;
	String sfn,sln,sem,sdob,sun,spwd,sdpt,hos,sreg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testxml);
		etfn=(EditText)findViewById(R.id.editText_fname);
		etln=(EditText)findViewById(R.id.editText_lname);
		etem=(EditText)findViewById(R.id.editText_email);
		etdob=(EditText)findViewById(R.id.editText_dob);
		etun=(EditText)findViewById(R.id.editText_username);
		etpwd=(EditText)findViewById(R.id.editText_password);
		ereg=(EditText)findViewById(R.id.editText_rgno);
		bcn=(Button)findViewById(R.id.button_cancel);
		bcr=(Button)findViewById(R.id.button_create);
		sp_dpt=(Spinner)findViewById(R.id.spinner_dpmnt);
		
		 String[] dpt = getResources().getStringArray(R.array.department);
	        
	        s1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dpt);
		    s1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    sp_dpt.setAdapter(s1);
		    sp_dpt.setOnItemSelectedListener(new OnItemSelectedListener()
	        {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					sdpt=arg0.getItemAtPosition(arg2).toString();
					//((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
				
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub	
				}
	        	
	        });
		    bcn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(getApplicationContext(),MainPage.class);
					startActivity(i);
					finish();
				}
			});
		    bcr.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					sfn=etfn.getText().toString();
					sln=etln.getText().toString();
					sem=etem.getText().toString();
					spwd=etpwd.getText().toString();
					sun=etun.getText().toString();
					sdob=etdob.getText().toString();
					sreg=ereg.getText().toString();
					
				if(sfn.isEmpty()||sln.isEmpty()||sem.isEmpty()||spwd.isEmpty()||sun.isEmpty()||sdob.isEmpty()||sreg.isEmpty())
				{
					Toast.makeText(getApplicationContext(), "Empty fields detected", Toast.LENGTH_SHORT).show();
				}
				else	
				{
						if(sem.indexOf("@")!=-1)
				    {
				       if(isValidFormat("yyyy-MM-dd", sdob)){
				    	   
				       
				    	   new reg_usr().execute();
				       }
				       else{
				    	   etdob.setError("Please enter valid DOB");
				       }
				    }
				    else
				    {
				    	etem.setError("Please enter valid mail id");
				    }
					
				}				
					}
			});
	}
	public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

	
	public class reg_usr extends AsyncTask<String, String, String>
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
	                        + "reg_no=" + URLEncoder.encode(sreg, "UTF-8") + "&&"
	                        +"department=" + URLEncoder.encode(sdpt, "UTF-8") + "&&"
	                        +"u_typ=" + URLEncoder.encode("Student", "UTF-8") + "&&"
	                        + "dob=" + URLEncoder.encode(sdob, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            hos = Connectivity.excutePost(Constants.STUDENTREGISTER_URL,
	                    urlParameters);
	            Log.e("You are at", "" + hos);
	       return hos;
    			
    		}
    		@Override
    		protected void onPostExecute(String result) {
    			// TODO Auto-generated method stub
    			if(hos.contains("success"))
    			{
    				Toast.makeText(getApplicationContext(), "Successfully registered. Please log in.", Toast.LENGTH_LONG).show();	
    				Intent i=new Intent(getApplicationContext(),MainPage.class);
    				startActivity(i);
    			}
    			else
    			{
    				Toast.makeText(getApplicationContext(), "Registration error. ", Toast.LENGTH_LONG).show();
    			}
    			super.onPostExecute(result);
    			
    	
    }
    	}
}
