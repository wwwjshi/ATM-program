package xyzATM;

import java.time.LocalDate;
import java.util.*;
import java.io.*;

// CARD STATUS ENUM
enum Status{
    NORMAL,
    BLOCKED,
    LOST,
    STOLEN,
    CONFISCATED;
}

// CARD CLASS for testing
class Card {

    public String cardNum;
    public LocalDate startDate;
    public LocalDate expDate;
    public String pin;
    public float balance;
    public Status stat;

    // constructor
    public Card(){
    };

    public Card(String num, String pin, String start, String exp, String stat, String balance) {
        this.cardNum = num;
        this.pin = pin;
        this.startDate = LocalDate.parse(start);
        this.expDate = LocalDate.parse(exp);
        for(Status s : Status.values()){
            if(s.toString().equals(stat)) {
                this.stat = s;
            }
        }
        this.balance = Float.parseFloat(balance);
    }

    //To get the card list from the given file
    public static ArrayList<Card> getCardList(String cardsPath) {

        ArrayList<Card> cardlist = new ArrayList<Card>();
        try {
            File f = new File(cardsPath);
            Scanner sc = new Scanner(f);
            String description = sc.nextLine();
            while(sc.hasNextLine()) {
                String strCard = sc.nextLine();
                strCard.replace("\n", "");
                String[] temp = strCard.split(",", 6);
                Card c = new Card(temp[0],temp[1],temp[2],temp[3],temp[4],temp[5]);
                cardlist.add(c);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            return cardlist;
        }
        return cardlist;
    }

    public void change_balance(float new_bal) {
        this.balance = new_bal;
    }

    public void change_status(Status sta) {
        this.stat = sta;
    }

    //To update the new information into the cardlist
    public static void updateCards(Atm a, String cardsPath) {
        try {
            File f = new File(cardsPath);
            PrintWriter w = new PrintWriter(f);
            w.println("num,pin,start,exp,status,balance");
            for(Card c : a.cardsInSystem) {
                w.print(c.cardNum+",");
                w.print(c.pin+",");
                w.print(c.startDate+",");
                w.print(c.expDate+",");
                w.print(c.stat+",");
                w.print(String.format("%.02f", c.balance)+"\n");
            }
            w.close();
        } catch (FileNotFoundException e) {
            return;
        }

    }

}