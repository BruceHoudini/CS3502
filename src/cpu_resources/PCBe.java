package cpu_resources;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import os_resources.MemoryException;
import os_resources.PCB;
import os_resources.PState;
import os_resources.Process;

public class PCBe {
	private volatile int cpuID, PID, PC, numInst, numData, sizeInBuff, sizeOutBuff, sizeTempBuff, baseRegister, inBuffAddr, outBuffAddr, tempBuffAddr, inCount, outCount, tempCount, priority, pAddr;
	private volatile PState state;
	public volatile Registers cpuRegister = new Registers();
	private volatile String[] cache;
	private volatile Queue<Integer> changes = new LinkedList<Integer>();
	private volatile int sizeCache;
	
	public PCBe(int sizeCache){
		this.sizeCache = sizeCache;
		cache = new String[sizeCache];
	}
	
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
	
	//FOLLOWING ARE COPY/PASTED METHODS FROM RAM WHICH WILL BE MODIFIED FOR USE WITH CACHE ARRAY
	//THIS WILL BE THE SIMPLEST METHOD OF MAINTAINING PROGRAM STRUCTURE WHILE STILL ALLOWING
	//FOR THE ADDITION OF INDIVIDUAL CPU CACHES
	
	//Don't know if pointer will be necessary but I thought I would go ahead and add it anyways.
		//EDIT: Whether it was necessary or not it is now a central part of the cache system.
		private int pointer;
		private int tail = 0;
		
		public void save(int index, String value) throws MemoryException{
			if (index < 0 || index >=sizeCache)
				throw new MemoryException("Invalid cache address: Expected value between 0 - " + sizeCache);
			cache[index] = value;
			pointer = index;
			if (tail < pointer)
				tail = pointer;
			pointer++;
		}
		
		//@overload
		//Accepts hexadecimal String as index
		public void save(String hexValIndex, String value) throws MemoryException{
			int index = Integer.valueOf(hexValIndex, 16);
			if (index < 0 || index >=sizeCache)
				throw new MemoryException("Invalid cache address: Expected value between 0 - " + sizeCache);
			cache[index] = value;
			pointer = index;
			if (tail < pointer)
				tail = pointer;
			pointer++;
		}
		
		//@overload
		//Saves passed value to current pointer location
		public void save(String value){
			cache[pointer] = value;
			if (tail < pointer)
				tail = pointer;
			pointer++;
		}
		
		public String load(int index) throws MemoryException{
			if (index < 0 || index >=sizeCache)
				throw new MemoryException("Invalid cache address: Expected value between 0 - " + sizeCache);
			return cache[index];
		}
		
		//@overload
		//Accepts hexadecimal String as index
		public String load(String hexValIndex) throws MemoryException{
			int index = Integer.valueOf(hexValIndex, 16);
			if (index < 0 || index >=sizeCache)
				throw new MemoryException("Invalid cache address: Expected value between 0 - " + sizeCache);
			return cache[index];
		}
		
		//Pointer is the index of the next available location in cache (usually tail + 1)
		public int getPointer(){
			return pointer;
		}
		
		//tail contains the highest index written to in cache
		public int getTail(){
			return tail;
		}
		
		public void clearCache(){
			int i = 0;
			while(i < sizeCache){
				cache[i] = null;
				i++;
			}
			pointer = 0;
			tail = 0;
		}
		
		//Used by the IOForm Instructions to save data to RAM
		//Assembly gives indirect address in bytes
		//RAM is indexed by word, 4 bytes per word. 
		//Must divide offset by 4 in order to obtain correct RAM index.
		public void indirectSave(int base, int offset, String value){
			int index = (offset/4);
			value = Integer.toHexString(Integer.parseInt(value, 2));
			value = stringResize(value, 8);
			cache[index] = value;
			pointer = index + 1;
			changes.add(index);
		}
		//Used by the IOForm Instructions to load data from RAM
		//Assembly gives indirect address in bytes
		//RAM is indexed by word, 4 bytes per word. 
		//Must divide offset by 4 in order to obtain correct RAM index.
		public String indirectLoad(int base, int offset){
			int index = (offset/4);
		
			if(cache[index] == null){
				cache[index] = "0";
				cache[index] = stringResize(cache[index], 8);
			}
			
			String result = Integer.toBinaryString(Integer.parseInt(cache[index], 16));
			result = stringResize(result, 32);
			
			return result;
		}
		
		public void setPointer(int x){
			pointer = x;
		}
		public String stringResize(String string, int size){
			if (string.length() == size || string.length() > size){
				System.out.println("ERROR IN STRINGRESIZE: String is already larger than or equal to specified size.");
				return string;
			}
			while (string.length() < size)
				string = "0" + string;
			return string;
		}
		
	//END OF COPY/PASTE MODIFIED RAM METHODS
		
	public int getChange(){
		return changes.remove();
	}
	public boolean isChangeEmpty(){
		return changes.isEmpty();
	}
	public void clearChanges(){
		changes.clear();
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
