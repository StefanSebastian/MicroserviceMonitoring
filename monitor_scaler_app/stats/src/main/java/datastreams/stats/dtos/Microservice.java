package datastreams.stats.dtos;

/**
 * @author stefansebii@gmail.com
 */
public class Microservice {
    private long pid;
    private String name;
    private String machine;

    public Microservice() {
    }

    public Microservice(long pid, String name, String machine) {
        this.pid = pid;
        this.name = name;
        this.machine = machine;
    }

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
