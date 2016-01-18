package ch.bfh.projekt1.vatra.model;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

/**
 * Die Entität AlgorithmRequestResult ist hier, um das jeweilige Resultat (if isvalid) pro Request und Algorithm.
 * <p>
 * Created by dave on 23.10.15.
 */
@Entity
public class AlgorithmRequestResult {

    @Id
    @Nonnull
    private String id;

    @ManyToOne
    @Nonnull
    private Request request;

    @ManyToOne
    @Nonnull
    private Algorithm algorithm;

    private boolean valid;

    public AlgorithmRequestResult() {
        this.id = UUID.randomUUID().toString();
        this.request = new Request();
        this.algorithm = new Algorithm();
    }

    @Nonnull
    public String getId() {
        return id;
    }

    public void setId(@Nonnull String id) {
        this.id = id;
    }

    @Nonnull
    public Request getRequest() {
        return request;
    }

    public void setRequest(@Nonnull Request request) {
        this.request = request;
    }

    @Nonnull
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(@Nonnull Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "AlgorithmRequestResult{" +
                "id='" + id + '\'' +
                ", request=" + request +
                ", algorithm=" + algorithm +
                ", valid=" + valid +
                '}';
    }
}

