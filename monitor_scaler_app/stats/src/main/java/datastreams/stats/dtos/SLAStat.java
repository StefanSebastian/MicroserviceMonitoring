package datastreams.stats.dtos;

public class SLAStat {
	private String service;
	private Long ninetyPercentileResponseTime;
	
	public SLAStat(String service, Long ninetyPercentileResponseTime) {
		this.service = service;
		this.ninetyPercentileResponseTime = ninetyPercentileResponseTime;
	}
	
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
	
}
