package os_resources;
import java.util.ArrayList;

import cpu_resources.CPU;
import cpu_resources.PCBe;
import cpu_resources.Status;

public class Dispatcher {
	
	public void dispatch(CPU[] cpuArray, int numProcessors) throws MemoryException{
				while (!PCB.readyQueue.isEmpty() && !PCB.cpuWaitingQueue.isEmpty())
					transferAllValuesToCPUPCB(PCB.readyQueue.remove(), PCB.cpuWaitingQueue.remove().getPCB());
	}
		
	private void transferAllValuesToCPUPCB(Process readyProcess, PCBe cpuPCB) throws MemoryException{
		cpuPCB.copyAllFrom(readyProcess.getAllRegisters());
		//cpuPCB.cpuRegister.resetRegisters();
		cpuPCB.setCPUID(cpuPCB.getCpuID());
		cpuPCB.setPID(readyProcess.getPID());
		cpuPCB.setPriority(readyProcess.getPriority());
		cpuPCB.setPAddr(readyProcess.getPAddr());
		cpuPCB.setPC(readyProcess.getProgramCounter());;
		cpuPCB.setNumInst(readyProcess.getNumInst());;
		cpuPCB.setSizeInBuff(readyProcess.getSizeInBuff());
		cpuPCB.setSizeOutBuff(readyProcess.getSizeOutBuff());
		cpuPCB.setSizeTempBuff(readyProcess.getSizeTempBuff());
		cpuPCB.setBaseRegister(readyProcess.getRAddrBegin());
		cpuPCB.setInBuffAddr(readyProcess.getInBuffAddr());
		cpuPCB.setOutBuffAddr(readyProcess.getOutBuffAddr());
		cpuPCB.setTempBuffAddr(readyProcess.getTempBuffAddr());
		cpuPCB.setInCount(readyProcess.getInCount());
		cpuPCB.setOutCount(readyProcess.getOutCount());
		cpuPCB.setTempCount(readyProcess.getTempCount());
		readyProcess.setState(PState.RUNNING);
		Scheduler.shortSchedule(readyProcess, cpuPCB);
		cpuPCB.setState(PState.READY);
	}

}
