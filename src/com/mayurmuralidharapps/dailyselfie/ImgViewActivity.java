package com.mayurmuralidharapps.dailyselfie;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

public class ImgViewActivity extends Activity {

	
	public String DIR_KEY = "File Directory";
	public String mCurrentPhotoPath;
	ImageView view;
	int finalHeight,finalWidth;
	
	private void setPic() {
	    // Get the dimensions of the View
	    int targetW = finalWidth;
	    int targetH = finalHeight;

	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;

	    // Determine how much to scale down the image
	    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;

	    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
	    view.setImageBitmap(bitmap);
	}
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_img_view);
		view = (ImageView) findViewById(R.id.img);
		Intent in = getIntent();
		mCurrentPhotoPath = in.getStringExtra(DIR_KEY);
		Log.i("View", mCurrentPhotoPath);
		
		ViewTreeObserver vto = view.getViewTreeObserver(); 
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
		    @SuppressWarnings("deprecation")
			@Override 
		    public void onGlobalLayout() { 
		        view.getViewTreeObserver().removeGlobalOnLayoutListener(this); 
		        finalWidth  = view.getMeasuredWidth();
		        finalHeight = view.getMeasuredHeight(); 
		        Log.i("Info", "W:"+finalWidth+"H:"+finalHeight);
		        setPic();

		    } 
		});
		
	    
		
		
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.img_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
