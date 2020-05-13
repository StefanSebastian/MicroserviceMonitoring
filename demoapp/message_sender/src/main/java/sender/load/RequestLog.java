package sender.load;

public class RequestLog {
	
	private long timestamp;
	private long duration;
	private int respCode;
	
	public RequestLog() {}
	
	public RequestLog(long timestamp, long duration, int respCode) {
		super();
		this.timestamp = timestamp;
		this.duration = duration;
		this.respCode = respCode;
	}

	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public int getRespCode() {
		return respCode;
	}
	public void setRespCode(int respCode) {
		this.respCode = respCode;
	}

	@Override
	public String toString() {
		return timestamp + "," + duration + "," + respCode;
	}
	
}
