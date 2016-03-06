package ch.bfh.projekt1.vatra.model;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Die Entität AlgorithmDTO ist hier, um bei Algorithmen für den Client sinnvolle Informationen zu übertragen.
 * <p>
 * Created by raess on 30.11.2015.
 */
public class AlgorithmDTO {

    @Nonnull
    private String algorithmId;

    @Nonnull
    private String name;

    @Nonnull
    private List<String> neededKeys;

    private boolean enabled;

    public AlgorithmDTO(@Nonnull String algorithmId, @Nonnull String name, @Nonnull List<String> neededKeys, boolean enabled) {
        this.algorithmId = algorithmId;
        this.name = name;
        this.neededKeys = neededKeys;
        this.enabled = enabled;
    }

    @Nonnull
    public String getAlgorithmId() {
        return algorithmId;
    }

    public void setAlgorithmId(@Nonnull String algorithmId) {
        this.algorithmId = algorithmId;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    public List<String> getNeededKeys() {
        return neededKeys;
    }

    public void setNeededKeys(@Nonnull List<String> neededKeys) {
        this.neededKeys = neededKeys;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "AlgorithmDTO [algorithmId=" + algorithmId + ", name="
                + name + ", enabled=" + enabled + "]";
    }
}
