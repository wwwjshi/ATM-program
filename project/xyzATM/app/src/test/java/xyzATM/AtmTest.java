package xyzATM;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AtmTest {

    public String atmConfig_2 = "src/main/resources/AtmTest.csv";
    public String atmConfig_3 = "src/main/resources/NewAtmTest.csv";
    public String cardConfig_2 = "src/main/resources/CardsInSys.csv";
    public Atm atm_2 = new Atm();

    @BeforeEach
    public void initAtm(){

        atm_2.fetch(atmConfig_2, cardConfig_2);
        atm_2.insertedCard.balance = (float)0.0;
    }

    @AfterEach
    public void tearDown(){
        this.atm_2 = null;
    }

    @Test
    void testFetch(){

        assertEquals((float)27.2, atm_2.balance);
    }

    @Test
    void testAdminAdd(){

        atm_2.adminAddBalance(new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, (float)100);

        assertEquals((float)127.2, atm_2.balance);
    }

    @Test
    void testDeposit(){
        atm_2.deposit(new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, (float)100);
        assertEquals((float)127.2, atm_2.balance);
        assertEquals((float)100.0, atm_2.insertedCard.balance);
    }

    @Test
    void testWithdraw(){
        atm_2.deposit(new int[]{1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, (float)150);
        boolean output1 = atm_2.withdraw(new int[]{1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0}, (float)200);
        assertFalse(output1);

        //1,1,1,0,1,1,0,0,1,0,0
        boolean output2 = atm_2.withdraw(new int[]{1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}, (float)110);
        assertFalse(output2);

        boolean output3 = atm_2.withdraw(new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, (float)100);
        assertTrue(output3);
        assertEquals((float)77.2, atm_2.balance);
        assertEquals((float)50, atm_2.insertedCard.balance);
    }

    @Test
    void testUpdate(){
        atm_2.adminAddBalance(new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, (float)100);
        atm_2.update(atmConfig_3);
        Atm atm_3 = new Atm();
        atm_3.fetch(atmConfig_3, cardConfig_2);
        assertEquals((float)127.2, atm_2.balance);

    }

    @Test
    void testTick_1() {
        App a_1 = new App();
        atm_2.display = Display.INITAL;
        a_1.userInput = "123";
        atm_2.tick(a_1);
        assertEquals("1", a_1.userInput);


        atm_2.savedInput = "1";
        atm_2.tick(a_1);
        assertEquals(Display.CARDNUM, atm_2.display);

        atm_2.savedInput = "2";
        atm_2.display = Display.INITAL;
        atm_2.tick(a_1);
        assertEquals(Display.ADMIN, atm_2.display);
    }

    @Test
    void testTick_2(){
        App a_2 = new App();
        atm_2.display = Display.ADMIN;
        atm_2.savedInput = "123";
        atm_2.tick(a_2);
        assertEquals("", atm_2.savedInput);


    }

    @Test
    void testTick_3(){
        App a_3 = new App();
        atm_2.display = Display.CARDNUM;
        a_3.userInput = "123456";
        atm_2.tick(a_3);
        assertEquals("12345", a_3.userInput);


        atm_2.savedInput = "12345";
        atm_2.tick(a_3);
        assertEquals("", atm_2.savedInput);


    }

    @Test
    void testTick_4(){
        App a_4 = new App();
        atm_2.display = Display.PIN;
        a_4.userInput = "123456";
        atm_2.tick(a_4);
        assertEquals("12345", a_4.userInput);

        atm_2.savedInput = "12345";
        atm_2.tick(a_4);
        assertEquals("", atm_2.savedInput);
    }


    @Test
    void testTick_5(){
        App a_5 = new App();
        atm_2.display = Display.SELECTION;
        a_5.userInput = "123";
        atm_2.tick(a_5);
        assertEquals("1", a_5.userInput);

        atm_2.savedInput = "3";
        atm_2.tick(a_5);
        assertEquals(Display.BALANCE, atm_2.display);

        atm_2.display = Display.SELECTION;
        atm_2.savedInput = "2";
        atm_2.tick(a_5);
        assertEquals(Display.DEPOSIT, atm_2.display);

        atm_2.display = Display.SELECTION;
        atm_2.savedInput = "1";
        atm_2.tick(a_5);
        assertEquals(Display.WITHDRAW, atm_2.display);
        assertEquals("", atm_2.savedInput);


    }

    @Test
    void testTick_6(){
        App a_6 = new App();
        atm_2.display = Display.DEPOSIT;
        atm_2.savedInput = "123";
        atm_2.tick(a_6);
        assertEquals("", atm_2.savedInput);
    }

    @Test
    void testMakeTrans(){
        String atmConfig_3 = "src/main/resources/AtmForTrans.csv";
        Atm atm_3 = new Atm();
        atm_3.fetch(atmConfig_3, cardConfig_2);
        atm_3.insertedCard.balance = (float)0.0;
        assertEquals((float)27.2, atm_3.balance);


        atm_3.display = Display.ADMIN;
        atm_3.makeTransaction(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, (float)0);
        assertEquals((float)27.2, atm_3.balance);

        atm_3.display = Display.WITHDRAW;
        atm_3.makeTransaction(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, (float)0);
        assertEquals((float)27.2, atm_3.balance);

        atm_3.display = Display.DEPOSIT;
        atm_3.makeTransaction(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, (float)0);
        assertEquals((float)27.2, atm_3.balance);


    }

    @Test
    void testTick_7() {
        App a_7 = new App();
        atm_2.display = Display.SUCCESS;

        atm_2.tick(a_7);
        assertEquals("", a_7.userInput );
    }






}
