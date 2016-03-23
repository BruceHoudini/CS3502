package os_resources;
import java.io.File;
import java.io.FileNotFoundException;
import cpu_resources.CPU;

/*
 * NOTE: Anything sandwiched between two "//debug" comments
 * are... well, debugging stuff. They can be safely commented
 * out without affecting the overall behavior of the program
 * Also, most (I think) can be uncommented in order to use them
 * for further testing or to better understand the structure
 * and functioning of the memory and loader systems.
 */

public class Driver {
	
	public static void main(String[] args) throws FileNotFoundException, OSException, MemoryException{
	
		//File path for my PC, important to note that right click: Copy qualified name doesn't give you the 
		//Explicit file path for the test document. Also, "\" is an escape character in java so in order to
		//Use file paths which include "\" (which is all Windows files) you need to double down on the slashes
		//hence the "\\"'s instead of the "\"'s.
		File testfile = new File("C:\\Users\\Bruce Houdini\\OSProjectWorkspace\\OperatingSystemSimulation\\src\\os_resources\\testprogfile.txt");	
		Loader loadertest = new Loader(testfile);
		CPU cputest = new CPU();
		Dispatcher dispatchertest= new Dispatcher();
		Scheduler scheduletest = new Scheduler();
		scheduletest.schedule();
		dispatchertest.dispatch(cputest);
		
		//debug
		System.out.println(PCB.memory.size());
		//debug
	}

}
