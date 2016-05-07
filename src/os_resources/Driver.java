package os_resources;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import cpu_resources.CPU;
import cpu_resources.CPUException;

/*
 * NOTE: Anything sandwiched between two "//debug" comments
 * are... well, debugging stuff. They can be safely commented
 * out without affecting the overall behavior of the program
 * Also, most (I think) can be uncommented in order to use them
 * for further testing or to better understand the structure
 * and functioning of the memory and loader systems.
 */

public class Driver {
	
	public static void main(String[] args) throws FileNotFoundException, OSException, MemoryException, CPUException, InterruptedException{
	
		//File path for my PC, important to note that right click: Copy qualified name doesn't give you the 
		//Explicit file path for the test document. Also, "\" is an escape character in java so in order to
		//Use file paths which include "\" (which is all Windows files) you need to double down on the slashes
		//hence the "\\"'s instead of the "\"'s.
		
		File testfile = new File("C:\\Users\\Bruce Houdini\\OSProjectWorkspace\\OperatingSystemSimulation\\src\\os_resources\\testprogfile.txt");
		//File testfile = new File("C:\\Users\\Bruce Houdini\\OSProjectWorkspace\\OperatingSystemSimulation\\src\\os_resources\\temptestprogfile.txt");
		
		Loader loadertest;
		Dispatcher dispatchertest;
		Scheduler scheduletest;
		CPU[] cpuArray;
		Thread[] threadArray;
		
		long startTime;
		long stopTime;
		int numProcessors = 4;
		int maxProcessors = 4;
		
		//for (numProcessors = 1; numProcessors <= maxProcessors; numProcessors++){
			PCB.killFlag = false;
			
			loadertest = new Loader(testfile);
			dispatchertest = new Dispatcher();
			scheduletest = new Scheduler();
			
			cpuArray = new CPU[numProcessors];
			threadArray = new Thread[numProcessors];
			
			for (int i = 0; i < numProcessors; i++){
				int copy = i;
				cpuArray[copy] = new CPU(copy, loadertest.getLargestProcessSize());
				threadArray[copy] = new Thread(cpuArray[copy]);
			}
			
			startTime = System.nanoTime();
			
			for (int i = 0; i < numProcessors; i++){
				System.out.println("THIS HAS WORKED BEFORE START CALL: " + i + " NUMBER OF TIMES.");
				//System.out.println("THIS IS THE THREAD ID BEFORE START CALL " + Thread.currentThread().getId());
				threadArray[i].start();
				//System.out.println("THIS IS THE THREAD ID AFTER START CALL " + Thread.currentThread().getId());
				System.out.println("THIS HAS WORKED AFTER START CALL: " + i + " NUMBER OF TIMES.");
			}
			while (PCB.processTotal != PCB.completedProcesses){
				
				scheduletest.schedule();
				/*
				 * 
				 * FIFO Scheduling for performance testing.
				 * Toggle either via commenting/uncommenting.
				 * Although both can be active simultaneously
				 * The first method to be called will dominate
				 * the other.
				 * 
				 */
				//scheduletest.scheduleFIFO();
				
				dispatchertest.dispatch(cpuArray, numProcessors);
				
				
			}
			PCB.killFlag = true;
			
			for (int i = 0; i < numProcessors; i++){
				threadArray[i].join();
			}
			
			stopTime = System.nanoTime();
			
			System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("CORE COUNT: " + numProcessors);
			System.out.println("Numbers of Processes completed: " + PCB.completedProcesses);
			System.out.print("TOTAL NANOSECONDS FOR COMPLETION: ");
			System.out.println(stopTime - startTime + " nanoseconds.");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
			//Static variables mainly used for debugging and, in this case, to control the testing loop for 
			//CPU times in the driver. Need to be reset or they will accumulate each iteration's program count.
			PCB.processTotal = 0;
			PCB.completedProcesses = 0;
		//}
	}

}
