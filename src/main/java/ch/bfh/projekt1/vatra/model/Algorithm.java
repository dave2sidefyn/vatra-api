package ch.bfh.projekt1.vatra.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import ch.bfh.projekt1.vatra.algorithm.AlgorithmEnum;

import java.util.Set;
import java.util.UUID;

/**
 * Created by dave on 23.10.15.
 */
@Entity
public class Algorithm {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotNull
    @NotEmpty
    private String name;
    
    @Enumerated(EnumType.STRING)
    private AlgorithmEnum type;

    @OneToMany
    private Set<App> apps;

    @OneToMany
    private Set<AlgorithmRequestResult> algorithmRequestResults;

    public Algorithm() {
    }
    
    public Algorithm(String name, AlgorithmEnum type) {
    	this.name = name;
    	this.type = type;
    }

    public Algorithm(String name, Set<App> apps, Set<AlgorithmRequestResult> algorithmRequestResults) {
        this.name = name;
        this.apps = apps;
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

    public Set<App> getApps() {
        return apps;
    }

    public void setApps(Set<App> apps) {
        this.apps = apps;
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
                ", apps=" + apps +
                ", algorithmRequestResults=" + algorithmRequestResults +
                '}';
    }
}
