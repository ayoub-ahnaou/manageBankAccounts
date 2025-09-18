package models;

public class Deposit extends Operation {
    public Deposit(float amount) {
        super("Deposit", amount);
    }

    @Override
    public void apply(BankAccount account) {
        account.setSold(account.getSold() + amount);
    }
}
