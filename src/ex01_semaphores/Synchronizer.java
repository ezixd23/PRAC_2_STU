 package ex01_semaphores;

import java.util.concurrent.Semaphore;

public class Synchronizer {

	/* Declare and initialize your semaphores here. 
	 * Semaphores and only semaphores. Nothing else, not even simple-typed variables
	 */

	Semaphore carIn = new Semaphore(1);
	Semaphore finished = new Semaphore(0);
	Semaphore tires = new Semaphore(0, true);
	Semaphore canChange = new Semaphore(0, true);

	private volatile int currentCar = -1; // the id of the car being serviced
	private Kerberos analyser = new Kerberos(); // the trace analyser
	
	//--- Operations invoked by LIFTER
	
	public void startRaising () {
		/* COMPLETE */
		tires.release(4);
	}
	
	public void endRaising () {
		analyser.writeString("Car["+currentCar+"] is UP"); // leave this line at the very beginning
		/* COMPLETE */
		canChange.release();
	}
	
	public void startLowering () {
		/* COMPLETE */
		canChange.acquireUninterruptibly();
	}
	
	public void endLowering () {
		analyser.writeString("Car["+currentCar+"] is DOWN"); // leave this line at the very beginning
		/* COMPLETE */
		finished.release();
	}
	
	
	// --- Operations invoked by CHANGERs
	
	public void startReplacement() {
		/* COMPLETE */
		tires.acquireUninterruptibly();
		canChange.acquireUninterruptibly();
	}
	
	public void endReplacement(String changer) {
		analyser.writeStringSync("\tTYRE REPLACED["+currentCar+"]: "+changer); // leave this line at the very beginning
		/* COMPLETE */
		canChange.release();
	}
	
	// --- operations invoked by CAR
	
	
	public void pitStop(int car) {
		/* COMPLETE */
		carIn.acquireUninterruptibly();
		
		analyser.writeString("REPLACEMENT starts for car["+car+"]");
		currentCar = car;

		/* COMPLETE */
		finished.acquireUninterruptibly();
		
		analyser.writeString("Car["+car+"] RE-ENTERS circuit\n");

		/* COMPLETE */ 
		carIn.release();
	}
	
}
