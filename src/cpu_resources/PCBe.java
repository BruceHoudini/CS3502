package cpu_resources;
import os_resources.PCB;
import os_resources.PState;
import os_resources.Process;

public class PCBe {
	private int cpuID, PID, PC, numInst, numData, sizeInBuff, sizeOutBuff, sizeTempBuff, baseRegister, inBuffAddr, outBuffAddr, tempBuffAddr, inCount, outCount, tempCount, priority, pAddr;
	private PState state;
	public Registers cpuRegister = new Registers();
	
	public Process savePCBeToProcess(){
		Process result = new Process(PID, pAddr, numInst, numData, priority, sizeInBuff, sizeOutBuff, sizeTempBuff);
		result.setRAddrBegin(baseRegister);
		result.setRAddrEnd(baseRegister + numInst + sizeInBuff + sizeOutBuff + sizeTempBuff);
		result.setInBuffAddr(inBuffAddr);
		result.setOutBuffAddr(outBuffAddr);
		result.setTempBuffAddr(tempBuffAddr);
		result.setPC(PC);
		
		result.copyAllFrom(getAllRegisters());
		return result;
	}
	public void suspendProcess(){
		Process result = savePCBeToProcess();
		result.setState(PState.WAITING);
		PCB.readyQueue.add(result);
		cpuRegister.resetRegisters();
		state = PState.WAITING;
	}
	
	
	public int getPID(){
		return PID;
	}
	public int getCpuID(){
		return cpuID;
	}
	public int getPC(){
		return PC;
	}
	public int getNumInst(){
		return numInst;
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
	public int getBaseRegister(){
		return baseRegister;
	}
	public int getInBuffAddr(){
		return inBuffAddr;
	}
	public int getOutBuffAddr(){
		return outBuffAddr;
	}
	public int getTempBuffAddr(){
		return tempBuffAddr;
	}
	public PState getState(){
		return state;
	}
	public int getInCount(){
		return inCount;
	}
	public int getOutCount(){
		return outCount;
	}
	public int getTempCount(){
		return tempCount;
	}
	public int getPAddr(){
		return pAddr;
	}
	public int getPriority(){
		return priority;
	}
	public void setPriority(int x){
		priority = x;
	}
	public void setPAddr(int x){
		pAddr = x;;
	}
	public void setCPUID(int x){
		cpuID = x;
	}
	public void setPID(int x){
		PID = x;
	}
	public void setPC(int x){
		PC = x;
	}
	public void setNumInst(int x){
		numInst = x;
	}
	public void setSizeInBuff(int x){
		sizeInBuff = x;
	}
	public void setSizeOutBuff(int x){
		sizeOutBuff = x;
	}
	public void setSizeTempBuff(int x){
		sizeTempBuff = x;
	}
	public void setBaseRegister(int x){
		baseRegister = x;
	}
	public void setInBuffAddr(int x){
		inBuffAddr = x;
	}
	public void setOutBuffAddr(int x){
		outBuffAddr = x;
	}
	public void setTempBuffAddr(int x){
		tempBuffAddr = x;
	}
	public void setState(PState x){
		state = x;
	}
	public void setInCount(int x){
		inCount = x;
	}
	public void setOutCount(int x){
		outCount = x;
	}      
	public void setTempCount(int x){
		tempCount = x;
	}
	public void pcPlus(){
		PC++;
	}
	public void inCountPlus(){
		inCount++;
	}
	public void outCountPlus(){
		outCount++;
	}
	public void tempCountPlus(){
		tempCount++;
	}
	public void copyAllFrom(Registers toSave){
		cpuRegister.copyAllRegs(toSave);
	}
	public Registers getAllRegisters(){
		return cpuRegister;
	}
}
