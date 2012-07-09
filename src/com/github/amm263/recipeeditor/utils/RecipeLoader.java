package com.github.amm263.recipeeditor.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import com.github.amm263.recipeeditor.Recipe;

public class RecipeLoader {

	ArrayList<String> buffer;
	
	public void loadRecipe(String name)
	{
		readFile(name);
	}
	
	public RecipeLoader()
	{
		
	}
	
	private void readFile(String name)
	{
		Recipe recipe= Recipe.getInstance();
		buffer= new ArrayList<String>();
		File sdcard = Environment.getExternalStorageDirectory();
		File file = new File(sdcard,"/Recipe Editor/recipes/"+name+".html");
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) !=null)
			{
				buffer.add(line);
			}
			br.close();
		}
		catch(IOException e)
		{
			//Popup error?
		}
		for(int j=0;j<buffer.size();j++)
		{
			if (buffer.get(j).contains("<title>"))
			{
				recipe.setName(buffer.get(j).substring(buffer.get(j).indexOf("<title>")+7, buffer.get(j).indexOf("</title>")));
			}
			else if (buffer.get(j).contains("<img src="))
			{
				StringBuffer buf = new StringBuffer(500);
				buf.append(buffer.get(j).substring(buffer.get(j).indexOf("base64,")+7));
				j++;
				int i=j;
				int oldJ=j;
				while(!buffer.get(i).contains("/>"))
				{
					//if(i==oldJ+300)
					//{
						//buffer.subList(oldJ, oldJ+i).clear();
						//i=oldJ;
						//j-=300;
					//}
					buf.append(buffer.get(i));
					i++;
					//j++;
				}
				byte[] imageAsBytes;
				String imageString= buf.toString();
				buf= new StringBuffer();
				imageAsBytes= Base64.decode(imageString, Base64.DEFAULT);
				imageString="";
				Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
				recipe.setImage(bmp);
			}
		}
	}
}
