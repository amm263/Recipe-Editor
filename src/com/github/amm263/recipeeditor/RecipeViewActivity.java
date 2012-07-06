package com.github.amm263.recipeeditor;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecipeViewActivity extends Activity {

	Recipe recipe;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        recipe = Recipe.getInstance();
        loadRecipe(recipe);
        
        final Button btnEdit = (Button)findViewById(R.id.viewEditButton);
        btnEdit.setOnClickListener(new Button.OnClickListener(){

        	   public void onClick(View arg0) {
        		   Intent intent = new Intent();
        		   intent.setClass(getApplicationContext(), RecipeEditorActivity.class);
        		   startActivity(intent);
        	   }});
        
        final Button decreasePeople = (Button)findViewById(R.id.increaseButton);
        decreasePeople.setOnClickListener(new Button.OnClickListener(){

        	   public void onClick(View arg0) {
        		   recipe.updatePeople(recipe.getPeople()+1);
        	   }});
        
        final Button increasePeople = (Button)findViewById(R.id.decreaseButton);
        increasePeople.setOnClickListener(new Button.OnClickListener(){

        	   public void onClick(View arg0) {
        		   recipe.updatePeople(recipe.getPeople()-1);
        	   }});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_recipe_view, menu);
        return true;
    }
    
    private void loadRecipe(Recipe rep)
    {
    	TextView name = (TextView)findViewById(R.id.viewNameText);
    	TextView description = (TextView)findViewById(R.id.viewDescription);
    	TextView viewpeople = (TextView)findViewById(R.id.viewPeopleText);
    	Bitmap thumbnail = recipe.getImage();
    	ImageView image = (ImageView) findViewById(R.id.viewPhoto);  
    	image.setImageBitmap(thumbnail); 
    	name.setText(recipe.getName());
    	viewpeople.setText(recipe.getPeople().toString());
    	description.setText(recipe.getDescription());    	
    	ArrayList<Ingredient> ingredients= recipe.getIngredients();
		LinearLayout ingredientsLayout = (LinearLayout)findViewById(R.id.viewIngredientsLayout);
		for (int i=0; i<ingredients.size();i++)
		{
    	 	TextView newIngredient= new TextView(getBaseContext());
    	 	newIngredient.setText(ingredients.get(i).getIngredient()+" : "+ingredients.get(i).getQuantity()+" "+ingredients.get(i).getUnitMeasure());
    	 	ingredientsLayout.addView(newIngredient);
		}
    }

    
}
