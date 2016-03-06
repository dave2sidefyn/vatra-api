package ch.bfh.projekt1.vatra.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Dieser Key hilft uns, Felder mit für VaTra wichtigen Informationen zu finden und zu definieren.
 * <p>
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

    /**
     * Ist für den Algorithmus Honypot relevant
     */
    VATRA_HONEYPOT("VaTra.Honeypot"),

    /**
     * Kann für eine Zahlung verwendet werden
     */
    VATRA_PAYMENT_AMOUNT("VaTra.Payment.Amount"),
    VATRA_PAYMENT_CURRENCY("VaTra.Payment.Currency"),

    /**
     * Wird auch im Kreditkartenvalidator verwerden!
     */
    VATRA_PAYMENT_CREDIT_CARD_NUMBER("VaTra.Payment.CreditCardNumber"),

    /**
     * Weitere Kreditkartenfelder
     */
    VATRA_PAYMENT_CREDIT_CARD_HOLDER("VaTra.Payment.CreditCardHolder"),
    VATRA_PAYMENT_CREDIT_CARD_EXPIRATION_MONTH("VaTra.Payment.CreditCardExpMonth"),
    VATRA_PAYMENT_CREDIT_CARD_EXPIRATION_YEAR("VaTra.Payment.CreditCardExpYear"),
    VATRA_PAYMENT_CREDIT_CARD_CVC("VaTra.Payment.CreditCardCvc"),

    /**
     * Longitude wird im GEOLOCATION Algorithmus verwendet!
     */
    VATRA_GEOLOCATION_LONGITUDE("VaTra.Geolocation.Longitude"),
    VATRA_GEOLOCATION_LATITUDE("VaTra.Geolocation.Latitude");

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
