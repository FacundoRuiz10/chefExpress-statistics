package core;

import entities.Recipe;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoricalRecipesCounter implements PropertyChangeListener {


    private Map<Recipe, Integer> historicRecipes;

    public HistoricalRecipesCounter(ChefExpress chefExpress)
    {
        this.historicRecipes =  new HashMap<>();
        chefExpress.attach(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        List<Recipe> recipes = (List<Recipe>) evt.getNewValue();
        this.updateRecipesQuantity(recipes);
    }

    private void updateRecipesQuantity(List<Recipe> recipes) {
        for(Recipe recipe : recipes){
            int quantity = 0;
            if(historicRecipes.containsKey(recipe)){
                quantity = historicRecipes.get(recipe);
            }
            historicRecipes.put(recipe, quantity + 1);
        }
    }

    public Map<Recipe, Integer> getHistoricRecipes()  {
        return historicRecipes;
    }
}
