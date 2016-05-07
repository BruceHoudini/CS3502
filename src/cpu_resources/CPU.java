package cpu_resources;
import java.math.BigInteger;

import os_resources.MemoryException;
import os_resources.PCB;
import os_resources.PState;
import os_resources.RAM;
import os_resources.Scheduler;

//Finish decode, decode will return Instruction object.
//Finish execute, execute will take an Instruction object.

public class CPU implements Runnable{
	public PCBe pcb;
	public int ID;
	public Status status;
	private int waitLoop = 0;
	//private InsFormat insForm;
	//private InsName opCode;
	public CPU(int id, int sizeCache){
		pcb = new PCBe(sizeCache);
		pcb.setCPUID(id);
		pcb.setState(PState.WAITING);
		PCB.cpuWaitingQueue.add(this);
	}
	
	//public void compute() throws MemoryException, CPUException{
	
	public void run(){
		while (PCB.killFlag == false)
			if (pcb.getState() == PState.READY)
				execute();
	}
	
	public void execute(){
		
		pcb.setState(PState.RUNNING);
		while (pcb.getPC() < pcb.getNumInst())
			try {
				Execute(Decode(Fetch()));
			} catch (CPUException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MemoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		
		
		
			PCB.cpuBlockedQueue.add(this);
		
		while (pcb.getState() != PState.RUNNING){
			waitLoop++;
		}
		
		System.out.println("This is the value of waitLoop: " + waitLoop);
		System.out.println("From thread number: " + Thread.currentThread().getId());
		
		pcb.cpuRegister.resetRegisters();
		pcb.setState(PState.WAITING);
		PCB.cpuWaitingQueue.add(this);
	}
	
	
	public PCBe getPCB(){
		return pcb;
	}
	private String Fetch() throws MemoryException{
		int temp = new BigInteger(RAM.load(pcb.getBaseRegister()+pcb.getPC()), 16).intValue();
		String binString = Integer.toBinaryString(temp);
		while (binString.length() < 32)
			binString = "0" + binString;
		
		return binString;
	}
	private Instruction Decode(String inst) throws CPUException{
		Instruction decodedInstruction;
		if (inst.length() != 32)
			throw new CPUException("Instruction too short return error");
		else if(inst.charAt(0) == '0'){
			if(inst.charAt(1) == '0'){
				decodedInstruction = new ArithmeticForm(inst, pcb);
				return decodedInstruction;
			}
			else{
				decodedInstruction = new CBIForm(inst, pcb);
				return decodedInstruction;
			}
		}
		else if(inst.charAt(0) == '1'){
			if(inst.charAt(1) == '0'){
				decodedInstruction = new UJumpForm(inst, pcb);
				return decodedInstruction;
			}
			else{
				decodedInstruction = new IOForm(inst, pcb);
				return decodedInstruction;
			}
		}
		else
			throw new CPUException("Instruction not created");
		

	}
	
	private void Execute(Instruction currentInstruction) throws CPUException{

		if(currentInstruction.execute())
			pcb.pcPlus();
		else
			throw new CPUException("Failed to execute instruction");
	}
	public int getCPUID(){
		return ID;
	}
	public Status getStatus(){
		return status;
	}


}
