package models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SavingAccount extends BankAccount {
    public float interestRate;
    public LocalDate lastInterestDate = LocalDate.now();

    public SavingAccount(String code, String username, float sold, float interestRate) {
        super(code, username, sold);
        this.interestRate = interestRate;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        float interest = sold * (interestRate / 100);
        sold += interest;
        addOperation(new Deposit(interest));
        System.out.println("Interest applied: " + interest);
    }

    public void applyInterestIfDue() {
        long weeksPassed = ChronoUnit.WEEKS.between(lastInterestDate, LocalDate.now());
        if (weeksPassed >= 1) {
            applyInterest();
            lastInterestDate = LocalDate.now();
        }
    }

    @Override
    public void deposit(float amount) {
        sold += amount;
        addOperation(new Deposit(amount));
    }

    @Override
    public void withdraw(float amount) throws Exception {
        if (sold >= amount) {
            sold -= amount;
            addOperation(new Withdrawal(amount));
        } else {
            throw new Exception("Insufficient balance in saving account!");
        }
    }
}
