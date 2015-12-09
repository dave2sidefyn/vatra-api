package ch.bfh.projekt1.vatra.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;

/**
 * Created by dave on 23.10.15.
 */
@Entity
public class Request {

    @Id
    private String id = UUID.randomUUID().toString();

    private String identify;

    @ManyToOne
    private App app;

    private boolean valid;

    private String clientInformation;

    private Date createdDate;

    public Request() {
    }

    public Request(String identify, App app, String clientInformation, boolean valid, Date createdDate) {
        this.identify = identify;
        this.app = app;
        this.clientInformation = clientInformation;
        this.valid = valid;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public String getClientInformation() {
        return clientInformation;
    }

    public void setClientInformation(String clientInformation) {
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


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
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
