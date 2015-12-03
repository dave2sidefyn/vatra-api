package ch.bfh.projekt1.vatra.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Created by dave on 23.10.15.
 */
@Entity
public class App {


    @Id
    private String id = UUID.randomUUID().toString();

    @NotNull
    private String name;
    private String scheme;
    @NotNull
    private Integer toleranz;

    @ManyToOne
    private User user;
    private Date validFrom;
    private Date validTo;

    @ManyToMany
    private Set<Algorithm> algorithms;

    public App() {
    }

    public App(String name, String scheme, Integer toleranz, User user, Date validFrom, Date validTo, Set<Algorithm> algorithms) {
        this.name = name;
        this.scheme = scheme;
        this.toleranz = toleranz;
        this.user = user;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.algorithms = algorithms;
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

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public Integer getToleranz() {
        return toleranz;
    }

    public void setToleranz(Integer toleranz) {
        this.toleranz = toleranz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Set<Algorithm> getAlgorithmResults() {
        return algorithms;
    }

    public void setAlgorithms(Set<Algorithm> algorithms) {
        this.algorithms = algorithms;
    }

    @Override
    public String toString() {
        return "App{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", scheme='" + scheme + '\'' +
                ", user=" + user +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", algorithms=" + algorithms +
                '}';
    }
}
