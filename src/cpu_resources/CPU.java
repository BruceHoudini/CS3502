package cpu_resources;
import java.math.BigInteger;

import os_resources.MemoryException;
import os_resources.RAM;

//Finish decode, decode will return Instruction object.
//Finish execute, execute will take an Instruction object.

public class CPU {
	public PCBe pcb;
	//private InsFormat insForm;
	//private InsName opCode;
	public CPU(){
		pcb = new PCBe();
	}
	
	public void compute() throws MemoryException, CPUException{
		while (pcb.getPC() < pcb.getNumInst())
			Execute(Decode(Fetch()));	
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
		//debug
		System.out.println(inst);
		//debug
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
				//debug
				System.out.println(inst.charAt(0));
				System.out.println(inst.charAt(1));
				//debug
				decodedInstruction = new IOForm(inst, pcb);
				return decodedInstruction;
			}
		}
		else
			throw new CPUException("Instruction not created");
		/*
		Instruction instruction = new ArithmeticForm(inst);
		Instruction instruction2 = new CBIForm(inst);
		System.out.println(instruction.getFormat());
		System.out.println(instruction2.getFormat());
		*/
		//opCode = getOpCode(inst, insForm);
	}
	
	private void Execute(Instruction currentInstruction) throws CPUException{
		if(currentInstruction.execute())
			pcb.pcPlus();
		else
			throw new CPUException("Failed to execute instruction");
		
	}
	//private InsName getOpCode(String inst, InsForm form)
		//Implement this tomorrow.

}
