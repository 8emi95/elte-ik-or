
public class Parh4 {
	public static void main(String[] args) throws InterruptedException {
		Bankszamla bankszamla = new Bankszamla();

		new Thread(() -> {
			for (int i = 0; i < 100000; i++) {
				bankszamla.betesz(1);
			}
		}).start();

		new Thread(() -> {
			for (int i = 0; i < 100000; i++) {
				bankszamla.kivesz(1);
			}
		}).start();
		
		new Thread(() -> {
			for (int i = 0; i < 100000; i++) {
				System.out.println(bankszamla.get());
			}
		}).start();
		
	}
}

class Bankszamla {
	private int osszeg = 0;

	public void betesz(int osszeg) {
		synchronized (this) {
			this.osszeg += osszeg;
		}
	}
	
	public synchronized void kivesz(int osszeg) {
		this.osszeg -= osszeg;
	}

	public synchronized int get() {
		return osszeg;
	}
}
