package cpu_resources;

import java.math.BigInteger;

public class ArithmeticForm extends Instruction{
	
	private int sourceOne;
	private int sourceTwo;
	private int destination;
	private PCBe pcb;
	
	public ArithmeticForm(String instruct, PCBe pcb) throws CPUException{
		this.pcb = pcb;
		format = InsFormat.ARI_FORM;
		opcode = parseOpCode(instruct);
		parseRegisters(instruct);
	}
	
	
	@Override
	public boolean execute() {
		if (opcode == InsName.MOV_INS){
			pcb.cpuRegister.setReg(sourceOne, pcb.cpuRegister.getReg(sourceTwo));
			return true;
		}
		else if(opcode == InsName.ADD_INS){
			
			int base = new BigInteger(pcb.cpuRegister.getReg(sourceOne), 2).intValue();
			int secondary = new BigInteger(pcb.cpuRegister.getReg(sourceTwo), 2).intValue();
			int sum = base + secondary;
			String result = Integer.toBinaryString(sum);
			result = resizeString(result);
			pcb.cpuRegister.setReg(destination, result);
			return true;
		}
		else if(opcode == InsName.SUB_INS){
			//Subtracting sourceTwo from sourceOne
			//Could be source of error if order of operations reversed.
			int base = new BigInteger(pcb.cpuRegister.getReg(sourceOne), 2).intValue();
			int secondary = new BigInteger(pcb.cpuRegister.getReg(sourceTwo), 2).intValue();
			int sum = base - secondary;
			String result = Integer.toBinaryString(sum);
			result = resizeString(result);
			pcb.cpuRegister.setReg(destination, result);
			return true;
		}
		else if(opcode == InsName.MUL_INS){
			int base = new BigInteger(pcb.cpuRegister.getReg(sourceOne), 2).intValue();
			int secondary = new BigInteger(pcb.cpuRegister.getReg(sourceTwo), 2).intValue();
			int sum = base * secondary;
			String result = Integer.toBinaryString(sum);
			result = resizeString(result);
			pcb.cpuRegister.setReg(destination, result);
			return true;
		}
		else if(opcode == InsName.DIV_INS){
			//Dividing sourceOne by sourceTwo
			//Could be source of error if order of operations reversed.
			int base = new BigInteger(pcb.cpuRegister.getReg(sourceOne), 2).intValue();
			int secondary = new BigInteger(pcb.cpuRegister.getReg(sourceTwo), 2).intValue();
			if (secondary == 0)
			{
				System.out.println("Divid by zero error, operation not completed");
				return true;
			}
			int sum = base / secondary;
			String result = Integer.toBinaryString(sum);
			result = resizeString(result);
			pcb.cpuRegister.setReg(destination, result);
			return true;
		}
		else if(opcode == InsName.AND_INS){
			String result = null;
			for(int i = 0; i < 32; i++){
				if(pcb.cpuRegister.getReg(sourceOne).charAt(i) == 1 && pcb.cpuRegister.getReg(sourceTwo).charAt(i) == 1)
					result = result + "1";
				else
					result = result + "0";
			}
			pcb.cpuRegister.setReg(destination, result);
			return true;
		}
		else if(opcode == InsName.OR_INS){
			String result = null;
			for(int i = 0; i < 32; i++){
				if(pcb.cpuRegister.getReg(sourceOne).charAt(i) == 1 || pcb.cpuRegister.getReg(sourceTwo).charAt(i) == 1)
					result = result + "1";
				else
					result = result + "0";
			}
			pcb.cpuRegister.setReg(destination, result);
		}
		else if(opcode == InsName.SLT_INS){
			int base = new BigInteger(pcb.cpuRegister.getReg(sourceOne), 2).intValue();
			int secondary = new BigInteger(pcb.cpuRegister.getReg(sourceTwo), 2).intValue();
			int sum;
			if (base < secondary)
				sum = 1;
			else
				sum = 0;
			String result = Integer.toBinaryString(sum);
			result = resizeString(result);
			pcb.cpuRegister.setReg(destination, result);
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
			case 4: insName = InsName.MOV_INS;
				break;
			case 5:	insName = InsName.ADD_INS;
				break;
			case 6:	insName = InsName.SUB_INS;
				break;
			case 7:	insName = InsName.MUL_INS;
				break;
			case 8:	insName = InsName.DIV_INS;
				break;
			case 9:	insName = InsName.AND_INS;
				break;
			case 10:insName = InsName.OR_INS;
				break;
			case 16:insName = InsName.SLT_INS;
				break;
			case 19: insName = InsName.NOP_INS;
				break;
		}
		if(insName == null)
			throw new CPUException("Opcode does not match expected instruction name");

		return insName;
	}
	@Override
	void parseRegisters(String instruct){
		sourceOne = Integer.parseInt(instruct.substring(8, 12), 2);
		sourceTwo = Integer.parseInt(instruct.substring(12, 16), 2);
		destination = Integer.parseInt(instruct.substring(16, 20), 2);
	}

}
