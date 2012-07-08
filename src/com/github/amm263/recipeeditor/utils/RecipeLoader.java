package com.github.amm263.recipeeditor.utils;

import com.github.amm263.recipeeditor.Recipe;

public class RecipeLoader {

	static public void loadRecipe(String name)
	{
		Recipe.clear();
		Recipe recipe= Recipe.getInstance();
		recipe.setName(name);
	}
	
}
