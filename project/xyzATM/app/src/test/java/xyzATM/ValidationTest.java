package xyzATM;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {
    public String atmConfig = "src/main/resources/AtmBalance.csv";
    public String cardConfig = "src/main/resources/CardsInSys.csv";
    public Atm atm = new Atm();

    @BeforeEach
    public void setup(){
        atm.fetch(atmConfig, cardConfig);
    }

    @Test 
    public void numExist() {
        atm.savedInput = "50000";
        assertNotNull(CardValidation.cardNumExist(atm));
    }   
    
    @Test 
    public void numNotExist() {
        atm.savedInput = "99999";
        assertNull(CardValidation.cardNumExist(atm));
    } 

    @Test
    public void validCard() {
        atm.savedInput = "50000";
        CardValidation.cardNumExist(atm);
        assertTrue(CardValidation.isValid(atm));
    }

    @Test 
    public void dateIssueStart() {
        atm.savedInput = "50005";
        CardValidation.cardNumExist(atm);
        assertFalse(CardValidation.isValid(atm));
    }

    @Test 
    public void dateIssueExp() {
        atm.savedInput = "50006";
        CardValidation.cardNumExist(atm);
        assertFalse(CardValidation.isValid(atm));
    }

    @Test 
    public void statBlock() {
        atm.savedInput = "50003";
        CardValidation.cardNumExist(atm);
        assertFalse(CardValidation.isValid(atm));
    }

    @Test 
    public void statConfiscated() {
        atm.savedInput = "50004";
        CardValidation.cardNumExist(atm);
        assertFalse(CardValidation.isValid(atm));
    }

    @Test 
    public void statLost() {
        atm.savedInput = "50001";
        CardValidation.cardNumExist(atm);
        assertFalse(CardValidation.isValid(atm));
    }

    @Test
    public void statStolen() {
        atm.savedInput = "50002";
        CardValidation.cardNumExist(atm);
        assertFalse(CardValidation.isValid(atm));
    }

    @Test 
    public void correctPin() {
        atm.savedInput = "50000";
        CardValidation.cardNumExist(atm);
        atm.savedInput = "00000";
        assertTrue(CardValidation.checkPin(atm));
    }

    @Test 
    public void incorrectPin() {
        atm.savedInput = "50000";
        CardValidation.cardNumExist(atm);
        atm.savedInput = "00001";
        assertFalse(CardValidation.checkPin(atm));
    }

    @Test 
    public void blockCard() {
        atm.savedInput = "50000";
        CardValidation.cardNumExist(atm);
        atm.savedInput = "00001";
        atm.pinAttemps = 0;
        CardValidation.checkPin(atm);
        assertEquals(atm.insertedCard.stat, Status.BLOCKED);
    }

    @Test
    public void validNum() {
        atm.savedInput = "50000";
        assertTrue(CardValidation.numCheck(atm));
    }

    @Test
    public void invalidNumExist() {
        atm.savedInput = "50001";
        assertFalse(CardValidation.numCheck(atm));
    }

    @Test
    public void invalidNumNotExist() {
        atm.savedInput = "99999";
        assertFalse(CardValidation.numCheck(atm));
    }
}
