package sender.load;

public class LoadMain {
	
	public static void main(String[] args) {
		String url = args[0];
		LoadTest tester = new LoadTest(url);
		tester.performTest();
	}
}
