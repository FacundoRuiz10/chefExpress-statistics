package factories;

import core.RecommendationLogger;
import core.VideoRecipeRecommender;
import providers.RecommendationProvider;

public class RecommendationLoggerFactory
{
    public RecommendationLogger createRecommendationLogger(VideoRecipeRecommender recommender)
    {
        RecommendationProvider provider = new RecommendationProvider();
        recommender.attach(provider);
        return new RecommendationLogger(provider);
    }
}
