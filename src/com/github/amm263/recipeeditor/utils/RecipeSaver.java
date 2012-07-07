package com.github.amm263.recipeeditor.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Base64;

import com.github.amm263.recipeeditor.Ingredient;
import com.github.amm263.recipeeditor.Recipe;

public class RecipeSaver {
	
	private Recipe recipe;
	ArrayList<String> buffer;
	
	public RecipeSaver(Recipe rec)
	{
		recipe= rec;
	}
	
	public Boolean saveOnSD() throws IOException
	{
		int i;
		writeHead();
		writeBody();
		File recipeDirectory = new File(Environment.getExternalStorageDirectory()+"/Recipe Editor/recipes/");
		recipeDirectory.mkdirs();
		File recipeFile = new File(recipeDirectory, recipe.getName()+".html");
		FileOutputStream fos = new FileOutputStream(recipeFile);
        if (recipeDirectory.canWrite())
        {	
	        for (i=0;i<buffer.size();i++)
	        {
	        	fos.write(buffer.get(i).getBytes());
	        }
        }
        fos.close();
        buffer.clear();
		return Boolean.TRUE;
	}
	
	private void writeHead()
	{
		buffer= new ArrayList<String>();
		// <version>
		buffer.add("<!-- Version 1 -->\n");
		buffer.add("<!-- DO NOT EDIT THIS FILE -->\n");
		// </version>
		buffer.add("<html>\n");
		buffer.add("\t<head>\n");
		buffer.add("\t\t<meta charset=\"UTF-8\" />\n");
		buffer.add("\t\t<title>"+recipe.getName()+"</title>\n");
		// <css>
		writeCss();
		// </css>
		buffer.add("\t</head>\n\n");
	}
	
	private void writeCss()
	{
		buffer.add("\t\t<!-- CSS -->\n");
		buffer.add("\t\t<style>\n");
		//body
		buffer.add("\t\t\tbody{\n\t\t\t\tmargin: 0px;\n\t\t\t\tpadding: 0px;\n\t\t\t\tfont-family: verdana, arial, helvetica, sans-serif;\n\t\t\t\tfont-size: 12px;\n\t\t\t\tline-height: 22px;\n\t\t\t\tcolor: #000000;\n\t\t\t\tbackground-color: #ffffff;\n\t\t\t}\n\n");
		//H1 - p
		buffer.add("\t\t\th1{\n\t\t\t\tmargin: 0px;\n\t\t\t\tpadding: 5px;\n\t\t\t\tfont-size: 32px;\n\t\t\t}\n\n\t\t\tp{\n\t\t\t\tmargin: 0px;\n\t\t\t\tpadding: 5px;\n\t\t\t}\n\n");
		// a:link - a:visited
		buffer.add("\t\t\ta:link{\n\t\t\t\tcolor: black;\n\t\t\t}\n\n");
		//Container
		buffer.add("\t\t\t#Container{\n\t\t\t\tposition: absolute;\n\t\t\t\tmargin: 0px;\n\t\t\t}\n\n");
		//Image
		buffer.add("\t\t\t\n\n");
		//Header
		buffer.add("\t\t\t\n\n");
		//Ingredients
		buffer.add("\t\t\t\n\n");
		//Recipe
		buffer.add("\t\t\t\n\n");		
		buffer.add("\t\t</style>\n");
	}
	
	private void writeBody()
	{
		buffer.add("\t<body>\n");
		//Header
		buffer.add("\t\t<div id=\"Header\">\n");
		buffer.add("\t\t\t<h1>"+recipe.getName()+"</h1>\n\n");
		buffer.add("\t\t</div>\n");
		//Start Container
		buffer.add("\t\t<div id=\"Container\">\n");
		//Image
		buffer.add("\t\t\t<div id=\"Image\">\n");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		recipe.getImage().compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] imageByte = baos.toByteArray();
		buffer.add("\t\t\t\t<img src=\"data:image/png;base64,"+Base64.encodeToString(imageByte, Base64.DEFAULT)+"\" />\n");
		buffer.add("\t\t\t</div>\n");
		//Ingredients
		buffer.add("\t\t\t<div id=\"Ingredients\">\n");
		//Number of People
		buffer.add("\t\t\t\t<strong><p>People: "+recipe.getPeople().toString()+"</p></strong>\n\n");
		buffer.add("\t\t\t\t<strong><p>Ingredients:</p></strong>\n\n");
		buffer.add("\t\t\t\t<ul>\n");
		ArrayList<Ingredient> ingredients = recipe.getIngredients();
		for(int i=0;i<ingredients.size();i++)
		{
			buffer.add("\t\t\t\t\t<li>"+ingredients.get(i).getIngredient()+" : "+ingredients.get(i).getQuantity()+" "+ingredients.get(i).getUnitMeasure()+"</li>\n");
		}
		buffer.add("\t\t\t\t</ul>\n");
		buffer.add("\t\t\t</div>\n");
		//Recipe
		buffer.add("\t\t\t<div id=\"Recipe\">\n");
		buffer.add("\t\t\t\t<strong><p>Description</p></strong>\n");
		buffer.add("\t\t\t\t<p>\n");
		for(int i=0;i<recipe.getDescription().length;i++)
		{
			buffer.add("\t\t\t\t"+recipe.getDescription()[i]+"<br />\n");
		}
		buffer.add("\t\t\t\t</p>\n");
		buffer.add("\t\t\t</div>\n");
		//End Container
		buffer.add("\t\t</div>\n");
		buffer.add("\t</body>\n");
		buffer.add("</html>");
	}
}
