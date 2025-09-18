package models;

public class CurrentAccount extends BankAccount {
    public float overdraftLimit;

    public CurrentAccount(String code, String username, float sold, float overdraftLimit) {
        super(code, username, sold);
        this.overdraftLimit = overdraftLimit;
    }

    public float getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(float overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void deposit(float amount) {
        sold += amount;
        addOperation(new Deposit(amount));
    }

    @Override
    public void withdraw(float amount) throws Exception {
        if (sold + overdraftLimit >= amount) {
            sold -= amount;
            addOperation(new Withdrawal(amount));
        } else {
            throw new Exception("Overdraft limit exceeded!");
        }
    }
}
