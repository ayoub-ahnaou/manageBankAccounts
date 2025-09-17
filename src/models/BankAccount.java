package models;

import java.util.ArrayList;

public abstract class BankAccount {
    public String code;
    public String username;
    public float sold;
    public ArrayList<Operation> operations = new ArrayList<>();

    public BankAccount(String code, String username, float sold) {
        this.code = code;
        this.username = username;
        this.sold = sold;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getSold() {
        return sold;
    }

    public void setSold(float sold) {
        this.sold = sold;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public abstract void withdraw(float amount) throws  Exception;
    public abstract void deposit();

    // add the operation to operations list
    public void addOperation(String type, float amount) {
        Operation op = new Operation(type, amount);
        operations.add(op);
    }

    // show all the operations
    public void showOperations() {
        if (operations.isEmpty()) {
            System.out.println("No operations yet.");
        } else {
            for (Operation op : operations) {
                System.out.println(op);
            }
        }
    }
}
