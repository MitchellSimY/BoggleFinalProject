import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;

//maybe get rid of letter array and just use buttonArray
@SuppressWarnings("serial")
public class BoggleGUI extends JFrame implements ActionListener {

	private JButton startBtn = new JButton("");

	
	private JButton confirmWordBtn = new JButton("");
	private JButton endGameBtn = new JButton("");

	private JPanel leftPanel = new JPanel(new GridLayout(4,4));
	private JPanel rightPanel = new JPanel(new GridLayout(3,2));
	private JPanel bottomPanel = new JPanel(new FlowLayout());
	
	private JTextArea displayArea = new JTextArea(7, 10);
	private JTextArea displayAreaConfirmedWords = new JTextArea(7, 10);
	
	// J label to display the given points. 
	private JLabel pointLabel = new JLabel("");
	private int characterCount = 0;
	
	private JLabel lblBackgroundImage = new JLabel("");
  
	// Timer
    private timer timer = new timer();
    
    // Button arrays.
	JButton[][] buttonArray = new JButton[4][4];
	boolean[][] isSelected = new boolean[4][4];
	boolean[][] nextSelection = new boolean[4][4];

	boolean firstLetter = true;
	boolean gameStarted = false;
	
	String possibleWords = "";
	
	//Global variables used for links
	linkedList list = new linkedList();
	boolean firstWord = true;
	
