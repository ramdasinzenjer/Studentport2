package srt.inz.studentport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Staff_home extends Activity{
	
ImageView bh,bp,be,bc,dn,uf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.staff_home);
		bh=(ImageView)findViewById(R.id.bhome1);
		bp=(ImageView)findViewById(R.id.bprofile1);
		be=(ImageView)findViewById(R.id.bevents1);
		bc=(ImageView)findViewById(R.id.bcourse1);
		dn=(ImageView)findViewById(R.id.imgDown1);
		uf=(ImageView)findViewById(R.id.imgUp1);
		
		dn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Download_files.class);
				startActivity(i);
				
			}
		});
		uf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Upfile_notes.class);
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
