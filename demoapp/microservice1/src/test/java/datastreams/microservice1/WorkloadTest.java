package datastreams.microservice1;

import org.junit.Test;

public class WorkloadTest {
	
	@Test
	public void fibonacciTime() {
		long startTime = System.currentTimeMillis();
		WorkloadSimulator sim = new WorkloadSimulator();
		long res = sim.fibonacci(41);
		System.out.println(res);
		System.out.println("Elapsed: " + (System.currentTimeMillis() - startTime));
	}

}
