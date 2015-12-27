package ch.bfh.projekt1.vatra.model;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

/**
 * Created by dave on 23.10.15.
 */
@Entity
public class Whitelabel {

    @Nonnull
    @Id
    private String id = UUID.randomUUID().toString();

    @Nonnull
    private String name;

    @ManyToOne
    @Nonnull
    private App app;


    public Whitelabel() {
    }

    public Whitelabel(@Nonnull String name, @Nonnull App app) {
        this.name = name;
        this.app = app;
    }

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
    public App getApp() {
        return app;
    }

    public void setApp(@Nonnull App app) {
        this.app = app;
    }

    @Override
    public String toString() {
        return "Whitelabel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", app=" + app +
                '}';
    }
}
