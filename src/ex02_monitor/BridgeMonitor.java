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
	private final Lock lock = new ReentrantLock();
	Condition northQueue = lock.newCondition();
	Condition southQueue = lock.newCondition();

	private int northCount = 0;
	private int southCount = 0;
	private int inTransit = 0;
	
	// Constructor. Do not modify it
	public BridgeMonitor (int maxWhenWaiting, StateViewer viewer) {
		MAX = maxWhenWaiting;
		safetyAnalyzer = new Kerberos(MAX, viewer); 
	}

	
	public void letMeGoSouth (int id) {
		/* COMPLETE */
		this.lock.lock();
		if(southBarrier == CLOSED)
			southQueue.awaitUninterruptibly();
		
		safetyAnalyzer.readyToGoSouth(id); // do not remove
		
		
		/* COMPLETE */
		this.southCount++;
		
		safetyAnalyzer.goingSouth(id); // do not remove
		
		/* COMPLETE */
		this.inTransit++;
		this.lock.unlock();
	}
	
	public void southReached (int id) {
		/* COMPLETE */
		this.lock.lock();
		
		safetyAnalyzer.southReached(id); // do not remove
		
		/* COMPLETE */
		this.inTransit--;

		if (this.southCount >= MAX) {
			this.southCount = 0;
			this.southBarrier = CLOSED;
			this.northBarrier = OPEN;
			this.northQueue.signal();
		} else {
			if(this.inTransit == 0) this.southQueue.signal();
		}
		this.lock.unlock();
	}
	
	//-----
	
	public void letMeGoNorth (int id) {
		this.lock.lock();
		/* COMPLETE */
		if(northBarrier == CLOSED)
			northQueue.awaitUninterruptibly();
		
		safetyAnalyzer.readyToGoNorth(id); // do not remove
		
		/* COMPLETE */
		this.northCount++;
		
		safetyAnalyzer.goingNorth(id); // do not remove
		/* COMPLETE */
		this.inTransit++;
		this.lock.unlock();
	}
	
	public void northReached (int id) {
		/* COMPLETE */
		this.lock.lock();
		
		safetyAnalyzer.northReached(id); // do not remove
		
		/* COMPLETE */
		System.out.println(this.northCount);
		System.out.println(this.inTransit);
		this.inTransit--;
		if (this.northCount >= MAX) {
			this.northCount = 0;
			this.northBarrier = CLOSED;
			this.southBarrier = OPEN;
			this.southQueue.signal();
		} else {
			if(this.inTransit == 0) this.northQueue.signal();
		}

		this.lock.unlock();
	}
	
}

