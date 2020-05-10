package sender.load;

public class LoadMain {
	
	public static void main(String[] args) {
		Integer users = 100;//Integer.valueOf(args[0]);
		LoadTest tester = new LoadTest();
		tester.startUsers(users);
	}
}
