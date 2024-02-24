package ex01_semaphores;

import java.util.concurrent.Semaphore;

public class Synchronizer {

	/* Declare and initialize your semaphores here. 
	 * Semaphores and only semaphores. Nothing else, not even simple-typed variables
	 */
	
	private Semaphore pitStop = new Semaphore(1);
	private Semaphore rentry = new Semaphore(0);
	private Semaphore raising = new Semaphore(0);
	private Semaphore lowering = new Semaphore(0);
	private Semaphore replacement = new Semaphore(0);
	
	private volatile int currentCar = -1; // the id of the car being serviced
	private Kerberos analyser = new Kerberos(); // the trace analyser
	
	//--- Operations invoked by LIFTER
	
	public void startRaising () {
		/* COMPLETE */
		raising.acquireUninterruptibly();
	}
	
	public void endRaising () {
		analyser.writeString("Car["+currentCar+"] is UP"); // leave this line at the very beginning
		/* COMPLETE */
		replacement.release();
		replacement.release();
		replacement.release();
		replacement.release();
	}
	
	public void startLowering () {
		/* COMPLETE */
		lowering.acquireUninterruptibly();
	}
	
	public void endLowering () {
		analyser.writeString("Car["+currentCar+"] is DOWN"); // leave this line at the very beginning
		/* COMPLETE */
		rentry.release();
		
	}
	
	
	// --- Operations invoked by CHANGERs
	
	public void startReplacement() {
		/* COMPLETE */
		replacement.acquireUninterruptibly();
	}
	
	public void endReplacement(String changer) {
		analyser.writeStringSync("\tTYRE REPLACED["+currentCar+"]: "+changer); // leave this line at the very beginning
		/* COMPLETE */
		lowering.release();
	}
	
	// --- operations invoked by CAR
	
	
	public void pitStop(int car) {
		/* COMPLETE */
		pitStop.acquireUninterruptibly();
		
		analyser.writeString("REPLACEMENT starts for car["+car+"]");
		currentCar = car;
		
		/* COMPLETE */
		raising.release();
		rentry.acquireUninterruptibly();
		analyser.writeString("Car["+car+"] RE-ENTERS circuit\n");

		/* COMPLETE */
		pitStop.release();
	}
	
}
