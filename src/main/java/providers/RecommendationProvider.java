package providers;

import entities.Recommendation;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class RecommendationProvider implements PropertyChangeListener {

    private List<Recommendation> historicRecommendations;

    public List<Recommendation> getHistoricRecommendations()  {
        return historicRecommendations;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        List<Recommendation> recommendations = (List<Recommendation>) evt.getNewValue();
        List<Recommendation> newRecommendations = new ArrayList<>(recommendations);
        newRecommendations.addAll(getHistoricRecommendations());
        setHistoricRecommendations(newRecommendations);
    }

    public void setHistoricRecommendations(List<Recommendation> historicRecommendations) {
        this.historicRecommendations = historicRecommendations;
    }
}
