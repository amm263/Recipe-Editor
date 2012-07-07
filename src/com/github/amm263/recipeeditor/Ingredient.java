package com.github.amm263.recipeeditor;

public class Ingredient {
	
	private String ingredient;
	private float quantity;
	private String unitMeasure;
	
	public Ingredient(String ing, float qt, String unit)
	{
		ingredient= ing;
		quantity= qt;
		unitMeasure= unit;
	}
	
	public String getIngredient()
	{
		return ingredient;
	}
	
	public float getQuantity()
	{
		return quantity;
	}
	
	public void setQuantity(float qty)
	{
		quantity = qty;
	}
	
	public String getUnitMeasure()
	{
		return unitMeasure;
	}
}
