package ch.bfh.projekt1.vatra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Die Entität User ist hier, für die Users zu definieren.
 * <p>
 * Created by David on 20.10.2015.
 */
@Entity
public class Benutzer {

    private static final String DEFAULT_NAME = "";
    private static final String DEFAULT_EMAIL = "default@vatra.ch";
    private static final String DEFAULT_PASSWORD = "defaultPassword";
    @Id
    @Nonnull
    private String id = UUID.randomUUID().toString();

    @Nonnull
    private String name = DEFAULT_NAME;

    @Nonnull
    private String email = DEFAULT_EMAIL;

    @Nonnull
    private String passwort = DEFAULT_PASSWORD;

    @OneToMany(mappedBy = "benutzer")
    @Nonnull
    @JsonIgnore
    private Set<App> apps = new HashSet<>();

    protected Benutzer() {
    }

    public Benutzer(@Nonnull String name, @Nonnull String email, @Nonnull String passwort) {
        this.name = name;
        this.email = email;
        this.passwort = passwort;
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
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nonnull String email) {
        this.email = email;
    }

    @Nonnull
    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(@Nonnull String passwort) {
        this.passwort = passwort;
    }

    @Nonnull
    public Set<App> getApps() {
        return apps;
    }

    public void setApps(@Nonnull Set<App> apps) {
        this.apps = apps;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", passwort='" + passwort + '\'' +
                '}';
    }


}