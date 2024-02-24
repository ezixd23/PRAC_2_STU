package ex01_semaphores;

import java.util.concurrent.Semaphore;

public class Synchronizer {

	/* Declare and initialize your semaphores here. 
	 * Semaphores and only semaphores. Nothing else, not even simple-typed variables
	 */
	
	
	private volatile int currentCar = -1; // the id of the car being serviced
	private Kerberos analyser = new Kerberos(); // the trace analyser
	
	//--- Operations invoked by LIFTER
	
	public void startRaising () {
		/* COMPLETE */
	}
	
	public void endRaising () {
		analyser.writeString("Car["+currentCar+"] is UP"); // leave this line at the very beginning
		/* COMPLETE */
	}
	
	public void startLowering () {
		/* COMPLETE */
	}
	
	public void endLowering () {
		analyser.writeString("Car["+currentCar+"] is DOWN"); // leave this line at the very beginning
		/* COMPLETE */
	}
	
	
	// --- Operations invoked by CHANGERs
	
	public void startReplacement() {
		/* COMPLETE */
	}
	
	public void endReplacement(String changer) {
		analyser.writeStringSync("\tTYRE REPLACED["+currentCar+"]: "+changer); // leave this line at the very beginning
		/* COMPLETE */
	}
	
	// --- operations invoked by CAR
	
	
	public void pitStop(int car) {
		/* COMPLETE */
		
		analyser.writeString("REPLACEMENT starts for car["+car+"]");
		currentCar = car;
		
		/* COMPLETE */
		
		analyser.writeString("Car["+car+"] RE-ENTERS circuit\n");

		/* COMPLETE */ 
	}
	
}
