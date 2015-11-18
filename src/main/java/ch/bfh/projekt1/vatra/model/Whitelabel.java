package ch.bfh.projekt1.vatra.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

/**
 * Created by dave on 23.10.15.
 */
@Entity
public class Whitelabel {
    @Id
    private String id = UUID.randomUUID().toString();

    private String name;

    @ManyToOne
    private App app;


    public Whitelabel() {
    }

    public Whitelabel(String id, String name, App app) {
        this.id = id;
        this.name = name;
        this.app = app;
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

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    @Override
    public String toString() {
        return "Whitelabel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", secure=" + app +
                '}';
    }
}
