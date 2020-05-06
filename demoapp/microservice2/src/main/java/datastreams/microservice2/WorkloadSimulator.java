package datastreams.microservice2;

import org.springframework.stereotype.Component;

@Component
public class WorkloadSimulator {
	
	public long fibonacci(int n) {
		if (n <= 1) {
			return n;
		}
		return fibonacci(n - 1) + fibonacci(n - 2);
	}

}
