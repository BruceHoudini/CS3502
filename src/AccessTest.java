
public class AccessTest {
	
	private int inty;
	
	public AccessTest(){
		inty = ArrayTest.getEntry(0);
	}
	
	public void intyPlus(){
		inty++;
	}
	
	public int getInty(){
		return inty;
	}
	
}
