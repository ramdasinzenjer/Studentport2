package srt.inz.studentport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Student_home extends Activity{
	
	 ImageView bh,bp,be,bc,dn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_home);
		bh=(ImageView)findViewById(R.id.bhome);
		bp=(ImageView)findViewById(R.id.bprofile);
		be=(ImageView)findViewById(R.id.bevents);
		bc=(ImageView)findViewById(R.id.bcourse);
		dn=(ImageView)findViewById(R.id.imgDown);
		
		
		dn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Download_files.class);
				startActivity(i);
				
			}
		});
		bh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Shome.class);
				startActivity(i);
				
			}
		});
		bp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Sprofile.class);
				startActivity(i);
				
			}
		});
		be.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),S_Events.class);
				startActivity(i);
				
			}
		});
		bc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Courses.class);
				startActivity(i);
				
			}
		});
	}

}
