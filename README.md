# ATM-program
This is a basic GUI based ATM application, which users can log in using a unique card number and pin to access their accounts, make withdrawals, deposits, check balances, etc.

## Description
1. The customers of XYZ Bank, who have valid ATM cards, perform three types of transactions: Withdrawal of funds, Deposit of Funds and Balance Check. As long as:
    * The card number entered by the user is valid (5 digits)
    * The card is used after the start date
    * The card is used before the expiration date, i.e., the date when the card expires
    * The card has not been reported lost or stolen
    * The customer provides the correct PIN
2. If a customer selects Withdraw, the system shall prompt the customer to enter the amount to be dispensed. 
3. If a withdrawal transaction is approved, the requested amount of cash shall be dispensed, a receipt shall be printed containing information about the transaction, and the card shall be ejected. 
4. The system shall also check if there are enough funds available in the account and if there are insufficient funds should display the available account balance
5. If a customer selects Deposited money, the system shall prompt the customer to enter the amount to be deposited.
6. If a customer selects Check Balance, the account balance should be displayed on the screen.
7. The system shall cancel any transaction if it has not been completed if the user selects the Cancel option
8. The system should also ATM administrators to perform routine maintenance by adding cash to the system.
9. If the ATM has insufficient cash available, it should provide an error message and the transaction should be cancelled


## Getting Started

### Installing
To download this ATM application from GitHub by following the steps below:
*    Choose the Download ZIP option from the Code pull-down menu.


### Executing program
1. Ensure you are at the project directory
```
.../project/xyzATM
```
2. Clean and build with Gradle
```
Gradle clean build
```
3. Run the application with Gradle
```
Gradle run
```

