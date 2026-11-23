package pl.engineering.dto.requset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
public class CreateTransactionRequest {
    private String senderAccountNumber;
    private String recipientAccountNumber;
    private BigDecimal amount;

}