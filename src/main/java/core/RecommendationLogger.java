package core;

import entities.Recipe;
import providers.RecommendationProvider;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecommendationLogger {
    private RecommendationProvider provider;

    public RecommendationLogger(RecommendationProvider provider) {
        this.provider = provider;
    }

    public List<Recipe> getTopRecipes() {
        if (this.provider.getHistoricRecommendations().isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        Map<Integer, Long> recipesIdCount = this.provider.getHistoricRecommendations()
                .stream()
                .map(r -> r.getRecipe())
                .collect(Collectors.groupingBy(recipe -> recipe.getId(), Collectors.counting()));

        Long maxCount = recipesIdCount
                .values()
                .stream()
                .max(Long::compareTo)
                .get();

        List<Integer> filteredIds = recipesIdCount
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(maxCount))
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());

        return this.provider.getHistoricRecommendations()
                .stream()
                .filter(r -> filteredIds.contains(r.getRecipe().getId()))
                .map(r -> r.getRecipe())
                .collect(Collectors.toMap(Recipe::getId, Function.identity(), (existing, replacement) -> existing))
                .values()
                .stream()
                .collect(Collectors.toList());
    }
}
