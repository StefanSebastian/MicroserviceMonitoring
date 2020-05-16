package microservice.scalingmanager.reactive;

public class SLAStats {
	
	private String name;
	private Long reqsPerSec;
	
	public SLAStats() {}
	
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

	@Override
	public String toString() {
		return "SLAStats [service=" + name + ", reqsPerSec=" + reqsPerSec + "]";
	}
	
}
