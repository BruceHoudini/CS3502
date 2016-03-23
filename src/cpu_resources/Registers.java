package cpu_resources;

import java.math.BigInteger;

public class Registers {
	private String[] register;
	
	public Registers(){
		register = new String[16];
	}
	public void setReg(int index, String val){
		register[index] = val;
	}
	public String getReg(int index){
		return register[index];
	}
	public void copyAllRegs(Registers toSave){
		for (int i = 0; i < 16; i++)
			register[i] = toSave.getReg(i);
	}
	public void add(int baseIndex, int secondaryIndex){
		int base = new BigInteger(register[baseIndex], 2).intValue();
		int secondary = new BigInteger(register[secondaryIndex], 2).intValue();
		int result = base + secondary;
		register[baseIndex] = Integer.toBinaryString(result);
	}
	public void subtract(int baseIndex, int secondaryIndex){
		int base = new BigInteger(register[baseIndex], 2).intValue();
		int secondary = new BigInteger(register[secondaryIndex], 2).intValue();
		int result = base - secondary;
		register[baseIndex] = Integer.toBinaryString(result);
	}
}
