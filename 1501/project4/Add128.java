import java.util.*;

public class Add128 implements SymCipher{

	byte [] key;

	//creates random key of bytes
        //Store it in an array of bytes
	public Add128(){
		Random rgen = new Random();
		key = new byte[128];

		for(int i = 0; i < 128; i++)
		{
			int randomNumber = rgen.nextInt(256);
			key[i] = (byte)randomNumber;
		}
		System.out.println("Key: " + Arrays.toString(key));
	}

	public Add128(byte [] thekey){
		key = thekey;
	}

	//returns key
	public byte [] getKey(){
		return key;
	}

	//encodes string by adding each key[i] value to string.charAt(i)
	//key wraps to begginging if string is longer than key
	public byte [] encode (String S){

		System.out.println("Original message is: " + S);
                //Convert string to byte array
		byte [] encrypt = new byte [S.length()];

		System.out.print("Array of bytes is: ");

		for(int i = 0; i<S.length();i++)
			encrypt[i] = (byte) S.charAt(i);

		for(int i = 0; i < encrypt.length; i++)
			System.out.print(encrypt[i] + " ");
		System.out.println();

		int keyPos = 0;

		//adds key to text
		for(int i = 0; i<encrypt.length; i++){
			try{
				encrypt[i] = (byte)(encrypt[i] + key[keyPos]);
				keyPos++;
			}

			catch(ArrayIndexOutOfBoundsException e){
				keyPos = 0;
				encrypt[i] = (byte)(encrypt[i] + key[keyPos]);
				keyPos++;
			}
		}

		System.out.print("Encrypted Array: ");

		for(int i = 0; i<encrypt.length;i++)
			System.out.print(encrypt[i] + " " );
		System.out.println();

		return encrypt;
	}

	//decodes array of bytes 
	public String decode (byte [] bytes){

		System.out.print("Received bytes: " );
                byte [] decrypted = new byte[bytes.length];
		for(int i = 0; i< decrypted.length; i++)
			System.out.print(decrypted[i] + " ");
		System.out.println();

		int keyPos = 0; 

		for(int i = 0; i<decrypted.length; i++){
			try{
				decrypted[i] = (byte)(decrypted[i] - key[keyPos]);
				keyPos++;
			}

			catch(ArrayIndexOutOfBoundsException e){
				keyPos = 0;
				decrypted[i] = (byte)(decrypted[i] - key[keyPos]);
				keyPos++;
			}
		}

		System.out.print("The decrypted array of bytes is: ");

		for(int i = 0; i<decrypted.length;i++)
			System.out.print(decrypted[i] + " ");
		System.out.println();

		String decoded = new String(decrypted);

		System.out.println("The corresponding string is: " + decoded);
		return decoded;
	}
}