package datastreams.stats.dtos;

public class SLAStat {
	private String name;
	private Long reqsPerSec;
	
	public SLAStat(String name, Long reqsPerSec) {
		this.name = name;
		this.reqsPerSec = reqsPerSec;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getReqsPerSec() {
		return reqsPerSec;
	}
	public void setReqsPerSec(Long reqsPerSec) {
		this.reqsPerSec = reqsPerSec;
	}
	
}
