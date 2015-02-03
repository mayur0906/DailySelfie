package com.mayurmuralidharapps.dailyselfie;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelfieAdapter extends BaseAdapter {

	private ArrayList<SelfieInfo> list = new ArrayList<SelfieInfo>();
	private static LayoutInflater inflater = null;
	private Context mContext;
	//int finalHeight,finalWidth;
	//ImageView view;
	
	/*private Bitmap setPic(String mCurrentPhotoPath) {
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

	    return BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
	    //mImageView.setImageBitmap(bitmap);
	}*/
	
	public SelfieAdapter(Context context) {
		mContext = context;
		inflater = LayoutInflater.from(mContext);
	}
	
	public void addall()
	{
	    File storageDir =  new File(Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES)+"/DailySelfie");
	    if(storageDir.exists())
	    {
	    	//File f = new File(storageDir);        
	    	File file[] = storageDir.listFiles();
	    	Log.d("Files", "Size: "+ file.length);
	    	for (int i=file.length - 1; i >=0 ; i--)
	    	{
	    	    Log.d("Files", "FileName:" + file[i].getName());
	    	    add(new SelfieInfo(file[i].getName().substring(0, file[i].getName().length() - 4),
	    	    		BitmapFactory.decodeFile(file[i].getAbsolutePath()),
	    	    		file[i].getAbsolutePath()));
	    	}
	    }
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	static class ViewHolder{
		ImageView thumbNail;
		TextView SelfieNameTV;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		View newView = arg1;
		ViewHolder holder = new ViewHolder();
		
		SelfieInfo curr = list.get(arg0);
		
		if(arg1 == null)
		{
			holder = new ViewHolder();
			newView = inflater.inflate(R.layout.selfie_entry_layout, null);
			holder.thumbNail = (ImageView) newView.findViewById(R.id.thumb_view);
			//view = holder.thumbNail;			
			holder.SelfieNameTV = (TextView) newView.findViewById(R.id.snap_name_view);
			newView.setTag(holder);
		}
		
		else
		{
			holder = (ViewHolder) newView.getTag();
		}
		
		/*ViewTreeObserver vto = view.getViewTreeObserver(); 
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
		    @SuppressWarnings("deprecation")
			@Override 
		    public void onGlobalLayout() { 
		        view.getViewTreeObserver().removeGlobalOnLayoutListener(this); 
		        finalWidth  = view.getMeasuredWidth();
		        finalHeight = view.getMeasuredHeight(); 
		        Log.i("Info", "W:"+finalWidth+"H:"+finalHeight);
		        //setPic();

		    } 
		});*/
		
		//Bitmap b = Bitmap.createScaledBitmap(curr.getThumb(), finalWidth, finalHeight, false);
		holder.thumbNail.setImageBitmap(curr.getThumb());
		holder.SelfieNameTV.setText(curr.getSelfieName());
		return newView;
	}
	
	public void add(SelfieInfo newItem)
	{
		list.add(newItem);
		notifyDataSetChanged();
	}
	
	public ArrayList<SelfieInfo> getList()
	{
		return list;
	}

}
