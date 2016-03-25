package cpu_resources;

import java.math.BigInteger;

public class Registers {
	private String[] register;
	
	public Registers(){
		register = new String[16];
		for(int i = 0; i < 16; i++){
			register[i] = "0";
			while (register[i].length() < 32)
				register[i] = register[i] + "0";
		}
		//debug
		/*
		System.out.println("ARE THE REGISTERS SET?!");
		for(int i = 0; i < 16; i++)
			System.out.println(register[i]);
		*/
		//debug
	}
	public void setReg(int index, String val){
		//debug
		//System.out.println("Index at setReg: " + index);
		//System.out.println("StringValue at setReg: " + val);
		//debug
		if(val.length() < 32)
			while (val.length() < 32)
				val = "0" + val;
		register[index] = val;
	}
	public String getReg(int index){
		return register[index];
	}
	public void copyAllRegs(Registers toSave){
		for (int i = 0; i < 16; i++)
			register[i] = toSave.getReg(i);
	}
	
	
	/*public void add(int baseIndex, int secondaryIndex){
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
	*/
}
