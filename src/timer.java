import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class timer extends JPanel {

  JLabel label;
  Timer timer;
  int count = 180;
  boolean inGame;

  public timer() 
  {
	  JButton button = new JButton();
	  JButton button2 = new JButton();
	  JButton button3 = new JButton();
	  button.setOpaque(false);
	  button.setContentAreaFilled(false);
	  button.setBorderPainted(false);
	  button2.setOpaque(false);
	  button2.setContentAreaFilled(false);
	  button2.setBorderPainted(false);
	  button3.setOpaque(false);
	  button3.setContentAreaFilled(false);
	  button3.setBorderPainted(false);
	 //button.setPreferredSize(new Dimension(400,50));

	setLayout(new GridLayout(1,4));
	setPreferredSize(new Dimension(50, 100));
	setOpaque(false);
	add(button);
	add(button2);
	add(button3);



	//set to false until the counter starts
	inGame = false;

	
	//What shows when the timer is not running
    label = new JLabel("3:00");
	label.setForeground(Color.black);
	label.setFont(new Font("Dialog", Font.PLAIN, 40));

    //Adds label to panel
    add(label);
    
    //creates new timer that performs an action ever 1000Ms (1 second)
    timer = new Timer(1000, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) 
      {      
        //if counter is still >=0 it will reset the label 
        if (count >= 0) {
        	
        	timerDisplay();

        } 
        //else stop the timer & actionEvent
        else {
          ((Timer) (e.getSource())).stop();
          inGame = false;
         label.setFont(new Font("Dialog", Font.PLAIN, 25));

          label.setText("GAME OVER");

        }
        count--;
      }
    });
    
  }
  
  
  //Method to start the timer
  public void startTimer() 
  {
	    timer.start();
	    inGame = true;
  }
  
  
  //Method to Reset Timer
  public void resetTimer() 
  {
	  label.setForeground(Color.black);
	  inGame = false;
	  timer.stop();
	  count = 180;
      timerDisplay();
  }
  
  public void timerDisplay() {
  	int sec = count % 60;
    int min = (count / 60)%60;
    
    if (min>0 && sec>=10)
    {
    label.setText(Integer.toString(min) + ":" + Integer.toString(sec));
    }
    
    else if (min>0 && sec<10) 
    {
        label.setText(Integer.toString(min) + ":0" + Integer.toString(sec));
    }
    
    else if (min<=0 && sec>10) 
    {
        label.setText(Integer.toString(sec));
    }
    
    else if (min<=0 && sec<=10) 
    {
    	label.setForeground(Color.red);
        label.setText(Integer.toString(sec));
    }
    
    

  }
  //returns whether the timer is running or not (AKA user is in game)
  public boolean getInGame() {
	  return inGame;
  }

  

}