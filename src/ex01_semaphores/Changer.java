package ex01_semaphores;

import java.util.Random;

public class Changer extends Thread {

	private String name;
	private Synchronizer sync;
	private Random alea = new Random();
	private boolean lame = false;
	private static int created = 0;
	
	public Changer (String name, Synchronizer sync) {
		this.name = name;
		this.sync = sync;
		lame = created==0;
		created++;
	}
	
	
	public void run () {
		while (true) {
			sync.startReplacement();
			
			if (lame) randomDelay(200, 400);
			else randomDelay(50, 100);
				
			sync.endReplacement(name);
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


