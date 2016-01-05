package ch.bfh.projekt1.vatra.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by dave on 27.12.15.
 */
public enum VaTraKey {


    VATRA_API_KEY("apiKey"),

    VATRA_IDENTIFICATION_NUMBER("identification"),

    VATRA_PAYMENT_AMOUNT("amount"),

    VATRA_PAYMENT_CURRENCY("currency"),

    VATRA_PAYMENT_CREDIT_CARD_NUMBER("creditCardNumber"),

    VATRA_PAYMENT_CREDIT_CARD_HOLDER("creditCardHolder"),

    VATRA_PAYMENT_CREDIT_CARD_EXPIRATION_MONTH("creditCardExpMonth"),

    VATRA_PAYMENT_CREDIT_CARD_EXPIRATION_YEAR("creditCardExpYear"),

    VATRA_PAYMENT_CREDIT_CARD_CVC("creditCardCvc"),

    VATRA_GEOLOCATION_LONGITUDE("longitude"),

    VATRA_GEOLOCATION_LATTITUDE("latitude");

    @Nonnull
    private String id;

    VaTraKey(@Nonnull String id) {
        this.id = id;
    }

    @Nonnull
    public String getId() {
        return id;
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
