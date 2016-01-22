import java.util.*;


public class Substitute implements SymCipher{

	byte [] key;
	public static int  [] otherKey;
        //Create a random 256 byte arraylist
	public Substitute(){
		key = new byte[256];
		Random rand = new Random();

		ArrayList<Byte> Ascii = new ArrayList<Byte>();

		//load the 256 ASCII values into an ArrayList. 
		for(int i = 0; i<256; i++)
			Ascii.add(new Byte ((byte) i));

		int i = 0;

		 
		while(i<256){
			int random = rand.nextInt(Ascii.size());
			Byte newByte = Ascii.get(random);
			key[i] = (byte) newByte.byteValue();
			Ascii.remove(random);
			i++;
		}

		//creates other key used for decoding
		otherKey = new int[256];

		for(int j = 0; j<otherKey.length;j++){
			int enter = (int) (key[j] + 128); 		//holds substituted value for corresponding positon. 
			otherKey[enter] = j;					//Since array indexes cannot be negative, adds 128 to the byte value to counter two's compliment
		}
	}

	public Substitute(byte [] thekey){
		key = thekey;

		otherKey = new int[256];

		for(int j = 0; j<otherKey.length;j++){
			int enter = (int) (key[j] + 128); 		//holds substituted value for corresponding positon. 
			otherKey[enter] = j;
		}
	}

	//returns key
	public byte [] getKey()
	{
		return key;
	}

	//encodes string 
	public byte [] encode (String S){
		byte [] stringToByte = new byte[S.length()];
		byte [] encodedString =  new byte[S.length()];

		System.out.println("The original massage is: " + S);

		System.out.print("The corresponding array of bytes is: ");


		for(int i = 0; i < S.length(); i++){
			stringToByte[i] = (byte) S.charAt(i);
			System.out.print(stringToByte[i] + " ");
		}
		System.out.println();

		System.out.print("The encrypted array of bytes is: ");

		for(int i = 0; i < stringToByte.length; i++){
			encodedString[i] = (byte) key[(int) S.charAt(i)];
			System.out.print(encodedString[i] + " ");
		}

		System.out.println();

		return encodedString;
	}

	//decodes string 
	public String decode (byte [] bytes){
		char [] decodedString = new char[bytes.length];

		System.out.print("Encrypted Array: ");

		for(int i = 0; i < bytes.length; i++)
			System.out.print(bytes[i] + " ");
		System.out.println();

		System.out.print("Decrypted Array: ");

		for(int i = 0; i<bytes.length;i++){
			int currValue = (int) (bytes[i] + 128) ;
			decodedString[i] = (char) otherKey[currValue];
			System.out.print((byte) decodedString[i] + " ");
		}
		System.out.println();

		String decoded = new String(decodedString);
		System.out.println("String: " + decoded);

		return decoded;
	}
}