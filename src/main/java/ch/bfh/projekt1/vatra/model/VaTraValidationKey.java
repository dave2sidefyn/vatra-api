package ch.bfh.projekt1.vatra.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by dave on 27.12.15.
 */
public enum VaTraValidationKey {

    VATRA_REGEX_EMAIL("VaTra.Regex.email", "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"),

    VATRA_REGEX_URL("VaTra.Regex.URL", "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

    @Nonnull
    private String id;


    @Nonnull
    private String regex;

    VaTraValidationKey(@Nonnull String id, @Nonnull String regex) {
        this.id = id;
        this.regex = regex;
    }

    @Nonnull
    public String getId() {
        return id;
    }

    @Nonnull
    public String getRegex() {
        return regex;
    }

    @Nullable
    public static VaTraValidationKey getWithId(String id) {
        for (VaTraValidationKey vaTraKey : values()) {
            if (vaTraKey.getId().equals(id)) {
                return vaTraKey;
            }
        }
        return null;
    }
}
