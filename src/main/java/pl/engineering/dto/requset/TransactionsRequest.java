package pl.engineering.dto.requset;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionsRequest {
    private BigDecimal minAmount;
    private BigDecimal maxAmount;

    private LocalDate fromDate;
    private LocalDate toDate;

    private Integer userId;
}
