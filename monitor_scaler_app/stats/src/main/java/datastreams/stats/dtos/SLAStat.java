package datastreams.stats.dtos;

public class SLAStat {
	private String name;
	private Long ninetyPercentileResponseTime;
	
	public SLAStat(String name, Long ninetyPercentileResponseTime) {
		this.name = name;
		this.ninetyPercentileResponseTime = ninetyPercentileResponseTime;
	}
	
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
	
}
