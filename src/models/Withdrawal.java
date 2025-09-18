package models;

public class Withdrawal extends Operation {
    public Withdrawal(float amount) {
        super("Withdrawal", amount);
    }

    @Override
    public void apply(BankAccount account) throws Exception {
        account.withdraw(amount); // account-specific rule
    }
}
