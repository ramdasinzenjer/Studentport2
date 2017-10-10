package srt.inz.studentport;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;

import android.app.Activity;
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

@SuppressWarnings("deprecation")
public class Add_courses extends Activity{
	
	Spinner spd;	ArrayAdapter<String> s1; Button bad;
	
	String sdep,scnam,scdes,sdur,sfee,hos; EditText ecnam,ecdes,edur,efee;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_course);
		spd=(Spinner)findViewById(R.id.sp_depart);
		ecnam=(EditText)findViewById(R.id.edit_cnam);
		ecdes=(EditText)findViewById(R.id.edit_cdescr);
		edur=(EditText)findViewById(R.id.edit_cdur);
		efee=(EditText)findViewById(R.id.edit_cfee);
		bad=(Button)findViewById(R.id.bc_add);
		
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
				((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
						
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub	
			}
        	
        });
	    bad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				scnam=ecnam.getText().toString();
				scdes=ecdes.getText().toString();
				sdur=edur.getText().toString();
				sfee=efee.getText().toString();
				
				new crs_update().execute();
			}
		});
			
		
	}
	
	public class crs_update extends AsyncTask<String, String, String>
	{
	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String urlParameters = null;
            try {
                urlParameters =  "c_name=" + URLEncoder.encode(scnam, "UTF-8") + "&&"
                        + "c_descr=" + URLEncoder.encode(scdes, "UTF-8") + "&&"
                        +"duration=" + URLEncoder.encode(sdur, "UTF-8") + "&&"	                        
                        + "fee=" + URLEncoder.encode(sfee, "UTF-8") + "&&"
                        +"dept=" + URLEncoder.encode(sdep, "UTF-8") ;
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            hos = Connectivity.excutePost(Constants.COURSEUPDATE_URL,
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
				Toast.makeText(getApplicationContext(), "Course details updated.", Toast.LENGTH_SHORT).show();	
				m_refresh();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Error"+hos, Toast.LENGTH_SHORT).show();
				Log.e("Database error :", "Update course error");
				
			}	
		}
		
		
	}
	
	public void m_refresh()
	  {
		  finish();
		  startActivity(getIntent());
	  }
	

}
