package pl.engineering.repository.filter;

import org.springframework.data.jpa.domain.Specification;
import pl.engineering.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionSpecification {

    public static Specification<Transaction> amountBetween(BigDecimal from, BigDecimal to) {
        return ((root, query, criteriaBuilder) -> {
            if (from == null || to == null) return null;
            return criteriaBuilder.between(root.get("amount"), from, to);
        });
    }

    public static Specification<Transaction> dateBetween(LocalDate from, LocalDate to) {
        return ((root, query, criteriaBuilder) -> {
            if (from == null || to == null) return null;
            LocalDateTime startOfDay = from.atStartOfDay();
            LocalDateTime endOfDay = to.atTime(23, 59, 59);
            return criteriaBuilder.between(root.get("transactionDate"), startOfDay, endOfDay);
        });
    }
    public static Specification<Transaction> forUserId(Integer userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) return null;
            return criteriaBuilder.equal(root.get("user").get("id"), userId);
        };
    }

}
