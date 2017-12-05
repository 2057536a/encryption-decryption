/**
 * Programming AE2
 * Processes report on letter frequencies
 */
public class LetterFrequencies
{
	/** Size of the alphabet */
	private final int SIZE = 26;
	
	/** Count for each letter */
	private int [] alphaCounts;
	
	/** The alphabet */
	private char [] alphabet; 
												 	
	/** Average frequency counts */
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
							       0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
								   6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};
	
	/**The percentage of the appearance and the percentage difference*/
	private double[] perc;
	private double [] percDiff;
	

	/** Character that occurs most frequently */
	private char maxCh;

	/** Total number of characters */
	private int totChars;
	
	
	
	
	/**
	 * Instantiates a new letterFrequencies object.
	 */
	public LetterFrequencies()
	{
		
		//instance variables for the LetterFrequencies object
		alphaCounts = new int [SIZE];		//array with number of letters to take count for each one of them	
		alphabet = new char [SIZE];			// array with the number of letters in the alphabet
		perc = new double[SIZE];			// array with the frequency percentage of each character
		percDiff = new double[SIZE];		// array with the percentage difference
		
		
		//set the array's elements with initial values
		for (int i=0 ; i < SIZE; i++) {
			alphaCounts[i] = 0;
			alphabet[i] = (char)('A' +i);
			perc[i] = 0.0;
			percDiff[i] = 0.0;
		}
		
	}
	
	

		
	/**
	 * Increases frequency details for given character
	 * @param ch the character just read
	 */
	public void addChar(char ch)
	{
		//if character is found then increase 
		// its count and the total number of
		// characters. Then run another loop for getting the
		//percentages.
		
		for (int i = 0; i< SIZE; i++) {
			if (ch == alphabet[i]) {
				alphaCounts[i]++ ;
	    		totChars++;
			}
		}
		
		
		//calculates the percentage and the difference
		// since the total number of chars
		// has already been calculated
	    for(int i = 0; i <SIZE; i++) { 		
	    		double count = alphaCounts[i];				
				perc[i] = (count / totChars) * 100;	
				percDiff[i] = perc[i] - avgCounts[i];		
	    }	    
	    
	}
	
	/**
	 * Gets the maximum frequency
	 * @return the maximum frequency
	 */
	private double getMaxPC()
        {
		
		int start = 0;				
		double maximum = 0.0;
		
		
		// if the letter actually appears, e.g it's count is non zero
		// set the start at this count number, and as the first maximum 
		// percentage. At this point is is the most frequent letter. Do
		// the same for the whole array.
		for (int j = 0; j < SIZE; j++)
		{
			
			if(alphaCounts[j] > start)
			{
				start = alphaCounts[j];
				maximum = perc[j];
				maxCh = alphabet[j];
			}
			
			if(alphaCounts[j] == start) {
				maxCh = alphabet[j];
			}
		}
		
	    return maximum;  
	}
	
	/**
	 * Returns a String consisting of the full frequency report
	 * @return the report
	 */
	public String getReport()
	{
		
		double maximum = getMaxPC();
		String reportStr = "";
		
		String myStr = ("LETTER ANALYSIS\n\n"+"Letter"+"      "+"Freq"+"        "+
				"Freq%"+"       "+"AvgFreq%"+"      "+"Diff\n");
		
		
		for(int i = 0; i < SIZE; i++)
		{
			reportStr +=  String.format("%3s %11s %12s %12s %12s %n", 
					alphabet[i], alphaCounts[i], String.format("%.1f",perc[i]),
					avgCounts[i], String.format("%.1f",percDiff[i]));
		}
		
		String maxAppear = "The most frequent letter is "+maxCh+" at "+ String.format("%.1f",maximum)+"%";		
		myStr = myStr+reportStr+"\n\n"+maxAppear;	
		
	    return myStr; 
	}
}