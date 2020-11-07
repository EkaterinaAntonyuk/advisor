package fi.op.junction.advisor.controller;

import fi.op.junction.advisor.model.db.CategoryTransactions;
import fi.op.junction.advisor.model.dto.SpendingPlanResponse;
import fi.op.junction.advisor.model.dto.Suggestion;
import fi.op.junction.advisor.model.dto.Transaction;
import fi.op.junction.advisor.service.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/plan")
public class PlanController {
    private PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/current")
    public ResponseEntity<SpendingPlanResponse> getSpendingPlan(@RequestHeader("account") int account) {
        log.info("get current was called");
        return ResponseEntity.ok().body(planService.getSpendingPlan(account));
    }

    @GetMapping
    public SpendingPlanResponse getSpendingPlanForMonth(@RequestHeader("account") int account,
                                                        @RequestParam(name = "month") int month,
                                                        @RequestParam(name = "year") int year) {
        log.info("get previous was called");
        return planService.getSpendingPlanForMonth(account, month, year);
    }

    @GetMapping("/suggestions")
    public List<Suggestion> getSuggestionsList(@RequestHeader("account") int account){
        log.info("get suggestions was called");
        return planService.getSuggestionsList(account);
    }

    @GetMapping("/category")
    public List<Transaction> getCategoryDetailing(@RequestHeader("account") int account,
                                                  @RequestParam(name = "category") int category,
                                                  @RequestParam(name = "month") int month,
                                                  @RequestParam(name = "year") int year){
        log.info("get category was called");
        return planService.getCategoryDetailing(account, category, month, year);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateSpendingPlan(@RequestHeader("account") int account,
                                   @RequestParam(name = "month") int month,
                                   @RequestParam(name = "year") int year,
                                   @RequestBody SpendingPlanResponse spendingPlanResponse) {
        log.info("put current was called");
        planService.updateSpendingPlan(account, month, year, spendingPlanResponse);
    }

    @PostMapping
    public void createNewSpendingPlan(@RequestHeader("account") int account,
                                      @RequestParam(name = "month") int month,
                                      @RequestParam(name = "year") int year) {
        log.info("create new was called");
        planService.createNewSpendingPlan(account, month, year);
    }

}
