package ch.bfh.projekt1.vatra.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by dave on 27.12.15.
 */
public enum VaTraKey {


    VATRA_API_KEY("apiKey", String.class),

    VATRA_IDENTIFICATION_NUMBER("identification", String.class),

    VATRA_PAYMENT_AMOUNT("amount", String.class),
    
    VATRA_PAYMENT_CURRENCY("currency", String.class),
    
    VATRA_PAYMENT_CREDIT_CARD_NUMBER("creditCardNumber", String.class),
    
    VATRA_PAYMENT_CREDIT_CARD_HOLDER("creditCardHolder", String.class),
    
    VATRA_PAYMENT_CREDIT_CARD_EXPIRATION_MONTH("creditCardExpMonth", String.class),
    
    VATRA_PAYMENT_CREDIT_CARD_EXPIRATION_YEAR("creditCardExpYear", String.class),
    
    VATRA_PAYMENT_CREDIT_CARD_CVC("creditCardCvc", String.class),

    VATRA_GEOLOCATION_LONGITUDE("longitude", Double.class),

    VATRA_GEOLOCATION_LATTITUDE("latitude", Double.class);

    @Nonnull
    private String id;
    @Nonnull
    private Class type;

    VaTraKey(@Nonnull String id, @Nonnull Class type) {
        this.id = id;
        this.type = type;
    }

    @Nonnull
    public String getId() {
        return id;
    }

    @Nonnull
    public Class getType() {
        return type;
    }

    @Nullable
    public static VaTraKey getWithId(String id) {
        for (VaTraKey vaTraKey : values()) {
            if (vaTraKey.getId().equals(id)) {
                return vaTraKey;
            }
        }
        return null;
    }
}
