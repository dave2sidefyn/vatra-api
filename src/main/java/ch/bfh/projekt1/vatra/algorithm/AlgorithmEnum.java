package ch.bfh.projekt1.vatra.algorithm;

import javax.annotation.Nonnull;

/**
 * Diese Enum definiert die zuordnung von den EnumTypen zu deren Durchführungsklassen
 * <p>
 * Created by dave on 02.12.15.
 */
public enum AlgorithmEnum {

    GEO_ALGORITHM(GeoAlgorithm.class),
    ROBOTIC_ALGORITHM(RoboticAlgorithm.class),
    CREDITCARD_ALGORITHM(CreditcardAlgorithm.class),
    HONEYPOT_ALGORITHM(HoneypotAlgorithm.class),
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
