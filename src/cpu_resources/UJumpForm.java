package cpu_resources;

import java.math.BigInteger;

public class UJumpForm extends Instruction{
	private PCBe pcb;
	int address;
	
	public UJumpForm(String instruct, PCBe pcb) throws CPUException{
		this.pcb = pcb;
		format = InsFormat.UNJ_FORM;
		opcode = parseOpCode(instruct);
		parseRegisters(instruct);
	}

	@Override
	boolean execute() {
		if (opcode == InsName.HLT_INS){
			return true;
		}
		else if(opcode == InsName.JMP_INS){
			pcb.setPC((address/4)-1);
			return true;
		}
		else if(opcode == InsName.NOP_INS){
			return true;
		}
		return false;
	}

	@Override
	InsName parseOpCode(String instruct)throws CPUException {
		InsName insName = null;
		int x = Integer.parseInt(instruct.substring(2, 8), 2);
		//debug
		//System.out.println(x);
		//debug
		switch(x){
			case 18: insName = InsName.HLT_INS;
				break;
			case 20: insName = InsName.JMP_INS;
				break;
			case 19: insName = InsName.NOP_INS;
				break;
		}
		if(insName == null)
			throw new CPUException("Opcode does not match expected instruction name");
		//debug
		//System.out.println(insName);
		//debug
		return insName;
	}

	@Override
	void parseRegisters(String binaryString) {
		address = new BigInteger(binaryString.substring(8, 32), 2).intValue();
	}

}
