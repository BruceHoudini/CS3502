package os_resources;

public class TripleP {
	
	private int lower;
	private int upper;
	private int PID;
	
	public TripleP(int PID, int lower, int upper){
		this.PID = PID;
		this.lower = lower;
		this.upper = upper;
	}
	public int getLow(){
		return lower;
	}
	public int getUpper(){
		return upper;
	}
	public int getPID(){
		return PID;
	}
	public int compare(TripleP arg0, TripleP arg1) {
		
		return arg0.getLow()-arg1.getLow();
	}

}
