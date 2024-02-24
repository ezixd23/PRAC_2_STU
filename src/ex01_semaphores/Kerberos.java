package ex01_semaphores;

import java.util.Random;

public class Kerberos {

	private static final int REPLACEMENT_STARTS = 0;
	private static final int UP = 1;
	private static final int DOWN = 2;
	private static final int TYRE_REPLACED = 3;
	private static final int RE_ENTER = 4;
	
	private static final int W_STOP = -10;
	private static final int W_UP = -20;
	private static final int W_TYRE = -30;
	private static final int W_DOWN = -40;
	private static final int W_ENTER = -50;
	
	private Random alea = new Random();
	private StringBuffer sbuffer = new StringBuffer();
	
	private volatile int state = W_STOP;
	private volatile int currentId = -1;
	private volatile int numTyres = 0;
	
	
	private void analyze (String s) {
		int type = determineType(s);
		int idCar = getId(s);
		switch (state) {
			case W_STOP: 
				if (type==REPLACEMENT_STARTS) {
					currentId = idCar;
					state = W_UP;
				}
				else report("Unexpected situation. Expected: car enters pit for REPLACEMENT");
				break;
				
			case W_UP: 
				checkId(idCar);
				if (type!=UP) report("Unexpected situation. Expected: cars is RAISED");
				else state = W_TYRE;
				break;
			case W_TYRE: 
				checkId(idCar);
				if (type!=TYRE_REPLACED) report("Unexpected situation. Expected: TYRE-REPLACED");
				else {
					numTyres++;
					if (numTyres==4) {numTyres=0; state=W_DOWN;}
					else state = W_TYRE;
				}
				break;
			case W_DOWN: 
				checkId(idCar);
				if (type!=DOWN) report("Unexpected situation. Expected: cars is LOWERED");
				else state = W_ENTER;
				break;
			case W_ENTER: 
				checkId(idCar);
				if (type!=RE_ENTER) report("Unexpected situation. Expected: cars RE-ENTERS");
				else state = W_STOP;
				break;
		}
	}
	
	private int determineType (String s) {
		if (s.contains("REPLACEMENT")) return REPLACEMENT_STARTS;
		if (s.contains("UP")) return UP;
		if (s.contains("DOWN")) return DOWN;
		if (s.contains("TYRE")) return TYRE_REPLACED;
		if (s.contains("ENTER")) return RE_ENTER;
		
		System.err.println("ERROR: string "+s+" is WEIRD!!! Bad synchro...");
		System.exit(0);
		
		return -1; // dummy return;
	}
	
	private int getId (String s) {
		int i = s.indexOf('[');
		int j = s.indexOf(']');
		int id=-10;
		try {
			id = Integer.parseInt(s.substring(i+1, j));
		}
		catch (Exception e) {
			System.err.println("ERROR: string "+s+" is WEIRD!!! Bad synchro...");
			System.exit(0);
		}
		return id;
	}
	
	public void writeString (String string ) {
		int n;
		sbuffer.setLength(0); // empty it!
		for (int i=0; i<string.length(); i++) {
			System.out.print(string.charAt(i));
			n = alea.nextInt(100);
			if (n>=90) try {Thread.sleep(1);} catch(Exception ex) {}
			else if (n>=50) Thread.yield();
			sbuffer.append(string.charAt(i));
		}
		System.out.println();
		analyze(sbuffer.toString()); // injects the string to the analyzer 
	}
	
	// changers requiere a sync version since there are four of them who can 
	// work concurrently.
	public synchronized void writeStringSync (String string) {
		writeString(string);
	}
	
	private void checkId (int id) {
		if (id!=currentId) {
			System.err.println("Id of car ("+id+") differs from expected id ("+currentId+")");
			System.exit(0);
		}
	}
	
	private void report (String s) {
		System.err.println(s);
		System.exit(0);
	}
	
}
