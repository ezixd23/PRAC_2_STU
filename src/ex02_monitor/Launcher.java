package ex02_monitor;

import java.awt.EventQueue;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Launcher {
	
	static StateViewer viewer;
	
	public static void main (String [] args) {
		
		// launcher creates the viewer
		
		try {
			EventQueue.invokeAndWait(()->{
				try {
					viewer = new StateViewer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				viewer.setVisible(true);
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private static BridgeMonitor monitor;
	
	public static final int CONGESTED = 1;
	public static final int UNBALANCED = 2;
	public static final int EASY = 3;
	public static final int LIMIT = 4;
	public static final int ONLYNORTH = 5;
	public static final int ONLYSOUTH = 6;
	
	public static void launch (int mode, boolean fast, int max) {
		
		monitor = new BridgeMonitor(max,viewer);
		
		switch (mode) {
			case CONGESTED: 
				superCongested(fast);
				break;
			case UNBALANCED: 
				unbalanced(fast);
				break;
			case EASY: 
				easyGoing(fast);
				break;
			case LIMIT: 
				limit(fast);
				break;
			case ONLYNORTH: 
				onlyNorth(fast);
				break;
			case ONLYSOUTH: 
				onlySouth(fast);
				break;
		}
		
	}
	
	private static void superCongested (boolean fast) {
		int INSTANCES = 15;
		
		NorthBoundCar[] goingNorth = new NorthBoundCar[INSTANCES];
		SouthBoundCar[] goingSouth = new SouthBoundCar[INSTANCES];
		
		for (int i=0; i<INSTANCES; i++) {
			goingNorth[i] = new NorthBoundCar(i, monitor, fast);
			goingSouth[i] = new SouthBoundCar(i, monitor, fast);
		}
		
		for (int i=0; i<INSTANCES; i++) {
			goingNorth[i].start();
			goingSouth[i].start();
		}
	}
	
	private static void unbalanced (boolean fast) {
		int INSTANCES_N = 15;
		int INSTANCES_S = 1;
		
		NorthBoundCar[] goingNorth = new NorthBoundCar[INSTANCES_N];
		SouthBoundCar[] goingSouth = new SouthBoundCar[INSTANCES_S];
		
		for (int i=0; i<INSTANCES_N; i++) {
			goingNorth[i] = new NorthBoundCar(i, monitor, fast);
		}
		for (int i=0; i<INSTANCES_S; i++) {
			goingSouth[i] = new SouthBoundCar(i, monitor, fast);
		}
		
		for (int i=0; i<INSTANCES_N; i++) {
			goingNorth[i].start();
		}
		for (int i=0; i<INSTANCES_S; i++) {
			goingSouth[i].start();
		}
	}
	
	private static void easyGoing (boolean fast) {
		int INSTANCES = 3;
		
		NorthBoundCar[] goingNorth = new NorthBoundCar[INSTANCES];
		SouthBoundCar[] goingSouth = new SouthBoundCar[INSTANCES];
		
		for (int i=0; i<INSTANCES; i++) {
			goingNorth[i] = new NorthBoundCar(i, monitor, fast);
			goingSouth[i] = new SouthBoundCar(i, monitor, fast);
		}
		
		for (int i=0; i<INSTANCES; i++) {
			goingNorth[i].start();
			goingSouth[i].start();
		}
	}
	
	private static void limit (boolean fast) {
		int INSTANCES = 4;
		
		NorthBoundCar[] goingNorth = new NorthBoundCar[INSTANCES];
		SouthBoundCar[] goingSouth = new SouthBoundCar[INSTANCES];
		
		for (int i=0; i<INSTANCES; i++) {
			goingNorth[i] = new NorthBoundCar(i, monitor, fast);
			goingSouth[i] = new SouthBoundCar(i, monitor, fast);
		}
		
		for (int i=0; i<INSTANCES; i++) {
			goingNorth[i].start();
			goingSouth[i].start();
		}
	}
	
	private static void onlyNorth (boolean fast) {
		int INSTANCES = 10;
		
		NorthBoundCar[] goingNorth = new NorthBoundCar[INSTANCES];
		//SouthBoundCar[] goingSouth = new SouthBoundCar[INSTANCES];
		
		for (int i=0; i<INSTANCES; i++) {
			goingNorth[i] = new NorthBoundCar(i, monitor, fast);
			//goingSouth[i] = new SouthBoundCar(i, monitor);
		}
		
		for (int i=0; i<INSTANCES; i++) {
			goingNorth[i].start();
			//goingSouth[i].start();
		}
	}
	
	private static void onlySouth (boolean fast) {
		int INSTANCES = 5;
		
		//NorthBoundCar[] goingNorth = new NorthBoundCar[INSTANCES];
		SouthBoundCar[] goingSouth = new SouthBoundCar[INSTANCES];
		
		for (int i=0; i<INSTANCES; i++) {
			//goingNorth[i] = new NorthBoundCar(i, monitor);
			goingSouth[i] = new SouthBoundCar(i, monitor, fast);
		}
		
		for (int i=0; i<INSTANCES; i++) {
			//goingNorth[i].start();
			goingSouth[i].start();
		}
	}
}
