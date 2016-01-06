package ch.bfh.projekt1.vatra.algorithm;

import javax.annotation.Nonnull;

/**
 * Created by dave on 02.12.15.
 */
public enum AlgorithmEnum {

    GEO_ALGORITHM(GeoAlgorithm.class),
    ROBOTIC_ALGORITHM(RoboticAlgorithm.class),
    DEFAULT_ALGORITHM(DefaultAlgorithm.class);

    @Nonnull
    private Class<?> algorithm;


    AlgorithmEnum(@Nonnull Class<?> algorithm) {
        this.algorithm = algorithm;

    }

    @Nonnull
    public Class<?> getAlgorithmClass() {
        return algorithm;
    }
}
