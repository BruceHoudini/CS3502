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
	}
	public void resetRegisters(){
		for(int i = 0; i < 16; i++){
			register[i] = "0";
			while (register[i].length() < 32)
				register[i] = register[i] + "0";
		}
	}
	public void setReg(int index, String val){
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
	
}
