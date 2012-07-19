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

import com.github.amm263.recipeeditor.Ingredient;
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
			//NAME
			if (buffer.get(j).contains("<title>"))
			{
				recipe.setName(buffer.get(j).substring(buffer.get(j).indexOf("<title>")+7, buffer.get(j).indexOf("</title>")));
			}
			//IMAGE
			else if (buffer.get(j).contains("<img src="))
			{
				StringBuffer buf = new StringBuffer(500);
				buf.append(buffer.get(j).substring(buffer.get(j).indexOf("base64,")+7));
				j++;
				int i=j;
				while(!buffer.get(i).contains("/>"))
				{
					buf.append(buffer.get(i));
					i++;
				}
				byte[] imageAsBytes;
				String imageString= buf.toString();
				buf= new StringBuffer();
				imageAsBytes= Base64.decode(imageString, Base64.DEFAULT);
				imageString="";
				Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
				recipe.setImage(bmp);
			}
			//People
			else if (buffer.get(j).contains("<strong><p>People"))
			{
				recipe.setPeople(Integer.valueOf(buffer.get(j).subSequence(buffer.get(j).indexOf("People: ")+8, buffer.get(j).indexOf("</p>")).toString()));
			}
			//Ingredients
			else if (buffer.get(j).contains("<strong><p>Ingredients"))
			{
				while(!buffer.get(j).contains("</ul>"))
				{
					if (buffer.get(j).contains("<li>"))
					{
						int counter= 0;
						String[] ingredientValues= buffer.get(j).split(" ");
						String ingredientName= ingredientValues[counter].replaceAll("<li>", "").replaceAll("\t\t\t\t\t", "");
						counter++;
						while(!ingredientValues[counter].equals(":"))
						{
							ingredientName= ingredientName+" "+ingredientValues[counter];
							counter++;
						}
						counter++;
						float ingredientQuantity= Float.valueOf(ingredientValues[counter]);
						counter++;
						String ingredientMeasure= ingredientValues[counter].replaceAll("</li>", "");
						Ingredient ingredient = new Ingredient(ingredientName,ingredientQuantity,ingredientMeasure);
						recipe.addIngredient(ingredient);
					}
					j++;
				}
			}
			//Description
			else if (buffer.get(j).contains("<strong><p>Description"))
			{
				ArrayList<String> description= new ArrayList<String>();
				j++;
				while(!buffer.get(j).contains("</p>"))
				{
					if (buffer.get(j).contains("<br"))
					{
						description.add(buffer.get(j).replaceAll("\t", "").replaceAll("<br />", ""));
					}
					j++;
				}
				String[] descArray= new String[description.size()];
				for (int i=0;i<descArray.length;i++)
				{
					descArray[i]=description.get(i);
				}
				recipe.setDescription(descArray);
			}
		}
	}
}
