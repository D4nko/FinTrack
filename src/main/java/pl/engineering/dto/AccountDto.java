package pl.engineering.dto;

import pl.engineering.model.Account;

import java.math.BigDecimal;

public record AccountDto(int id, String accountNumber, BigDecimal balance,int userId) {

    public static AccountDto from(Account account) {
        return new AccountDto(account.getId(), account.getAccountNumber(), account.getBalance(),account.getUser().getId());
    }
}
