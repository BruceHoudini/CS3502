package os_resources;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import cpu_resources.CPU;

//Static ArrayList which contains Process objects
//Should have been called JOB queue
public class PCB {
	public static ArrayList<Process> memory = new ArrayList<Process>();
	
	public static Queue<Process> readyQueue = new LinkedList<Process>();
	public static Queue<Process> waitingQueue = new LinkedList<Process>();
	public static ArrayList<Process> terminatedList = new ArrayList<Process>();
	public volatile static Queue<CPU> cpuWaitingQueue = new LinkedList<CPU>();
	public volatile static Queue<CPU> cpuBlockedQueue = new LinkedList<CPU>();
	
	public static int processTotal = 0;
	public static int completedProcesses = 0;
	
	public static boolean killFlag = false;
}
