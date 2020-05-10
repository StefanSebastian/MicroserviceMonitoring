package microservice.scalingmanager.reactive;

public class SLAStats {
	
	private String name;
	private Long ninetyPercentileResponseTime;
	
	public SLAStats() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getNinetyPercentileResponseTime() {
		return ninetyPercentileResponseTime;
	}
	public void setNinetyPercentileResponseTime(Long ninetyPercentileResponseTime) {
		this.ninetyPercentileResponseTime = ninetyPercentileResponseTime;
	}

	@Override
	public String toString() {
		return "SLAStats [service=" + name + ", ninetyPercentileResponseTime=" + ninetyPercentileResponseTime + "]";
	}
	
}
