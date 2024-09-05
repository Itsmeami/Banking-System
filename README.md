# Banking-System
Banking System using Java

**Overview**
The project is a simple banking system implemented in Java that allows users to manage bank accounts with functionalities like creating accounts, depositing, withdrawing, closing accounts, and viewing account details. The data is persisted using serialization to read from and write to a file (Bank.data).

**Components**
Account Class

**Attributes:**
accountNumber: Unique identifier for the account.
firstName: Account holder's first name.
lastName: Account holder's last name.
balance: The balance in the account.
nextAccountNumber: Static variable to generate unique account numbers.

**Methods:**
Constructors: Initializes an account with the given details and generates a unique account number.
Getters: Methods to retrieve account details.
Deposit: Increases the account balance by the specified amount.
Withdraw: Decreases the account balance by the specified amount if it doesn't fall below the minimum balance (500). Throws InsufficientFunds if there aren’t enough funds.
Static Methods: setLastAccountNumber and getLastAccountNumber for managing the last used account number.
toString: Returns a string representation of the account details.
Bank Class

**Attributes:**
accounts: A HashMap to store accounts, using the account number as the key and Account object as the value.

**Methods:**
Constructor: Loads existing accounts from the Bank.data file if it exists.
Open Account: Creates a new account and saves it to the file.
Balance Enquiry: Retrieves the details of an account using the account number.
Deposit: Adds funds to an existing account and updates the file.
Withdraw: Removes funds from an existing account, if sufficient funds are available, and updates the file.
Close Account: Removes an account from the system and updates the file.
Show All Accounts: Displays details of all accounts.
Load Accounts: Reads account data from the Bank.data file using object deserialization.
Save Accounts: Writes account data to the Bank.data file using object serialization.
BankingSystem Class

**Main Method:**
Menu: Presents a menu to the user to choose various banking operations.
User Interaction: Takes input from the user for performing operations such as opening an account, checking balance, depositing, withdrawing, closing accounts, and showing all accounts.

**Operation Handling:** Executes the selected operation and displays appropriate messages.
Working of the Project

**Initialization:**
When the Bank object is created, it attempts to load previously saved account data from Bank.data. If the file doesn’t exist or can’t be read, it starts with an empty bank.

**Creating an Account:**
User provides details like first name, last name, and initial balance. The system creates a new Account object with a unique account number and saves it to the file.


**Balance Enquiry:**
User inputs an account number, and the system retrieves and displays the details of the account.

**Depositing Funds:**
User inputs an account number and the amount to deposit. The system updates the account balance and saves the updated details to the file.

**Withdrawing Funds:**
User inputs an account number and the amount to withdraw. The system checks if the balance after withdrawal is above the minimum balance (500) and updates the balance accordingly. If not enough funds are available, an InsufficientFunds exception is thrown.
Closing an Account:

User inputs an account number. The system deletes the account from the bank and updates the file.

**Showing All Accounts:**
The system iterates through all accounts and displays their details.

**File Operations:**

Saving: When any modification is made (like creating, updating, or closing an account), the system writes all account details to Bank.data.
Loading: On initialization, it reads account details from Bank.data.

**Additional Notes**
Serialization: Used to persist account data between program runs. Java’s ObjectOutputStream and ObjectInputStream are used for writing and reading objects to/from a file.
Exception Handling: Custom exception InsufficientFunds is used to handle scenarios where a withdrawal would result in an account balance below the minimum required balance.
User Input: Uses Scanner for reading input from the user.
