package ex01_semaphores;

import java.util.Random;

public class RaceCar extends Thread{
	
	private int id;
	private Synchronizer sync;
	private Random alea = new Random();
	
	public RaceCar (int id, Synchronizer sync ) {
		this.id = id;
		this.sync = sync;
	}
	
	public void run () {
		while (true) {
			// run for a while
			randomDelay(10, 20);
			// have tires replaced
			sync.pitStop(id);
		}
	}
	
	
	
	private void randomDelay(int min, int max) {
		int dist = max-min;
		int r = alea.nextInt(dist);
		delay(r+min);
	}
	
	private void delay (int millis) {
		try {Thread.sleep(millis);} catch (InterruptedException ie) {}
	}

}
