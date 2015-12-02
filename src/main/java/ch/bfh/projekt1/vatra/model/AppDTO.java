package ch.bfh.projekt1.vatra.model;

import java.util.Date;

/**
 * Created by dave on 02.12.15.
 */
public class AppDTO {

    String id;
    String name;
    Long request;
    Long valid;
    Long invalid;
    Date lastRequest;

    public AppDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRequest() {
        return request;
    }

    public void setRequest(Long request) {
        this.request = request;
    }

    public Long getValid() {
        return valid;
    }

    public void setValid(Long valid) {
        this.valid = valid;
    }

    public Long getInvalid() {
        return invalid;
    }

    public void setInvalid(Long invalid) {
        this.invalid = invalid;
    }

    public Date getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(Date lastRequest) {
        this.lastRequest = lastRequest;
    }
}
