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
	private ReentrantLock lock = new ReentrantLock(true);
	private Condition southWaits = lock.newCondition();
	private Condition northWaits = lock.newCondition();
	private int enterS = 0;
	private int waitingS = 0;
	private int enterN = 0;
	private int waitingN = 0;
	
	// Constructor. Do not modify it
	public BridgeMonitor (int maxWhenWaiting, StateViewer viewer) {
		MAX = maxWhenWaiting;
		safetyAnalyzer = new Kerberos(MAX, viewer); 
	}

	
	public void letMeGoSouth (int id) {
		/* COMPLETE */
		lock.lock();
		safetyAnalyzer.readyToGoSouth(id); // do not remove
		if(enterN==MAX && waitingS!=0) southWaits.signal();
		else waitingS ++;
		/* COMPLETE */ 
		safetyAnalyzer.goingSouth(id); // do not remove
		
		/* COMPLETE */
		lock.unlock();
	}
	
	public void southReached (int id) {
		/* COMPLETE */
		lock.lock();
		safetyAnalyzer.southReached(id); // do not remove
		if(enterS<MAX) {
			southWaits.awaitUninterruptibly();
			waitingS --;
			enterS++;
		}else
			enterS = 0;
		/* COMPLETE */
		lock.unlock();
	}
	
	//-----
	
	public void letMeGoNorth (int id) {
		/* COMPLETE */
		lock.lock();
		safetyAnalyzer.readyToGoNorth(id); // do not remove
		
		/* COMPLETE */
		if(enterS==MAX && waitingN!=0) northWaits.signal();
		else waitingN ++;
		safetyAnalyzer.goingNorth(id); // do not remove
		/* COMPLETE */
		lock.unlock();
	}
	
	public void northReached (int id) {
		/* COMPLETE */
		lock.lock();
		safetyAnalyzer.northReached(id); // do not remove
		if(enterN<MAX) {
			northWaits.awaitUninterruptibly();
			waitingN --;
			enterN++;
		}else
			enterN = 0;
		/* COMPLETE */
		lock.unlock();
	}
	
}

