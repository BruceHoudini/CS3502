package cpu_resources;

import java.math.BigInteger;

import os_resources.RAM;

public class CBIForm extends Instruction{
	private PCBe pcb;
	private int sourceReg;
	private int destination;
	private int addressData;
	
	public CBIForm(String instruct, PCBe pcb) throws CPUException{
		this.pcb = pcb;
		format = InsFormat.CAI_FORM;
		opcode = parseOpCode(instruct);
		parseRegisters(instruct);
	}

	@Override
	boolean execute() {
		if(opcode == InsName.ST_INS){
			RAM.indirectSave(pcb.getBaseRegister(), Integer.parseInt(pcb.cpuRegister.getReg(destination), 2), pcb.cpuRegister.getReg(sourceReg));
			return true;
		}
		else if (opcode == InsName.LW_INS  ){
			pcb.cpuRegister.setReg(destination, RAM.indirectLoad(pcb.getBaseRegister(), Integer.parseInt(pcb.cpuRegister.getReg(sourceReg), 2)));
			return true;
		}
		else if (opcode == InsName.MOVI_INS){
			String result = Integer.toBinaryString(addressData);
			result = resizeString(result);
			pcb.cpuRegister.setReg(destination, result);
			return true;
		}
		else if (opcode == InsName.ADDI_INS){
		
			int base = new BigInteger(pcb.cpuRegister.getReg(destination), 2).intValue();
			int sum = base + addressData;
			String result = Integer.toBinaryString(sum);
			result = resizeString(result);
			pcb.cpuRegister.setReg(destination, result);
			
			return true;
		}
		else if (opcode == InsName.MULI_INS){
			int base = new BigInteger(pcb.cpuRegister.getReg(destination), 2).intValue();
			int sum = base * addressData;
			String result = Integer.toBinaryString(sum);
			result = resizeString(result);
			pcb.cpuRegister.setReg(destination, result);
			return true;
		}
		else if (opcode == InsName.DIVI_INS){
			int base = new BigInteger(pcb.cpuRegister.getReg(destination), 2).intValue();
			int sum = base / addressData;
			String result = Integer.toBinaryString(sum);
			result = resizeString(result);
			pcb.cpuRegister.setReg(destination, result);
			return true;
		}
		else if (opcode == InsName.LDI_INS ){

			String result = Integer.toBinaryString(addressData);
			result = resizeString(result);
			pcb.cpuRegister.setReg(destination, result);
			
			return true;
		}
		else if (opcode == InsName.SLTI_INS){
			int base = new BigInteger(pcb.cpuRegister.getReg(sourceReg), 2).intValue();
			int secondary = addressData;
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
		else if (opcode == InsName.BEQ_INS ){
			int base = new BigInteger(pcb.cpuRegister.getReg(sourceReg), 2).intValue();
			int secondary = new BigInteger(pcb.cpuRegister.getReg(destination), 2).intValue();
			if (base == secondary){
				pcb.setPC((addressData/4)-1);
			}
			return true;
		}
		else if (opcode == InsName.BNE_INS ){
			int base = new BigInteger(pcb.cpuRegister.getReg(sourceReg), 2).intValue();
			int secondary = new BigInteger(pcb.cpuRegister.getReg(destination), 2).intValue();
			if (base != secondary){
				pcb.setPC((addressData/4)-1);
			}
			return true;
		}
		else if (opcode == InsName.BEZ_INS ){
			int base = new BigInteger(pcb.cpuRegister.getReg(sourceReg), 2).intValue();
			if (base == 0){
				pcb.setPC((addressData/4)-1);
			}
			return true;
		}
		else if (opcode == InsName.BNZ_INS ){
			int base = new BigInteger(pcb.cpuRegister.getReg(sourceReg), 2).intValue();
			if (base != 0){
				pcb.setPC((addressData/4)-1);
			}
			return true;
		}
		else if (opcode == InsName.BGZ_INS ){
			int base = new BigInteger(pcb.cpuRegister.getReg(sourceReg), 2).intValue();
			if (base > 0){
				pcb.setPC((addressData/4)-1);
			}
			return true;
		}
		else if (opcode == InsName.BLZ_INS ){
			int base = new BigInteger(pcb.cpuRegister.getReg(sourceReg), 2).intValue();
			if (base < 0){
				pcb.setPC((addressData/4)-1);
			}
			return true;
		}
		else if (opcode == InsName.NOP_INS ){
			return true;
		}
		else
			return false;
	}
	
	@Override
	void parseRegisters(String instruct){
		
		sourceReg = Integer.parseInt(instruct.substring(8, 12), 2);
		destination = Integer.parseInt(instruct.substring(12, 16), 2);
		addressData = Integer.parseInt(instruct.substring(16, 32), 2);
	}

	@Override
	InsName parseOpCode(String instruct)throws CPUException {
		InsName insName = null;
		int x = Integer.parseInt(instruct.substring(2, 8), 2);

		switch(x){
			case 2:	insName = InsName.ST_INS;
				break;
			case 3:	insName = InsName.LW_INS;
				break;
			case 11:insName = InsName.MOVI_INS;
				break;
			case 12:insName = InsName.ADDI_INS;
				break;
			case 13:insName = InsName.MULI_INS;
				break;
			case 14:insName = InsName.DIVI_INS;
				break;
			case 15:insName = InsName.LDI_INS;
				break;
			case 17:insName = InsName.SLTI_INS;
				break;
			case 21:insName = InsName.BEQ_INS;
				break;
			case 22:insName = InsName.BNE_INS;
				break;
			case 23:insName = InsName.BEZ_INS;
				break;
			case 24:insName = InsName.BNZ_INS;
				break;
			case 25:insName = InsName.BGZ_INS;
				break;
			case 26:insName = InsName.BLZ_INS;
				break;
			case 19: insName = InsName.NOP_INS;
				break;
		}
		if(insName == null)
			throw new CPUException("Opcode does not match expected instruction name");
		return insName;
	}

}
