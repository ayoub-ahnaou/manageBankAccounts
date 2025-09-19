package controllers;

import models.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Scanner;

public class AccountController {
    private Map<String, BankAccount> accounts = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    // create either saving or current account
    public void createSavingAccount(String code, String username, float sold) {
        BankAccount acc = new SavingAccount(code, username, sold);
        accounts.put(code, acc);
        System.out.println("Saving account created: " + code);
    }

    public void createCurrentAccount(String code, String username, float sold, float overdraft) {
        BankAccount acc = new CurrentAccount(code, username, sold, overdraft);
        accounts.put(code, acc);
        System.out.println("Current account created: " + code);
    }

    // make a deposit operation
    public void performDeposit(String accountId, float amount) throws Exception {
        BankAccount acc = accounts.get(accountId);
        if (acc != null) {
            Operation op = new Deposit(amount);
            op.apply(acc);
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Account not found.");
        }
    }

    // make a withdrawal operation
    public void performWithdrawal(String accountId, float amount) {
        BankAccount acc = accounts.get(accountId);
        if (acc != null) {
            try {
                Operation op = new Withdrawal(amount);
                op.apply(acc);
                System.out.println("Withdrawal successful.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    // transfer from account to another
    public void transfer(String fromId, String toId, float amount) {
        BankAccount src = accounts.get(fromId);
        BankAccount dest = accounts.get(toId);

        if (src == null || dest == null) {
            System.out.println("One or both accounts not found.");
            return;
        }

        try {
            src.withdraw(amount);
            dest.deposit(amount);
            src.addOperation(new Withdrawal(amount));
            dest.addOperation(new Deposit(amount));
            System.out.println("Transfer successful.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // show account details and operations
    public void showOperations(String accountId) {
        BankAccount acc = accounts.get(accountId);
        if (acc != null) {
            // Show account details
            System.out.println("===== Account Information =====");
            System.out.println("Code      : " + acc.getCode());
            System.out.println("Username  : " + acc.getUsername());
            System.out.println("Balance   : " + acc.getSold());
            if (acc instanceof CurrentAccount) {
                CurrentAccount ca = (CurrentAccount) acc;
                System.out.println("Overdraft : " + ca.getOverdraftLimit());
            } else if (acc instanceof SavingAccount) {
                SavingAccount sa = (SavingAccount) acc;
                System.out.println("Interest  : " + sa.getInterestRate() + "%");
            }
            System.out.println("================================\n");

            acc.showOperations();
        } else {
            System.out.println("Account not found.");
        }
    }

    public void start() {
        int choice;
        int nextId = 1000;
        String code, username;
        float sold, rate, overdraft, amount;

        do {
            System.out.println("\n===== BANK SYSTEM MENU =====");
            System.out.println("1. Create Saving Account");
            System.out.println("2. Create Current Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. Show Account Operations");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = -1; // invalid
            }

            switch (choice) {
                case 1:
                    System.out.print("Username: ");
                    username = scanner.nextLine();
                    System.out.print("Initial balance: ");
                    sold = Float.parseFloat(scanner.nextLine());
                    this.createSavingAccount("CPT-" + nextId++, username, sold);
                    break;
                case 2:
                    System.out.print("Username: ");
                    username = scanner.nextLine();
                    System.out.print("Initial balance: ");
                    sold = Float.parseFloat(scanner.nextLine());
                    System.out.print("Overdraft limit: ");
                    overdraft = Float.parseFloat(scanner.nextLine());
                    this.createCurrentAccount("CPT-" + nextId++, username, sold, overdraft);
                    break;
                case 3:
                    try {
                        System.out.print("Account code: ");
                        code = scanner.nextLine();
                        System.out.print("Amount: ");
                        amount = Float.parseFloat(scanner.nextLine());
                        this.performDeposit(code, amount);
                        System.out.println("Deposit successful.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("Account code: ");
                    code = scanner.nextLine();
                    System.out.print("Amount: ");
                    amount = Float.parseFloat(scanner.nextLine());
                    this.performWithdrawal(code, amount);
                    break;
                case 5:
                    System.out.print("Source account code: ");
                    String from = scanner.nextLine();
                    System.out.print("Destination account code: ");
                    String to = scanner.nextLine();
                    System.out.print("Amount: ");
                    amount = Float.parseFloat(scanner.nextLine());
                    this.transfer(from, to, amount);
                    break;
                case 6:
                    System.out.print("Account code: ");
                    code = scanner.nextLine();
                    this.showOperations(code);
                    break;
                case 7: System.out.println("Exiting... Goodbye!"); break;
                default: System.out.println("Invalid choice. Try again."); break;
            }
        } while (choice != 7);
    }
}
