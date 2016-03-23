package cpu_resources;

public class CPU {
	private PCBe pcb;
	public CPU(){
		pcb = new PCBe();
	}
	public PCBe getPCB(){
		return pcb;
	}

}
