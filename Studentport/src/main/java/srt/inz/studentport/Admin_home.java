package srt.inz.studentport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Admin_home extends Activity{
	
	 ImageView bvau,buev,bcr,bupfile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_home);
		bvau=(ImageView)findViewById(R.id.button_vauser);
		buev=(ImageView)findViewById(R.id.button_vuevents);
		bcr=(ImageView)findViewById(R.id.button_vucourses);
		bupfile=(ImageView)findViewById(R.id.imageView_Upfile);
				
		bvau.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Approve_user.class);
				startActivity(i);
				
			}
		});
		buev.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Add_events.class);
				startActivity(i);
				
			}
		});
		bcr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Add_courses.class);
				startActivity(i);
				
			}
		});
		bupfile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Upfile_notes.class);
				startActivity(i);
				
			}
		});
	}

}
