package com.github.amm263.recipeeditor.utils;

import java.io.File;
import java.util.ArrayList;

import android.os.Environment;

public class RecipeListLoader {
	
	ArrayList<String> list;
	
	public RecipeListLoader()
	{
		list = new ArrayList<String>();
	}
	
	public ArrayList<String> getList()
	{
		list.clear();
		buildList();
		return list;
	}
	
	private void buildList()
	{
		File sdCardRoot = Environment.getExternalStorageDirectory();
		File directory = new File(sdCardRoot, "/Recipe Editor/recipes");
		for (File f : directory.listFiles()) {
		    if (f.isFile())
		        list.add(f.getName().substring(0, f.getName().length()-5));
		}
	}

}
