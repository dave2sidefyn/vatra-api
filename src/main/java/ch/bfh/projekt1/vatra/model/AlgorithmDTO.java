package ch.bfh.projekt1.vatra.model;

/**
 * Created by raess on 30.11.2015.
 */
public class AlgorithmDTO {

	private String algorithmId;
	
    private String appAlgorithmResultId;

    private String name;
    
    private boolean enabled;

    public AlgorithmDTO(String algorithmId, String appAlgorithmResultId, String name, boolean enabled) {
        this.algorithmId = algorithmId;
        this.appAlgorithmResultId = appAlgorithmResultId;
        this.name = name;
        this.enabled = enabled;
    }

    public String getAlgorithmId() {
		return algorithmId;
	}

	public void setAlgorithmId(String algorithmId) {
		this.algorithmId = algorithmId;
	}

	public String getAppAlgorithmResultId() {
		return appAlgorithmResultId;
	}

	public void setAppAlgorithmResultId(String appAlgorithmResultId) {
		this.appAlgorithmResultId = appAlgorithmResultId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "AlgorithmDTO [algorithmId=" + algorithmId + ", appAlgorithmResultId=" + appAlgorithmResultId + ", name="
				+ name + ", enabled=" + enabled + "]";
	}
}
