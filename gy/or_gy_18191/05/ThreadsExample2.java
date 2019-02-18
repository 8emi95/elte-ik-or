public class ThreadsExample2 {
	public static void main(String[] args) throws InterruptedException {
		Object lock = new Object();

		Runnable r = () -> {
			System.out.println("Hello");
		};

//		r.run();
//		r.run();
//		r.run();

		final int[] szamlalo = { 0 };

		Thread thread = new Thread(r);

		Thread thread2 = new Thread(() -> {
			for (int i = 0; i < 100000; i++) {
				synchronized (lock) {
					++szamlalo[0];
					System.out.println("Szam1: " + szamlalo[0]);
				}

//				myPrintln("aaaaaaaaaaaaaaaaaaaaaaaaaa", lock);
			}

			System.out.println("world");
//			System.console().readLine();
		});

		new Thread(() -> {
			for (int i = 0; i < 100000; i++) {
				synchronized (lock) {
					++szamlalo[0];
					System.out.println("Szam2: " + szamlalo[0]);
				}

//				myPrintln("bbbbbbbbbbbbbbbbbbbbbbbbbb", lock);
			}
		}).start();

		thread2.setName("konzol");

//		thread.run();
//		thread2.run();
		thread.start();
		thread2.start();

		Thread thread3 = new Thread(() -> {
			System.out.println("12345678");
		});
		thread3.start();

		MyThread myThread = new MyThread();
		myThread.start();

//		thread.join();
//		thread2.join();
//		thread3.join();
//		myThread.join();

		System.out.println("main vege");
	}

	private static void myPrintln(String text, Object lock) {
		// kritikus szakasz
		synchronized (System.out) {
//		synchronized (text) {
			for (char c : text.toCharArray()) {
				System.out.print(c);
			}
			System.out.println();
		}

	}
}


class MyThread extends Thread {
	int adat;

	@Override
	public void run() {
		System.out.println("fut");
	}

	private void f() {
		synchronized (this) {
			// ....
		}
	}

	private synchronized void f2() {

	}
}
