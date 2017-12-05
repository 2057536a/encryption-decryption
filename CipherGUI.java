import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

/**
 * Programming AE2
 * Class to display cipher GUI and listen for events
 */
public class CipherGUI extends JFrame implements ActionListener
{
	//instance variables which are the components
	private JPanel top, bottom, middle;
	private JButton monoButton, vigenereButton;
	private JTextField keyField, messageField;
	private JLabel keyLabel, messageLabel;
	
	
	// instance variables for parts that have been created
	private String keyFieldString, messageFileString, stringToWrite;	
	
	
	//application instance variables
	//including the 'core' part of the text file filename
	//some way of indicating whether encoding or decoding is to be done
	private MonoCipher mcipher;
	private VCipher vcipher;
	private LetterFrequencies freqs;
	private FileWriter writer, writer2,writer3,writer4;
	
	
	/**
	 * The constructor adds all the components to the frame
	 */
	public CipherGUI()
	{
		this.setSize(400,150);
		this.setLocation(500,250);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
	}
	
	
	
	/**
	 * Helper method to add components to the frame
	 */
	public void layoutComponents()
	{
		//top panel is yellow and contains a text field of 10 characters
		top = new JPanel();
		top.setBackground(Color.yellow);
		keyLabel = new JLabel("Keyword : ");
		top.add(keyLabel);
		keyField = new JTextField(10);
		top.add(keyField);
		this.add(top,BorderLayout.NORTH);
		
		//middle panel is yellow and contains a text field of 10 characters
		middle = new JPanel();
		middle.setBackground(Color.yellow);
		messageLabel = new JLabel("Message file : ");
		middle.add(messageLabel);
		messageField = new JTextField(10);
		middle.add(messageField);
		this.add(middle,BorderLayout.CENTER);
		
		//bottom panel is green and contains 2 buttons
		
		bottom = new JPanel();
		bottom.setBackground(Color.green);
		//create mono button and add it to the top panel
		monoButton = new JButton("Process Mono Cipher");
		monoButton.addActionListener(this);
		bottom.add(monoButton);
		//create vigenere button and add it to the top panel
		vigenereButton = new JButton("Process Vigenere Cipher");
		vigenereButton.addActionListener(this);
		bottom.add(vigenereButton);
		//add the top panel
		this.add(bottom,BorderLayout.SOUTH);
	}
	
	
	
	/**
	 * Listen for and react to button press events
	 * (use helper methods below)
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e)
	{
		
		// Depending on which button is clicked
		// I will validate the keyword and the message file name
		//and the cipher and frequencies object will be created
	    if(e.getSource() == monoButton) {

	    	if (getKeyword() == true && processFileName() == true) {
	    		
    			mcipher = new MonoCipher(keyFieldString);
    			
    			//The LetterFrequencies object will be created here
    			freqs = new LetterFrequencies();
	    		   			
    			//Processing the file if it is mono cipher
    			// Encodes or decodes depending on the file
    			// and writes an output file with the result
	    		processFile(false);
  
	    	}
	    }
	    
	    
	    else if (e.getSource() == vigenereButton) {

	    	if (getKeyword() == true && processFileName() == true) {
	    		
	    		vcipher = new VCipher(keyFieldString);
	    				
	    		//The LetterFrequencies object will be created here
    			freqs = new LetterFrequencies();
    			
    			//Processing the file if it is Vigenere cipher
    			// Encodes or decodes depending on the file
    			// and writes an output file with the result
	    		processFile(true);

	    	}
	    }
	    
	}
	
	
	
	/**
	 * Obtains cipher keyword
	 * If the keyword is invalid, a message is produced
	 * @return whether a valid keyword was entered
	 */
	private boolean getKeyword()
	{
		keyFieldString = keyField.getText();
		int length = keyFieldString.length();
		
			
		//If keyword field is empty user will be notified
		if (keyFieldString.equals("")) {
			JOptionPane.showMessageDialog(null,"You have to enter a keyword","Error",JOptionPane.ERROR_MESSAGE);
			keyField.setText("");
			return false;
		}
		
		else {		//i.e. if the keyword field contains something
			if (!keyFieldString.matches("[a-zA-Z]+")) {	//if what is contains has something else than letters
				JOptionPane.showMessageDialog(null,"Keyword must contain only alphabetic characters","Error",JOptionPane.ERROR_MESSAGE);
				keyField.setText("");
				return false;	
			}
			else {		//if non empty and doesn't contain non letters check capital letters validity and duplicates
				for (int index = 0; index < length; index++) {
					char myCharacter = keyFieldString.charAt(index);
				
					if (myCharacter >= 90) {     //i.e. if it is lower case
						JOptionPane.showMessageDialog(null,"You have encounter the lowercase letter: " + myCharacter,"Error",JOptionPane.ERROR_MESSAGE);
						keyField.setText("");
						return false;
						}		
					
					else {
							for (int i = 0; i < length; i++) {
								int count = 0;
								for (int j = 0; j < length; j++) {
									if (keyFieldString.charAt(i) == keyFieldString.charAt(j)) {
										count++;
										if (count++ > 1) {
											JOptionPane.showMessageDialog(null,"Keyword must not contain any duplicates","Error",JOptionPane.ERROR_MESSAGE);
											keyField.setText("");
											return false;
										}
									}
								}
							}						
					}					
				}
			}
		}
		return true;
	}
		
		

	
	/**
	 * Obtains filename from GUI
	 * The details of the filename and the type of coding are extracted
	 * If the filename is invalid, a message is produced
	 * The details obtained from the filename must be remembered
	 * @return whether a valid filename was entered
	 */
	private boolean processFileName()
	{
		messageFileString = messageField.getText();
		
		//If the file name is empty user will be notified
		if (messageFileString.equals("") ) {
			JOptionPane.showMessageDialog(null,"You have to enter a message filename","Error",JOptionPane.ERROR_MESSAGE);
			messageField.setText("");
			return false;
		}
		
		else {
			String p = "P";
			String c = "C";
			int messFileStrLength = messageFileString.length();
			
			//extract the message file last letter
			char lastLetter = messageFileString.charAt(messFileStrLength - 1);		
			
			//The strings p and c as defined above, as characters
			char P = p.charAt(0);
			char C = c.charAt(0);
			
			if (lastLetter != P && lastLetter != C){
				JOptionPane.showMessageDialog(null,"You have to enter a file of correct type","Error",JOptionPane.ERROR_MESSAGE);
				messageField.setText("");
				return false;
			}
			else {
				try {
					FileReader reader = new FileReader(messageFileString + ".txt");
				}
				catch (IOException e) {
					JOptionPane.showMessageDialog(null,"File not found.","Error",JOptionPane.ERROR_MESSAGE);
					messageField.setText("");
					return false;	
				}
			}
			
		}
		return true;
	}
		
		

		
	
