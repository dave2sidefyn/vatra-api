package ch.bfh.projekt1.vatra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Die Entität Whitelistlabel ist hier, für die Whitelistlabels zu definieren.
 * <p>
 * Created by dave on 23.10.15.
 */
@Entity
public class Whitelistlabel {

    @Nonnull
    @Id
    private String id = UUID.randomUUID().toString();

    @Nonnull
    private String name;

    @Nonnull
    @NotNull
    @ManyToOne
    @JsonIgnore
    private App app;


    public Whitelistlabel() {
        this.name = "";
        this.app = new App();
    }

    public Whitelistlabel(@Nonnull String name, @Nonnull App app) {
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
        return "Whitelistlabel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
