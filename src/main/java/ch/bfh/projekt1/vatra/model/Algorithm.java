package ch.bfh.projekt1.vatra.model;

import ch.bfh.projekt1.vatra.algorithm.AlgorithmEnum;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Die Entität Algorithm ist hier, um Algorithmen zu definieren.
 * <p>
 * Created by dave on 23.10.15.
 */
@Entity
public class Algorithm {

    @Id
    @Nonnull
    private String id = UUID.randomUUID().toString();

    @NotNull
    @NotEmpty
    @Nonnull
    private String name = "DEFAULT";

    @Nonnull
    @NotNull
    @Enumerated(EnumType.STRING)
    private AlgorithmEnum type = AlgorithmEnum.DEFAULT_ALGORITHM;

    @OneToMany
    @Nonnull
    private Set<App> apps = new HashSet<>();

    @OneToMany
    @Nonnull
    private Set<AlgorithmRequestResult> algorithmRequestResults = new HashSet<>();

    public Algorithm() {
    }

    public Algorithm(@Nonnull String name, @Nonnull AlgorithmEnum type) {
        this.name = name;
        this.type = type;
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
    public AlgorithmEnum getType() {
        return type;
    }

    public void setType(@Nonnull AlgorithmEnum type) {
        this.type = type;
    }

    @Nonnull
    public Set<App> getApps() {
        return apps;
    }

    public void setApps(@Nonnull Set<App> apps) {
        this.apps = apps;
    }

    @Nonnull
    public Set<AlgorithmRequestResult> getRequests() {
        return algorithmRequestResults;
    }

    public void setRequests(@Nonnull Set<AlgorithmRequestResult> algorithmRequestResults) {
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
