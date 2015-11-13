package ch.bfh.projekt1.vatra.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;

/**
 * Created by David on 20.10.2015.
 */
@Entity
public class User {

    @Id
    private String id = UUID.randomUUID().toString();


    private String name;
    private String email;
    private String passwort;

    @OneToMany
    private Set<App> apps;

    protected User() {
    }

    protected User(User user) {
        this.id = user.id;
        this.name = user.name;
        this.email = user.email;
        this.passwort = user.passwort;
    }


    public User(String name, String email, String passwort) {
        this.name = name;
        this.email = email;
        this.passwort = passwort;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public Set<App> getApps() {
        return apps;
    }

    public void setApps(Set<App> apps) {
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