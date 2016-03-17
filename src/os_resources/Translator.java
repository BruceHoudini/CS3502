package os_resources;

public class Translator {
		public Translator()
		{
		
		}
	
		public byte toHex(int value)
		{
			Number hex;
			hex = value;
			return hex.byteValue();
		}
		public int toDec(int value)
		{
			Number dec;
			dec = value;
			return dec.intValue();
		}
		//INCOMPLETE! CONVERT BIN TO BINARY VALUE
		
}
