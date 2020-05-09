package microservice.scalingmanager.reactive;

public class OnlineService {
	
	private long pid;
	private String name;
	private String machine;
	
	public OnlineService() {}
	
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
}
