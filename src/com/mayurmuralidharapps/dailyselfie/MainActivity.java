package com.mayurmuralidharapps.dailyselfie;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	
	private SelfieAdapter mAdapter;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	private File currPic = null;
	protected String DIR_KEY = "File Directory";
	
	private static final long INITIAL_ALARM_DELAY = 2 * 60 * 1000L;
	
	private AlarmManager mAlarmManager;
	private Intent mNotificationReceiverIntent; 
	private PendingIntent mNotificationReceiverPendingIntent ;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		ListView mSelfieList = getListView();
		mAdapter = new SelfieAdapter(getApplicationContext());
		setListAdapter(mAdapter);
		mSelfieList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				SelfieInfo s = (SelfieInfo) mAdapter.getItem(arg2);	
				Intent i = new Intent(MainActivity.this,ImgViewActivity.class);
				i.putExtra(DIR_KEY,s.getFileNameView());
				startActivity(i);
			}			
		});
		
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		mNotificationReceiverIntent = new Intent(MainActivity.this,
					NotificationReciever.class);
		mNotificationReceiverPendingIntent= PendingIntent.getBroadcast(
				MainActivity.this, 0, mNotificationReceiverIntent, 0);
		mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
				SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY,
				INITIAL_ALARM_DELAY,
				mNotificationReceiverPendingIntent);
		Log.i("Alarm","Alarm Set");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private File createImageFile() throws IOException {
	    // Create an image file name
	    
		
		String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
	    String imageFileName = "Selfie_" + timeStamp ;
	    File storageDir =  new File(Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES)+"/DailySelfie");
	    
	    
	    Log.i("Self", storageDir.toURI().toString());
	    Log.i("Self", "Dir exists : "+storageDir.isDirectory());
	    
	    
	    if(storageDir.mkdirs()||storageDir.isDirectory())
	    {
	    	File image = File.createTempFile(
	    	        imageFileName,  /* prefix */
	    	        ".jpg",         /* suffix */
	    	        storageDir      /* directory */
	    	    );
	    	return image;
	    }
	    else
	    {
	    	Log.i("Self", "File creation error");
	    	Toast.makeText(getApplicationContext(), "Please insert SD Card to use this app", Toast.LENGTH_LONG).show();
	    	return null;
	    }    
	    
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			View messageView = getLayoutInflater().inflate(R.layout.about, null,false);
			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setIcon(R.drawable.ic_launcher_selfieapp);
		        builder.setTitle(R.string.app_name);
		        builder.setView(messageView);
		        builder.create();
		        builder.show();
			return true;
		}
		if (id == R.id.camera)
		{
			Intent ClickPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			if(ClickPicture.resolveActivity(getPackageManager())!=null)
			{
				
		        try {
		            currPic = createImageFile();
		        } catch (IOException ex) {
		            // Error occurred while creating the File
		            ex.printStackTrace();
		        }
		        
		        if(currPic != null)
		        {
		        	ClickPicture.putExtra(MediaStore.EXTRA_OUTPUT,
		                    Uri.fromFile(currPic));
		        	Log.i("Selfie", Uri.fromFile(currPic).toString());
		        	startActivityForResult(ClickPicture, REQUEST_IMAGE_CAPTURE);
					return true;
		        }
				
			}
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK  && data == null) {
	        
	    	//Bundle extras = data.getExtras();
	        
	    	String SelfieDir = currPic.getAbsolutePath();
	    	String SelfieName = currPic.getName().substring(0, currPic.getName().length() - 4);
	    	Bitmap thumbBitmap = BitmapFactory.decodeFile(SelfieDir);
	                
	        
	        //SelfieInfo newSelfie = ;
	        mAdapter.add(new SelfieInfo(SelfieName, thumbBitmap, SelfieDir));
	        setListAdapter(mAdapter);
	        
	    }
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(mAdapter.getCount()==0)
			mAdapter.addall();
		
	}
	
}
