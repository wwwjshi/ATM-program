package xyzATM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;

// ATM STATUS ENUM
enum Display{
    INITAL,
    ADMIN,
    CARDNUM,
    NOTEXIST,
    INVALID,
    CONFISCATED,//
    BLOCKED,//
    PIN,
    SELECTION,
    BALANCE,
    WITHDRAW,//
    DEPOSIT,//
    SUCCESS,
    NOCARDFUND,
    NOATMFUND,
    CANCEL,
    FINISH,
    OFF;

    public Display cancel(){
        if(this == ADMIN){
            return INITAL;
        } else if(this == SELECTION || this == DEPOSIT || this == WITHDRAW || this == PIN || this == CARDNUM) {
            return CANCEL;
        }
        return this;
    }


}



// EXAMPLE CARD CLASS for testing
public class Atm {

    public float[] values = {100, 50, 20, 10, 5, 2, 1, (float)0.5, (float)0.2, (float)0.1, (float)0.05};
    public int[] numOfEachValue = new int[11];
    public float balance;
    public Display display = Display.INITAL;
    public String savedInput = "";
    public Card insertedCard = new Card();
    public int pinAttemps = 3;
    public boolean badInput = false;
    public float transAmount = 0;

    public ArrayList<Card> cardsInSystem = new ArrayList<Card>();

    public String configPath;
    public String configHeader;


    // constructor
    public void Atm(){
    }

    /**
     * Fetch Atm data
     * -> get atm balance
     * -> load info of all cards in system
     */
    public void fetch(String configPath, String cardsPath) {
        this.cardsInSystem = Card.getCardList(cardsPath);
        this.configPath = configPath;
        this.balance = 0;
        try {
            File f = new File(configPath);
            Scanner sc = new Scanner(f);
            this.configHeader = sc.nextLine(); // header
            String line = sc.nextLine();
            line.replace("\n", "");
            String[] strVals = line.split(",", 11);
            int i = 0;
            while(i < numOfEachValue.length) {
                numOfEachValue[i] = Integer.parseInt(strVals[i]);
                this.balance += numOfEachValue[i] * values[i];
                i++;
            }
            sc.close();
        } catch (FileNotFoundException e) {
            this.display = Display.OFF;
        }
    }

    /**
     * Update atm data
     * -> write balance to file
     */
    public void update(String configPath) {
        String updated_config = configHeader + '\n';
        int i = 0;
        char separ = ',';
        while(i < numOfEachValue.length) {
            if(i == numOfEachValue.length - 1){
                separ = '\n';
            }
            updated_config += Integer.toString(numOfEachValue[i])+ separ;
            i++;
        }
        try {
            FileWriter fw = new FileWriter(configPath);
            fw.write(updated_config);
            fw.close();
        } catch (java.io.IOException e) {
            this.display = Display.OFF;
        }
    }


    /**
     * admin adding balanace
     */
    public void adminAddBalance(int[] nums, float amount){
        int i = 0;
        while(i < numOfEachValue.length) {
            this.numOfEachValue[i] += nums[i];
            this.balance += nums[i] * values[i];
            i++;
        }

    }


    /**
     * deposit
     */
    public void deposit(int[] nums, float amount){
        int i = 0;
        while(i < numOfEachValue.length) {
            this.numOfEachValue[i] += nums[i];
            this.balance += nums[i] * values[i];
            i++;
        }
        this.insertedCard.balance += amount;

        Receipt.printReceipt(this, TransType.DEPOSIT);
        this.display = Display.SUCCESS;
    }


    /**
     * withdraw
     */
    public boolean withdraw(int[] nums, float amount){
        // not enough card balance
        if(this.insertedCard.balance < amount) {
            this.display = Display.NOCARDFUND;
            return false;
        }

        // not enough specific notes/coins
        int i = 0;
        while(i < numOfEachValue.length) {
            if(this.numOfEachValue[i] < nums[i]){
                this.display = Display.NOATMFUND;
                return false;
            }
            i++;
        }

        // passed all above -> make transaction
        i = 0;
        while(i < numOfEachValue.length) {
            this.numOfEachValue[i] -= nums[i];
            i++;
        }
        this.balance -= amount;
        this.insertedCard.balance -= amount;
        //
        // also update card csv
        Receipt.printReceipt(this, TransType.WITHDRAW);
        this.display = Display.SUCCESS;
        return true;
    }



    public void makeTransaction(int[] nums, float amount) {
        this.transAmount = amount;
        if(this.display == Display.ADMIN) {
            adminAddBalance(nums, amount);
        }
        else if(this.display == Display.WITHDRAW) {
            withdraw(nums, amount);
        }
        else if(this.display == Display.DEPOSIT) {
            deposit(nums, amount);
        }

        update(this.configPath);
        transAmount = 0;
    }



    public void tick(App app) {
        if(this.display == Display.INITAL) {
            if(app.userInput.length() >= 1) {
                app.userInput = app.userInput.substring(0, 1);
            }
            if(this.savedInput.equals("1")){
                this.display = Display.CARDNUM;
            }
            if(this.savedInput.equals("2")){
                this.display = Display.ADMIN;
            }
            this.savedInput = "";
        }
        else if(this.display == Display.ADMIN) {
            if(this.savedInput.length() > 0) {
                this.savedInput = "";
            }
        }
        else if(this.display == Display.CARDNUM) {
            if(app.userInput.length() >= 5) {
                app.userInput = app.userInput.substring(0, 5);
            }
            if(this.savedInput.length() == 5) {
                CardValidation.numCheck(this);
                this.savedInput = "";
            }
        }
        else if(this.display == Display.PIN) {
            if(app.userInput.length() >= 5) {
                app.userInput = app.userInput.substring(0, 5);
            }
            if(this.savedInput.length() == 5) {
                CardValidation.checkPin(this);
                this.savedInput = "";
            }
        }
        else if(this.display == Display.SELECTION) {
            if(app.userInput.length() >= 1) {
                app.userInput = app.userInput.substring(0, 1);
            }
            // -> balance
            if(this.savedInput.equals("3")){
                this.display = Display.BALANCE;
            }
            // -> deposit
            if(this.savedInput.equals("2")){
                this.display = Display.DEPOSIT;
            }
            // -> withdraw
            if(this.savedInput.equals("1")){
                this.display = Display.WITHDRAW;
            }
            this.savedInput = "";
        }
        else if(this.display == Display.DEPOSIT
                || this.display == Display.WITHDRAW) {
            if(this.savedInput.length() > 0) {
                this.savedInput = "";
            }
        }
        else if(this.display == Display.SUCCESS
                || this.display == Display.BALANCE
                || this.display == Display.NOCARDFUND
                || this.display == Display.NOATMFUND){
            app.returnDisplay(Display.FINISH);
        }

        // Issue/finishing display -> wait for few sec -> back to inital stage
        else {
            // save cards infos
            Card.updateCards(this, app.cardConfig);
            app.returnDisplay(Display.INITAL);
        }

    }



}