	Trie tree = new Trie();

	
	private BoggleGUI () {
		// Window Requirements.
		super("Boggle");
		
		JLabel background;
		
		setSize(700, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
				
		
		// Boggle Board is divided into three panels. 
		// Left for the dies
		// right for the UI
		// Bottom for the timer. 
		buildLeftPanel();
		buildRightPanel();
		addPanelsToFrame();
		addListener();
		tree.importDictionary();

		
		setVisible(true);
	}
	
	// new game method
	private void buildLeftPanel() {

		
		Dice die = new Dice();
		char[] diceSet = new char[16];
		diceSet = die.getDieSet();
		JButton button;
		int count = 0;

		clearBooleanBoard(nextSelection);


		
		 //2 for loops that get the x & y coordinate
		for(int x=0; x<4; x++) {
        	 for (int y=0; y<4; y++) {
        		  int yPosition = y;
      			  int xPosition = x;

      			button = new JButton("" + diceSet[count]);
      		    button.setFont(new Font("Dialog", Font.BOLD, 40));

      			try {
      		        Image img = ImageIO.read(getClass().getResource("Resources/blankDice.png"));
      		      Image imgResize = img.getScaledInstance(90, 90, Image.SCALE_DEFAULT);
      		      button.setBorder(BorderFactory.createEmptyBorder());
      		      button.setContentAreaFilled(false);
      		      button.setHorizontalTextPosition(JButton.CENTER);
      		      button.setVerticalTextPosition(JButton.CENTER);
        	      button.setIcon(new ImageIcon(imgResize));         
      		      } catch (IOException ex) {
      		    	  System.out.println("Dice pic not found");
      		      }

    			buttonArray[xPosition][yPosition] = button;
    			count++;
    			
     			leftPanel.add(buttonArray[xPosition][yPosition]);
     			buttonArray[xPosition][yPosition].addActionListener(new ActionListener() {   
        			
     				/** Action performed
     				 * Parameters: "e" which is the user click. It'll depend on whatever button is being clicked. 
     				 * Precondition: Precondition is making sure that all buttons are click-able, which is defined
     				 * 				in addListner() method
     				 * PostCondition/Returns: returns nothing. Method is to make sure all buttons are correctly placed.
     				 * Throws: none
     				 */
     				
     				@SuppressWarnings("deprecation")
					public void actionPerformed(ActionEvent e) {
        				 //if canClick() returns true then the button is click able
        				 if (timer.getInGame() == true && canClick(xPosition, yPosition) == (true)){
        					 //temporary print statement
        					 System.out.println(xPosition + "," + yPosition + "     " + buttonArray[xPosition][yPosition].getLabel());
        				     buttonArray[xPosition][yPosition].setBackground(Color.ORANGE); 
        				     
        				     try {
        		      		        Image img = ImageIO.read(getClass().getResource("Resources/selectedDice.png"));
        		      		      Image imgResize = img.getScaledInstance(90, 90, Image.SCALE_DEFAULT);
        		      		    buttonArray[xPosition][yPosition].setBorder(BorderFactory.createEmptyBorder());
        		      		  buttonArray[xPosition][yPosition].setContentAreaFilled(false);
        		      		buttonArray[xPosition][yPosition].setHorizontalTextPosition(JButton.CENTER);
        		      		buttonArray[xPosition][yPosition].setVerticalTextPosition(JButton.CENTER);
        		      		buttonArray[xPosition][yPosition].setIcon(new ImageIcon(imgResize));         
        		      		      } catch (IOException ex) {
        		      		    	  System.out.println("Dice pic not found");
        		      		      }
        				  
        				     // Displaying the word onto the first display area.
        				     String letterSelection = buttonArray[xPosition][yPosition].getLabel();
        				     displayArea.append(letterSelection);
        				     
        				     
        				  // =====================
        				 }
        			 }
        			 });
        	 }//end of y for loop
        }//end of x for loop
		leftPanel.setOpaque(false);
	
	}
	
	/** RIGHT USER INTEFACE PANEL
	 * Parameters: None
	 * Precondition: none 
	 * PostCondition/Returns: returns nothing. Method is to make sure all buttons are correctly placed.
	 * Throws: none
	 */
	private void buildRightPanel() {
	    Image startImage, confirmImage, endGameImage;
		try {
			// Images
			startImage = ImageIO.read(getClass().getResource("Resources/startImage.png"));
		    Image startImageResized = startImage.getScaledInstance(110, 90, Image.SCALE_DEFAULT);
		    
		    confirmImage = ImageIO.read(getClass().getResource("Resources/confirmWordImage.png"));
		    Image confirmWordResized = confirmImage.getScaledInstance(110, 90, Image.SCALE_DEFAULT);
		    
		    endGameImage = ImageIO.read(getClass().getResource("Resources/endGameImage.png"));
		    Image endGameResized = endGameImage.getScaledInstance(110, 90, Image.SCALE_DEFAULT);
		    
		    startBtn.setIcon(new ImageIcon(startImageResized));   
		    startBtn.setBorder(BorderFactory.createEmptyBorder());
		    startBtn.setContentAreaFilled(false);
		    startBtn.setHorizontalTextPosition(JButton.CENTER);
		    startBtn.setVerticalTextPosition(JButton.CENTER);
		    // Hiding the text from the start button.
			startBtn.setActionCommand("Start");

		    
		    confirmWordBtn.setIcon(new ImageIcon(confirmWordResized));   
		    confirmWordBtn.setBorder(BorderFactory.createEmptyBorder());
		    confirmWordBtn.setContentAreaFilled(false);
		    confirmWordBtn.setHorizontalTextPosition(JButton.CENTER);
		    confirmWordBtn.setVerticalTextPosition(JButton.CENTER);
		    confirmWordBtn.setActionCommand("Confirm Word");
		    
		    endGameBtn.setIcon(new ImageIcon(endGameResized));   
		    endGameBtn.setBorder(BorderFactory.createEmptyBorder());
		    endGameBtn.setContentAreaFilled(false);
		    endGameBtn.setHorizontalTextPosition(JButton.CENTER);
		    endGameBtn.setVerticalTextPosition(JButton.CENTER);
		    endGameBtn.setActionCommand("End Game");
 
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




		rightPanel.add(startBtn);
		rightPanel.add(confirmWordBtn);
		rightPanel.add(endGameBtn);
		rightPanel.add(displayAreaConfirmedWords);
		rightPanel.add(displayArea);
		pointLabel.setFont(new Font("Dialog", Font.BOLD, 26));

		
		rightPanel.add(pointLabel);
		displayArea.setEditable(false);

		JScrollPane scroll = new JScrollPane (displayArea);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JScrollPane scroll2 = new JScrollPane (displayAreaConfirmedWords);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		rightPanel.add(scroll);
		rightPanel.add(scroll2);
		rightPanel.setOpaque(false);
	}
	
	private void buildBottomPanel() {
	timer.setPreferredSize(new Dimension(100, 50));

	bottomPanel.add(timer);

	}

	/** // Making buttons click able.
	 * Parameters: None
	 * Precondition: Making sure the buttons are click-able.
	 * PostCondition/Returns: None - Main functionality is to provide functions to buttons. 
	 * Throws: None 
	 */
	private void addListener() {
		startBtn.addActionListener(this);
		confirmWordBtn.addActionListener(this);
		endGameBtn.addActionListener(this);
	}
	// Action Events for UI
	@Override
	public void actionPerformed(ActionEvent e) {
	String clicker = e.getActionCommand();

      if (clicker == "Start"){
			timer.startTimer();
			 for(int x=0; x<isSelected.length; x++){
	    		  for (int y=0; y<isSelected.length; y++){
	    			  isSelected[x][y] = false;
	    		  }
	    	  }
			 gameStarted = true;
		}
      else if (clicker == "End Game") {
    	  
    	  if(gameStarted != true) {
    		  //Code that makes sure appended line outputs nicely. 
    		  displayArea.setLineWrap(true);
    		  displayArea.setWrapStyleWord(true);
    		  displayArea.append("You must start the game before you end it!");
    	  } else {
    		  displayArea.append("All possible words on current board:\n" );
        	  
        	  //Bit of code that will return all possible words on board
        	  for(int x=0; x<4; x++) {
              	 for (int y=0; y<4; y++) {
            		findWords(isSelected, x, y, possibleWords);
              	 }
       		}
    	  }

    	 timer.resetTimer();

    	  
      }
      else if (clicker == "Confirm Word"){
    	  

      	
      	// Lower casing the user word. 
      	String userWord = displayArea.getText().toLowerCase();
      	/*  If the word is true.
      	 *  the word is sent into the isWord method in Trie
      	 *  for validation. 
      	 */
      	System.out.println("User word length: " + userWord.length());
      	if (tree.isWord(userWord) == true && userWord.length() >= 3) {
      		
      		
      		//Puts word to linkedlist
			list.add(userWord.toLowerCase());
			
      		//If statement double checks word is not being used twice
			//otherwise else statement awards points to player
      		if(list.doublesTester() == true) {
      		  displayAreaConfirmedWords.setLineWrap(true);
      		  displayAreaConfirmedWords.setWrapStyleWord(true);
      		  displayAreaConfirmedWords.append("You Already Used That Word\n");
      		  list.remove(userWord.toLowerCase());
      		  displayArea.setText(null);
      		} else {
          		// Appending the second display area to show the confirmed word.
              	displayAreaConfirmedWords.append(userWord + "\n");
              	// incrementing the CharacterCount/points.
              	characterCount += bogglePoints(userWord);
              	// Clearing the display area.
                displayArea.setText(null);
      		}
  
      		
      		
      	
            
      	} else {
      		System.out.println("False word");
      		displayAreaConfirmedWords.append("Invalid Word\n");
      		System.out.println(userWord);
      		displayArea.setText(null);
      	}

      	
      	// Displaying the number of points. 
      	pointLabel.setText("    " + characterCount);
      	
      	
      	
      	// ==================================================================
      	// ==================================================================
    	  
    	  
    	  
    	  
    	  
    	  
    	  //Will reset button backgrounds so no longer orange. 
    	  for(int x = 0; x < 4; x++) {
    		  for(int y = 0; y < 4; y++) {
    	    	  buttonArray[x][y].setBackground(null);
    	    	  try {
	      		        Image img = ImageIO.read(getClass().getResource("Resources/blankDice.png"));
	      		      Image imgResize = img.getScaledInstance(90, 90, Image.SCALE_DEFAULT);
	      		    buttonArray[x][y].setBorder(BorderFactory.createEmptyBorder());
	      		  buttonArray[x][y].setContentAreaFilled(false);
	      		buttonArray[x][y].setHorizontalTextPosition(JButton.CENTER);
	      		buttonArray[x][y].setVerticalTextPosition(JButton.CENTER);
	      		buttonArray[x][y].setIcon(new ImageIcon(imgResize));         
	      		      } catch (IOException ex) {
	      		    	  System.out.println("Dice pic not found");
	      		      }

    	    	  
    	    	  nextSelection[x][y] = true;
    		  }
    	  }
    	  
    	  clearBooleanBoard(isSelected);
    	 // clearBooleanBoard(nextSelection);

  	  
      }//end of else if()
	}
	// Adding the panels to the frame after they've been built. 
    private void addPanelsToFrame() {

        lblBackgroundImage.setLayout(new BorderLayout());
        lblBackgroundImage.setBounds(0, 0, 800, 600);

        lblBackgroundImage.setSize(1000, 1000); //this will resize window

        lblBackgroundImage.setIcon(new ImageIcon("src/Resources/Board2.png"));
		JButton buttonex = new JButton();
		buttonex.setOpaque(false);
		buttonex.setContentAreaFilled(false);
		buttonex.setBorderPainted(false);
		buttonex.setPreferredSize(new Dimension(20,100));

		//pane.add(button, BorderLayout.CENTER);


        lblBackgroundImage.add(leftPanel, BorderLayout.WEST);
       // lblBackgroundImage.add(buttonex);
        lblBackgroundImage.add(rightPanel, BorderLayout.EAST);
        lblBackgroundImage.add(timer, BorderLayout.SOUTH);
        getContentPane().add(lblBackgroundImage);
        
    }
	
    
	//if returns true the user can click the button
	//if returns false the user cannot click the button
	private boolean canClick(int x, int y){

		//all possible combinations
		 final int[][] cell = {
			        {x + 1,  y    },  //bottom
			        {x    ,  y + 1},  //right
			        {x - 1,  y    },  //top
			        {x    ,  y - 1},  //left
			        {x + 1,  y + 1},  //bottom-right
			        {x + 1,  y - 1},  //bottom-left
			        {x - 1,  y - 1},  //top-left
			        {x - 1,  y + 1}   //top-right
			    };
		
		 //determines if it is the first click on the board
		 if (firstLetter == true){
			 isSelected[x][y] = true;
			 firstLetter = false;
			 
			 //for loop that runs through every combination in cell[][] 
			 //what this does is sets every surrounding cell = true
	    	  for(int i=0; i<cell.length; i++){
	    			  if (cell[i][0] >=0 && cell[i][1] >=0 && cell[i][0] <4 && cell[i][1] <4){
	    			  nextSelection[cell[i][0]] [cell[i][1]] = true;
	    			  }
	    	  }
	     return true;
		 }
		 
		 //isSelected[x][y] prevents the user from clicking the same button twice
		 //nextSelection[x][y] only lets the user click the surrounding buttons
		 else if (isSelected[x][y] == false && nextSelection[x][y] == true){
			 clearBooleanBoard(nextSelection);
			 //sets nextSelection[][] (surrounding cells) to true
	    	  for(int h=0; h<cell.length; h++){
	    			  if (cell[h][0] >=0 && cell[h][1] >=0 && cell[h][0] <4 && cell[h][1] <4){
	    				  nextSelection[cell[h][0]] [cell[h][1]] = true;
	    			  }
	    	  }
			 isSelected[x][y] = true;
			 return true;
		 }
		return false;
	}

	 public void findWords(boolean isSelected[][], int x, int y, String word) { 
		 
		 	if(x>3 || x<0 || y>3 || y<0) {
		 		return;//an error that causes the recursion to break
		 	}
		 	if (isSelected[x][y] == true) {
		 		return;
		 	}
		 
		 		// Mark current cell as visited and append current character 
				// to str 
				isSelected[x][y] = true; 
				word = word + buttonArray[x][y].getLabel().charAt(0);
				
				
				// If str is present in dictionary, then print it 
				if (word.length() >= 3 && tree.isWord(word.toLowerCase())) {
					  //Code that makes sure appended line outputs nicely. 
		    		  displayArea.setLineWrap(true);
		    		  displayArea.setWrapStyleWord(true);
		    		  displayArea.append(word + "\n");
		    		  //System.out.println(word);
		    		  //The above line can be de-commented out 
		    		  //If wish to test words in text area match system out
				}
				 
				
				
				//row <=x+1 ensures it won't go more than 1 to the right
				//row<4 ensures it won't go past the 4th cell (edge of board)
				for (int column=x-1; column<=x+1 && column<4; column++) {
					for (int row=y-1; row<=y+1 && row<4; row++) {
						findWords(isSelected, column, row, word); 
					}
				}
		        	 

				// Erase current character from string and mark visited 
				// of current cell as false 
				word="";
				isSelected[x][y] = false; 
			} 
	 
	 //clears the boolean board setting everything to false
	 public boolean[][] clearBooleanBoard(boolean[][] booleanBoard){
			//sets all of nextSelection[][] to false (condense)
		 for(int x=0; x<booleanBoard.length; x++){
			  for (int y=0; y<booleanBoard.length; y++){
				  booleanBoard[x][y] = false;
			  }
		  }
		return booleanBoard;
	 }
	
	// Boggle points method.
	private int bogglePoints(String tempWord) {
			// Words must be 3+ characters.
			// for each character, points +1.
			// five letter character = 5 points.
			int characterCount = 0;
			
			// For each letter in the string, count+1;
			
			for (int i = 0; i < tempWord.length(); i++) {
				characterCount++;	
			}

			return(characterCount);
		}
		

	// ============= Main ================
	public static void main(String[] args) {
		BoggleGUI gui = new BoggleGUI();
		
	}	
}

