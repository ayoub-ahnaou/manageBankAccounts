package models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SavingAccount extends BankAccount {
    public float interestRate;
    private LocalDate lastInterestDate;

    public SavingAccount(String code, String username, float sold, float interestRate) {
        super(code, username, sold);
        this.interestRate = interestRate;
        this.lastInterestDate = LocalDate.now();
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(float amount) throws Exception {
        calculateInterest();
        if (getSold() >= amount) {
            setSold(getSold() - amount);
            System.out.println("Withdraw successful. New balance: " + getSold());
        } else {
            throw new Exception("Insufficient funds in Saving Account.");
        }
    }

    @Override
    public void deposit() {}

    public void calculateInterest() {
        long weeksPassed = ChronoUnit.WEEKS.between(lastInterestDate, LocalDate.now());

        // interest applied every week
        if (weeksPassed >= 1) {
            float interest = getSold() * (interestRate / 100);
            setSold(getSold() + interest);
            lastInterestDate = LocalDate.now();
            System.out.println("Interest of " + interest + " applied. New balance: " + getSold());
        } else {
            System.out.println("Interest not due yet. Last applied: " + lastInterestDate);
        }
    }

    @Override
    public String toString() {
        return "Saving account: " + this.getCode() + ", " + this.getUsername() + ", " + this.getSold() + "$";
    }
}
