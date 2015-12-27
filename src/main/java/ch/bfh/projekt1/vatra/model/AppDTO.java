package ch.bfh.projekt1.vatra.model;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * Created by dave on 02.12.15.
 */
public class AppDTO {

    @Nonnull
    String id;

    @Nonnull
    String name;

    @Nonnull
    Long request;

    @Nonnull
    Long valid;

    @Nonnull
    Long invalid;

    @Nonnull
    String apiKey;

    @Nonnull
    Date lastRequest;

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
