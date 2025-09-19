package models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SavingAccount extends BankAccount {
    public float interestRate = 0.05F;
    public LocalDate lastInterestDate = LocalDate.now();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);;

    public SavingAccount(String code, String username, float sold) {
        super(code, username, sold);

        Runnable task = this::applyInterest;

        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        float interest = getSold() * (interestRate);
        setSold(getSold() + interest);
        addOperation(new Deposit(interest));
        lastInterestDate = LocalDate.now();
        System.out.println("Interest applied: " + interest + ", new sold: " + getSold());
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
