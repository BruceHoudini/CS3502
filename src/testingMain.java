
public class testingMain {

	public static void main(String[] args) {
		ArrayTest.setEntry(0, 1);
		System.out.println(ArrayTest.getEntry(0));
		
		AccessTest cool = new AccessTest();
		
		System.out.println("This is the value of inty: " + cool.getInty());
		
		cool.intyPlus();
		
		System.out.println("This is the new value of inty: " + cool.getInty());
		

	}

}
