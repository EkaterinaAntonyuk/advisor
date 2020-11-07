package fi.op.junction.advisor.repository;

import fi.op.junction.advisor.model.db.AverageSpending;
import fi.op.junction.advisor.model.db.DBCategory;
import fi.op.junction.advisor.model.db.SpendingPlan;
import fi.op.junction.advisor.model.db.CategoryTransactions;
import fi.op.junction.advisor.model.dto.Transaction;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlanRepository {
    private JdbcTemplate jdbcTemplate;
    SimpleJdbcInsert simpleJdbcInsert;

    public PlanRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("spending_plan");
    }

    public List<SpendingPlan> getSpendingPlan(int account, int month, int year) {
        try {
            String sql = "select c.id, c.name_eng, c.name_fin, sp.plan \n" +
                    "from spending_plan sp \n" +
                    "join categories c on sp.category_id = c.id \n" +
                    "where sp.account = ?\n" +
                    "and sp.planning_month = ?\n" +
                    "and sp.planning_year = ?";
            Object[] param = new Object[]{account, month, year};
            return jdbcTemplate.query(sql, param,
                    BeanPropertyRowMapper.newInstance(SpendingPlan.class));
        } catch (
                EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Map<String, Object> getLastDate(int account) {
        try {
            String sql = "select distinct sp.planning_year,sp.planning_month \n" +
                    "from spending_plan sp \n" +
                    "where sp.account = ?\n" +
                    "order by \n" +
                    "sp.planning_year desc,sp.planning_month desc\n" +
                    "limit 1";
            Object[] param = new Object[]{account};
            return jdbcTemplate.queryForMap(sql, param);
        } catch (
                EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<CategoryTransactions> getTransactionsSum(int account, int month, int year) {
        try {
            String sql = "select t2.category, sum(t2.rahamaara)\n" +
                    "from \"transaction_big\" t2  \n" +
                    "where t2.tilinro = ?\n" +
                    "and to_char(t2.tstamp, 'YYYY') = ?\n" +
                    "and to_char(t2.tstamp, 'MM') = ?\n" +
                    "group by t2.category\n " +
                    "having sum(t2.rahamaara) < 0";
            Object[] param = new Object[]{account, Integer.toString(year), String.format("%02d", month)};
            return jdbcTemplate.query(sql, param,
                    BeanPropertyRowMapper.newInstance(CategoryTransactions.class));
        } catch (
                EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void updateSpendingPlanForCategory(int account, SpendingPlan spendingPlan, int month, int year) {
        Object[] params = new Object[]{spendingPlan.getPlan(), account, spendingPlan.getId(), month, year};
        String sql = "update spending_plan set plan = ? \n" +
                "where account = ?\n" +
                "and category_id = ?\n" +
                "and planning_month = ?\n" +
                "and planning_year = ?";
        jdbcTemplate.update(sql, params);
    }

    public void insert(SpendingPlan spendingPlan, int account, int month, int year) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("account", account);
        parameters.put("planning_month", month);
        parameters.put("planning_year", year);
        parameters.put("category_id", spendingPlan.getId());
        parameters.put("plan", spendingPlan.getPlan());
        simpleJdbcInsert.execute(parameters);
    }

    public List<AverageSpending> getCurrentUserAverage(int account) {
        try {
            String sql = "select t2.category, sum(t2.rahamaara)/6 as month_sum\n" +
                    "from \"transaction_big\" t2  \n" +
                    "where t2.tilinro = ?\n" +
                    "and t2.tstamp between current_timestamp - INTERVAL '180 DAYS' and current_timestamp\n" +
                    "group by t2.category \n" +
                    "having sum(t2.rahamaara) < 0";
            Object[] param = new Object[]{account};
            return jdbcTemplate.query(sql, param,
                    BeanPropertyRowMapper.newInstance(AverageSpending.class));
        } catch (
                EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<AverageSpending> getAllUserAverage() {
        try {
            String sql = "select t2.category, sum(t2.rahamaara)/(6*(select count(DISTINCT tb.tilinro)\n" +
                    "from transaction_big tb)) as month_sum\n" +
                    "from \"transaction_big\" t2  \n" +
                    "where t2.tstamp between current_timestamp - INTERVAL '180 DAYS' and current_timestamp\n" +
                    "group by t2.category\n" +
                    "having sum(t2.rahamaara) < 0";
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(AverageSpending.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<DBCategory> getCategoryNames() {
        try {
            String sql = "select * \n" +
                    "from categories c ";
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(DBCategory.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Transaction> getCategoryDetailing(int account, int category, int month, int year) {
        try {
            String sql = "select t2.tstamp, t2.saldo, c3.\"name\" \n" +
                    "from \"transaction_big\" t2, counterparties c3, categories c2 \n" +
                    "where t2.tilinro = ?\n" +
                    "and to_char(t2.tstamp, 'YYYY') = ?\n" +
                    "and to_char(t2.tstamp, 'MM') = ?\n" +
                    "and c2.id = ?\n" +
                    "and t2.counterparty_account_id = c3.id \n" +
                    "and t2.category = c2.name_fin";
            Object[] param = new Object[]{account, Integer.toString(year), String.format("%02d", month), category};
            return jdbcTemplate.query(sql, param,
                    BeanPropertyRowMapper.newInstance(Transaction.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}