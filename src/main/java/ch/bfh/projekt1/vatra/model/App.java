package ch.bfh.projekt1.vatra.model;

import ch.bfh.projekt1.vatra.service.RequestRepository;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Die Entität App ist hier, für die Apps zu definieren.
 * <p>
 * Created by dave on 23.10.15.
 */
@Entity
public class App {

    private static final int DEFAULT_TOLERANZ = 5;
    private static final String DEFAULT_APP = "DEFAULT_APP";
    private static final String DEFAULT_SCHEME = "{\"VaTra.ApiKey\": \"VaTra.ApiKey\",\"VaTra.Identification\": \"VaTra.Identification\", \"creditCardNumber\":\"VaTra.Payment.CreditCardNumber\"}";

    @Id
    @Nonnull
    private String id = UUID.randomUUID().toString();

    @NotNull
    @Nonnull
    private String name = DEFAULT_APP;

    @Nonnull
    @NotNull
    private String scheme = DEFAULT_SCHEME;

    @Nonnull
    private Integer toleranz = DEFAULT_TOLERANZ;

    @ManyToOne
    @Nonnull
    private User user;

    @Nonnull
    private Date validFrom = new Date();

    @Nonnull
    private Date validTo = new Date();

    @Column(unique = true)
    @Nonnull
    private String apiKey = UUID.randomUUID().toString();

    @ManyToMany
    @Nonnull
    private Set<Algorithm> algorithms = new HashSet<>();

    public App() {
        user = new User();
    }

    public App(@Nonnull String name, @Nonnull Integer toleranz, @Nonnull User user, @Nonnull Date validFrom, @Nonnull Date validTo, @Nonnull Set<Algorithm> algorithms) {
        this.name = name;
        this.toleranz = toleranz;
        this.user = user;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.algorithms = algorithms;
    }

    public App(@Nonnull String name, @Nonnull Integer toleranz, @Nonnull User user, @Nonnull Date validFrom, @Nonnull Date validTo, @Nonnull Set<Algorithm> algorithms, @Nonnull String apiKey) {
        this.name = name;
        this.toleranz = toleranz;
        this.user = user;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.algorithms = algorithms;
        this.apiKey = apiKey;
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
    public String getScheme() {
        return scheme;
    }

    public void setScheme(@Nonnull String scheme) {
        this.scheme = scheme;
    }

    @Nonnull
    public Integer getToleranz() {
        return toleranz;
    }

    public void setToleranz(@Nonnull Integer toleranz) {
        this.toleranz = toleranz;
    }

    @Nonnull
    public User getUser() {
        return user;
    }

    public void setUser(@Nonnull User user) {
        this.user = user;
    }

    @Nonnull
    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(@Nonnull Date validFrom) {
        this.validFrom = validFrom;
    }

    @Nonnull
    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(@Nonnull Date validTo) {
        this.validTo = validTo;
    }

    @Nonnull
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(@Nonnull String apiKey) {
        this.apiKey = apiKey;
    }

    @Nonnull
    public Set<Algorithm> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(@Nonnull Set<Algorithm> algorithms) {
        this.algorithms = algorithms;
    }

    @Nonnull
    public Optional<Request> findLastValidRequest(@Nonnull String identify, @Nonnull CrudRepository[] crudRepositories) {
        for (CrudRepository crudRepository : crudRepositories) {
            if (crudRepository instanceof RequestRepository) {
                List<Request> allByApp = ((RequestRepository) crudRepository).findAllByAppAndIdentify(this, identify);
                allByApp.stream().filter(Request::isValid).sorted((o1, o2) -> -o1.getCreatedDate().compareTo(o2.getCreatedDate())).findFirst();
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "App [id=" + id + ", name=" + name + ", scheme=" + scheme + ", toleranz=" + toleranz + ", user=" + user
                + ", validFrom=" + validFrom + ", validTo=" + validTo + ", apiKey=" + apiKey + ", algorithms="
                + algorithms + "]";
    }
}
