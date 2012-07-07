package com.github.amm263.recipeeditor;

import java.io.IOException;
import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

public class RecipeEditorActivity extends Activity {
	
	 private static final int CAMERA_PIC_REQUEST = 666;

	Recipe recipe;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_editor); 
        recipe= Recipe.getInstance();
        loadRecipe(recipe);
        final Button btnOpenPopup = (Button)findViewById(R.id.editorIngredientsButton);
        btnOpenPopup.setOnClickListener(new Button.OnClickListener(){

        	   public void onClick(View arg0) {
        	    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
        	    final View popupView = layoutInflater.inflate(R.layout.new_ingredient_popup, null);  
        	             final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT, true);  
        	             
        	             Button btnDismiss = (Button)popupView.findViewById(R.id.saveIngredientButton);
        	             btnDismiss.setOnClickListener(new Button.OnClickListener(){

        	            	 public void onClick(View v) {
        	            		 EditText name= (EditText)popupView.findViewById(R.id.ingredientName);
        			        	 EditText quantity= (EditText)popupView.findViewById(R.id.ingredientQuantity);
        			        	 EditText unitMeasure= (EditText)popupView.findViewById(R.id.ingredientMeasure);
        			        	 if((name.getText().length()>0)&&(quantity.getText().length()>0)&&(unitMeasure.getText().length()>0))
        			        	 {
        			        		Ingredient ingredient = new Ingredient(name.getText().toString(),Float.valueOf(quantity.getText().toString()),unitMeasure.getText().toString());
        			        	 	recipe.addIngredient(ingredient);
        			        	 	LinearLayout ingredients = (LinearLayout)findViewById(R.id.editorIngredientsLayout);
        			        	 	TextView newIngredient= new TextView(getBaseContext());
        			        	 	newIngredient.setText(ingredient.getIngredient()+" : "+ingredient.getQuantity()+" "+ingredient.getUnitMeasure());
        			        	 	ingredients.addView(newIngredient);
        			        	 }
        	            		 popupWindow.dismiss();
        	            	 }});
        	               
        	             popupWindow.showAsDropDown(btnOpenPopup, 50, -70);
        	         
        	   }});
        
        final Button btnSave = (Button)findViewById(R.id.editorSaveButton);
        btnSave.setOnClickListener(new Button.OnClickListener(){

        	   public void onClick(View arg0) {
        		   EditText name = (EditText)findViewById(R.id.editorNameField);
        		   EditText people = (EditText)findViewById(R.id.editorPeopleField);
        		   EditText description = (EditText)findViewById(R.id.editorDescriptionField);
        		   if ((name.getText().length()>0)&&(people.getText().length()>0)&&(description.getText().length()>0))
        		   {
        			   recipe.setName(name.getText().toString());
        			   recipe.setPeople(Integer.valueOf(people.getText().toString()));
        			   recipe.setDescription(description.getText().toString().split("\n"));
		        		   try {
							recipe.saveRecipe();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		   Intent intent = new Intent();
		        		   intent.setClass(getApplicationContext(), RecipeViewActivity.class);
		        		   startActivity(intent);
        		   }
        	   }});
        
        final Button btnImage = (Button)findViewById(R.id.editorImageButton);
        btnImage.setOnClickListener(new Button.OnClickListener(){

     	   public void onClick(View arg0) {
     		  Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
     		  startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);  
     	   }});
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == CAMERA_PIC_REQUEST) {  
        	Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        	recipe.setImage(thumbnail);
        	ImageView image = (ImageView) findViewById(R.id.photoResultView);  
        	image.setImageBitmap(thumbnail); 
        }  
    }  
    
    private void loadRecipe(Recipe rec)
    {
    	EditText name = (EditText)findViewById(R.id.editorNameField);
		EditText people = (EditText)findViewById(R.id.editorPeopleField);
		EditText description = (EditText)findViewById(R.id.editorDescriptionField);
		name.setText(recipe.getName());
		people.setText(recipe.getPeople().toString());
		String formattedDescription= new String("");
		for(int i=0;i<recipe.getDescription().length;i++)
		{
			formattedDescription=formattedDescription+recipe.getDescription()[i]+"\n";
		}
		description.setText(formattedDescription);
		ArrayList<Ingredient> ingredients= recipe.getIngredients();
		LinearLayout ingredientsLayout = (LinearLayout)findViewById(R.id.editorIngredientsLayout);
		Bitmap thumbnail= recipe.getImage();
		if (thumbnail!=null)
		{
			ImageView image = (ImageView) findViewById(R.id.photoResultView);  
			image.setImageBitmap(thumbnail);
		}
		for (int i=0; i<ingredients.size();i++)
		{
    	 	TextView newIngredient= new TextView(getBaseContext());
    	 	newIngredient.setText(ingredients.get(i).getIngredient()+" : "+ingredients.get(i).getQuantity()+" "+ingredients.get(i).getUnitMeasure());
    	 	ingredientsLayout.addView(newIngredient);
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_recipe_editor, menu);
        return true;
    }

    
}
