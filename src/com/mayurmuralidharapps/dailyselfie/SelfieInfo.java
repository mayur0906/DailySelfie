package com.mayurmuralidharapps.dailyselfie;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SelfieInfo {
	private String SelfieName;
	private Bitmap thumb;
	private String FileNameView;
	
	public SelfieInfo(String name, Bitmap thumbnail, String filePath)
	{
		this.SelfieName = name;
		this.thumb = thumbnail;
		this.FileNameView = filePath;
	}
	
	/*public SelfieInfo(File selfie)
	{
		this.SelfieName = selfie.getName().substring(0, selfie.getName().length() - 4);
		this.FileNameView = selfie.getAbsolutePath();
		this.thumb = BitmapFactory.decodeFile(FileNameView);		
	}*/
	
	public SelfieInfo()
	{
		
	}
	
	public String getSelfieName()
	{
		return SelfieName;
	}
	
	public void setSelfieName(String path)
	{
		this.SelfieName = path;
	}
	
	public Bitmap getThumb()
	{
		return thumb;
	}
	
	public void setThumb(Bitmap nail)
	{
		this.thumb = nail;
	}
	
	public String getFileNameView()
	{
		return FileNameView;
	}
	
	public void setFileNameView(String file)
	{
		this.FileNameView = file;
	}
}
