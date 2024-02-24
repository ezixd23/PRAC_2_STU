
package ex01_semaphores;

import java.util.Scanner;

public class RLauncher {

	public static void main (String [] args) {
		String [] names = {"Front-Right", "Front-Left", "Rear-Right", "Rear-Left"};
		
		Scanner scanner = new Scanner(System.in);
		
		Synchronizer synchronizer = new Synchronizer();
		Lifter lifter = new Lifter(synchronizer);
		RaceCar [] cars = new RaceCar[5];
		Changer [] changers = new Changer[4];
		
	
		System.out.print("\nPress return to start the race. Press it again to stop everything ");
		scanner.nextLine();
		System.out.println();
		
		for (int i=0; i<4; i++) {
			changers[i] = new Changer(names[i], synchronizer);
			changers[i].start();
		}
		
		lifter.start();
		
		for (int i=0; i<cars.length; i++) {
			cars[i] = new RaceCar(i, synchronizer);
			cars[i].start();
		}
		
		
		scanner.nextLine();
		System.out.println("\nTERMINATING...\n");
		
		System.exit(0);
		
	}
	
	
	private static void delay (int millis) {
		try {Thread.sleep(millis);} catch (InterruptedException ie) {}
	}
}
