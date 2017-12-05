/**
 * Programming AE2
 * Class contains Vigenere cipher and methods to encode and decode a character
 */
public class VCipher
{
	private char [] alphabet;   	//the letters of the alphabet
	private char [][] cipher;		//the multidimensional vigenere cipher array
	private final int SIZE = 26;
	private int keyWordLength = 0;
	private int row_num_used = 0;
	
	/** 
	 * The constructor generates the cipher
	 * @param keyword the cipher keyword
	 */
	public VCipher(String keyword)
	{
		//CHECKING THE KEYWORD. CAN DELETE THAT
		System.out.println("You have entered the keyword: " + keyword);
		System.out.println("--------------------");
		
		keyWordLength = keyword.length();
				
				
		//create alphabet
		alphabet = new char [SIZE];
		int i = 0;
		for ( i = 0; i < SIZE; i++) {
			alphabet[i] = (char)('A' + i);
		}
		
		System.out.println("The alphabet array for the Vigenere cipher that is to be created is: ");
		System.out.println(alphabet);				// prints the whole alphabet array
		System.out.println("---------------------");
		
		
	
		//Creating the array for the vigenere cipher
		//It will have the same number of rows as the keyword
		// and the same number of columns as the alphabet array
		cipher = new char [keyWordLength][SIZE];		
		
		
		
		//generate the multidimensional array
		//For the first part it will take the letters of
		// the keyword and place them at the first position
		// of each row
		
		for (int row = 0 ; row < keyWordLength; row++)
			for (int col = 0; col < 1; col++) 					
				cipher[row][col] = keyword.charAt(row);
				
		
		//The remaining array 
		for (int r = 0 ; r < keyWordLength; r++) {
			char first = cipher[r][0];					//The first letter for each row
			for (int c =0 ; c < SIZE; c++) {		//Iterate through columns for each try of row with the first letter constant (for this row)
					
				cipher[r][c] = first++;	        //For each row, the rest of the letters are the additions of the first one in order
				if(first == 'Z'+ 1)				// ie if it is non capital = 'a'
				{
					first = 'A';				//make it capital
				}
							
			}
		}
						
		// The vigenere array printed in required form
		System.out.println("The array for the Vigenere cipher is: ");
		for (int a = 0; a < keyWordLength; a++)
		{
			for (int b = 0; b < SIZE; b++)			
				System.out.print(" "+cipher[a][b]);
				System.out.print("\n");
		}
		System.out.println("-------------");
	}
	
	
	
	
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */	
	public char encode(char ch)
	{
		int enc_pos_ind = ch - 'A';						
		char encrypted_char = ' ';						
		
		if (ch >='A' && ch <= 'Z') {							//Check if only letters encountered
			if (row_num_used < keyWordLength)					//check if the total number of rows used in every char encoding is less than the keyword
			{													//if it is I am not going to use up all the rows after the whole encoding
				encrypted_char = cipher[row_num_used ][enc_pos_ind];	
				row_num_used++;									
			}
		
			if (row_num_used == keyWordLength)			//If I have reached this point, I have to start over from row 0, and re- use the rows
					row_num_used = 0;						
		}
		else {
			encrypted_char = ch;						//Character is not a letter, and it will remain without encoding
		}
	    return encrypted_char;  						// returns the encoded character
	}
	
	

	
	/**
	 * Decode a character
	 * @param ch the character to be decoded
	 * @return the decoded character
	 */  
	public char decode(char ch)
	{	
		char decrypted_char = ' '; 
		
		//again checks if the letter is between A abd Z
		// if it is not, the decrypted char will be the same 
		// char, eg spaces etc
		if (ch >='A' && ch <= 'Z') {
			
			//If the numbers of rows used after each decoding is
			// greater than the keyword, start over from row 0
			for (int x = 0 ; x < SIZE; x++) {
				if(row_num_used > keyWordLength-1) {
					row_num_used = 0;
				}
				if (ch == cipher[row_num_used][x]) {
					decrypted_char = alphabet[x];
					row_num_used++;
					break;
				}
			}
		}
		else {
			decrypted_char = ch;
		}
		return decrypted_char;	
	}
				
}
