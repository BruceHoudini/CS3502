package os_resources;

import cpu_resources.Signal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
	
	//readyQueue is a queue of Process objects which have been loaded into
	//RAM by the scheduler and are ready for dispatch. readyQueue may be relocated later.
	public static Queue<Process> readyQueue = new LinkedList<Process>();
	public static Queue<Process> terminatedQueue = new LinkedList<Process>();
	public static Queue<Process> waitingQueue = new LinkedList<Process>();
	public static ArrayList<TripleP> availableRAM = new ArrayList<TripleP>();
	public static Signal signal;
	
	
	
	//Creates a copy of the highest priority Process within the PCB
	//Loads Process instructions from DISK into RAM, records RAM Address
	//of instructions, input, output, and temporary buffers within corresponding
	//variables of the copied Process. The copied process is added to the readyQueue.
	public void schedule() throws MemoryException{
		
		//Priority set to some arbitrarily large value for the purposes of initial priority check
		int priority = 1000;
		int index = 0;
		
		//Finds Process in PCB with highest priority (meaning, lowest priority number) that is not already
		//in ready queue and records its index
		while (index != -1){
			index = -1;
		for (int i = 0; i < PCB.memory.size(); i++){
			if (PCB.memory.get(i).getState() == PState.NEW || PCB.memory.get(i).getState() == PState.WAITING){
					if (priority > PCB.memory.get(i).getPriority()){
							priority = PCB.memory.get(i).getPriority();
							index = i;
					}
			}
			System.out.println(index);
		}
		if (index != -1){
			if (isSpace(PCB.memory.get(index).getNumInst() + PCB.memory.get(index).getSizeInBuff() + PCB.memory.get(index).getSizeOutBuff() + PCB.memory.get(index).getSizeTempBuff()))
				loadProcess(index);
			else
				index = -1;
		}
		priority = 1000;
		}
	}
		//Should there not be enough memory to schedule a process, index will remain -1 schedule will terminate until CPU is called again.
		
		//Creates copy of highest priority Process in PCB
		//Process preLoad = PCB.memory.get(index);
		
		//Assigns base register to rAddrBegin and loads Process
		//instructions from DISK to RAM
		public void loadProcess(int index) throws MemoryException{
		
		PCB.memory.get(index).setRAddrBegin(RAM.getPointer());
		for (int i = 0; i < PCB.memory.get(index).getNumInst(); i++){
			RAM.save(DISK.load(i + PCB.memory.get(index).getPAddr()));
		}
		
		//Assigns RAM address of input buffer and loads input 
		//from DISK into RAM
		PCB.memory.get(index).setInBuffAddr(RAM.getPointer());
		for (int i = 0; i < PCB.memory.get(index).getSizeInBuff(); i++)
		{
			RAM.save(DISK.load(i + PCB.memory.get(index).getPAddr() + PCB.memory.get(index).getNumInst()));
		}
		
		//As there are no values for output in DISK, the necessary
		// output buffer memory is "allocated" and its beginning address 
		//recorded. RAM pointer is moved manually as no save() commands are used.
		PCB.memory.get(index).setOutBuffAddr(RAM.getPointer());
		RAM.setPointer(PCB.memory.get(index).getOutBuffAddr() + PCB.memory.get(index).getSizeOutBuff());
		
		//As there are no values for output in DISK, the necessary
		//temporary buffer memory is "allocated" and its beginning address 
		//recorded. RAM pointer is moved manually as no save() commands are used.
		PCB.memory.get(index).setTempBuffAddr(RAM.getPointer());
		RAM.setPointer(PCB.memory.get(index).getTempBuffAddr() + PCB.memory.get(index).getSizeTempBuff());
		
		//Records the last address in RAM belonging to current process
		PCB.memory.get(index).setRAddrEnd(RAM.getPointer() - 1);
		
		PCB.memory.get(index).isLoaded();
		PCB.memory.get(index).setState(PState.READY);
		//PCB.memory.get(index).Ready();
		
		
		
		//Adds cloned process with added RAM Address values to readyQueue
		TripleP trip = new TripleP(PCB.memory.get(index).getPID(), PCB.memory.get(index).getRAddrBegin(), PCB.memory.get(index).getRAddrEnd());
		
		//debug
		System.out.println("TRIPLE P ADDED WITH PID: " + trip.getPID() + " WITH LOWER BOUND: " + trip.getLow() + " WITH UPPER BOUND: " + trip.getUpper());
		
		availableRAM.add(trip);
		readyQueue.add(PCB.memory.get(index));
		
		
		//debug
		System.out.println("");
		System.out.println(PCB.memory.get(index).getState());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("***********************************************");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("");
		System.out.println("THIS IS THE PROCESS ID OF THE READIED PROCESS: " + PCB.memory.get(index).getPID() + "\n");
		System.out.println("");
		System.out.println("THIS PROCESS HAS INSTRUCTION LENGTH: " + PCB.memory.get(index).getNumInst());
		System.out.println("THIS PROCESS HAS NUMDATA: " + PCB.memory.get(index).getNumData());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("***********************************************");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("");
		//debug
		sortRAMList();
		//debug
		printRAMList();
		//debug
		
		//MIGHT CAUSE PROBLEMS
		PCB.memory.remove(index);
		
		}
		
		
	
	
	
	public static void removeFromRAMList(int pid){
		for (int i = 0; i < availableRAM.size(); i++){
			if (availableRAM.get(i).getPID() == pid){
				//debug
				System.out.println("Removed from RAM LIST PID: " + availableRAM.get(i).getPID());
				//debug
				availableRAM.remove(i);
			}
		}
		sortRAMList();
	}
	
	 public static void sortRAMList(){
	        TripleP temp;
	        if (availableRAM.size() > 1){
	        for(int i=0; i < availableRAM.size()-1; i++){
	            for(int j = 1; j < availableRAM.size()-i; j++){
	                if(availableRAM.get(j-1).getLow() > availableRAM.get(j).getLow()){
	                    temp=new TripleP(availableRAM.get(j-1).getPID(), availableRAM.get(j-1).getLow(), availableRAM.get(j-1).getUpper());
	                    availableRAM.set(j-1, availableRAM.get(j));
	                    availableRAM.set(j, temp);
	                }
	            }
	        }
	        }
	    }
	 
	 public boolean isSpace(int size){
		 boolean result = false;
		 if (availableRAM.isEmpty()){
			 RAM.setPointer(0);
			 result = true;
			 return result;
		 }
		 if(availableRAM.get(0).getLow() > size){
			 result = true;
			 RAM.setPointer(0);
			 return result;
		 }
		 else if ((1024 - availableRAM.get(availableRAM.size()-1).getUpper() > size)){
			 result = true;
			 RAM.setPointer(availableRAM.get(availableRAM.size()-1).getUpper()+1);
			 return result;
		 }
		 else {
			 for (int i = 0; i < availableRAM.size()-1; i++){
				 if (availableRAM.get(i).getUpper() - availableRAM.get(i+1).getLow() > size){
					 result = true;
					 RAM.setPointer(availableRAM.get(i).getUpper()+1);
					 return result;
				 }
			 }
		 }
			 return result;
			 
	 }
	 
	 public static void printRAMList(){
		 for (int i = 0; i < availableRAM.size(); i++){
			 System.out.println("This is the PID: " + availableRAM.get(i).getPID());
			 System.out.println("This is the lower bound: " + availableRAM.get(i).getLow());
			 System.out.println("This is the upper bound: " + availableRAM.get(i).getUpper());
		 }
	 }
	

}
