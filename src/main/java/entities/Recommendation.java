package entities;

public class Recommendation {
    private Recipe recipe;

    public Recommendation(Recipe recipes) {
        this.recipe = recipes;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
