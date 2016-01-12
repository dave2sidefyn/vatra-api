package ch.bfh.projekt1.vatra.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by dave on 23.10.15.
 */
@Entity
public class Request {

    private static final String DEFAULT_IDENTIFY = "";
    private static final String DEFAULT_CLIENTINFORMATION = "";

    @Id
    private String id = UUID.randomUUID().toString();

    @Nonnull
    @NotEmpty
    @NotNull
    private String identify = DEFAULT_IDENTIFY;

    @ManyToOne
    @Nonnull
    @NotNull
    private App app;

    private boolean valid;

    @Nonnull
    @NotEmpty
    @NotNull
    private String clientInformation = DEFAULT_CLIENTINFORMATION;


    @Nonnull
    @ElementCollection(targetClass = String.class)
    @MapKeyEnumerated(EnumType.STRING)
    private Map<VaTraKey, String> vatraFields = new HashMap<>();

    @Nonnull
    @ElementCollection(targetClass = String.class)
    @MapKeyEnumerated(EnumType.STRING)
    private Map<VaTraValidationKey, String> vatraValidationFields = new HashMap<>();

    @Nonnull
    @ElementCollection(targetClass = String.class)
    private Map<String, String> manualFields = new HashMap<>();

    @NotNull
    @Nonnull
    private Date createdDate = new Date();


    //For SpringBoot Entity Initialize
    public Request() {
        this.app = new App();
    }

    public Request(@Nonnull String identify, @Nonnull App app, @Nonnull String clientInformation) {
        this.identify = identify;
        this.app = app;
        this.clientInformation = clientInformation;
    }


    public Request(@Nonnull String identify, @Nonnull App app, @Nonnull String clientInformation, boolean valid, @Nonnull Date createdDate) {
        this.identify = identify;
        this.app = app;
        this.clientInformation = clientInformation;
        this.valid = valid;
        this.createdDate = createdDate;
    }

    @Nonnull
    public String getId() {
        return id;
    }

    public void setId(@Nonnull String id) {
        this.id = id;
    }

    @Nonnull
    public String getIdentify() {
        return identify;
    }

    public void setIdentify(@Nonnull String identify) {
        this.identify = identify;
    }

    @Nonnull
    public App getApp() {
        return app;
    }

    public void setApp(@Nonnull App app) {
        this.app = app;
    }

    @Nonnull
    public String getClientInformation() {
        return clientInformation;
    }

    public void setClientInformation(@Nonnull String clientInformation) {
        this.clientInformation = clientInformation;
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isInValid() {
        return !valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Nonnull
    public Map<VaTraKey, String> getVatraFields() {
        return vatraFields;
    }

    public void setVatraFields(@Nonnull Map<VaTraKey, String> vatraFields) {
        this.vatraFields = vatraFields;
    }

    @Nonnull
    public Map<VaTraValidationKey, String> getVatraValidationFields() {
        return vatraValidationFields;
    }

    public void setVatraValidationFields(@Nonnull Map<VaTraValidationKey, String> vatraValidationFields) {
        this.vatraValidationFields = vatraValidationFields;
    }

    @Nonnull
    public Map<String, String> getManualFields() {
        return manualFields;
    }

    public void setManualFields(@Nonnull Map<String, String> manualFields) {
        this.manualFields = manualFields;
    }

    @Nonnull
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(@Nonnull Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", identify='" + identify + '\'' +
                ", secure=" + app +
                ", clientInformation='" + clientInformation + '\'' +
                ", valid='" + valid + '\'' +
                '}';
    }
}
