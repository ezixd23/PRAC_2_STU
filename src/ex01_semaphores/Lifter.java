package ex01_semaphores;

import java.util.Random;

public class Lifter extends Thread{
	
	private Synchronizer sync;
	private Random alea = new Random();
	
	public Lifter (Synchronizer sync) {
		this.sync = sync;
	}
	
	public void run () {
		while (true) {
			sync.startRaising();
			randomDelay(50,100);
			sync.endRaising();
			
			sync.startLowering();
			randomDelay(50,100);
			sync.endLowering();
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
