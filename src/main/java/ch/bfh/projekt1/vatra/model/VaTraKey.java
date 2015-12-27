package ch.bfh.projekt1.vatra.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by dave on 27.12.15.
 */
public enum VaTraKey {


    VATRA_API_KEY("vatra.apikey", String.class),

    VATRA_IDENTIFICATION_NUMBER("vatra.identification.number", String.class),

    VATRA_PAYMENT_RECEIVER_IBAN("vatra.payment.receiver.iban", String.class),

    VATRA_PAYMENT_RECEIVER_FIRSTNAME("vatra.payment.receiver.firstname", String.class),

    VATRA_PAYMENT_RECEIVER_LASTNAME("vatra.payment.receiver.lastname", String.class),

    VATRA_PAYMENT_SENDER_IBAN("vatra.payment.sender.iban", String.class),

    VATRA_PAYMENT_SENDER_FIRSTNAME("vatra.payment.sender.firstname", String.class),

    VATRA_PAYMENT_SENDER_LASTNAME("vatra.payment.sender.lastname", String.class),

    VATRA_GEOLOCATION_LONGITUDE("vatra.geolocation.longitude", Double.class),

    VATRA_GEOLOCATION_LATTITUDE("vatra.geolocation.lattitude", Double.class);

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
