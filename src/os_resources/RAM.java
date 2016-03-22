package os_resources;

//1024 word RAM
//Static array and methods allows memory to be accessed and modified
//by other classes. Does not require RAM object to be instantiated.

public class RAM {
	
	private static String[] memory = new String[1024];

	//Don't know if pointer will be necessary but I thought I would go ahead and add it anyways.
	private static int pointer;
	private static int tail = 0;
	
	public static void save(int index, String value) throws MemoryException{
		if (index < 0 || index >=1024)
			throw new MemoryException("Invalid RAM address: Expected value between 0 - 1023.");
		memory[index] = value;
		pointer = index;
		if (tail < pointer)
			tail = pointer;
		pointer++;
	}
	
	//@overload
	//Accepts hexadecimal String as index
	public static void save(String hexValIndex, String value) throws MemoryException{
		int index = Integer.valueOf(hexValIndex, 16);
		if (index < 0 || index >=1024)
			throw new MemoryException("Invalid RAM address: Expected value between 0 - 1023.");
		memory[index] = value;
		pointer = index;
		if (tail < pointer)
			tail = pointer;
		pointer++;
	}
	
	//@overload
	//Saves passed value to current pointer location
	public static void save(String value){
		memory[pointer] = value;
		if (tail < pointer)
			tail = pointer;
		pointer++;
	}
	
	public static String load(int index) throws MemoryException{
		if (index < 0 || index >=1024)
			throw new MemoryException("Invalid RAM address: Expected value between 0 - 1023.");
		return memory[index];
	}
	
	//@overload
	//Accepts hexadecimal String as index
	public static String load(String hexValIndex) throws MemoryException{
		int index = Integer.valueOf(hexValIndex, 16);
		if (index < 0 || index >=1024)
			throw new MemoryException("Invalid RAM address: Expected value between 0 - 1023.");
		return memory[index];
	}
	
	//Pointer is the index of the next available location in memory (usually tail + 1)
	public static int getPointer(){
		return pointer;
	}
	
	//tail contains the highest index written to in memory
	public static int getTail(){
		return tail;
	}
	
	public static void clearRAM(){
		int i = 0;
		while(i < 2048){
			memory[i] = null;
			i++;
		}
		pointer = 0;
		tail = 0;
	}
	
	public static void setPointer(int x){
		pointer = x;
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
