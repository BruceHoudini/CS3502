package os_resources;

import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
	
	//readyQueue is a queue of Process objects which have been loaded into
	//RAM by the scheduler and are ready for dispatch. readyQueue may be relocated later.
	public Queue<Process> readyQueue = new LinkedList<Process>();
	
	//Creates a copy of the highest priority Process within the PCB
	//Loads Process instructions from DISK into RAM, records RAM Address
	//of instructions, input, output, and temporary buffers within corresponding
	//variables of the copied Process. The copied process is added to the
	//readyQueue and is removed from the PCB.
	public void schedule() throws MemoryException{
		int priority = 0;
		int index = 0;
		
		//Finds Process in PCB with highest priority and records its index
		for (int i = 0; i < PCB.memory.size(); i++){
			if (priority >= PCB.memory.get(i).getPriority()){
				priority = PCB.memory.get(i).getPriority();
				index = i;
			}
		}
		
		//Creates copy of highest priority Process in PCB
		Process preLoad = PCB.memory.get(index);
		
		//Assigns base register to rAddrBegin and loads Process
		//instructions from DISK to RAM
		preLoad.setRAddrBegin(RAM.getPointer());
		for (int i = 0; i < preLoad.getNumInst(); i++)
			RAM.save(DISK.load(i + preLoad.getPAddr()));
		
		//Assigns RAM address of input buffer and loads input 
		//from DISK into RAM
		preLoad.setInBuffAddr(RAM.getPointer());
		for (int i = 0; i < preLoad.getSizeInBuff(); i++)
		{
			RAM.save(DISK.load(i + preLoad.getInBuffAddr()));
		}
		
		//As there are no values for output in DISK, the necessary
		// output buffer memory is "allocated" and its beginning address 
		//recorded. RAM pointer is moved manually as no save() commands are used.
		preLoad.setOutBuffAddr(RAM.getPointer());
		RAM.setPointer(preLoad.getOutBuffAddr() + preLoad.getSizeOutBuff());
		
		//As there are no values for output in DISK, the necessary
		//temporary buffer memory is "allocated" and its beginning address 
		//recorded. RAM pointer is moved manually as no save() commands are used.
		preLoad.setTempBuffAddr(RAM.getPointer());
		RAM.setPointer(preLoad.getTempBuffAddr() + preLoad.getSizeTempBuff());
		
		//Records the last address in RAM belonging to current process
		preLoad.setRAddrEnd(RAM.getPointer() - 1);
		
		preLoad.isLoaded();
		preLoad.Ready();
		
		//Adds cloned process with added RAM Address values to readyQueue
		readyQueue.add(preLoad);
		System.out.println("This is the processe ID of the first Element");
		System.out.println(readyQueue.element().getPID());
		
		//Destroys original process in PCB
		PCB.memory.remove(index);
		
	}

}
