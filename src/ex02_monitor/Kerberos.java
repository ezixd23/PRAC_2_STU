package ex02_monitor;

import java.awt.EventQueue;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Kerberos {
	
	private final int MAX;
	private StateViewer viewer;

	
	public Kerberos (int max, StateViewer viewer) {
		this.MAX = max;	
		this.viewer = viewer;
	}
	
	private volatile int waitingSouthBound = 0;
	private volatile int waitingNorthBound = 0;
	private volatile int goingSouth = 0;
	private volatile int goingNorth = 0;
	
	private volatile int allowedSouthBound = 0;
	private volatile int allowedNorthBound = 0;
	
	public void readyToGoSouth (int id) {
		System.out.println("READY to go SOUTH ("+id+")");
		viewer.appendText("READY to go SOUTH ("+id+")");
		waitingSouthBound++;
		viewer.setWaitingSouthbound(waitingSouthBound);
	}
	
	public void readyToGoNorth (int id) {
		System.out.println("READY to go NORTH ("+id+")");
		viewer.appendText("READY to go NORTH ("+id+")");
		waitingNorthBound++;
		viewer.setWaitingNorthbound(waitingNorthBound);
	}
	
	public void goingSouth (int id) {
		System.out.println("\tGoing SOUTH ("+id+")");
		viewer.appendText("&emsp;&emsp;Going SOUTH ("+id+")");
		waitingSouthBound--;
		goingSouth++;
		viewer.setWaitingSouthbound(waitingSouthBound);
		viewer.setGoingSouth(goingSouth);
		
		allowedNorthBound = 0;
		viewer.setAllowedNorthbound(-1);
		if (waitingNorthBound!=0) {
			allowedSouthBound++;
			viewer.setAllowedSouthbound(allowedSouthBound);
		}
		
		if (goingNorth!=0) {
			System.err.println("ERROR: potential collision detected");
			System.err.println("Car entered southbound when there was northbound traffic");
			viewer.appendRedText("ERROR: potential collision detected<br>Car entered southbound when there was northbound traffic");
			System.exit(0);
		}
		
		if (allowedSouthBound>MAX) {
			System.err.println("ERROR: bad handling of congestion. Southbound entrance should be closed");
			viewer.appendRedText("ERROR: bad handling of congestion. Southbound entrance should be closed");
			System.exit(0);
		}
		
	}
	
	public void goingNorth (int id) {
		System.out.println("\tGoing NORTH ("+id+")");
		viewer.appendText("&emsp;&emsp;Going NORTH ("+id+")");
		waitingNorthBound--;
		goingNorth++;
		viewer.setWaitingNorthbound(waitingNorthBound);
		viewer.setGoingNorth(goingNorth);
		
		allowedSouthBound = 0;
		viewer.setAllowedSouthbound(-1);
		if (waitingSouthBound!=0) {
			allowedNorthBound++;
			viewer.setAllowedNorthbound(allowedNorthBound);
		}
		
		if (goingSouth!=0) {
			System.err.println("ERROR: potential collision detected");
			System.err.println("Car entered northbound when there was southbound traffic");
			viewer.appendRedText("ERROR: potential collision detected<br>Car entered northbound when there was southbound traffic");
			System.exit(0);
		}
		
		if (allowedNorthBound>MAX) {
			System.err.println("ERROR: bad handling of congestion. Northbound entrance should be closed");
			viewer.appendRedText("ERROR: bad handling of congestion. Northbound entrance should be closed");
			System.exit(0);
		}
		
	}
	
	public void southReached (int id) {
		System.out.println("\t\tSOUTH REACHED ("+id+")");
		viewer.appendText("&emsp;&emsp;&emsp;&emsp;SOUTH REACHED ("+id+")");
		goingSouth--;
		viewer.setGoingSouth(goingSouth);
	}
	
	public void northReached (int id) {
		System.out.println("\t\tNORTH REACHED ("+id+")");
		viewer.appendText("&emsp;&emsp;&emsp;&emsp;NORTH REACHED ("+id+")");
		goingNorth--;
		viewer.setGoingNorth(goingNorth);
	}
	
	
}
