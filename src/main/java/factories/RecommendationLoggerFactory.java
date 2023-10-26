package factories;

import core.RecommendationLogger;
import core.VideoRecipeRecommendator;
import providers.RecommendationProvider;

public class RecommendationLoggerFactory
{
    public RecommendationLogger createRecommendationLogger(VideoRecipeRecommendator recommender)
    {
        RecommendationProvider provider = new RecommendationProvider();
        recommender.attach(provider);
        return new RecommendationLogger(provider);
    }
}
