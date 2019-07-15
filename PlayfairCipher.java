//PlayfairCipher.java

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.text.*;
import java.util.*;
import java.lang.String.*;
import java.util.List;
	

//Note:
/** Special cases:
 * J: if user input is abjcde, j would be left as is, c would compare to d, and e would compare to an x.
      if user input is abcjde, j would be left as is, c would compare to d, and e would compare to an x.
      abcjde would be considered to have an odd number of characters: therefore an x would be added at the end and e would compare with x.
      abcjd would be considered to have an even number of characters: therefore no x is added at the end.
      if there are 2 j's next to each other, an x is added between them and used to encrypt. The j's are then added to their original positions after encryption.
 * Odd #: an x is added at the end.
      If the last letter of the input is already an x (example: abcdx) and it's ALSO an odd # of char, a 'z' is added to the end instead.
 * Wrap Around: Since the grid is always 5x5, if one of the letters to be switched has a row+1 or col+1 = 5, that row or col automatically becomes 0
 * 2 of the Same Letter: An x is inserted between them BEFORE it goes into the switch.
      If there are 2 x's next to each other, a 'z' is inserted between them instead.
      Example: abxxde -> abxzxde  |   xx -> xzxz (z btwn x's & z at the end for odd #)
 * All spaces, numbers, special characters & upper case letters are removed before the switch occurs. Outputs do not contain spaces or special char
 */
 
//Bonus (Grid Maker):
/** The bonus is done exactly as described in the assignment. 
 * User can input a message (as long as they'd like) and each letter will be
	added to the grid only the first time it comes up in the input.
 * Letters of the alphabet are added right after in sequential order, but not 
	including any letters that are already in the grid.
 * The program will eliminate all spaces, numbers, special characters & upper case letters before the switch occurs. Outputs do not contain spaces or special char
 * j will not always be the missing grid char: the first 25 letters used in the input and alphabet combined 
	will determine which letter is left out.
 * All special cases are treated the same as outlined above, only exception is whatever normally happens to a j, 
  	the same same happens to the new missing char
*/


public class PlayfairCipher extends JFrame {
	
	private final int WINDOW_WIDTH = 385, WINDOW_HEIGHT = 200;
	private JLabel text, text1, text2, text3, title1, title2, welcometext, repeattext, inputtext;
	private JTextField input, input1;
	private JPanel panel, panel1;
	private JLabel blank, blank1, blank2, blank3, blank4, blank5;
	private Container contentPane;
	public int yesno=1;
	
