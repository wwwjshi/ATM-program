package xyzATM;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DisplayTest {

    @Test
    void cancelAtAdmin(){
        Display d = Display.ADMIN;
        assertEquals(d.cancel(), Display.INITAL);
    }

    @Test
    void cancelAtSelect(){
        Display d = Display.SELECTION;
        assertEquals(d.cancel(), Display.CANCEL);
    }

    @Test
    void cancelAtDeposit(){
        Display d = Display.DEPOSIT;
        assertEquals(d.cancel(), Display.CANCEL);
    }

    @Test
    void cancelAtWithdraw(){
        Display d = Display.WITHDRAW;
        assertEquals(d.cancel(), Display.CANCEL);
    }

    @Test
    void cancelAtPin(){
        Display d = Display.PIN;
        assertEquals(d.cancel(), Display.CANCEL);
    }

    @Test
    void cancelAtNum(){
        Display d = Display.CARDNUM;
        assertEquals(d.cancel(), Display.CANCEL);
    }

    @Test
    void cancelAtINVALID(){
        Display d = Display.INVALID;
        assertEquals(d.cancel(), Display.INVALID);
    }
}