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
	private final Lock lock = new ReentrantLock(true);
	Condition northQueue = lock.newCondition();
	Condition southQueue = lock.newCondition();

	private int northCount = 0;
	private int southCount = 0;
	private int northWaitCount = 0;
	private int southWaitCount = 0;
	private int inTransit = 0;
	
	// Constructor. Do not modify it
	public BridgeMonitor (int maxWhenWaiting, StateViewer viewer) {
		MAX = maxWhenWaiting;
		safetyAnalyzer = new Kerberos(MAX, viewer); 
	}

	
	public void letMeGoSouth (int id) {
		/* COMPLETE */
		this.lock.lock();
		
		safetyAnalyzer.readyToGoSouth(id); // do not remove
		while (southBarrier == CLOSED || this.southCount >= MAX) {
			this.southWaitCount++;
			southQueue.awaitUninterruptibly();
			this.southWaitCount--;
		}
		this.northBarrier = CLOSED;
		System.out.println("closed north");
		
		
		/* COMPLETE */
		this.southCount++;
		this.inTransit++;
		
		safetyAnalyzer.goingSouth(id); // do not remove
		
		/* COMPLETE */
		this.lock.unlock();
	}
	
	public void southReached (int id) {
		/* COMPLETE */
		this.lock.lock();
		
		safetyAnalyzer.southReached(id); // do not remove
		
		/* COMPLETE */
		this.inTransit--;

		if (this.southCount >= MAX) {
			if(this.northWaitCount == 0) {
				this.southQueue.signal();
			} else {
				if (this.inTransit == 0) {
					this.southCount = 0;
					this.southBarrier = CLOSED;
					this.northBarrier = OPEN;
					this.northQueue.signal();
				} else {
					this.southQueue.signal();
				}
			}
		} else {
			this.southQueue.signal();
		}
		this.lock.unlock();
	}
	
	//-----
	
	public void letMeGoNorth (int id) {
		/* COMPLETE */
		this.lock.lock();
		
		safetyAnalyzer.readyToGoNorth(id); // do not remove

		while (northBarrier == CLOSED || this.northCount >= MAX) {
			this.northWaitCount++;
			northQueue.awaitUninterruptibly();
			this.northWaitCount--;
		}
		this.southBarrier = CLOSED;
		System.out.println("closed south");
		
		/* COMPLETE */
		this.northCount++;
		this.inTransit++;
		
		safetyAnalyzer.goingNorth(id); // do not remove
		/* COMPLETE */
		this.lock.unlock();
	}
	
	public void northReached (int id) {
		/* COMPLETE */
		this.lock.lock();
		
		safetyAnalyzer.northReached(id); // do not remove
		
		/* COMPLETE */
		this.inTransit--;
		if (this.northCount >= MAX) {
			if (this.southWaitCount == 0) {
				this.northQueue.signal();
			} else {
				if (this.inTransit == 0) {
					this.northCount = 0;
					this.northBarrier = CLOSED;
					this.southBarrier = OPEN;
					this.southQueue.signal();
				} else {
					this.northQueue.signal();
				}
			}
		} else {
			this.northQueue.signal();
		}

		this.lock.unlock();
	}
	
}

