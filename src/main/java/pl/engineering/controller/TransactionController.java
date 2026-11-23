package pl.engineering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.engineering.dto.TransactionDto;
import pl.engineering.dto.requset.CreateTransactionRequest;
import pl.engineering.dto.requset.TransactionsRequest;
import pl.engineering.service.TransactionService;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody CreateTransactionRequest request) {
        try {
            this.transactionService.transferFunds(request.getSenderAccountNumber(), request.getRecipientAccountNumber(), request.getAmount());
            return ResponseEntity.status(HttpStatus.OK).body("Transaction successful.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Transaction failed: " + ex.getMessage());
        }
    }

    @GetMapping("/history")
    public ResponseEntity<Page<TransactionDto>> getTransactionHistory(@ModelAttribute TransactionsRequest request, @PageableDefault(size = 10) Pageable pageable) {
        Page<TransactionDto> txs = this.transactionService.getTransactionHistory(
                request.getMinAmount(),
                request.getMaxAmount(),
                request.getFromDate(),
                request.getToDate(),
                request.getUserId(),
                pageable
        );
        return ResponseEntity.status(HttpStatus.OK).body(txs);
    }
}
