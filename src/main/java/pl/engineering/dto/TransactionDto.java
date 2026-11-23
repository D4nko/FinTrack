package pl.engineering.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TransactionDto {
    private Integer id;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String senderAccountNumber;
    private String recipientAccountNumber;
}
