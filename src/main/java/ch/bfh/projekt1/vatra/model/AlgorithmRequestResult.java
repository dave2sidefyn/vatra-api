package ch.bfh.projekt1.vatra.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

/**
 * Created by dave on 23.10.15.
 */
@Entity
public class AlgorithmRequestResult {

    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    private Request request;

    @ManyToOne
    private Algorithm algorithm;

    private boolean result;


    public AlgorithmRequestResult() {
    }

    public AlgorithmRequestResult(String id, Request request, Algorithm algorithm, boolean result) {
        this.id = id;
        this.request = request;
        this.algorithm = algorithm;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "AlgorithmRequestResult{" +
                "id='" + id + '\'' +
                ", request=" + request +
                ", algorithm=" + algorithm +
                ", result=" + result +
                '}';
    }
}

