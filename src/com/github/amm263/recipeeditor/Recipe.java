package com.github.amm263.recipeeditor;

import java.io.IOException;
import java.util.ArrayList;
import android.graphics.Bitmap;
import com.github.amm263.recipeeditor.utils.RecipeSaver;

public class Recipe{

	private static Recipe instance = null;
	private String[] description;
	private ArrayList<Ingredient> ingredients;
	private String name="";
	private Integer people=2;
	private Bitmap image = null;
	
	public static Recipe getInstance()
	{
		if (instance==null)
			instance = new Recipe();
		return instance;
	}
	
	public void updatePeople(int howMany)
	{
		if(howMany>0)
		{
			for(int i=0;i<ingredients.size();i++)
			{
				// quantity : people = x : howMany
				ingredients.get(i).setQuantity((ingredients.get(i).getQuantity()*howMany)/people);
			}
			people= howMany;
		}
	}
	
	public static void clear()
	{
		instance = new Recipe();
	}

	private Recipe()
	{
		ingredients= new ArrayList<Ingredient>();
		description= new String[1];
		description[0]= "";
	}
	
	public void setImage(Bitmap im)
	{
		image= im;
	}
	
	public Bitmap getImage()
	{
		return image;
	}
	
	public void setPeople(Integer n)
	{
		people= n;
	}
	
	public Integer getPeople()
	{
		return people;
	}
	
	public void setName(String pname)
	{
		name= pname;
	}
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<Ingredient> getIngredients()
	{
		return ingredients;
	}
	
	public void addIngredient(Ingredient ing)
	{
		ingredients.add(ing);
	}
	
	public void setDescription(String[] pdescription)
	{
		description= pdescription;
	}
	
	public String[] getDescription()
	{
		return description;
	}
	
	
	public Boolean saveRecipe() throws IOException
	{
		RecipeSaver saver= new RecipeSaver(this);
		return saver.saveOnSD();
	}

}
