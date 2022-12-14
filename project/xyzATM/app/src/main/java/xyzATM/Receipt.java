package xyzATM;

import java.util.*;
import java.io.*;


enum TransType {
    WITHDRAW,
    DEPOSIT;
}

public class Receipt {

    public static int transId = 1;
    public static String receiptPath = "src/main/resources/receipts/";

    public static boolean printReceipt(Atm a, TransType type) {
        try {
            File f = new File(receiptPath + "receipt" + transId + ".txt");
            PrintWriter w = new PrintWriter(f);
            w.println("XYZ ATM Receipt");
            w.println("Transaction number: " + transId);
            w.println("Transaction type: " + type.toString());
            w.println("Transaction amount: " + String.format("%.02f", a.transAmount));
            w.println("Account balance: " + String.format("%.02f", a.insertedCard.balance));
            w.close();
            transId += 1;
        } catch (FileNotFoundException e) {
            a.display = Display.OFF;
            return false;
        }
        return true;
    }
}
