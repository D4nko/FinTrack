package pl.engineering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.engineering.dto.TransactionDto;
import pl.engineering.exeptions.AccountNotFoundExeption;
import pl.engineering.exeptions.InsufficientBalanceException;
import pl.engineering.exeptions.UserNotFoundException;
import pl.engineering.model.Account;
import pl.engineering.model.Transaction;
import pl.engineering.model.User;
import pl.engineering.repository.AccountRepository;
import pl.engineering.repository.TransactionRepository;
import pl.engineering.repository.UserRepository;
import pl.engineering.repository.filter.TransactionSpecification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private final TransactionRepository transactionRepository;
    @Autowired
    private final UserRepository userRepository;


    @Transactional
    public void transferFunds(String senderAccountNumber, String recipientAccountNumber, BigDecimal amount) throws Exception {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        Account senderAccount = accountRepository.findByAccountNumber(senderAccountNumber)
                .orElseThrow(() -> new AccountNotFoundExeption("Sender account not found"));

        Account recipientAccount = accountRepository.findByAccountNumber(recipientAccountNumber)
                .orElseThrow(() -> new AccountNotFoundExeption("Recipient account not found"));

        int senderUserId = senderAccount.getUser().getId();
        int recipientUserId = recipientAccount.getUser().getId();

        int minId = Math.min(senderUserId, recipientUserId);
        int maxId = Math.max(senderUserId, recipientUserId);

        List<User> users = userRepository.findByIdWithLock(minId, maxId);
        if (users.size() < 2) {
            throw new UserNotFoundException("User not found");
        }

        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        User sender = userMap.get(senderUserId);
        User recipient = userMap.get(recipientUserId);

        if (sender == null || recipient == null) {
            throw new UserNotFoundException("User not found");
        }
        if (sender.equals(recipient)) {
            throw new IllegalArgumentException("Sender and recipient cannot be the same");
        }
        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
        recipientAccount.setBalance(recipientAccount.getBalance().add(amount));

        accountRepository.save(senderAccount);
        accountRepository.save(recipientAccount);

        Transaction tx = new Transaction(amount, senderAccount, recipientAccount, sender);
        transactionRepository.save(tx);
    }


    public Page<TransactionDto> getTransactionHistory(BigDecimal amountFrom, BigDecimal amountTo, LocalDate dateFrom, LocalDate dateTo, Integer userId, Pageable pageable) {
        Specification<Transaction> spec = Specification.where(
                        TransactionSpecification.amountBetween(amountFrom, amountTo)
                                .and(TransactionSpecification.dateBetween(dateFrom, dateTo)))
                .and(TransactionSpecification.forUserId(userId));

        Page<Transaction> ormTxs = transactionRepository.findAll(spec, pageable);
        return ormTxs.map(tx -> new TransactionDto(
                tx.getId(),
                tx.getAmount(),
                tx.getTransactionDate(),
                tx.getSender().getAccountNumber(),
                tx.getRecipient().getAccountNumber()));
    }
}
