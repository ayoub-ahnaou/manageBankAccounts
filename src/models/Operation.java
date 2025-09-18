package models;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Operation {
    public UUID numero;
    public float amount;
    public LocalDate date;
    public String type;

    public Operation(String type, float amount) {
        this.numero = UUID.randomUUID();
        this.amount = amount;
        this.date = LocalDate.now();
        this.type = type;
    }

    public abstract void apply(BankAccount account) throws Exception;

    @Override
    public String toString() {
        return "[" + date + "] " + type + " | Amount: " + amount + " | ID: " + numero;
    }
}
