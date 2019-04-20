package datastreams.stats;

/**
 * @author stefansebii@gmail.com
 */
public class HeartbeatDTO {
    private String microserviceName;
    private long timestamp;
    private long pid;
    private String machine;

    public HeartbeatDTO() {}

    public String getMicroserviceName() {
        return microserviceName;
    }

    public void setMicroserviceName(String microserviceName) {
        this.microserviceName = microserviceName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    @Override
    public String toString() {
        return "HeartbeatDTO{" +
                "microserviceName='" + microserviceName + '\'' +
                ", timestamp=" + timestamp +
                ", pid=" + pid +
                ", machine='" + machine + '\'' +
                '}';
    }
}