	//Constructor
	public PlayfairCipher() {
	
		setTitle("Playfair Cipher");
		setSize(WINDOW_WIDTH-90, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildPanel();
		contentPane = getContentPane();
		contentPane.add(panel);
		
		//Create the stuff you'll see
		inputtext = new JLabel("Enter your message: ");
		input = new JTextField(5);
		repeattext = new JLabel("Would you like to start over with a new message?");
		
		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");
		setVisible(true);
	}
		
	private void buildPanel() {
		JButton enCrypt = new JButton("Encrypt a Message");
		JButton deCrypt = new JButton("Decrypt a Message");
		JButton selfGrid = new JButton("Make My Own Grid");
		
		enCrypt.setForeground(Color.RED);
		deCrypt.setForeground(Color.RED);
		selfGrid.setForeground(Color.RED);
		
		panel = new JPanel(); //(new GridLayout(5,3));
		panel.setBackground(Color.BLUE);
		Font font1 = new Font("SanSerif",Font.BOLD, 16);
		
		welcometext = new JLabel("Hello, welcome to Playfair's Cipher!");
		text1 = new JLabel("What would you like to do?");
		welcometext.setFont(font1);
		welcometext.setForeground(Color.GREEN);
		text1.setForeground(Color.GREEN);
	
		enCrypt.addActionListener(new EncryptListener());
		deCrypt.addActionListener(new DecryptListener());
		selfGrid.addActionListener(new SelfGridListener());
		panel.add(welcometext);
		panel.add(text1);
		panel.add(enCrypt);
		panel.add(deCrypt);
		panel.add(selfGrid);
	}
	
	private class EncryptListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			setSize(350, WINDOW_HEIGHT-80);
			
			remove(panel);

			setTitle("Encrypt a Message");
			setLayout(new GridLayout(3, 2));
			panel.setBackground(Color.BLUE);
			Font font1 = new Font("SanSerif",Font.BOLD, 14);
					
			//Create the stuff you'll see
			text1 = new JLabel("   Enter your message:");
			input = new JTextField(5);
			blank1 = new JLabel("");
			blank2 = new JLabel("");
			blank3 = new JLabel("");

			JButton encryptButton = new JButton("Encrypt");
		
			text1.setFont(font1);
			text1.setForeground(Color.BLUE);
		
			//Add the stuff
			add(text1);
			add(input);
			add(blank1);
			add(encryptButton);
			add(blank2);
			add(blank3);
			
			encryptButton.addActionListener(new EncryptListener2());
			setVisible(true);
		}
	}
	
	private class DecryptListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			
			setSize(350, WINDOW_HEIGHT-80);
			
			remove(panel);

			setTitle("Decrypt a Message");
			setLayout(new GridLayout(3, 2));
			panel.setBackground(Color.BLUE);
			Font font1 = new Font("SanSerif",Font.BOLD, 14);
					
			//Create the stuff you'll see
			text1 = new JLabel("   Enter your message:");
			input = new JTextField(5);
			blank1 = new JLabel("");
			blank2 = new JLabel("");
			blank3 = new JLabel("");

			JButton decryptButton = new JButton("Decrypt");
		
			text1.setFont(font1);
			text1.setForeground(Color.BLUE);
		
			//Add the stuff
			add(text1);
			add(input);
			add(blank1);
			add(decryptButton);
			add(blank2);
			add(blank3);
			
			decryptButton.addActionListener(new DecryptListener2());
			setVisible(true);
		}
	}
	
	private class SelfGridListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			
			setSize(360, WINDOW_HEIGHT+10);
			
			remove(panel);
			
			JFrame frame = new JFrame();
			JPanel panel = new JPanel();

			panel.setLayout(new GridLayout(5,5));

			JLabel[][] labels = new JLabel[5][5];
			for (int i = 0; i < 5; i++)
			{
				for (int j = 0; j < 5; j++)
				{
					labels[j][i] = new JLabel("("+j+", "+i+")");
					panel.add(labels[j][i]);
				}
			}

			labels[0][0] = new JLabel("Hello World");

			frame.add(panel);

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			frame.pack();
			frame.setResizable(false);
		
			setTitle("Make My Own Grid");
			setLayout(new GridLayout(8, 2));
			
			panel.setBackground(Color.BLUE);
			Font font1 = new Font("SanSerif",Font.BOLD, 14);
			Font font2 = new Font("SanSerif",Font.BOLD, 16);
					
			//Create the stuff you'll see
			title1 = new JLabel("   Grid Info:");
			title2 = new JLabel("   En/Decrypt Info:");
			text1 = new JLabel("   Enter your message:");
			text2 = new JLabel("    Click '?' for important info:");
			text3 = new JLabel("   Enter your message:");
			input = new JTextField(5);
			input1 = new JTextField(5);
			blank = new JLabel("");
			blank1 = new JLabel("");
			blank2 = new JLabel("");
			blank3 = new JLabel("");
			blank4 = new JLabel("");
			blank5 = new JLabel("");

			//JButton gridButton = new JButton("Make my Grid!");
			JButton qButton = new JButton("?");
			JButton gridEncrypter = new JButton("Encrypt");
			JButton gridDecrypter = new JButton("Decrypt");
		
			text1.setFont(font1);
			text3.setFont(font1);
			title1.setFont(font2);
			title2.setFont(font2);
			text1.setForeground(Color.BLUE);
			text2.setForeground(Color.RED);
			text3.setForeground(Color.BLUE);
		
			//Add the stuff
			add(title1);
			add(blank);
			add(text1);
			add(input);
			add(blank4);
			add(blank5);
			add(title2);
			add(blank1);
			add(text3);
			add(input1);
			add(blank2);
			add(blank3);
			add(gridEncrypter);
			add(gridDecrypter);
			add(text2);
			add(qButton);
			
			qButton.addActionListener(new qListener());
			gridEncrypter.addActionListener(new GridEncrypter());
			gridDecrypter.addActionListener(new GridDecrypter());
			setVisible(true);
		}
	}
	
	private class EncryptListener2 implements ActionListener {
		public void actionPerformed (ActionEvent e) {
		
			char M[][] = {	{'z','c','b','x','m'},
							{'v','n','a','g','l'},
							{'s','f','h','k','q'},
							{'e','t','u','o','w'},
							{'r','y','i','p','d'}
						  };
						  
			List switched = new ArrayList ();
			List messagelist = new ArrayList ();
			
			int a=0, xadded=0;
			int row1=0, row2=0, col1=0, col2=0;
			String message;
			
			message = input.getText();
			message = message.replaceAll("\\s+","");
			message = message.replaceAll("[^a-z]","");
			
			int msglength = message.length()-1;
			int istherex =0;
			
			//Convert input into a list & check for same letters!
			for (int c=0; c<msglength; c++) {
				messagelist.add(message.charAt(c));
				/** Add an x if the double char aren't x's */
				if ((message.charAt(c)==message.charAt(c+1))&&(message.charAt(c)!='x')) {
					messagelist.add('x');
					istherex = 1;
				}	
				/** Add a z if the double char are x's */
				if ((message.charAt(c)==message.charAt(c+1))&&(message.charAt(c)=='x')) {
					messagelist.add('z');
					istherex = 1;
				}
			}
			messagelist.add(message.charAt(msglength));		
			
			//Get position of j's & put into a list (in case there's multiple j's)
			List jposition = new ArrayList () ;
			for (int c=0; c<messagelist.size(); c++) {
				if ((char)messagelist.get(c)=='j')
					jposition.add(c);
			}
			
			if (jposition.size()==0)
				jposition.add(-1);
			
			//Temporarily remove j from messagelist
			if ((int)jposition.get(0)!=-1) {
				for (int c=0; c<jposition.size(); c++) {
					messagelist.remove(messagelist.indexOf('j'));	
				}
			}
			
			//Odd #? Add an x or z:
			/** Add an x if it's odd */
			if (((messagelist.size()%2)==1)&&((char)messagelist.get(messagelist.size()-1)!='x')) {
				messagelist.add('x');
			}	
			/** Add a z if it's odd but already has an x at the end */
			if (((messagelist.size()%2)==1)&&((char)messagelist.get(messagelist.size()-1)=='x')) {
				messagelist.add('z');
			}	
				
			//switching | encrypting		
			for (int b=1; b<messagelist.size(); b+=2) {
				char letter1 = (char)messagelist.get(a);
				char letter2 = (char)messagelist.get(b);
				int coord1 = M[row1][col1]; 
				int coord2 = M[row2][col2];
		
				for (int col=0; col<5; col++) {
					for (int row=0; row<5; row++) {
						if ((int)letter1==M[row][col]) {
							row1 = row;
							col1 = col;
							/**System.out.println(letter1);
							System.out.println(row1+", "+col1);*/
						}
					}
				}
				
				for (int col=0; col<5; col++) {
					for (int row=0; row<5; row++) {
						if ((int)letter2==M[row][col]) {
							row2 = row;
							col2 = col;
							/**System.out.println(letter2);
							System.out.println(row2+", "+col2);*/
						}
					}
				}
					
				//Same row
				if (row1==row2) {
					
					/**wrap around*/
					if ((col1+1)>4) {
						coord1 = M[row1][0];
						if (col2<=4)
							coord2 = M[row2][col2+1];
					}
					if ((col2+1)>4) {
						coord2 = M[row2][0];
						if (col1<=4)
							coord1 = M[row1][col1+1];
					}
					
					/**normal*/
					if ((col1+1<=4) && (col2+1<=4)) {
						coord1 = M[row1][col1+1];
						coord2 = M[row2][col2+1];
					}
				}
				
				//Same column
				else if (col1==col2) {
					/**wrap around*/
					if ((row1+1)>4) {
						coord1 = M[0][col1];
						if (row2<=4)
							coord2 = M[row2+1][col2];
					}
					if ((row2+1)>4) {
						coord2 = M[0][col2];
						if (row1<=4)
							coord1 = M[row1+1][col1];
					}
					
					/**normal*/
					if ((row1+1<=4) && (row2+1<=4)) {
						coord1 = M[row1+1][col1];
						coord2 = M[row2+1][col2];
					}
				}
				
				else {
					coord1 = M[row1][col2];
					coord2 = M[row2][col1];
				}
				
				String strcoord1 = Character.toString((char)coord1);
				switched.add(strcoord1);
				String strcoord2 = Character.toString((char)coord2);
				switched.add(strcoord2);
				/**System.out.println(switched);*/

				a+=2;
			}
			
			//Add the j's
			if ((int)jposition.get(0)!=-1) {
				for (int c=0; c<jposition.size(); c++) {
					int jpos = (int)jposition.get(c);
					switched.add(jpos, 'j');
				}
			}
			/**System.out.println(switched);*/
			String strswitched="";
		for (int c=0; c<switched.size(); c++) {
			strswitched = strswitched + String.valueOf(switched.get(c));
		}	
		JOptionPane.showMessageDialog(null, "Your encrypted code: "+strswitched);
			
		}
	}
	
	private class DecryptListener2 implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			
			char M[][] = {	{'z','c','b','x','m'},
							{'v','n','a','g','l'},
							{'s','f','h','k','q'},
							{'e','t','u','o','w'},
							{'r','y','i','p','d'}
						  };
						  
			List switched = new ArrayList ();
			List messagelist = new ArrayList ();
						  
			int a=0, xadded=0;
			int row1=0, row2=0, col1=0, col2=0;
			String message;
			
			message = input.getText();
			message = message.replaceAll("\\s+","");
			message = message.replaceAll("[^a-z]","");
			
			int msglength = message.length()-1;
			int istherex =0;
			
			//Convert input into a list & check for same letters!
			for (int c=0; c<msglength; c++) {
				messagelist.add(message.charAt(c));
				/** Add an x if the double char aren't x's */
				if ((message.charAt(c)==message.charAt(c+1))&&(message.charAt(c)!='x')) {
					messagelist.add('x');
					istherex = 1;
				}	
				/** Add a z if the double char are x's */
				if ((message.charAt(c)==message.charAt(c+1))&&(message.charAt(c)=='x')) {
					messagelist.add('z');
					istherex = 1;
				}
			}
			messagelist.add(message.charAt(msglength));		
			
			//Get position of j's & put into a list (in case there's multiple j's)
			List jposition = new ArrayList () ;
			for (int c=0; c<messagelist.size(); c++) {
				if ((char)messagelist.get(c)=='j')
					jposition.add(c);
			}
			
			if (jposition.size()==0)
				jposition.add(-1);
			
			//Temporarily remove j from messagelist
			if ((int)jposition.get(0)!=-1) {
				for (int c=0; c<jposition.size(); c++) {
					messagelist.remove(messagelist.indexOf('j'));	
				}
			}
			
			//Odd #? Add an x or z:
			/** Add an x if it's odd */
			if (((messagelist.size()%2)==1)&&((char)messagelist.get(messagelist.size()-1)!='x')) {
				messagelist.add('x');
			}	
			/** Add a z if it's odd but already has an x at the end */
			if (((messagelist.size()%2)==1)&&((char)messagelist.get(messagelist.size()-1)=='x')) {
				messagelist.add('z');
			}	
				
			//switching | decrypting		
			for (int b=1; b<messagelist.size(); b+=2) {
				char letter1 = (char)messagelist.get(a);
				char letter2 = (char)messagelist.get(b);
				int coord1 = M[row1][col1]; 
				int coord2 = M[row2][col2];
		
				for (int col=0; col<5; col++) {
					for (int row=0; row<5; row++) {
						if ((int)letter1==M[row][col]) {
							row1 = row;
							col1 = col;
							/**System.out.println(letter1);
							System.out.println(row1+", "+col1);*/
						}
					}
				}
				
				for (int col=0; col<5; col++) {
					for (int row=0; row<5; row++) {
						if ((int)letter2==M[row][col]) {
							row2 = row;
							col2 = col;
							/**System.out.println(letter2);
							System.out.println(row2+", "+col2);*/
						}
					}
				}
					
				//Same row
				if (row1==row2) {
					
					/**wrap around*/
					if ((col1-1)<0) {
						coord1 = M[row1][4];
						coord2 = M[row2][col2-1];
					}
					if ((col2-1)<0) {
						coord2 = M[row2][4];
						coord1 = M[row1][col1-1];
					}
					
					/**normal*/
					if ((col1-1>=0) && (col2-1>=0)) {
						int col11 = col1-1;
						coord1 = M[row1][col1-1];
						coord2 = M[row2][col2-1];
					}
				}
				
				//Same column
				else if (col1==col2) {
					/**wrap around*/
					if ((row1-1)<0) {
						coord1 = M[4][col1];
						coord2 = M[row2-1][col2];
					}
					if ((row2-1)<0) {
						coord2 = M[4][col2];
						coord1 = M[row1-1][col1];
					}
					
					/**normal*/
					if ((row1-1>=0) && (row2-1>=0)) {
						coord1 = M[row1-1][col1];
						coord2 = M[row2-1][col2];
					}
				}
				
				else {
					coord1 = M[row1][col2];
					coord2 = M[row2][col1];
				}
				
				String strcoord1 = Character.toString((char)coord1);
				switched.add(strcoord1);
				String strcoord2 = Character.toString((char)coord2);
				switched.add(strcoord2);
				/**System.out.println(switched);*/

				a+=2;
			}
			
			//Add the j's
			if ((int)jposition.get(0)!=-1) {
				for (int c=0; c<jposition.size(); c++) {
					int jpos = (int)jposition.get(c);
					switched.add(jpos, 'j');
				}
			}
			/**System.out.println(switched);*/
			String strswitched="";
			for (int c=0; c<switched.size(); c++) {
				strswitched = strswitched + String.valueOf(switched.get(c));
			}	
			JOptionPane.showMessageDialog(null, "Your decrypted code: "+strswitched);
		}
	}	
	
	private class qListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			//Your message will be used to create a grid. Characters will be entered into the grid individually, and if there are leftover spaces in the grid (message < 25 characters), the remainder of the grid will be filled with the alphabet in order
			JOptionPane.showMessageDialog(null, "'Grid Info' is used to make the grid, 'En/Decrypt Info' is the text to be en/decrypted. View your new grid in the execute window.");
		}
	}
	
	private class GridEncrypter implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			
			//GRID MAKER
			System.out.println("Your new grid!:");
			String strword = input.getText();
			strword = strword.replaceAll("\\s+","");
			strword = strword.replaceAll("[^a-z]","");
			int pointer = 0;
			char ascii = 97;
			
			char M[][] = new char[5][5];
			
			for (int c=0; c<5; c++){
				for (int d=0; d<5; d++){
					int dbl = 0, dd=0;
					if (pointer<strword.length()) {
						
						for (int z=0; z<5; z++){
							for (int x=0; x<5; x++){
								if (M[z][x]==strword.charAt(pointer)) {
									dbl = 1;
									dd = 1;
								}
							}
						}
							
						if (dbl==0)
							M[c][d] = strword.charAt(pointer);
					}
					pointer++;
					int dd2=0;
					dbl =0;
					if (dd==1)
						d--;
					if (pointer>strword.length()) {
						for (int z=0; z<5; z++){
							for (int x=0; x<5; x++){
								if (M[z][x]==ascii) {
									dbl = 1;
									dd2=1;
								}
							}
						}
						if (dbl==0)
							M[c][d] = ascii;
						ascii++;
					}
					if (dd2==1)
						d--;
				}
			}
			for (int c=0; c<5; c++){
				for (int d=0; d<5; d++){
					System.out.print(M[c][d]);
				}
				System.out.println();
			}
			System.out.println();
			
			//Find "the new j"
			List Mlist = new ArrayList ();
			for (int c=0; c<5; c++){
				for (int d=0; d<5; d++){
					Mlist.add(M[c][d]);
				}
			}
			
			int missingletter = 0;
			char newj = 'a';
			for (int c=97; c<123; c++){
				char asciinum = (char)c;
				//System.out.print("c is: "+c);
				for (int d=0; d<25; d++){
					if ((char)Mlist.get(d)==asciinum)
						missingletter = 1;
				}
				if (missingletter==0)
						newj = (char)asciinum;
				missingletter = 0;
			}
			
			
		//***ENCRYPTION***
			List switched = new ArrayList ();
			List messagelist = new ArrayList ();
						  
			int a=0, xadded=0;
			int row1=0, row2=0, col1=0, col2=0;
			String message = input1.getText();;
	
			message = message.replaceAll("\\s+","");
			message = message.replaceAll("[^a-z]","");
			
			int msglength = message.length()-1;
			int istherex =0;
			
			//Convert input into a list & check for same letters!
			for (int c=0; c<msglength; c++) {
				messagelist.add(message.charAt(c));
				/** Add an x if the double char aren't x's */
				if ((message.charAt(c)==message.charAt(c+1))&&(message.charAt(c)!='x')) {
					messagelist.add('x');
					istherex = 1;
				}	
				/** Add a z if the double char are x's */
				if ((message.charAt(c)==message.charAt(c+1))&&(message.charAt(c)=='x')) {
					messagelist.add('z');
					istherex = 1;
				}
			}
			messagelist.add(message.charAt(msglength));		
			
			//Get position of missing letters & put into a list (in case there's multiple letters)
			List jposition = new ArrayList () ;
			for (int c=0; c<messagelist.size(); c++) {
				if ((char)messagelist.get(c)==newj)
					jposition.add(c);
			}
			
			if (jposition.size()==0)
				jposition.add(-1);
			
			//Temporarily remove missing char from messagelist
			if ((int)jposition.get(0)!=-1) {
				for (int c=0; c<jposition.size(); c++) {
					messagelist.remove(messagelist.indexOf(newj));	
				}
			}
			
			//Odd #? Add an x or z:
			/** Add an x if it's odd */
			if (((messagelist.size()%2)==1)&&((char)messagelist.get(messagelist.size()-1)!='x')) {
				messagelist.add('x');
			}	
			/** Add a z if it's odd but already has an x at the end */
			if (((messagelist.size()%2)==1)&&((char)messagelist.get(messagelist.size()-1)=='x')) {
				messagelist.add('z');
			}	
				
			//switching | encrypting		
			for (int b=1; b<messagelist.size(); b+=2) {
				char letter1 = (char)messagelist.get(a);
				char letter2 = (char)messagelist.get(b);
				int coord1 = M[row1][col1]; 
				int coord2 = M[row2][col2];
		
				for (int col=0; col<5; col++) {
					for (int row=0; row<5; row++) {
						if ((int)letter1==M[row][col]) {
							row1 = row;
							col1 = col;
							/**System.out.println(letter1);
							System.out.println(row1+", "+col1);*/
						}
					}
				}
				
				for (int col=0; col<5; col++) {
					for (int row=0; row<5; row++) {
						if ((int)letter2==M[row][col]) {
							row2 = row;
							col2 = col;
							/**System.out.println(letter2);
							System.out.println(row2+", "+col2);*/
						}
					}
				}
					
				//Same row
				if (row1==row2) {
					
					/**wrap around*/
					if ((col1+1)>4) {
						coord1 = M[row1][0];
						if (col2<=4)
							coord2 = M[row2][col2+1];
					}
					if ((col2+1)>4) {
						coord2 = M[row2][0];
						if (col1<=4)
							coord1 = M[row1][col1+1];
					}
					
					/**normal*/
					if ((col1+1<=4) && (col2+1<=4)) {
						coord1 = M[row1][col1+1];
						coord2 = M[row2][col2+1];
					}
				}
				
				//Same column
				else if (col1==col2) {
					/**wrap around*/
					if ((row1+1)>4) {
						coord1 = M[0][col1];
						if (row2<=4)
							coord2 = M[row2+1][col2];
					}
					if ((row2+1)>4) {
						coord2 = M[0][col2];
						if (row1<=4)
							coord1 = M[row1+1][col1];
					}
					
					/**normal*/
					if ((row1+1<=4) && (row2+1<=4)) {
						coord1 = M[row1+1][col1];
						coord2 = M[row2+1][col2];
					}
				}
				
				else {
					coord1 = M[row1][col2];
					coord2 = M[row2][col1];
				}
				
				String strcoord1 = Character.toString((char)coord1);
				switched.add(strcoord1);
				String strcoord2 = Character.toString((char)coord2);
				switched.add(strcoord2);
				/**System.out.println(switched);*/

				a+=2;
			}
			
			//Add the missing char(s)
			if ((int)jposition.get(0)!=-1) {
				for (int c=0; c<jposition.size(); c++) {
					int jpos = (int)jposition.get(c);
					switched.add(jpos, newj);
				}
			}
			/**System.out.println(switched);*/
			String strswitched="";
		for (int c=0; c<switched.size(); c++) {
			strswitched = strswitched + String.valueOf(switched.get(c));
		}	
		JOptionPane.showMessageDialog(null, "Your encrypted code: "+strswitched);
		}
	}
	private class GridDecrypter implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			
			//GRID MAKER
			System.out.println("Your new grid!:");
			String strword = input.getText();
			strword = strword.replaceAll("\\s+","");
			strword = strword.replaceAll("[^a-z]","");
			int pointer = 0;
			char ascii = 97;
			
			char M[][] = new char[5][5];
			
			for (int c=0; c<5; c++){
				for (int d=0; d<5; d++){
					int dbl = 0, dd=0;
					if (pointer<strword.length()) {
						
						for (int z=0; z<5; z++){
							for (int x=0; x<5; x++){
								if (M[z][x]==strword.charAt(pointer)) {
									dbl = 1;
									dd = 1;
								}
							}
						}
							
						if (dbl==0)
							M[c][d] = strword.charAt(pointer);
					}
					pointer++;
					int dd2=0;
					dbl =0;
					if (dd==1)
						d--;
					if (pointer>strword.length()) {
						for (int z=0; z<5; z++){
							for (int x=0; x<5; x++){
								if (M[z][x]==ascii) {
									dbl = 1;
									dd2=1;
								}
							}
						}
						if (dbl==0)
							M[c][d] = ascii;
						ascii++;
					}
					if (dd2==1)
						d--;
				}
			}
			for (int c=0; c<5; c++){
				for (int d=0; d<5; d++){
					System.out.print(M[c][d]);
				}
				System.out.println();
			}
			System.out.println();
			
			//Find "the new j"
			List Mlist = new ArrayList ();
			for (int c=0; c<5; c++){
				for (int d=0; d<5; d++){
					Mlist.add(M[c][d]);
				}
			}
			
			int missingletter = 0;
			char newj = 'a';
			for (int c=97; c<123; c++){
				char asciinum = (char)c;
				//System.out.print("c is: "+c);
				for (int d=0; d<25; d++){
					if ((char)Mlist.get(d)==asciinum)
						missingletter = 1;
				}
				if (missingletter==0)
						newj = (char)asciinum;
				missingletter = 0;
			}
			
			
		//***DECRYPTION***
			List switched = new ArrayList ();
			List messagelist = new ArrayList ();
						  
			int a=0, xadded=0;
			int row1=0, row2=0, col1=0, col2=0;
			String message;
			
			message = input1.getText();
			message = message.replaceAll("\\s+","");
			message = message.replaceAll("[^a-z]","");
			
			int msglength = message.length()-1;
			int istherex =0;
			
			//Convert input into a list & check for same letters!
			for (int c=0; c<msglength; c++) {
				messagelist.add(message.charAt(c));
				/** Add an x if the double char aren't x's */
				if ((message.charAt(c)==message.charAt(c+1))&&(message.charAt(c)!='x')) {
					messagelist.add('x');
					istherex = 1;
				}	
				/** Add a z if the double char are x's */
				if ((message.charAt(c)==message.charAt(c+1))&&(message.charAt(c)=='x')) {
					messagelist.add('z');
					istherex = 1;
				}
			}
			messagelist.add(message.charAt(msglength));		
			
			//Get position of missing letters & put into a list (in case there's multiple missing letters)
			List jposition = new ArrayList () ;
			for (int c=0; c<messagelist.size(); c++) {
				if ((char)messagelist.get(c)==newj)
					jposition.add(c);
			}
			
			if (jposition.size()==0)
				jposition.add(-1);
			
			//Temporarily remove missing char from messagelist
			if ((int)jposition.get(0)!=-1) {
				for (int c=0; c<jposition.size(); c++) {
					messagelist.remove(messagelist.indexOf(newj));	
				}
			}
			
			//Odd #? Add an x or z:
			/** Add an x if it's odd */
			if (((messagelist.size()%2)==1)&&((char)messagelist.get(messagelist.size()-1)!='x')) {
				messagelist.add('x');
			}	
			/** Add a z if it's odd but already has an x at the end */
			if (((messagelist.size()%2)==1)&&((char)messagelist.get(messagelist.size()-1)=='x')) {
				messagelist.add('z');
			}	
				
			//switching | decrypting		
			for (int b=1; b<messagelist.size(); b+=2) {
				char letter1 = (char)messagelist.get(a);
				char letter2 = (char)messagelist.get(b);
				int coord1 = M[row1][col1]; 
				int coord2 = M[row2][col2];
		
				for (int col=0; col<5; col++) {
					for (int row=0; row<5; row++) {
						if ((int)letter1==M[row][col]) {
							row1 = row;
							col1 = col;
							/**System.out.println(letter1);
							System.out.println(row1+", "+col1);*/
						}
					}
				}
				
				for (int col=0; col<5; col++) {
					for (int row=0; row<5; row++) {
						if ((int)letter2==M[row][col]) {
							row2 = row;
							col2 = col;
							/**System.out.println(letter2);
							System.out.println(row2+", "+col2);*/
						}
					}
				}
					
				//Same row
				if (row1==row2) {
					
					/**wrap around*/
					if ((col1-1)<0) {
						coord1 = M[row1][4];
						coord2 = M[row2][col2-1];
					}
					if ((col2-1)<0) {
						coord2 = M[row2][4];
						coord1 = M[row1][col1-1];
					}
					
					/**normal*/
					if ((col1-1>=0) && (col2-1>=0)) {
						int col11 = col1-1;
						coord1 = M[row1][col1-1];
						coord2 = M[row2][col2-1];
					}
				}
				
				//Same column
				else if (col1==col2) {
					/**wrap around*/
					if ((row1-1)<0) {
						coord1 = M[4][col1];
						coord2 = M[row2-1][col2];
					}
					if ((row2-1)<0) {
						coord2 = M[4][col2];
						coord1 = M[row1-1][col1];
					}
					
					/**normal*/
					if ((row1-1>=0) && (row2-1>=0)) {
						coord1 = M[row1-1][col1];
						coord2 = M[row2-1][col2];
					}
				}
				
				else {
					coord1 = M[row1][col2];
					coord2 = M[row2][col1];
				}
				
				String strcoord1 = Character.toString((char)coord1);
				switched.add(strcoord1);
				String strcoord2 = Character.toString((char)coord2);
				switched.add(strcoord2);
				/**System.out.println(switched);*/

				a+=2;
			}
			
			//Add the missing char(s)
			if ((int)jposition.get(0)!=-1) {
				for (int c=0; c<jposition.size(); c++) {
					int jpos = (int)jposition.get(c);
					switched.add(jpos, newj);
				}
			}
			/**System.out.println(switched);*/
			String strswitched="";
			for (int c=0; c<switched.size(); c++) {
				strswitched = strswitched + String.valueOf(switched.get(c));
			}	
			JOptionPane.showMessageDialog(null, "Your decrypted code: "+strswitched);
		}
	}

	public static void main (String args[]) {	  
		new PlayfairCipher();
	}

}

