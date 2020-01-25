
public class Dice {


	//Potential Side labels (Depending on die number)
	final private char[] die1 = {'R','I','F','O','B','X'};
	final private char[] die2 = {'I','F','E','H','E','Y'};
	final private char[] die3 = {'D','E','N','O','W','S'};
	final private char[] die4 = {'U','T','O','K','N','D'};
	final private char[] die5 = {'H','M','S','R','A','O'};
	final private char[] die6 = {'L','U','P','E','T','S'};
	final private char[] die7 = {'A','C','I','T','O','A'};
	final private char[] die8 = {'Y','L','G','K','U','E'};
	final private char[] die9 = {'Q','B','M','J','O','A'};
	final private char[] die10 = {'E','H','I','S','P','N'};
	final private char[] die11 = {'V','E','T','I','G','N'};
	final private char[] die12 = {'B','A','L','I','Y','T'};
	final private char[] die13 = {'E','Z','A','V','N','D'};
	final private char[] die14 = {'R','A','L','E','S','C'};
	final private char[] die15 = {'U','W','I','L','R','G'};
	final private char[] die16 = {'P','A','C','E','M','D'};
	char[][] dieArray;

	
	Dice()
	{
		dieArray = new char[][] {die1, die2, die3, die4,
			  die5, die6, die7, die8,
			  die9, die10, die11, die12,
			  die13, die14, die15, die16};
	}
	
	/** dice set. 
	 * Parameters: None
	 * Precondition: none 
	 * PostCondition/Returns: returns "temp". Essentially sends the correct array of dies and their randomly
	 * 						generated letters.
	 * Throws: none
	 */
	public char[] getDieSet()
	{
				
		char[] temp = new char[16];
		
		for (int x=0; x<temp.length; x++) {
			int ran = (int) ((Math.random() * 5) + 1);
			temp[x] = dieArray[x][ran];
		}
		
		return temp;
	}
	


}