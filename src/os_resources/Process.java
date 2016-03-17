package os_resources;

public class Process {
	private int PID, pAddr, numInst, numData, priority, sizeInBuff, sizeOutBuff, sizeTempBuff;
	
	public Process(int PID, int pAddr, int numInst, int numData, int priority, int sizeInBuff, int sizeOutBuff, int sizeTempBuff){
		this.PID = PID;
		this.pAddr = pAddr;
		this.numInst = numInst;
		this.numData = numData;
		this.priority = priority;
		this.sizeInBuff = sizeInBuff;
		this.sizeOutBuff = sizeOutBuff;
		this.sizeTempBuff = sizeTempBuff;
	}
	
	public int getPID(){
		return PID;
	}
	public int getPAddr(){
		return pAddr;
	}
	public int getNumInst(){
		return numInst;
	}
	public int getNumData(){
		return numData;
	}
	public int getPriority(){
		return priority;
	}
	public int getSizeInBuff(){
		return sizeInBuff;
	}
	public int getSizeOutBuff(){
		return sizeOutBuff;
	}
	public int getSizeTempBuff(){
		return sizeTempBuff;
	}
}


