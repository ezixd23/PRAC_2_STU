package ex02_monitor;

import java.util.Random;

public class SouthBoundCar extends Thread {
	
	private Random alea = new Random();
	
	private int id;
	private BridgeMonitor monitor;
	private boolean fast;
	
	public SouthBoundCar (int id, BridgeMonitor monitor, boolean fast) {
		this.id=id;
		this.monitor = monitor;
		this.fast = fast;
	}
	
	public void run () {
		while (true) {
			if (!fast) randomDelay(2000,7000);
			monitor.letMeGoSouth(id);
			if (!fast) randomDelay(4000, 5000);
			monitor.southReached(id);
		}
	}

	private void randomDelay(int min, int max) {
		int millis = alea.nextInt(max-min)+min;
		try {Thread.sleep(millis);} catch(InterruptedException ie) {}
	}
	
}
