package factories;

import core.ChefExpress;
import core.HistoricalRecipesCounter;

public class HistoricalRecipesCounterFactory
{
    public HistoricalRecipesCounter createHistoricalRecipesCounter(ChefExpress recommender)
    {
        HistoricalRecipesCounter historicalRecipesCounter = new HistoricalRecipesCounter();
        recommender.attach(historicalRecipesCounter);
        return historicalRecipesCounter;
    }
}
