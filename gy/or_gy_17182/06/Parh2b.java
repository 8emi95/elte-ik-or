
public class Parh2b {
	public static void main(String[] args) throws InterruptedException {
		final int szam = 123;
		final int[] trukk = new int[]{ 0 };
		String szoveg = "abc";
//		final String szoveg = "abc";
		
		// Java 8: lambda-kifejezes
		Thread t = new Thread(() -> {
//			++szam;
			for (int i = 0; i < 100000; i++) {
				synchronized (trukk) {
					++trukk[0];
					System.out.println(szoveg + " " + szam + " " + trukk[0]);
				}
			}
		});

		t.start();

		String szoveg2 = "abc2";
		Thread t2 = new Thread(new MyRunnable2(szoveg2, trukk));
		t2.start();
	}
}


class MyRunnable2 implements Runnable {
	String szoveg;
	private int[] trukk;
	
	public MyRunnable2(String szoveg, int[] trukk) {
		this.szoveg = szoveg;
		this.trukk = trukk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 100000; i++) {
			synchronized (trukk) {
				++trukk[0];
				System.out.println(szoveg + " " + trukk[0]);
			}
		}
	}
}
