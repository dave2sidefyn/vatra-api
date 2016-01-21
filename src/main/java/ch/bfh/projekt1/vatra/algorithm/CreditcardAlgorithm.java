package ch.bfh.projekt1.vatra.algorithm;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Request;
import ch.bfh.projekt1.vatra.model.VaTraKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by dave on 02.12.15.
 * <p/>
 * Überprüft, ob Kreditkartennummer überhaupt möglich ist
 * Benötigte Felder:
 * <p/>
 * VATRA_CREDITCARD
 */
public class CreditcardAlgorithm implements Algorithm {

    private static final Logger log = LoggerFactory.getLogger(CreditcardAlgorithm.class);

    @Override
    @Nonnull
    public List<VaTraKey> neededKeys() {
        return Collections.singletonList(VaTraKey.VATRA_PAYMENT_CREDIT_CARD_NUMBER);
    }

    @Override
    public int check(@Nonnull App app, @Nonnull Request request, @Nonnull CrudRepository... crudRepositories) {
        String number = request.getVatraFields().get(VaTraKey.VATRA_PAYMENT_CREDIT_CARD_NUMBER);

        if (Objects.isNull(number)) {
            log.error("number was null!");
            return MAX_WEIGHT;
        }


        if (isValidCardNumber(number)) {
            log.info("CreditcardAlgorithm weight: 0");
            return MIN_WEIGHT;
        }

        log.info("CreditcardAlgorithm weight: 10");
        return MAX_WEIGHT;
    }

    public boolean isValidCardNumber(@Nonnull String ccNumber) {

        ccNumber = ccNumber.replaceAll("\\D", "");
        char[] ccNumberArry = ccNumber.toCharArray();

        int checkSum = 0;
        for (int i = ccNumberArry.length - 1; i >= 0; i--) {
            char ccDigit = ccNumberArry[i];
            if ((ccNumberArry.length - i) % 2 == 0) {
                int doubleddDigit = Character.getNumericValue(ccDigit) * 2;
                checkSum += (doubleddDigit % 9 == 0 && doubleddDigit != 0) ? 9 : doubleddDigit % 9;

            } else {
                checkSum += Character.getNumericValue(ccDigit);
            }

        }

        return (checkSum != 0 && checkSum % 10 == 0);
    }
}
