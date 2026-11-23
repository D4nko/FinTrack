package pl.engineering.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transaction> sentTransactions = new HashSet<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transaction> receivedTransactions = new HashSet<>();

    public Account(String accountNumber, BigDecimal balance, User user, Set<Transaction> sentTransactions, Set<Transaction> receivedTransactions) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.user = user;
        this.sentTransactions = sentTransactions != null ? sentTransactions : new HashSet<>();
        this.receivedTransactions = receivedTransactions != null ? receivedTransactions : new HashSet<>();
    }


    public Account(String pl25109024028938237414992113, BigDecimal bigDecimal, User user1) {
    }
}