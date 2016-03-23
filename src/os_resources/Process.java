package os_resources;
import cpu_resources.Registers;

public class Process {
	
	private int PID, pAddr, numInst, numData, priority, sizeInBuff, sizeOutBuff, sizeTempBuff, rAddrBegin, rAddrEnd, inBuffAddr, outBuffAddr, tempBuffAddr, programCounter;
	private boolean loadedRAM, isReady;
	private PState state;
	private Registers pRegister;
	public Process(int PID, int pAddr, int numInst, int numData, int priority, int sizeInBuff, int sizeOutBuff, int sizeTempBuff){
		this.PID = PID;
		this.pAddr = pAddr;
		this.numInst = numInst;
		this.numData = numData;
		this.priority = priority;
		this.sizeInBuff = sizeInBuff;
		this.sizeOutBuff = sizeOutBuff;
		this.sizeTempBuff = sizeTempBuff;
		rAddrBegin = -1;
		rAddrEnd = -1;
		inBuffAddr = -1;
		outBuffAddr = -1;
		tempBuffAddr = -1;
		loadedRAM = false;
		isReady = false;
		programCounter = 0;
		state = PState.NEW;
		pRegister = new Registers();
	}
	
	//Obnoxiously long list of getters/setters
	//May be entirely superfluous
	
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
	public int getRAddrBegin(){
		return rAddrBegin;
	}
	public int getRAddrEnd(){
		return rAddrEnd;
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
	public boolean ramLoadState(){
		return loadedRAM;
	}
	public boolean readyStatus(){
		return isReady;
	}
	public int getProgramCounter(){
		return programCounter;
	}
	public PState getState(){
		return state;
	}
	public void setPID(int x){
		PID = x;
	}
	public void setPAddr(int x){
		pAddr = x;
	}
	public void setNumInst(int x){
		numInst = x;
	}
	public void setNumData(int x){
		numData = x;
	}
	public void setPriority(int x){
		priority = x;
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
	public void setRAddrBegin(int x){
		rAddrBegin = x;
	}
	public void setRAddrEnd(int x){
		rAddrEnd = x;
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
	public void isLoaded(){
		loadedRAM = true;
	}
	public void isNotLoaded(){
		loadedRAM = false;
	}
	public void Ready(){
		isReady = true;
	}
	public void notReady(){
		isReady = false;
	}
	public void pcPlus(){
		programCounter++;
	}
	public void setState(PState newState){
		state = newState;
	}
	public void saveRegisterState(Registers toSave){
		pRegister.copyAllRegs(toSave);
	}
	public Registers getAllRegisters(){
		return pRegister;
	}
	
}

