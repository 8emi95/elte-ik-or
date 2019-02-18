
public class Parh3 {
	public static void main(String[] args) throws InterruptedException {
		Object lock = new Object();
		
		Thread t = new Thread(() -> {
//			synchronized (lock) {
			for (int i = 0; i < 100000; i++) {
				synchronized (lock) {
					myPrint("aaaaaaaaaaaaaaaaaaaaaaaa");
				}
			}
		});

		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 100000; i++) {
				synchronized (args) {
					myPrint("bbbbbbbbbbbbbbbbbbbbb");
				}
			}
		});
		
		t.start();
		t2.start();

	}

	private static void myPrint(String szoveg) {
		for (char c : szoveg.toCharArray()) {
			System.out.print(c);
		}
		System.out.println();
	}
}
