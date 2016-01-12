package ch.bfh.projekt1.vatra.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by dave on 27.12.15.
 */
public enum VaTraKey {


    /**
     * Obligatorisch!
     */
    VATRA_API_KEY("VaTra.ApiKey"),

    /**
     * Obligatorisch!
     */
    VATRA_IDENTIFICATION("VaTra.Identification"),

    VATRA_HONEYPOT("VaTra.Honeypot"),

    VATRA_PAYMENT_AMOUNT("VaTra.Payment.Amount"),

    VATRA_PAYMENT_CURRENCY("VaTra.Payment.Currency"),

    VATRA_PAYMENT_CREDIT_CARD_NUMBER("VaTra.Payment.CreditCardNumber"),

    VATRA_PAYMENT_CREDIT_CARD_HOLDER("VaTra.Payment.CreditCardHolder"),

    VATRA_PAYMENT_CREDIT_CARD_EXPIRATION_MONTH("VaTra.Payment.CreditCardExpMonth"),

    VATRA_PAYMENT_CREDIT_CARD_EXPIRATION_YEAR("VaTra.Payment.CreditCardExpYear"),

    VATRA_PAYMENT_CREDIT_CARD_CVC("VaTra.Payment.CreditCardCvc"),

    VATRA_GEOLOCATION_LONGITUDE("VaTra.Geolocation.longitude"),

    VATRA_GEOLOCATION_LATTITUDE("VaTra.Geolocation.latitude");

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
