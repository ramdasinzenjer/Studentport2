package srt.inz.studentport;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class Download_files extends Activity{

	// button to show progress dialog
//		Button btnShowProgress;
		
		// Progress Dialog
		private ProgressDialog pDialog;
		ImageView my_image;
		// Progress dialog type (0 - for Horizontal progress bar)
		public static final int progress_bar_type = 0; 
		
		ListView mlistview;
		ListAdapter adapter;
		ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
		String response,sname,sdat;
		String value;
		// File url to download
		private static String file_url = Constants.BASE_URL+"uploads/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_files);
		mlistview=(ListView)findViewById(R.id.mylistvw);
		
		
		// show progress bar button
		//		btnShowProgress = (Button) findViewById(R.id.btnProgressBar);
				// Image view to show image after downloading
				my_image = (ImageView) findViewById(R.id.my_image);
				/**
				 * Show Progress bar click event
				 * */
				
				new ret_fileitem().execute();
				/*btnShowProgress.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// starting new Async Task
					//	new DownloadFileFromURL().execute(file_url);
					}
				});*/
			}

			/**
			 * Showing Dialog
			 * */
			@Override
			protected Dialog onCreateDialog(int id) {
				switch (id) {
				case progress_bar_type:
					pDialog = new ProgressDialog(this);
					pDialog.setMessage("Downloading file. Please wait...");
					pDialog.setIndeterminate(false);
					pDialog.setMax(100);
					pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					pDialog.setCancelable(true);
					pDialog.show();
					return pDialog;
				default:
					return null;
				}
			}

			/**
			 * Background Async Task to download file
			 * */
			class DownloadFileFromURL extends AsyncTask<String, String, String> {

				/**
				 * Before starting background thread
				 * Show Progress Bar Dialog
				 * */
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					showDialog(progress_bar_type);
				}

				/**
				 * Downloading file in background thread
				 * */
				@Override
				protected String doInBackground(String... f_url) {
					int count;
			        try {
			            URL url = new URL(f_url[0]);
			            URLConnection conection = url.openConnection();
			            conection.connect();
			            // getting file length
			            int lenghtOfFile = conection.getContentLength();

			            // input stream to read file - with 8k buffer
			            InputStream input = new BufferedInputStream(url.openStream(), 8192);
			            
			            // Output stream to write file
			            OutputStream output = new FileOutputStream("/sdcard/"+value);

			            byte data[] = new byte[1024];

			            long total = 0;

			            while ((count = input.read(data)) != -1) {
			                total += count;
			                // publishing the progress....
			                // After this onProgressUpdate will be called
			                publishProgress(""+(int)((total*100)/lenghtOfFile));
			                
			                // writing data to file
			                output.write(data, 0, count);
			            }

			            // flushing output
			            output.flush();
			            
			            // closing streams
			            output.close();
			            input.close();
			            
			        } catch (Exception e) {
			        	Log.e("Error: ", e.getMessage());
			        }
			        
			        return null;
				}
				
				/**
				 * Updating progress bar
				 * */
				protected void onProgressUpdate(String... progress) {
					// setting progress percentage
		            pDialog.setProgress(Integer.parseInt(progress[0]));
		       }

				/**
				 * After completing background task
				 * Dismiss the progress dialog
				 * **/
				@Override
				protected void onPostExecute(String file_url) {
					// dismiss the dialog after the file was downloaded
					dismissDialog(progress_bar_type);
					Toast.makeText(getApplicationContext(), "Download completed.", Toast.LENGTH_SHORT).show();
					// Displaying downloaded image into image view
					// Reading image path from sdcard
				//	String imagePath = Environment.getExternalStorageDirectory().toString() + "/ett_log.txt";
					// setting downloaded into image view
				//	my_image.setImageDrawable(Drawable.createFromPath(imagePath));
				}

			}
			
			public class ret_fileitem extends AsyncTask<String, String, String>
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
			                urlParameters =  "" ;
			            } catch (Exception e) {
			                // TODO Auto-generated catch block
			                e.printStackTrace();
			            }

			            response = Connectivity.excutePost(Constants.DOWNLOADITEM_URL,
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
					mlistview.setAdapter(null);
					for(int i=0;i<length;i++)
					{
			          
						JSONObject c = ja.getJSONObject(i);
			            // Storing  JSON item in a Variable
			            sname = c.getString("f_name");
			           sdat=c.getString("f_date");
			           
			            // Adding value HashMap key => value
			            HashMap<String, String> map = new HashMap<String, String>();
			            map.put("f_name", sname);
			            map.put("f_date", sdat);
			            
			            map.put("show", sname+"\n Updated on date : "+sdat+".");
			            //Toast.makeText(getApplicationContext(), ""+sname,Toast.LENGTH_SHORT).show();
			            oslist.add(map);
			            
			            adapter = new SimpleAdapter(getApplicationContext(), oslist,
			                R.layout.singleitem,
			                new String[] {"show"}, new int[] {R.id.textsingle});
			            mlistview.setAdapter(adapter);
			            mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			               @Override
			               public void onItemClick(AdapterView<?> parent, View view,
			                                            int position, long id) {               
			              // Toast.makeText(getApplicationContext(), " "+oslist.get(+position).get("topic"), Toast.LENGTH_SHORT).show();
			               
			               value=oslist.get(+position).get("f_name");
			               String value2=oslist.get(+position).get("f_date");
			               
			               openDialog();
			               			                          
			               }
			                });
			            }
				}
			        catch (JSONException e) {
			          e.printStackTrace();
			        }
			       }
			
			 public void openDialog(){
			      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			      alertDialogBuilder.setMessage("Do you want to download the file ?");
			      alertDialogBuilder.setTitle("Download !!!");
			      alertDialogBuilder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
			         @Override
			         public void onClick(DialogInterface arg0, int arg1) {
			           
			        	 new DownloadFileFromURL().execute(file_url+value);  
			           
			            
			         }
			      });
			      
			      alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			         @Override
			         public void onClick(DialogInterface dialog, int which) {
			        	 
			        	 Toast.makeText(getApplicationContext(),"Cancenlled.",Toast.LENGTH_SHORT).show();
			        	 //finish();
			         }
			      });
			      
			      AlertDialog alertDialog = alertDialogBuilder.create();
			      alertDialog.show();
			   }
	}