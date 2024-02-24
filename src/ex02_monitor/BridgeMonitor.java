package ex02_monitor;

import java.util.concurrent.locks.*;

public class BridgeMonitor {
	
	private Kerberos safetyAnalyzer;
	private final int MAX;
	
	private static final int OPEN = 1;
	private static final int CLOSED = -1;
	
	private volatile int northBarrier = OPEN;
	private volatile int southBarrier = OPEN;
	
	/* Declare lock, condition objects and all the simple-typed variables that
	 * you deem necessary to solve the problem
	 */
	
	// Constructor. Do not modify it
	public BridgeMonitor (int maxWhenWaiting, StateViewer viewer) {
		MAX = maxWhenWaiting;
		safetyAnalyzer = new Kerberos(MAX, viewer); 
	}

	
	public void letMeGoSouth (int id) {
		/* COMPLETE */
		
		safetyAnalyzer.readyToGoSouth(id); // do not remove
		
		
		/* COMPLETE */
		
		safetyAnalyzer.goingSouth(id); // do not remove
		
		/* COMPLETE */
	}
	
	public void southReached (int id) {
		/* COMPLETE */
		
		safetyAnalyzer.southReached(id); // do not remove
		
		/* COMPLETE */
	}
	
	//-----
	
	public void letMeGoNorth (int id) {
		/* COMPLETE */
		
		safetyAnalyzer.readyToGoNorth(id); // do not remove
		
		/* COMPLETE */
		
		safetyAnalyzer.goingNorth(id); // do not remove
		/* COMPLETE */
	}
	
	public void northReached (int id) {
		/* COMPLETE */
		
		safetyAnalyzer.northReached(id); // do not remove
		
		/* COMPLETE */
	}
	
}

