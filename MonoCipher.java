/**
 * Programming AE2
 * Contains monoalphabetic cipher and methods to encode and decode a character.
 */
public class MonoCipher
{
	/** The size of the alphabet. */
	private final int SIZE = 26;

	/** The alphabet. */
	private char [] alphabet;

	
	/** The cipher array. */
	private char [] cipher;

	
	/**
	 * Instantiates a new mono cipher.
	 * @param keyword the cipher keyword
	 */
	public MonoCipher(String keyword)
	{
		
		//Checking that I get the correct keyword
		System.out.println("You have entered the keyword: " + keyword);
		System.out.println("--------------------");
		
		
		//create alphabet
		alphabet = new char [SIZE];
		int i = 0;
		for ( i = 0; i < SIZE; i++) {
			alphabet[i] = (char)('A' + i);
		}
		System.out.println("The alphabet array is: ");
		System.out.println(alphabet);				// prints the whole alphabet array
		System.out.println("---------------------");
				
		
		
		// create first part of cipher from keyword	
		cipher = new char [SIZE];
		int k = 0;
		for (k = 0; k < keyword.length(); k++) {
			cipher[k] = keyword.charAt(k);
		}
		
		
		// create remainder of cipher from the remaining characters of the alphabet
		// print cipher array for testing and tutors
		int l = keyword.length();
		int subtract = 1;
		for( l = keyword.length(); l < SIZE; l++) {
			
			if (keyword.contains(Character.toString(alphabet[SIZE -subtract]))) {
				subtract++;
				l--;
			}
			else {
				cipher[l] = alphabet[SIZE -subtract];
				subtract ++;
			}	
		}
		System.out.println("The final cipher array is : ");
		System.out.println(cipher);
		System.out.println("--------------");
		
	}

	
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */
	public char encode(char ch)
	{	
		char encrypted_char = ' ';
		
		if (ch >='A' && ch <= 'Z') {
			int enc_pos_ind = ch - 'A';
			 encrypted_char = cipher[enc_pos_ind];			
		}
		else {
			 encrypted_char = ch;
		}
		return encrypted_char;
	}
	

	/**
	 * Decode a character
	 * @param ch the character to be encoded
	 * @return the decoded character
	 */
	public char decode(char ch)
	{
		char decrypted_char = ' ';
		if (ch >='A' && ch <= 'Z') {
			int m = 0;
			for (m = 0; m < SIZE; m++) {
				if (ch == cipher[m]) {
					decrypted_char = alphabet[m];
				}
			}
		}
		else {
			decrypted_char = ch;
		}
	    return decrypted_char;  
	}
}