	/**
	 * Reads the input text file character by character
	 * Each character is encoded or decoded as appropriate
	 * and written to the output text file
	 * @param vigenere whether the encoding is Vigenere (true) or Mono (false)
	 * @return whether the I/O operations were successful
	 */
	private boolean processFile(boolean vigenere)
	{
		//Determine the last letter of the file to see if it is P or C
		char a_char = messageFileString.charAt(messageFileString.length()-1);
		
		//Extract just the file name without the type letter.
		String subMessageName = messageFileString.substring(0,  messageFileString.length()-1);
		
		FileReader reader = null;
		FileWriter writer = null;				
		FileWriter writer2 = null;
		FileWriter writer3 = null;
		FileWriter writer4 = null;
		
		
		//Conditionals for each file case, P or C ended
		if (a_char == 'P') {
			
			//Take the substring of the message file name so a character can be added in
			// the end to write an output file, depending on the type of the input file
			String outputFileName = subMessageName + "C.txt";
			String reportFileName =  subMessageName + "F.txt";
			
			try {
				try {
					reader = new FileReader(messageFileString + ".txt");
					// flag indicating whether finished reading
					boolean done = false;
					
					stringToWrite ="";
					
					char encrypted = ' ';
					while (!done) {
	                    // read a character, represented by an int
					    int next = reader.read();
					    // -1 represents end of file
						if (next == -1)
						        done = true;
						else {
						        // convert to character
						        char c = (char) next;
						        
						        //Check if the vigenere or mono method will
						        //be used, so the correct method can be applied
						        if (vigenere == false) {
						        	//Apply the mono encryption method
						        	encrypted = mcipher.encode(c);
						        }  
						        else if (vigenere == true) {
						        	//Apply the Vigenere encryption method
						        	encrypted = vcipher.encode(c);        	
						        	
						        }

						        //Print the encoded text and calculates frequencies
						        System.out.print(encrypted );
					
						        freqs.addChar(encrypted);
						        
						        //Change the encrypted message to string, ready to be written later
						        stringToWrite =  stringToWrite + Character.toString(encrypted);
						        writer = new FileWriter(outputFileName);
						        writer3 = new FileWriter(reportFileName);
        		        		
								// write the characters to the file
						        //and the frequency report to another file
								writer.write(stringToWrite);
								writer3.write(freqs.getReport());											
						}						
					}
					
					System.out.println("\n" + "--------------");
					System.out.print(freqs.getReport());
					
					writer.close();
					writer3.close();
				}
				finally {
					// close the input file assuming it was opened successfully
					if (reader != null) reader.close();
				}
			}
			catch (IOException ex) {
				System.out.println("Error opening file");
			}
		}
			

		//If the file ends with C, needs decoding and the outcome will be the P file
		else {					
			//Take the substring of the message file name so a character can be added in
			// the end to write an output file, depending on the type of the input file
			String outputFileName = subMessageName + "D.txt";
			String reportFileName =  subMessageName + "F.txt";
			
				
			try {
				try {
					reader = new FileReader(messageFileString + ".txt");
					
					// flag indicating whether finished reading
					boolean done = false;
					stringToWrite ="";
					char decoded = ' ';
					while (!done) {
	                    // read a character, represented by an int
					    int next = reader.read();
					    // -1 represents end of file
						if (next == -1)
						        done = true;
						else {
						        // convert to character
						        char c = (char) next;
						        
						        					        
						        if (vigenere == false) {
						        	//Apply the decoding method method
						        	decoded = mcipher.decode(c);
						        	
						        }
						        else if (vigenere == true) {
						        	decoded = vcipher.decode(c);
						        }
						        
						        
						        //Print the decoded message
						        System.out.print(decoded);
						        
						        freqs.addChar(decoded);
						        

						        //Change the decoded message to string, ready to be written later
						        stringToWrite =  stringToWrite + Character.toString(decoded);
						        
						        writer2 = new FileWriter(outputFileName);
						        writer4 = new FileWriter(reportFileName);
        		        		
								// write the characters to the file
								writer2.write(stringToWrite);
								writer4.write(freqs.getReport());
						}
					}
					
					System.out.println("\n" + "--------------");
					System.out.print(freqs.getReport());
					
					writer2.close();
					writer4.close();
				}	
				finally {
					// close the input file assuming it was opened successfully
					if (reader != null) reader.close();
				}
			}
			catch (IOException ex) {
				System.out.println("Error opening file");
			}		
		}
	    return true; 
	}
}
