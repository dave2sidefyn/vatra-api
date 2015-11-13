package ch.bfh.projekt1.vatra.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    private String name;

    @ManyToOne
    private User user;
    private Date validFrom;
    private Date validTo;

    @OneToMany
    private Set<AppAlgorithmResult> algorithmResults;

    public App() {
    }

    public App(String name, User user, Date validFrom, Date validTo, Set<AppAlgorithmResult> algorithmResults) {
        this.name = name;
        this.user = user;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.algorithmResults = algorithmResults;
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

    public Set<AppAlgorithmResult> getAlgorithms() {
        return algorithmResults;
    }

    public void setAlgorithms(Set<AppAlgorithmResult> algorithms) {
        this.algorithmResults = algorithmResults;
    }

    @Override
    public String toString() {
        return "App{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", user=" + user +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", algorithmResults=" + algorithmResults +
                '}';
    }
}
