package cpu_resources;

public class IOForm extends Instruction {
	
	private PCBe pcb;
	private int sourceReg;
	private int sourceTwo;
	private int ioAddress;
	
	public IOForm(String instruct, PCBe pcb)throws CPUException{
		this.pcb = pcb;
		format = InsFormat.IOI_FORM;
		opcode = parseOpCode(instruct);
		parseRegisters(instruct);
	}

	@Override
	boolean execute() {
		if (opcode == InsName.RD_INS){
			if (ioAddress == 0){
				pcb.cpuRegister.setReg(sourceReg, pcb.indirectLoad(pcb.getBaseRegister(), Integer.parseInt(pcb.cpuRegister.getReg(sourceTwo), 2)));}
			else
				pcb.cpuRegister.setReg(sourceReg, pcb.indirectLoad(pcb.getBaseRegister(), ioAddress));
			return true;
		}
		else if (opcode == InsName.WR_INS){
			pcb.indirectSave(pcb.getBaseRegister(), ioAddress, pcb.cpuRegister.getReg(sourceReg));
			return true;
		}
		else if (opcode == InsName.NOP_INS){
			return true;
		}
		return false;
	}
	
	@Override
	void parseRegisters(String instruct){
		sourceReg = Integer.parseInt(instruct.substring(8, 12), 2);
		sourceTwo = Integer.parseInt(instruct.substring(12, 16), 2);
		ioAddress = Integer.parseInt(instruct.substring(16, 32), 2);
	}

	@Override
	InsName parseOpCode(String instruct)throws CPUException{
		InsName insName = null;
		int x = Integer.parseInt(instruct.substring(2, 8), 2);
	
		switch(x){
			case 0:	insName = InsName.RD_INS;
				break;
			case 1:	insName = InsName.WR_INS;
				break;
			case 19: insName = InsName.NOP_INS;
				break;
		}
		if(insName == null)
			throw new CPUException("Opcode does not match expected instruction name");
	
		return insName;
	}

}
