package sender.load;

public class LoadMain {
	
	public static void main(String[] args) {
		LoadTest tester = new LoadTest();
		if (args.length > 0) {
			String url = args[0];
			tester.setUrl(url);
		}
		tester.performTest();
	}
}
