package IT1;

import core.ChefExpress;
import core.HistoricalRecipesCounter;
import core.RecipesProvider;
import entities.Recipe;
import interfaces.RecipeScorer;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserStory3
{
    private HistoricalRecipesCounter historicalRecipesCounter;
    private RecipeScorer scorerMock;
    private RecipesProvider recipesProvider;
    private ChefExpress chefExpress;
    private List<Recipe> recipes;

    @BeforeEach
    public void setUp()
    {
        List<Integer> recipeIds = Arrays.asList(1, 2, 3);
        this.recipes = mockRecipes(recipeIds);

        Map<Recipe, Integer> scorerResults = Map.of(recipes.get(0),0,
                                                    recipes.get(1), 10,
                                                    recipes.get(2), 10);

        this.scorerMock = mock(RecipeScorer.class);
        this.setScorerResult(scorerResults);

        this.recipesProvider = mock(RecipesProvider.class);

        this.chefExpress = new ChefExpress(recipesProvider, scorerMock, new HashMap<>());
        this.historicalRecipesCounter = new HistoricalRecipesCounter(chefExpress);
    }

    private List<Recipe> mockRecipes(List<Integer> ids)
    {
        return ids.stream()
                .map(recipeId -> new Recipe(recipeId, "R" + recipeId, new HashMap<>()))
                .collect(Collectors.toList());
    }

    private void setScorerResult(Map<Recipe, Integer> scorerResult)
    {
        for (Map.Entry<Recipe, Integer> entry : scorerResult.entrySet())
        {
            when(this.scorerMock.score(entry.getKey())).thenReturn(entry.getValue());
        }
    }

    @Test
    @Description("Sin recetas recomendadas")
    public void ca1SinRecetasRecomendadas()
    {
        // recipes.get(0) == R1
        when(recipesProvider.getRecipes()).thenReturn(Set.of(this.recipes.get(0)));
        this.chefExpress.recommend();

        assertTrue(historicalRecipesCounter.getHistoricRecipes().isEmpty());
    }

    @Test
    @Description("Receta recomendada una unica vez")
    public void ca2RecetaRecomendadaUnaUnicaVez()
    {
        // recipes.get(1) == R2
        when(recipesProvider.getRecipes()).thenReturn(Set.of(this.recipes.get(1)));
        this.chefExpress.recommend();

        Map<Recipe, Integer> historicRecipesResult = historicalRecipesCounter.getHistoricRecipes();

        assert historicRecipesResult.containsKey(this.recipes.get(1));
        assert historicalRecipesCounter.getHistoricRecipes().get(recipes.get(1)) == 1;
    }

    @Test
    @Description("Multiples recetas recomendadas")
    public void ca3MultiplesRecetasRecomendadas()
    {
        // recipes.get(1) == R2 y recipes.get(2) == R3
        when(recipesProvider.getRecipes()).thenReturn(Set.of(this.recipes.get(1), this.recipes.get(2)));
        this.chefExpress.recommend();

        Map<Recipe, Integer> historicRecipesResult = historicalRecipesCounter.getHistoricRecipes();

        assert historicRecipesResult.containsKey(this.recipes.get(1));
        assert historicRecipesResult.containsKey(this.recipes.get(2));

        assert  historicRecipesResult.get(recipes.get(1)) == 1;
        assert  historicRecipesResult.get(recipes.get(2)) == 1;
    }

    @Test
    @Description("Receta recomendada multiples veces")
    public void ca4RecetaRecomendadaMultiplesVeces()
    {
        // recipes.get(1) == R2
        when(recipesProvider.getRecipes()).thenReturn(Set.of(this.recipes.get(1)));
        this.chefExpress.recommend();

        Map<Recipe, Integer> historicRecipesResult = historicalRecipesCounter.getHistoricRecipes();

        assert historicRecipesResult.containsKey(this.recipes.get(1));
        assert  historicRecipesResult.get(recipes.get(1)) == 1;

        this.chefExpress.recommend();

        historicRecipesResult = historicalRecipesCounter.getHistoricRecipes();

        assert historicRecipesResult.containsKey(this.recipes.get(1));
        assert  historicRecipesResult.get(recipes.get(1)) == 2;
    }

    @Test
    @Description("Sin recetas recomendadas al historico con contenido")
    public void ca5SinRecetasRecomendadasAlHistoricoConContenido()
    {
        // recipes.get(1) == R2
        when(recipesProvider.getRecipes()).thenReturn(Set.of(this.recipes.get(1)));
        this.chefExpress.recommend();

        Map<Recipe, Integer> historicRecipesResult = historicalRecipesCounter.getHistoricRecipes();

        assert historicRecipesResult.containsKey(this.recipes.get(1));
        assert  historicRecipesResult.get(recipes.get(1)) == 1;

        // recipes.get(0) == R1
        when(recipesProvider.getRecipes()).thenReturn(Set.of(this.recipes.get(0)));
        this.chefExpress.recommend();

        historicRecipesResult = historicalRecipesCounter.getHistoricRecipes();

        assert historicRecipesResult.containsKey(this.recipes.get(1));
        assert  historicRecipesResult.get(recipes.get(1)) == 1;
    }
}