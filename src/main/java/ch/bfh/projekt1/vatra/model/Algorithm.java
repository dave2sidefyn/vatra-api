package ch.bfh.projekt1.vatra.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;

/**
 * Created by dave on 23.10.15.
 */
@Entity
public class Algorithm {

    @Id
    private String id = UUID.randomUUID().toString();

    private String name;

    @OneToMany
    private Set<AppAlgorithmResult> appAlgorithmResults;

    @OneToMany
    private Set<AlgorithmRequestResult> algorithmRequestResults;

    public Algorithm() {
    }

    public Algorithm(String id, String name, Set<AppAlgorithmResult> appAlgorithmResults, Set<AlgorithmRequestResult> algorithmRequestResults) {
        this.id = id;
        this.name = name;
        this.appAlgorithmResults = appAlgorithmResults;
        this.algorithmRequestResults = algorithmRequestResults;
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

    public Set<AppAlgorithmResult> getApps() {
        return appAlgorithmResults;
    }

    public void setApps(Set<AppAlgorithmResult> appAlgorithmResults) {
        this.appAlgorithmResults = appAlgorithmResults;
    }

    public Set<AlgorithmRequestResult> getRequests() {
        return algorithmRequestResults;
    }

    public void setRequests(Set<AlgorithmRequestResult> algorithmRequestResults) {
        this.algorithmRequestResults = algorithmRequestResults;
    }

    @Override
    public String toString() {
        return "Algorithm{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", appAlgorithmResults=" + appAlgorithmResults +
                ", algorithmRequestResults=" + algorithmRequestResults +
                '}';
    }
}
