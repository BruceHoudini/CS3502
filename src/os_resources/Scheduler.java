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
		int priority = 0;
		int index = 0;
		
		//Finds Process in PCB with highest priority that is not already
		//in ready queue and records its index
		for (int i = 0; i < PCB.memory.size(); i++){
			if (priority >= PCB.memory.get(i).getPriority()){
				if (PCB.memory.get(i).getState() != PState.READY){
						priority = PCB.memory.get(i).getPriority();
						index = i;
				}
			}
		}
		
		//Creates copy of highest priority Process in PCB
		//Process preLoad = PCB.memory.get(index);
		
		
		//Assigns base register to rAddrBegin and loads Process
		//instructions from DISK to RAM
		PCB.memory.get(index).setRAddrBegin(RAM.getPointer());
		for (int i = 0; i < PCB.memory.get(index).getNumInst(); i++)
			RAM.save(DISK.load(i + PCB.memory.get(index).getPAddr()));
		
		//Assigns RAM address of input buffer and loads input 
		//from DISK into RAM
		PCB.memory.get(index).setInBuffAddr(RAM.getPointer());
		for (int i = 0; i < PCB.memory.get(index).getSizeInBuff(); i++)
		{
			RAM.save(DISK.load(i + PCB.memory.get(index).getInBuffAddr()));
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
		//System.out.println("This is the processe ID of the first Element");
		//System.out.println(readyQueue.element().getPID());
		//debug
		
	
		
	}

}
