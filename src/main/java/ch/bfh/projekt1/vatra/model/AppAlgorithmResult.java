package ch.bfh.projekt1.vatra.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

/**
 * Created by dave on 23.10.15.
 */
@Entity
public class AppAlgorithmResult {

    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    private App app;

    @ManyToOne
    private Algorithm algorithm;

    private boolean enabled;

    public AppAlgorithmResult() {
    }

    public AppAlgorithmResult(String id, App app, Algorithm algorithm, boolean enabled) {
        this.id = id;
        this.app = app;
        this.algorithm = algorithm;
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "AppAlgorithmResult{" +
                "id='" + id + '\'' +
                ", app=" + app +
                ", algorithm=" + algorithm +
                ", enabled=" + enabled +
                '}';
    }
}
