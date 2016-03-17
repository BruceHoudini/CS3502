package cpu_resources;

public class InstructionReaderTest {
	
		//This is just for experimental purposes
		//Will implement real version at later time
	
		//Responsible for ensuring that instruction string
		//is a valid instruction based upon parameters
		//in document Instruction Format
		public InstructionReaderTest(){
			
		}
		
		public InsFormat getType(String inst) throws CPUException{
			if (inst.length() != 32){
				throw new CPUException("Instruction too short return error");
			}
			else if(inst.charAt(0) == 0)
			{
				if(inst.charAt(1) == 0)
					return InsFormat.ARI_FORM;
				else
					return InsFormat.CAI_FORM;
			}
			else{
				if(inst.charAt(1) == 0)
					return InsFormat.UNJ_FORM;
				else
					return InsFormat.IOI_FORM;
			}
		}
		
	

}
