import java.util.Calendar;

// Main Class
public class TransactionSystem {
    public static void main(String[] args) {
        // Create a bank account
        BankAccount account = new BankAccount(500.00);

        // Create transactions
        Calendar date = Calendar.getInstance();
        DepositTransaction deposit = new DepositTransaction(200.00, date, "D001");
        WithdrawalTransaction withdrawal = new WithdrawalTransaction(100.00, date, "W001");

        // Apply transactions
        deposit.apply(account);
        withdrawal.apply(account);

        // Handle large withdrawal
        WithdrawalTransaction largeWithdrawal = new WithdrawalTransaction(700.00, date, "W002");
        largeWithdrawal.apply(account, true);

        // Reverse a withdrawal
        withdrawal.reverse(account);

        // Base class behavior with type casting
        BaseTransaction baseTransaction = new DepositTransaction(300.00, date, "D002");
        baseTransaction.apply(account);
    }
}
