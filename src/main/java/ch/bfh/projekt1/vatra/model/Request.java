package ch.bfh.projekt1.vatra.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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

    private String clientInformation;

    public Request() {
    }

    public Request(String id, String identify, App app, String clientInformation) {
        this.id = id;
        this.identify = identify;
        this.app = app;
        this.clientInformation = clientInformation;
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

    @Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", identify='" + identify + '\'' +
                ", app=" + app +
                ", clientInformation='" + clientInformation + '\'' +
                '}';
    }
}
