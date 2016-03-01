package os_resources;

//2048 word Disk
//Static array and methods allows memory to be accessed and modified
//by other classes. Does not require Disk object to be instantiated.

public class DISK {
	
	private static int[] memory = new int[1024];
	
	public static void save(int index, int value){
		memory[index] = value;
	}
	public static int load(int index){
		return index;
	}

}

