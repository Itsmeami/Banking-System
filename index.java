import java.io.*;
import java.util.*;

class InsufficientFunds extends Exception {}

class Account implements Serializable {
    private long accountNumber;
    private String firstName;
    private String lastName;
    private float balance;
    private static long nextAccountNumber = 0;

    public Account() {}

    public Account(String fname, String lname, float balance) {
        nextAccountNumber++;
        this.accountNumber = nextAccountNumber;
        this.firstName = fname;
        this.lastName = lname;
        this.balance = balance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public float getBalance() {
        return balance;
    }

    public void deposit(float amount) {
        balance += amount;
    }

    public void withdraw(float amount) throws InsufficientFunds {
        if (balance - amount < 500) {
            throw new InsufficientFunds();
        }
        balance -= amount;
    }

    public static void setLastAccountNumber(long accountNumber) {
        nextAccountNumber = accountNumber;
    }

    public static long getLastAccountNumber() {
        return nextAccountNumber;
    }

    @Override
    public String toString() {
        return "First Name: " + firstName + "\n" +
               "Last Name: " + lastName + "\n" +
               "Account Number: " + accountNumber + "\n" +
               "Balance: " + balance + "\n";
    }
}

class Bank {
    private Map<Long, Account> accounts = new HashMap<>();

    public Bank() {
        loadAccounts();
    }

    public Account openAccount(String fname, String lname, float balance) {
        Account account = new Account(fname, lname, balance);
        accounts.put(account.getAccountNumber(), account);
        saveAccounts();
        return account;
    }

    public Account balanceEnquiry(long accountNumber) {
        return accounts.get(accountNumber);
    }

    public Account deposit(long accountNumber, float amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.deposit(amount);
            saveAccounts();
        }
        return account;
    }

    public Account withdraw(long accountNumber, float amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            try {
                account.withdraw(amount);
                saveAccounts();
            } catch (InsufficientFunds e) {
                System.out.println("Insufficient funds!");
            }
        }
        return account;
    }

    public void closeAccount(long accountNumber) {
        if (accounts.remove(accountNumber) != null) {
            System.out.println("Account Deleted");
            saveAccounts();
        }
    }

    public void showAllAccounts() {
        for (Map.Entry<Long, Account> entry : accounts.entrySet()) {
            System.out.println("Account " + entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    private void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Bank.data"))) {
            accounts = (Map<Long, Account>) ois.readObject();
            Account.setLastAccountNumber(Collections.max(accounts.keySet()));
        } catch (FileNotFoundException e) {
            // File not found, start with an empty bank
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Bank.data"))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class BankingSystem {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do {
            System.out.println("\n***Banking System***");
            System.out.println("1 Open an Account");
            System.out.println("2 Balance Enquiry");
            System.out.println("3 Deposit");
            System.out.println("4 Withdrawal");
            System.out.println("5 Close an Account");
            System.out.println("6 Show All Accounts");
            System.out.println("7 Quit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter First Name: ");
                    String fname = scanner.next();
                    System.out.print("Enter Last Name: ");
                    String lname = scanner.next();
                    System.out.print("Enter Initial Balance: ");
                    float balance = scanner.nextFloat();
                    Account acc = bank.openAccount(fname, lname, balance);
                    System.out.println("Congratulations! Account is Created");
                    System.out.println(acc);
                    break;
                case 2:
                    System.out.print("Enter Account Number: ");
                    long accountNumber = scanner.nextLong();
                    acc = bank.balanceEnquiry(accountNumber);
                    if (acc != null) {
                        System.out.println("Your Account Details:");
                        System.out.println(acc);
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;
                case 3:
                    System.out.print("Enter Account Number: ");
                    accountNumber = scanner.nextLong();
                    System.out.print("Enter Amount to Deposit: ");
                    float amount = scanner.nextFloat();
                    acc = bank.deposit(accountNumber, amount);
                    if (acc != null) {
                        System.out.println("Amount Deposited");
                        System.out.println(acc);
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;
                case 4:
                    System.out.print("Enter Account Number: ");
                    accountNumber = scanner.nextLong();
                    System.out.print("Enter Amount to Withdraw: ");
                    amount = scanner.nextFloat();
                    acc = bank.withdraw(accountNumber, amount);
                    if (acc != null) {
                        System.out.println("Amount Withdrawn");
                        System.out.println(acc);
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;
                case 5:
                    System.out.print("Enter Account Number: ");
                    accountNumber = scanner.nextLong();
                    bank.closeAccount(accountNumber);
                    System.out.println("Account Closed");
                    break;
                case 6:
                    bank.showAllAccounts();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Enter a correct choice");
            }
        } while (choice != 7);

        scanner.close();
    }
}
