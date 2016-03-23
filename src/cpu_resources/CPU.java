package cpu_resources;
import java.math.BigInteger;

import os_resources.MemoryException;
import os_resources.RAM;

//Finish decode, decode will return Instruction object.
//Finish execute, execute will take an Instruction object.

public class CPU {
	private PCBe pcb;
	private InsFormat insForm;
	private InsName opCode;
	public CPU(){
		pcb = new PCBe();
	}
	
	public void compute() throws MemoryException, CPUException{
		Fetch();
		
		while (pcb.getPC() < pcb.getInCount()){
			Decode(Fetch());
		}
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
	private void Decode(String inst) throws CPUException{
		if (inst.length() != 32){
			throw new CPUException("Instruction too short return error");
		}
		else if(inst.charAt(0) == 0){
			if(inst.charAt(1) == 0)
				insForm = InsFormat.ARI_FORM;
			else
				insForm = InsFormat.CAI_FORM;
		}
		else{
			if(inst.charAt(1) == 0)
				insForm = InsFormat.UNJ_FORM;
			else
				insForm = InsFormat.IOI_FORM;
		}
		//opCode = getOpCode(inst, insForm);
	}
	
	private void Execute(){
		
	}
	//private InsName getOpCode(String inst, InsForm form)
		//Implement this tomorrow.

}
