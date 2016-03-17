package os_resources;

//2048 word Disk
//Static array and methods allows memory to be accessed and modified
//by other classes. Does not require Disk object to be instantiated.

public class DISK{
	
	private static String[] memory = new String[2048];

	private static int pointer;
	private static int tail = 0;

	
	public static void save(int index, String value) throws MemoryException{
		if (index < 0 || index >=2048)
			throw new MemoryException("Invalid DISK address: Expected value between 0 - 2048.");
		memory[index] = value;
		pointer = index;
		if (tail < pointer)
			tail = pointer;
		pointer++;
	}
	public static String load(int index) throws MemoryException{
		if (index < 0 || index >=2048)
			throw new MemoryException("Invalid DISK address: Expected value between 0 - 2048.");
		return memory[index];
	}
	
	//Pointer is the index of the next available location in memory (usually tail + 1)
	public static int getPointer(){
		return pointer;
	}
	
	//Tail stores the highest index written to.
	public static int getTail(){
		return tail;
	}
	public static void clearDISK(){
		int i = 0;
		while(i < 2048){
			memory[i] = null;
			i++;
		}
		pointer = 0;
		tail = 0;
	}

}
