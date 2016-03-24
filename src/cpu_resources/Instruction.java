package cpu_resources;

public abstract class Instruction {
	
	protected InsFormat format;
	protected InsName opcode;
	
	abstract boolean execute();
	abstract InsName parseOpCode(String binaryString) throws CPUException;
	abstract void parseRegisters(String binaryString);
	//Implement this tomorrow. Decode() will create and return an Instruction object with all relevant
	//register information and instruction specific values.
	
	//Execute() will take an Instruction object and execute command based upon values.
	public InsFormat getFormat(){
		return format;
	}
	public InsName getOpCode(){
		return opcode;
	}
	public String resizeString(String string){
		while(string.length() < 32)
			string = "0" + string;
		return string;
	}

}
