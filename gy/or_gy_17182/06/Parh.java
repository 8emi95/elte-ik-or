
public class Parh {
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(() -> {
			f1();
		});

		// NEM parhuzamosan futtat
//		t.run();
		t.start();
		
//		new Thread(() -> {
//			f2();
//		}).start();

		Thread t2 = new Thread(() -> {
			f2();
		});
		t2.start();

		t.join();
		t2.join();

		System.out.println("kesz");
	}

	private static void f2() {
		for (int i = 0; i < 300000; i++) {
			System.out.println("world");
		}
	}

	private static void f1() {
		for (int i = 0; i < 300000; i++) {
			System.out.println("hello");
		}
	}
}
