package fi.op.junction.advisor.model.dto;

import java.util.List;

public class SpendingPlanResponse {
    private Summary summary;
    private List<Category> category;

    public SpendingPlanResponse(Summary summary, List<Category> category) {
        this.summary = summary;
        this.category = category;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }
}
