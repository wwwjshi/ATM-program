package xyzATM;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.*;

class CardTest {

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
    public void checkBalance() {
        Card c1 = cards.get(1);
        c1.change_balance(1000);
        assertEquals(c1.balance,1000);
    }

    @Test
    public void checkStatus() {
        Card c1 = cards.get(2);
        c1.change_status(Status.LOST);
        assertEquals(c1.stat,Status.LOST);
    }


    @Test
    public void checkWriteFile() {
        Card.updateCards(a,cardfile);
        File f = new File(cardfile);
        assertTrue(f.exists());
    }
}