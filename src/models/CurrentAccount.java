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
    public void withdraw(float amount) throws Exception {
        if (getSold() - amount >= -overdraftLimit) {
            setSold(getSold() - amount);
            System.out.println("Withdraw successful. New balance: " + getSold());
        } else {
            throw new Exception("Overdraft limit exceeded in Current Account.");
        }
    }

    @Override
    public void deposit() {}

    @Override
    public String toString() {
        return "Current account: " + this.getCode() + ", " + this.getUsername() + ", " + this.getSold() + "$";
    }
}
