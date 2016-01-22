/*************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *
 *************************************************************************/

import java.math.*;

public class LZWmod
{
	private static double readIn = 0.0;
	private static double readOut = 0.0;
	private static double oldReadIn = 0.0;
	private static double oldReadOut = 0.0;
        private static double compRatio  = 0;
        
        private static int R = 256;
   	private static int W = 9;
   	private static int L = createCodeWords(W);
        
   	private static int flag = 0;
   	private static boolean monFlag = false;
        
        private static int code;
   	private static int t;
	private static String input;
	private static String s;
	private static int i;
   	
   	private static TST<Integer> st = new TST<Integer>();
   	private static String[] string = new String[65536];
   	

	private static double threshold = 0.0;
	private static double oldRatio = 0.0;
	private static double newRatio = 0.0;
//////////////////////////////CREATE CODEWORDS///////////////
	//Calculates number of codewords by using 2^w
   public static int createCodeWords(int codeWordInt)
   {
	   double temp = Math.pow(2, codeWordInt);
	   int temp1 = (int)temp;
	   return temp1;
   }
///////////////////////////////////////////////////////////
/////////////////////////////RESIZE/////////////////////
   public static void resize()
   {
		if(W == 16)
		{
			switch(flag)
			{
				case 0:
					break;
				case 1:
					//Reset the dictionary
					reset();
					if(W != 9)
					{
						reset();
					}
					//else
					{
						i = 256;
						code = 257;
						break;
					}
				case 2:
				  	
					if (oldRatio == 0) 
                                        {
						oldRatio = (oldReadIn / oldReadOut);
					} 
					break;
			}
		}
		else
		{
			W = W + 1;
			L = createCodeWords(W);
		}
   }
 /////////////////////////////////////////////////////////////////////      
////////////////////////compress////////////////////////////////////////
   public static void compress()
   {
        //Get the string from BinaryStdIn
        String input = BinaryStdIn.readString();
		BinaryStdOut.write(flag);

        st = new TST<Integer>();
        for (int i = 0; i < R; i++)
        {
			//Put the string, codeword into TST
            st.put("" + (char) i, i);
		}
        code = R+1;

        while (input.length() > 0)
        {
			//Find max prefix match s
            s = st.longestPrefixOf(input);
            //Print s's encoding
            BinaryStdOut.write(st.get(s), W);
            t = s.length();
            //Add s to symbol table
            if (t < input.length() && code < L)
            {
				st.put(input.substring(0, t + 1), code++);
			}
            //CHECK IF CODEWORD LENGTH IS FULL
			if(code == L)
			{
				resize();
			}
			
            input = input.substring(t);
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }
   ///////////////////////////////////////////////////////////////////////////
   /////////////////////////RESET////////////////////////////////////////////
     public static void reset()
   {
		  st = new TST<Integer>();
		  for (int i = 0; i < R; i++)
		  {
			  //Put the string, codeword into TST
		      st.put("" + (char) i, i);
		  }
		  st.put("" + (char) 256, 256);
		  string = new String[65536];
		  for (i = 0; i < 256; i++)
		  {
			  string[i] = "" + (char) i;
		  }
		  string[i++] = "";
		  W = 9;
		  L = createCodeWords(W);
   }
/////////////////////////////////////////////////////////////////////////////
/////////////////////////////COMPARE RATIO/////////////////////////////////
   
   public static double compareRatio(double newRatio)
   {
	   threshold = oldRatio / newRatio;

		return threshold;
   }
   /////////////////////////////////////////////////////////////////////////////

    public static void expand()
    {
        //Make sure the array is extend to the largest value of L
        string = new String[65536];
        //Next available codeword value
		flag  = BinaryStdIn.readInt();

		if (flag == 2) {
			monFlag = true;
		} //if

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
        {
            string[i] = "" + (char) i;
		}//for
         // (unused) lookahead for EOF
        string[i++] = "";
        int codeword = BinaryStdIn.readInt(W);
        String val = string[codeword];
        while(true)
        {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if(codeword == R)
            {
				break;
			}//if
            String s = string[codeword];
            if(i == codeword)
            {
				//special case hack
				s = val + val.charAt(0);
			}//if
            if (i < L)
            {
				string[i++] = val + s.charAt(0);
			}//if
            if(i == L - 1)
			{
				resize();
			}//if
			if(monFlag == true)
			{
				//Keep track of bits read in
				readIn = readIn + W;
				readOut = readOut + (8 * val.length());

				t = s.length();

				oldReadOut = oldReadOut + (8 * t);
				oldReadIn = oldReadIn + W;

				//Calculate compression ratio
				newRatio = (readOut / readIn);

				//Determine threshold
				threshold = compareRatio(newRatio);

				//If threshold exceeds 1.1
				if(threshold > 1.1)
				{
					reset();
					oldRatio = 0;
					i = 257;
				}
			}//if
			val = s;
        }//while
        BinaryStdOut.close();
    }//expand

	//Command line input java LZWmod - n < input.txt > output.lzw
	//Command line input java LZWmod + < output.lzw > output.txt
    public static void main(String[] args)
    {
        if(args[0].equals("-"))
        {
			if(args[1].equals("n"))
			{
				flag = 0;
			}
			else if(args[1].equals("r"))
			{
				//Reset
				flag = 1;
			}
			else if (args[1].equals("m"))
			{
				//Monitor
				flag = 2;
				monFlag = true;
			}
			else throw new RuntimeException("Illegal command line argument");
			compress();
		}
        else if(args[0].equals("+"))
        {
			expand();
		}
        else throw new RuntimeException("Illegal command line argument");
    }
}
