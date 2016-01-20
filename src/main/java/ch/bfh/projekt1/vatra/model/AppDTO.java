package ch.bfh.projekt1.vatra.model;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * Die Entität AppDTO ist hier, um bei Apps für den Client sinnvolle Informationen zu übertragen.
 * <p>
 * Created by dave on 02.12.15.
 */
public class AppDTO {

    @Nonnull
    private String id = "";

    @Nonnull
    private String name = "";

    @Nonnull
    private Long request = 0L;

    @Nonnull
    private Long valid = 0L;

    @Nonnull
    private Long invalid = 0L;

    @Nonnull
    private String apiKey = "";

    @Nonnull
    private Date lastRequest = new Date();

    @Nonnull
    public String getId() {
        return id;
    }

    public void setId(@Nonnull String id) {
        this.id = id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    public Long getRequest() {
        return request;
    }

    public void setRequest(@Nonnull Long request) {
        this.request = request;
    }

    @Nonnull
    public Long getValid() {
        return valid;
    }

    public void setValid(@Nonnull Long valid) {
        this.valid = valid;
    }

    @Nonnull
    public Long getInvalid() {
        return invalid;
    }

    public void setInvalid(@Nonnull Long invalid) {
        this.invalid = invalid;
    }

    @Nonnull
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(@Nonnull String apiKey) {
        this.apiKey = apiKey;
    }

    @Nonnull
    public Date getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(@Nonnull Date lastRequest) {
        this.lastRequest = lastRequest;
    }
}
