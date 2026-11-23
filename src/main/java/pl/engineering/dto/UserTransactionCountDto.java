package pl.engineering.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserTransactionCountDto {
    private Integer userId;
    private Long transactionCount;
}
