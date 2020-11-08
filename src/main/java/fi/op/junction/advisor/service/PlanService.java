package fi.op.junction.advisor.service;

import fi.op.junction.advisor.model.db.AverageSpending;
import fi.op.junction.advisor.model.db.DBCategory;
import fi.op.junction.advisor.model.db.SpendingPlan;
import fi.op.junction.advisor.model.db.CategoryTransactions;
import fi.op.junction.advisor.model.dto.*;
import fi.op.junction.advisor.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlanService {
    private PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public SpendingPlanResponse getSpendingPlan(int account) {
        List<SpendingPlan> spendingPlanList = planRepository.getSpendingPlan(account,
                Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.YEAR));
        List<CategoryTransactions> categoryTransactionsList = planRepository.getTransactionsSum(account,
                Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.YEAR));
        return getSpendingPlanResponse(spendingPlanList, categoryTransactionsList);
    }

    public SpendingPlanResponse getSpendingPlanForMonth(int account, int month, int year) {
        List<SpendingPlan> spendingPlanList = planRepository.getSpendingPlan(account,
                month, year);
        List<CategoryTransactions> categoryTransactionsList = planRepository.getTransactionsSum(account,
                month, year);
        return getSpendingPlanResponse(spendingPlanList, categoryTransactionsList);
    }

    private SpendingPlanResponse getSpendingPlanResponse(List<SpendingPlan> spendingPlanList,
                                                         List<CategoryTransactions> categoryTransactionsList) {
        List<Category> categories = new ArrayList<>();
        Set<Integer> identifiers = new HashSet<>();
        double spentSum = 0;
        double plannedSum = 0;
        for (SpendingPlan spendingPlan : spendingPlanList) {
            for (CategoryTransactions categoryTransactions : categoryTransactionsList) {
                if (spendingPlan.getNameFin().equals(categoryTransactions.getCategory())) {
                    if (!categoryTransactions.getCategory().equals("Luokittelemattomat")) {
                        if (spendingPlan.getPlan() < -categoryTransactions.getSum()) {
                            categories.add(new Category(spendingPlan.getId(), spendingPlan.getNameEng(),
                                    -categoryTransactions.getSum(), spendingPlan.getPlan(), Status.OVERSPENT));
                        } else if (spendingPlan.getPlan() * 0.9 < -categoryTransactions.getSum()) {
                            categories.add(new Category(spendingPlan.getId(), spendingPlan.getNameEng(),
                                    -categoryTransactions.getSum(), spendingPlan.getPlan(), Status.WARNING));
                        } else {
                            categories.add(new Category(spendingPlan.getId(), spendingPlan.getNameEng(),
                                    -categoryTransactions.getSum(), spendingPlan.getPlan(), Status.OK));
                        }
                        spentSum = spentSum + categoryTransactions.getSum();
                        plannedSum = plannedSum + spendingPlan.getPlan();
                        identifiers.add(spendingPlan.getId());
                    }
                }
            }
        }
        for (SpendingPlan spendingPlan : spendingPlanList) {
            if (!identifiers.contains(spendingPlan.getId())){
                categories.add(new Category(spendingPlan.getId(), spendingPlan.getNameEng(),
                        0, spendingPlan.getPlan(), Status.OK));
            }
        }
        categories.sort(Comparator.comparingInt(Category::getId));
        return new SpendingPlanResponse(new Summary(-spentSum, plannedSum), categories);
    }


    public void updateSpendingPlan(int account, int month, int year, SpendingPlanResponse spendingPlanResponse) {
        for (Category category : spendingPlanResponse.getCategory()) {
            planRepository.updateSpendingPlanForCategory(account,
                    new SpendingPlan(category.getId(), category.getName(), category.getPlanned()),
                    month, year);
        }
    }

    public void createNewSpendingPlan(int account, int month, int year) {
        Map<String, Object> lastDate = planRepository.getLastDate(account);
        List<SpendingPlan> spendingPlanList = planRepository.getSpendingPlan(1,
                Integer.parseInt(lastDate.get("planning_month").toString()),
                Integer.parseInt(lastDate.get("planning_year").toString()));
        for (SpendingPlan spendingPlan : spendingPlanList) {
            planRepository.insert(spendingPlan, account, month, year);
        }

    }

    public List<Suggestion> getSuggestionsList(int account) {
        List<AverageSpending> currentUserAverageSpendings = planRepository.getCurrentUserAverage(account);
        List<AverageSpending> allUsersAverageSpendings = planRepository.getAllUserAverage();
        List<DBCategory> categoryList = planRepository.getCategoryNames();
        List<Suggestion> suggestionList = new ArrayList<>();
        for (DBCategory category : categoryList) {
            for (AverageSpending currUserAverageSpending : currentUserAverageSpendings) {
                for (AverageSpending allUsersAverageSpending : allUsersAverageSpendings) {
                    if (category.getNameFin().equals(currUserAverageSpending.getCategory()) &&
                            category.getNameFin().equals(allUsersAverageSpending.getCategory())) {
                        suggestionList.add(new Suggestion(category.getId(), category.getNameEng(),
                                -currUserAverageSpending.getMonthSum(), -allUsersAverageSpending.getMonthSum()));
                    }
                }
            }
        }
        return suggestionList;
    }

    public List<Transaction> getCategoryDetailing(int account, int category, int month, int year) {
        return planRepository.getCategoryDetailing(account, category, month, year);
    }
}
