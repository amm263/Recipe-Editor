package com.github.amm263.recipeeditor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newRecipeButton = (Button)findViewById(R.id.newRecipeButton);
        newRecipeButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            	Recipe.clear();
            	Intent myIntent = new Intent(getApplicationContext(), RecipeEditorActivity.class);
                startActivity(myIntent);          
            }
        });
        
        Button browseRecipesButton = (Button)findViewById(R.id.browseRecipesButton);
        browseRecipesButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            	Recipe.clear();
            	Intent myIntent = new Intent(getApplicationContext(), RecipeBrowserActivity.class);
                startActivity(myIntent);          
            }
        });
    }
    
}
