package factories;

import core.ChefExpress;
import core.RecommendationLogger;
import providers.RecommendationProvider;

public class RecommendationLoggerFactory {

    public RecommendationLogger createRecommendationLogger(ChefExpress chefExpress) {
        RecommendationProvider provider = new RecommendationProvider();
        chefExpress.attach(provider);
        return new RecommendationLogger(provider);
    }
}
