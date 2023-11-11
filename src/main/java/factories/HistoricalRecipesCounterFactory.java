package factories;

import core.ChefExpress;
import core.HistoricalRecipesCounter;

public class HistoricalRecipesCounterFactory
{
    public HistoricalRecipesCounterFactory(){

    }
    public HistoricalRecipesCounter createHistoricalRecipesCounter(ChefExpress recommender)
    {
        HistoricalRecipesCounter historicalRecipesCounter = new HistoricalRecipesCounter(recommender);
        return historicalRecipesCounter;
    }
}
