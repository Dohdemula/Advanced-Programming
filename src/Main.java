import Lecture1_adt.*; // Import all classes from Lecture1_adt package to be used in this client code

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/*
* Client Code for accessing the Lecture1_adt.TransactionInterface.java module
 */
// Interface defining transaction methods
interface TransactionInterface {
    double getAmount();
    Calendar getDate();
    String getTransactionID();
    void printTransactionDetails();
    void apply(BankAccount ba);
}

// Base Transaction Class
abstract class BaseTransaction implements TransactionInterface {
    private double amount;
    private Calendar date;
    private String transactionID;

    public BaseTransaction(double amount, Calendar date, String transactionID) {
        this.amount = amount;
        this.date = date;
        this.transactionID = transactionID;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public Calendar getDate() {
        return date;
    }

    @Override
    public String getTransactionID() {
        return transactionID;
    }

    @Override
    public void printTransactionDetails() {
        System.out.println("Transaction ID: " + transactionID);
        System.out.println("Amount: " + amount);
        System.out.println("Date: " + date.getTime());
    }

    @Override
    public abstract void apply(BankAccount ba);
}

// Deposit Transaction Class
class DepositTransaction extends BaseTransaction {
    public DepositTransaction(double amount, Calendar date, String transactionID) {
        super(amount, date, transactionID);
    }

    @Override
    public void apply(BankAccount ba) {
        ba.deposit(getAmount());
        System.out.println("Deposit of " + getAmount() + " applied.");
    }
}

// Withdrawal Transaction Class
class WithdrawalTransaction extends BaseTransaction {
    private double originalBalance;

    public WithdrawalTransaction(double amount, Calendar date, String transactionID) {
        super(amount, date, transactionID);
    }

    @Override
    public void apply(BankAccount ba) {
        try {
            if (ba.getBalance() < getAmount()) {
                throw new InsufficientFundsException("Insufficient funds for withdrawal.");
            }
            ba.withdraw(getAmount());
            System.out.println("Withdrawal of " + getAmount() + " applied.");
        } catch (InsufficientFundsException e) {
            System.err.println(e.getMessage());
        }
    }

    public void apply(BankAccount ba, boolean allowPartialWithdrawal) {
        try {
            if (ba.getBalance() >= getAmount()) {
                ba.withdraw(getAmount());
                System.out.println("Full withdrawal of " + getAmount() + " applied.");
            } else if (allowPartialWithdrawal) {
                double available = ba.getBalance();
                ba.withdraw(available);
                System.out.println("Partial withdrawal of " + available + " applied.");
            } else {
                throw new InsufficientFundsException("Insufficient funds for withdrawal.");
            }
        } catch (InsufficientFundsException e) {
            System.err.println(e.getMessage());
        } finally {
            System.out.println("Transaction process completed.");
        }
    }

    public boolean reverse(BankAccount ba) {
        ba.deposit(getAmount());
        System.out.println("Withdrawal of " + getAmount() + " reversed.");
        return true;
    }
}

// Custom Exception for Insufficient Funds
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// Bank Account Class
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount + ". New Balance: " + balance);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: " + amount + ". New Balance: " + balance);
        } else {
            System.out.println("Unable to withdraw " + amount + ". Insufficient balance.");
        }
    }
}

