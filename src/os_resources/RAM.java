package os_resources;

//1024 word RAM
//Static array and methods allows memory to be accessed and modified
//by other classes. Does not require RAM object to be instantiated.

public class RAM {
	
	private static int[] memory = new int[1024];
	//pointer contains the index of the last place written to in memory
	//tail contains the highest index written to in memory
	private static int pointer;
	private static int tail = 0;
	
	public static void save(int index, int value){
		memory[index] = value;
		pointer = index;
		if (tail <= pointer)
			tail = pointer;
	}
	public static int load(int index){
		return index;
	}
	public static int getPointer(){
		return pointer;
	}
	public static void clearRAM(){
		for(int i : memory){
			memory[i] = 0;
			pointer = 0;
			tail = 0;
		}
	}
	
	/*
	 * Implement if needed: checks if there are any gaps in memory between processes
	 * i.e. Process 1 spans index 0 to index 15, Process 2 spans index 18 to index 32
	 * where index 16 and 17 are empty.
	 * 
	 	public static boolean isContiguous(){
	 
	 	}
	 *
	 *
	 */

}
