package xyzATM;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.*;


public class ReceiptTest {

    public String cardfile = "src/main/resources/CardsInSys.csv";
    public String atmfile = "src/main/resources/AtmBalance.csv";
    public ArrayList<Card> cards = new ArrayList<>();
    public Atm a = new Atm();

    @BeforeEach
    public void setup() {
        cards = Card.getCardList(cardfile);
        a.fetch(atmfile, cardfile);
    }

    @Test
    public void testReceipt() {
        a.insertedCard = cards.get(6);
        a.transAmount = 1000;
        TransType t = TransType.DEPOSIT;
        assertTrue(Receipt.printReceipt(a, t));
    }


}