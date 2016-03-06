package ch.bfh.projekt1.vatra.algorithm;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Test ob der Algorithmus auf mit der richtigen Kreditkartennummer funktioniert
 * <p>
 * Created by dave on 21.01.16.
 */
public class CreditcardAlgorithmTest {


    @Test
    public void isValidCardNumber() throws Exception {
        CreditcardAlgorithm creditcardAlgorithm = new CreditcardAlgorithm();

        Assert.assertFalse(creditcardAlgorithm.isValidCardNumber("asdf"));
        Assert.assertFalse(creditcardAlgorithm.isValidCardNumber("5101970007626757"));
        Assert.assertFalse(creditcardAlgorithm.isValidCardNumber(""));
        Assert.assertTrue(creditcardAlgorithm.isValidCardNumber("5101970007626758"));
    }
}