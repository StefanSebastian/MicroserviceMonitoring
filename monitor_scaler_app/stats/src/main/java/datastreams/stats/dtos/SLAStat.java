package datastreams.stats.dtos;

public class SLAStat {
	private String name;
	private Long ninetyPercentileResponseTime;
	
	public SLAStat(String service, Long ninetyPercentileResponseTime) {
		this.name = service;
		this.ninetyPercentileResponseTime = ninetyPercentileResponseTime;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String service) {
		this.name = service;
	}
	public Long getNinetyPercentileResponseTime() {
		return ninetyPercentileResponseTime;
	}
	public void setNinetyPercentileResponseTime(Long ninetyPercentileResponseTime) {
		this.ninetyPercentileResponseTime = ninetyPercentileResponseTime;
	}
	
}
