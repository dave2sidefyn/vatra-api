package ch.bfh.projekt1.vatra.algorithm;

/**
 * Created by dave on 02.12.15.
 */
public enum AlgorithmEnum {

    GEO_ALGORITHM(GeoAlgorithm.class);

    private Class<?> algorithm;


    AlgorithmEnum(Class<?> algorithm) {
        this.algorithm = algorithm;

    }

    public Class<?> getAlgorithmClass() {
        return algorithm;
    }
}
