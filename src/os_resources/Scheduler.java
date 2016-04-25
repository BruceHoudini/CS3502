package os_resources;

import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
	
	//readyQueue is a queue of Process objects which have been loaded into
	//RAM by the scheduler and are ready for dispatch. readyQueue may be relocated later.
	public static Queue<Process> readyQueue = new LinkedList<Process>();
	
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
		for (int i = 0; i < PCB.memory.size(); i++){
			//debug
			//System.out.println("THIS IS THE PROCESS ID: " + PCB.memory.get(i).getPID() + " THIS IS THE PRIORITY: " + PCB.memory.get(i).getPriority());
			//System.out.println("THIS IS THE PROCESS ID: " + PCB.memory.get(i).getPID() + " THIS IS THE NUMBER OF INSTRUCTIONS: " + PCB.memory.get(i).getNumInst());
			//debug
			if (PCB.memory.get(i).getState() == PState.NEW || PCB.memory.get(i).getState() == PState.WAITING){
				if (priority > PCB.memory.get(i).getPriority()){
						//debug
						//System.out.println("Priority before: " + priority);
						//debug
						priority = PCB.memory.get(i).getPriority();
						
						//debug
						//System.out.println("Priority after: " + priority);
						//debug
						
						//debug
						//System.out.println("Index before: " +index);
						//debug
						
						index = i;
						
						//debug
						//System.out.println("Index after: " +index);
						//debug
				}
			}
		}
		//debug
		System.out.println(PCB.memory.get(index).getState());
		//debug
		
		//Creates copy of highest priority Process in PCB
		//Process preLoad = PCB.memory.get(index);
		
		
		//Assigns base register to rAddrBegin and loads Process
		//instructions from DISK to RAM
		PCB.memory.get(index).setRAddrBegin(RAM.getPointer());
		for (int i = 0; i < PCB.memory.get(index).getNumInst(); i++){
			RAM.save(DISK.load(i + PCB.memory.get(index).getPAddr()));
			//debug
			System.out.println("INSTRUCTION LOAD: PID: " + PCB.memory.get(index).getPID() + " : Loaded to RAM : " +DISK.load(i + PCB.memory.get(index).getPAddr()));
			//debug
		}
		
		//Assigns RAM address of input buffer and loads input 
		//from DISK into RAM
		PCB.memory.get(index).setInBuffAddr(RAM.getPointer());
		for (int i = 0; i < PCB.memory.get(index).getSizeInBuff(); i++)
		{
			RAM.save(DISK.load(i + PCB.memory.get(index).getPAddr() + PCB.memory.get(index).getNumInst()));
			//debug
			System.out.println("INPUT LOAD: PID: " + PCB.memory.get(index).getPID() + " : Loaded to RAM : " + DISK.load(i + PCB.memory.get(index).getPAddr() + PCB.memory.get(index).getNumInst()));
			//debug
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
		readyQueue.add(PCB.memory.get(index));
		
		//debug
		//System.out.println("This is the process ID of the first Element");
		//System.out.println(readyQueue.element().getPID());
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
		
	
		
	}

}
