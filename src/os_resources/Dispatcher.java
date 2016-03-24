package os_resources;
import cpu_resources.CPU;
import cpu_resources.PCBe;

public class Dispatcher {
	
	public void dispatch(CPU cpu){
		
		transferAllValuesToCPUPCB(Scheduler.readyQueue.remove(), cpu.getPCB());
	}
	private void transferAllValuesToCPUPCB(Process readyProcess, PCBe cpuPCB){
		//cpuPCB.copyAllFrom(readyProcess.getAllRegisters());
		cpuPCB.setCPUID(1);
		cpuPCB.setPID(readyProcess.getPID());
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
		cpuPCB.setState(readyProcess.getState());
	}

}
