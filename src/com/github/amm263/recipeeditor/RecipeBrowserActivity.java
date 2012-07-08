package com.github.amm263.recipeeditor;

import java.util.ArrayList;
import com.github.amm263.recipeeditor.utils.RecipeListLoader;
import com.github.amm263.recipeeditor.utils.RecipeLoader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecipeBrowserActivity extends Activity {
	
	ArrayList<TextView> viewList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_browser);
        viewList= new ArrayList<TextView>();
        LinearLayout listLayout = (LinearLayout)findViewById(R.id.browserRecipeLayout);
        RecipeListLoader loader = new RecipeListLoader();
        ArrayList<String> list = loader.getList();
        for (int i=0;i<list.size();i++)
        {
        	viewList.add(new TextView(getBaseContext()));
        	viewList.get(i).setText(list.get(i));
        	viewList.get(i).setClickable(true);
        	viewList.get(i).setBackgroundResource(R.layout.borders);
        	viewList.get(i).setTextAppearance(this, android.R.style.TextAppearance_Large);
        	viewList.get(i).setTextSize(30);
        	setClickEvent(i);
        	listLayout.addView(viewList.get(i));
        }
    }
    
    private void setClickEvent(final int i)
    {
    	viewList.get(i).setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Recipe.clear();
				RecipeLoader.loadRecipe(viewList.get(i).getText().toString());
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), RecipeEditorActivity.class);
				startActivity(intent);	
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_recipe_browser, menu);
        return true;
    }

    
}
