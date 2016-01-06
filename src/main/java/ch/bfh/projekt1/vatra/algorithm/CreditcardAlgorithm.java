package ch.bfh.projekt1.vatra.algorithm;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Request;
import ch.bfh.projekt1.vatra.model.VaTraKey;
import ch.bfh.projekt1.vatra.service.RequestRepository;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by dave on 02.12.15.
 * <p/>
 * Überprüft, ob es möglich ist, seit dem Letzten Request die neue Ortsangabe in der Zeit zu erreichen.
 * Benötigte Felder:
 * <p/>
 * VATRA_CREDITCARD
 */
public class CreditcardAlgorithm implements Algorithm {

    @Override
    public List<VaTraKey> neededKeys() {
        return Arrays.asList(
        		VaTraKey.VATRA_PAYMENT_CREDIT_CARD_NUMBER
		);
    }

    @Override
    public int check(@Nonnull App app, @Nonnull Request request, @Nonnull CrudRepository... crudRepositories) {
    	String number = request.getVatraFields().get(VaTraKey.VATRA_PAYMENT_CREDIT_CARD_NUMBER);

    	if (isValidCardNumber(number))
    		return MIN_WEIGHT;
    	
        return MAX_WEIGHT;
    }

    public boolean isValidCardNumber(String ccNumber){
        try {
            ccNumber = ccNumber.replaceAll("\\D", "");
            char[]      ccNumberArry    = ccNumber.toCharArray();

            int         checkSum        = 0;
            for(int i = ccNumberArry.length - 1; i >= 0; i--){

                char            ccDigit     = ccNumberArry[i];

                if((ccNumberArry.length - i) % 2 == 0){
                    int doubleddDigit = Character.getNumericValue(ccDigit) * 2;
                    checkSum    += (doubleddDigit % 9 == 0 && doubleddDigit != 0) ? 9 : doubleddDigit % 9;

                }else{
                    checkSum    += Character.getNumericValue(ccDigit);
                }

            }

            return (checkSum != 0 && checkSum % 10 == 0);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }
}
