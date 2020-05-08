package microservice.scalingmanager.reactive;

public class SLAStats {
	
	private String service;
	private Long ninetyPercentileResponseTime;
	
	public SLAStats() {}
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public Long getNinetyPercentileResponseTime() {
		return ninetyPercentileResponseTime;
	}
	public void setNinetyPercentileResponseTime(Long ninetyPercentileResponseTime) {
		this.ninetyPercentileResponseTime = ninetyPercentileResponseTime;
	}

	@Override
	public String toString() {
		return "SLAStats [service=" + service + ", ninetyPercentileResponseTime=" + ninetyPercentileResponseTime + "]";
	}
	
}
