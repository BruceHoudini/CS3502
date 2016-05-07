package os_resources;

//1024 word RAM
//Static array and methods allows memory to be accessed and modified
//by other classes. Does not require RAM object to be instantiated.

public class RAM {
	
	private static String[] memory = new String[1024];

	//Don't know if pointer will be necessary but I thought I would go ahead and add it anyways.
	//EDIT: Whether it was necessary or not it is now a central part of the memory system.
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
		while(i < 1024){
			memory[i] = null;
			i++;
		}
		pointer = 0;
		tail = 0;
	}
	
	//Used by the IOForm Instructions to save data to RAM
	//Assembly gives indirect address in bytes
	//RAM is indexed by word, 4 bytes per word. 
	//Must divide offset by 4 in order to obtain correct RAM index.
	public static void indirectSave(int base, int offset, String value){
		int index = base+(offset/4);
		value = Integer.toHexString(Integer.parseInt(value, 2));
		value = stringResize(value, 8);
		memory[index] = value;
		pointer = index + 1;
	}
	//Used by the IOForm Instructions to load data from RAM
	//Assembly gives indirect address in bytes
	//RAM is indexed by word, 4 bytes per word. 
	//Must divide offset by 4 in order to obtain correct RAM index.
	public static String indirectLoad(int base, int offset){
		int index = base+(offset/4);
	
		if(memory[index] == null){
			memory[index] = "0";
			memory[index] = stringResize(memory[index], 8);
		}
		
		String result = Integer.toBinaryString(Integer.parseInt(memory[index], 16));
		result = stringResize(result, 32);
		
		return result;
	}
	
	public static void setPointer(int x){
		pointer = x;
	}
	public static String stringResize(String string, int size){
		if (string.length() == size || string.length() > size){
			System.out.println("ERROR IN STRINGRESIZE: String is already larger than or equal to specified size.");
			return string;
		}
		while (string.length() < size)
			string = "0" + string;
		return string;
	}

}
