package xyzATM;

import java.time.LocalDate;
import java.util.ArrayList;

public class CardValidation {

    /**
     * Check the card number entered is in system
     * return Card if exist
     * return Null if not
     */
    public static Card cardNumExist(Atm a){
        // loop through arraylist of cards to find matching card
        ArrayList<Card> cards = a.cardsInSystem;
        String num = a.savedInput;
        boolean found = false;
        int i = 0;
        for(Card c : cards){
            if(c.cardNum.equals(num)){
                found = true;
                a.insertedCard = c;
                return a.insertedCard;
            }
        }

        return null;
    }

    /**
     * Check if a Card is valid
     */
    public static boolean isValid(Atm a){

        // conficated card -> should not been presented
        if(a.insertedCard.stat == Status.CONFISCATED){
            a.display = Display.CONFISCATED;
            return false;
        }

        // if lost or stolen card -> conficate card
        if(a.insertedCard.stat == Status.LOST || a.insertedCard.stat == Status.STOLEN){
            a.insertedCard.stat = Status.CONFISCATED;
            a.display = Display.CONFISCATED;
            return false;
        }

        // if blocked card
        if(a.insertedCard.stat == Status.BLOCKED){
            a.display = Display.BLOCKED;
            return false;
        }

        // Card with date issues
        if(a.insertedCard.startDate.isAfter(LocalDate.now()) || a.insertedCard.expDate.isBefore(LocalDate.now())){
            a.display = Display.INVALID;
            return false;
        }

        //card is valid -> card considered inserted and display for enter PIN
        a.display = Display.PIN;
        return true;
    }


    /** 
      * An card validation process 
            - exist and valid
      * return true if validation sucess -> can continue PIN ENTERING stage
      * return false when validation false -> card blocked or confiscated -> No need for next stage
      */
    public static boolean checkPin(Atm atm){
        if(atm.savedInput.equals(atm.insertedCard.pin)){
            atm.display = Display.SELECTION;
            atm.pinAttemps = 3;
            return true;
        } else {
            atm.pinAttemps--;
            atm.badInput = true;
        }
        if(atm.pinAttemps < 1) {
            atm.display = Display.BLOCKED;
            atm.pinAttemps = 3;
            // update card also
            atm.insertedCard.stat = Status.BLOCKED;
        }
        return false;
    }

    /** 
      * card validation process 
            - check for exist and valid
      * return true if validation sucess -> can continue to PIN ENTERING stage
      * return false when validation false -> invalid card -> No need for next stage
      */
    public static boolean numCheck(Atm atm) {
        // 1. check card exist and get that card
        Card c = cardNumExist(atm);
        if(c == null){
            atm.display = Display.NOTEXIST;
            return false;
        }

        if(isValid(atm) == false) {
            return false;
        }
        // validation success
        atm.savedInput = "";
        return true;
    }



}
