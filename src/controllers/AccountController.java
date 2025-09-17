package controllers;

import models.BankAccount;
import models.CurrentAccount;
import models.SavingAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AccountController {
    private final Map<String, BankAccount> accounts = new HashMap<>();
    Scanner scanner = new Scanner(System.in);
    static int nextId = 1000;

    // create a new account either saving or current
    public void createAccount() {
        int accountType;
        BankAccount account;

        System.out.println("1- Saving account");
        System.out.println("2- Current account");
        System.out.print("Choose an account type: ");
        accountType = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter your full name: ");
        String username = scanner.nextLine();

        System.out.print("Enter you initial amount: ");
        float sold = Float.parseFloat(scanner.nextLine());

        // concat the code with the CPT prefix
        String code = "CPT-" + nextId;
        if(accountType == 1) {
            account = new SavingAccount(code, username, sold, 0.05F); // overdraft limit 300
        } else if (accountType == 2) {
            account = new CurrentAccount(code, username, sold, 300); // interest 5%
        } else {
            throw new IllegalArgumentException("Invalid type account");
        }
        accounts.put(code, account);
        System.out.println("account created with success under code " + code);
        nextId++;
    }

    // deposit into account
    public void deposit() {
        try {
            System.out.print("Enter your account id (CPT-XXXX): ");
            String accountId = scanner.nextLine();

            System.out.print("Enter amount to be deposited: ");
            float amount = Float.parseFloat(scanner.nextLine());

            BankAccount account = accounts.get(accountId);
            if(account == null) {
                System.out.println("Account didn't exist.");
                return;
            }

            account.setSold(account.getSold() + amount);
            System.out.println("Deposit made with success");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    // withdraw from account
    public void withdraw() {
        try {
            System.out.print("Enter your account id (CPT-XXXX): ");
            String accountId = scanner.nextLine();

            System.out.print("Enter amount to withdraw: ");
            float amount = Float.parseFloat(scanner.nextLine());

            BankAccount account = accounts.get(accountId);
            if(account == null) {
                System.out.println("Account didn't exist.");
                return;
            }

            account.withdraw(amount);
            account.addOperation("Withdraw", amount);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    // make transfer (using withdraw and deposit methods)
    public void transfer() {
        try {
            System.out.print("Enter your source account id (CPT-XXXX): ");
            String sourceId = scanner.nextLine();

            System.out.print("Enter destination account id (CPT-XXXX): ");
            String destinationId = scanner.nextLine();

            System.out.print("Enter amount to transfer: ");
            float amount = Float.parseFloat(scanner.nextLine());

            BankAccount source = accounts.get(sourceId);
            BankAccount destination = accounts.get(destinationId);

            if (source == null) {
                System.out.println("Source account does not exist.");
                return;
            }
            if (destination == null) {
                System.out.println("Destination account does not exist.");
                return;
            }

            // Check balance in source account
            if (source.getSold() >= amount) {
                // Withdraw from source
                source.setSold(source.getSold() - amount);
                // Deposit into destination
                destination.setSold(destination.getSold() + amount);

                System.out.println("Transfer made with success");
                System.out.println("New balance of " + sourceId + ": " + source.getSold() + "$");
                System.out.println("New balance of " + destinationId + ": " + destination.getSold() + "$");
            } else {
                System.out.println("Insufficient funds in source account.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    // show account details
    public void showAccountDetails() {
        System.out.print("Enter your account id: ");
        String accountId = scanner.nextLine();

        BankAccount account = accounts.get(accountId);
        if(account == null) {
            System.out.println("Account didn't exist.");
            return;
        }
        System.out.println(account.toString());
    }

    // show all accounts
    public void showAccounts() {
        for (Map.Entry<String, BankAccount> entry : accounts.entrySet()) {
            BankAccount account = entry.getValue();
            System.out.println(account.toString());
        }
    }
}
