package scienceFair;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class JudgeGUI extends JFrame implements ActionListener {

	// constants
	public static final int WIDTH = 460;
	public static final int HEIGHT = 480;
	public static final int XCOORD = 140;
	public static final int YCOORD = 75;
	public static final int VERTICAL_STRUT_SIZE = 40;
	public static final int HORIZONTAL_STRUT_SIZE = 80;

	public static final int NUMBER_OF_SUBJECTS = 6;

	// GUI components
	JButton okButton, clearButton;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem okItem, clearItem, exitItem;
	JLabel topLabel, topLabel2, projectLabel, judgeLabel, outputLabel;
	JTextField projectField, judgeField, outputField;
	Dimension textSize, labelSize;

	// button style
	Dimension buttonSize;
	BevelBorder buttonBorder;

	//some visual style elements
	Image scienceImage, sciFairImage;
	ImageIcon scienceIcon, sciFairIcon;

	Container contentPane;

	//file names and String for processing input data
	String projectFile, judgeFile, outputFile, currentLine;

	//file I/O
	Scanner fileScanner;
	PrintWriter out;

	// keeps track of how many projects in each subject.
	int[] projCount;

	// keeps track of how many judges are willing to judge each subject.
	int[] judgeCount;

	// keeps track of how many groups we need for each subject
	int[] groupCount;

	//list of all judges and projects
	LinkedList<Judge> judgeList;
	LinkedList<Project> projList;

	//ArrayList of lists of groups indexed by subject
	ArrayList<LinkedList<Group>> arrayOfGroupLists;
	
	//Lists of groups to be stored in above. One for each subject.
	LinkedList<Group> groupsSub0;
	LinkedList<Group> groupsSub1;
	LinkedList<Group> groupsSub2;
	LinkedList<Group> groupsSub3;
	LinkedList<Group> groupsSub4;
	LinkedList<Group> groupsSub5;

	//constructor
	public JudgeGUI() {

		setTitle("Judge Assignment");
		setSize(WIDTH, HEIGHT);
		setLocation(XCOORD, YCOORD);

		contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		buttonSize = new Dimension(100, 50);
		buttonBorder = new BevelBorder(BevelBorder.RAISED);

		// Set the windows icon in the top left corner.
		scienceImage = null;
		try {
			scienceImage = ImageIO.read(getClass().getResource(
					"/images/science.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setIconImage(scienceImage);
		scienceIcon = new ImageIcon(scienceImage);

		//custom image for top middle of gui
		sciFairImage = null;
		try {
			sciFairImage = ImageIO.read(getClass().getResource(
					"/images/sciencefair3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		sciFairIcon = new ImageIcon(sciFairImage);

		// Add a Window Exit Listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		//set up menu items for drop down menu
		okItem = new JMenuItem("OK");
		okItem.setActionCommand("OK");
		okItem.addActionListener(this);
		clearItem = new JMenuItem("Clear");
		clearItem.setActionCommand("CLEAR");
		clearItem.addActionListener(this);
		exitItem = new JMenuItem("Exit");
		exitItem.setActionCommand("EXIT");
		exitItem.addActionListener(this);

		menu = new JMenu("Menu");
		menu.add(okItem);
		menu.add(clearItem);
		menu.add(exitItem);

		menuBar = new JMenuBar();
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		//some sizing components
		labelSize = new Dimension(1500, 60);
		Component hStrut = Box.createHorizontalStrut(HORIZONTAL_STRUT_SIZE);

		
		//visual set up of gui
		
		topLabel = new JLabel(sciFairIcon);
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(topLabel, BorderLayout.CENTER);

		projectLabel = new JLabel("Enter the project list file name:");
		projectLabel.setFont(new Font("Cooper Black", Font.PLAIN, 14));
		JPanel projPanel = new JPanel(new FlowLayout());
		projPanel.setSize(labelSize);
		projPanel.add(projectLabel);

		judgeLabel = new JLabel("Enter the judge list file name:");
		judgeLabel.setFont(new Font("Cooper Black", Font.PLAIN, 14));
		JPanel judgePanel = new JPanel(new FlowLayout());
		judgePanel.setSize(labelSize);
		judgePanel.add(judgeLabel);

		outputLabel = new JLabel(
				"Enter the file name for the output assignment document:");
		outputLabel.setFont(new Font("Cooper Black", Font.PLAIN, 14));
		JPanel outputPanel = new JPanel(new FlowLayout());
		outputPanel.setSize(labelSize);
		outputPanel.add(outputLabel);

		textSize = new Dimension(1200, 60);

		projectField = new JTextField("Projects.txt");
		projectField.setMaximumSize(textSize);
		judgeField = new JTextField("Judges.txt");
		judgeField.setMaximumSize(textSize);
		outputField = new JTextField("Output.txt");
		outputField.setMaximumSize(textSize);

		okButton = new JButton("OK");
		okButton.addActionListener(this);
		okButton.setPreferredSize(buttonSize);
		okButton.setBorder(buttonBorder);
		okButton.setToolTipText("OK");
		okButton.setActionCommand("OK");

		clearButton = new JButton("Clear");
		clearButton.addActionListener(this);
		clearButton.setPreferredSize(buttonSize);
		clearButton.setBorder(buttonBorder);
		clearButton.setToolTipText("Clear");
		clearButton.setActionCommand("CLEAR");

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(okButton);
		buttonPanel.add(clearButton);

		Component vStrut = Box.createVerticalStrut(VERTICAL_STRUT_SIZE);
		Component vGlue = Box.createVerticalGlue();

		contentPane.add(vGlue);
		contentPane.add(topPanel);
		contentPane.add(vStrut);
		contentPane.add(projPanel);
		contentPane.add(vStrut);
		contentPane.add(projectField);
		contentPane.add(vStrut);
		contentPane.add(judgePanel);
		contentPane.add(vStrut);
		contentPane.add(judgeField);
		contentPane.add(vStrut);
		contentPane.add(outputPanel);
		contentPane.add(vStrut);
		contentPane.add(outputField);
		contentPane.add(vStrut);
		contentPane.add(buttonPanel);

		//initializing some list variables
		judgeList = new LinkedList<Judge>();
		projList = new LinkedList<Project>();

		projCount = new int[NUMBER_OF_SUBJECTS];
		for (int i = 0; i < NUMBER_OF_SUBJECTS; i++)
			projCount[i] = 0;
		judgeCount = new int[NUMBER_OF_SUBJECTS];
		for (int i = 0; i < NUMBER_OF_SUBJECTS; i++)
			judgeCount[i] = 0;
		groupCount = new int[NUMBER_OF_SUBJECTS];
		for (int i = 0; i < NUMBER_OF_SUBJECTS; i++)
			groupCount[i] = 0;

		arrayOfGroupLists = new ArrayList<LinkedList<Group>>();
		groupsSub0 = new LinkedList<Group>();
		groupsSub1 = new LinkedList<Group>();
		groupsSub2 = new LinkedList<Group>();
		groupsSub3 = new LinkedList<Group>();
		groupsSub4 = new LinkedList<Group>();
		groupsSub5 = new LinkedList<Group>();
		
		arrayOfGroupLists.add(groupsSub0);
		arrayOfGroupLists.add(groupsSub1);
		arrayOfGroupLists.add(groupsSub2);
		arrayOfGroupLists.add(groupsSub3);
		arrayOfGroupLists.add(groupsSub4);
		arrayOfGroupLists.add(groupsSub5);

	}// end constructor

	public static void main(String[] srgs) {

		JudgeGUI myGUI = new JudgeGUI();
		myGUI.setVisible(true);

	}// end main method

	
	//stuff happens when you click buttons
	@Override
	public void actionPerformed(ActionEvent e) {

		int result;

		switch (e.getActionCommand()) {

		case "OK":
			//ready to process the data

			//getting file names
			projectFile = projectField.getText();
			judgeFile = judgeField.getText();
			outputFile = outputField.getText();
			
			// guts go here

			openFileScan(projectFile);

			readProjects(fileScanner);
			
			openFileScan(judgeFile);

			readJudges(fileScanner);

			// methods for assigning projects and judges to groups go here

			for (int i = 0; i < NUMBER_OF_SUBJECTS; i++){
				if(judgeCount[i] < 4)
					howManyGroups(i);
			}
			
			for(int i = 0; i < NUMBER_OF_SUBJECTS; i++){
				if(groupCount[i] > 0)
					howManyGroups(i);
			}
				
				
				
				
				//howManyGroups(i);

			// for debugging purposes
			//System.out.println(projList);
			//System.out.println(judgeList);
//			for (int i = 0; i < NUMBER_OF_SUBJECTS; i++)
//				System.out.print(projCount[i] + "    ");
//			System.out.println("");
//			for (int i = 0; i < NUMBER_OF_SUBJECTS; i++)
//				System.out.print(judgeCount[i] + "    ");
//			System.out.println("");
//			for (int i = 0; i < NUMBER_OF_SUBJECTS; i++)
//				System.out.print(groupCount[i] + "    ");
//			System.out.println("");
			


			fileScanner.close();
			
			break;

		case "CLEAR":
			result = JOptionPane.showConfirmDialog(contentPane,
					"Are you sure you would like to clear the fields?",
					"Clear", JOptionPane.YES_NO_OPTION, 2, scienceIcon);

			if (result == JOptionPane.YES_OPTION) {
				projectField.setText("");
				judgeField.setText("");
				outputField.setText("");
			}

			break;

		case "EXIT":

			result = JOptionPane.showConfirmDialog(contentPane,
					"Are you sure you would like to exit?", "Exit",
					JOptionPane.YES_NO_OPTION, 2, scienceIcon);
			if (result == JOptionPane.YES_OPTION)
				System.exit(0);

		}// end actionCommand switch

	}// end actionPerformed method

	private void readProjects(Scanner fScan) {

		while (fScan.hasNext()) {

			int projNum = fScan.nextInt();
			String projSubj = fScan.nextLine();
			projSubj = projSubj.trim();

			Project p = new Project(projNum);

			switch (projSubj) {
			
			case "Behavioral/Social Science":
				// p.doesSub0();
				p.doesSub(0);
				projCount[0]++;
				break;

			case "Chemistry":
				// p.doesSub1();
				p.doesSub(1);
				projCount[1]++;
				break;

			case "Earth/Space Science":
				// p.doesSub2();
				p.doesSub(2);
				projCount[2]++;
				break;

			case "Environmental Science":
				// p.doesSub3();
				p.doesSub(3);
				projCount[3]++;
				break;

			case "Life Science":
				// p.doesSub4();
				p.doesSub(4);
				projCount[4]++;
				break;

			case "Mathematics/Physics":
				// p.doesSub5();
				p.doesSub(5);
				projCount[5]++;
				break;

			default:
				// not sure what goes here
				break;

			}// end of switch to set project subject

			projList.add(p);

		}// end of while(fileScanner.hasNext()) for projectFile

	}// end of readProjects() method

	private void readJudges(Scanner fScan) {

		while (fScan.hasNextLine()) {

			Judge j;
			String currentLine = fScan.nextLine();
			Scanner lineScanner = new Scanner(currentLine);

			String name = lineScanner.next();// first name
			name = name + " " + lineScanner.next();// last name and :
			name = name.substring(0, name.length() - 1);// getting rid of the :

			j = new Judge(name);

			while (lineScanner.hasNext()) {

				String subj = lineScanner.next();
				subj = subj.trim();

				switch (subj) {

				case "Behavioral/Social":
					// j.doesSub0();
					j.doesSub(0);
					judgeCount[0]++;
					break;

				case "Chemistry,":
				case "Chemistry":
					// j.doesSub1();
					j.doesSub(1);
					judgeCount[1]++;
					break;

				case "Earth/Space":
					// j.doesSub2();
					j.doesSub(2);
					judgeCount[2]++;
					break;

				case "Environmental":
					// j.doesSub3();
					j.doesSub(3);
					judgeCount[3]++;
					break;

				case "Life":
					// j.doesSub4();
					j.doesSub(4);
					judgeCount[4]++;
					break;

				case "Mathematics/Physics,":
				case "Mathematics/Physics":
					// j.doesSub5();
					j.doesSub(5);
					judgeCount[5]++;
					break;

				case "Science,":
				case "Science":
					break;

				default:
					// not sure what might needs to go here
					break;

				}// end of switch for setting subj boolean cars for judges

			}// end of while(lineScanner.hasNext()) for currentLine in judgeFile

			lineScanner.close();
			
			judgeList.add(j);

		}// end of while(fileScanner.hasNextLine()) for judgeFile
		
	}// end of readJudgesMethod

	private void howManyGroups(int subjectNum) {

		//debugging
		System.out.println("Entering howManyGroups method");
		
		if (projCount[subjectNum] < 6){
			groupCount[subjectNum] = 1;
			assignGroups(projCount[subjectNum], groupCount[subjectNum], subjectNum);
		}
		else {

			switch ((projCount[subjectNum] % 6)) {

			case 0:
				
				groupCount[subjectNum] = projCount[subjectNum] / 6;
				assignGroups(0, groupCount[subjectNum], subjectNum);
				
				break;
				
			case 1:
				
				if (projCount[subjectNum] == 7)
					groupCount[subjectNum] = 2;
				
				else
					// one group of 3 one group of 4 plus
					// (#Proj - 7)/6 groups
					groupCount[subjectNum] = ((projCount[subjectNum] - 7) / 6) + 2;
				
				assignGroups(1, groupCount[subjectNum], subjectNum);
				
				break;
				
				
			case 2:
				
				if (projCount[subjectNum] == 8)
					groupCount[subjectNum] = 2;
				// assign 2 groups of four projects
				
				else
					// 2 groups of four plus
					// (#Proj - 8)/6 groups
					groupCount[subjectNum] = ((projCount[subjectNum] - 8) / 6) + 2;
				
				assignGroups(2, groupCount[subjectNum], subjectNum);
				
				break;
				
			case 3:
				
				if (projCount[subjectNum] == 9)
					groupCount[subjectNum] = 2;
				// assign 2 groups one of four one of five


				else
					// 2 groups one of four one of five plus
					// (#Proj - 9)/6 groups
					groupCount[subjectNum] = ((projCount[subjectNum] - 9) / 6) + 2;
				
				assignGroups(3, groupCount[subjectNum], subjectNum);
				
				break;
				
			case 4:
				
				if (projCount[subjectNum] == 10)
					groupCount[subjectNum] = 2;
				// assign 2 groups of five
				
				else
					// 2 groups of five plus
					// (#Proj - 10)/6 groups
					groupCount[subjectNum] = ((projCount[subjectNum] - 10) / 6) + 2;
				
				assignGroups(4, groupCount[subjectNum], subjectNum);
				
				break;
				
			case 5:
				
				if (projCount[subjectNum] == 11)
					groupCount[subjectNum] = 2;
				// assign 2 groups one of five one of six
				
				else
					// 2 groups one of five one of six plus
					// (#Proj - 11)/6 groups
					groupCount[subjectNum] = ((projCount[subjectNum] - 11) / 6) + 2;
				
				assignGroups(5, groupCount[subjectNum], subjectNum);
				
				break;

			}// end switch for determining number of groups

		}// end of else(projCount>=6)

	}// end of howManyGroups method

	
	//want to make sure we call this in  specific order which takes into account
	//the fact that some subjects may have only just enough people willing to judge
	private void assignGroups(int remainder, int numOfGroups, int subjectNum)
			/* not sure about param yet. Probably need to overload this
			* method with 2 signatures: one for the if in the switch in the
			* howManyGroups method and one for the else in the same switch */ 
	{

		switch (remainder) {

		case 0:
			for (int i = 0; i < numOfGroups; i++) {

				Group g = new Group(subjectNum, i + 1, 6);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 6, g);//when 0 i the remainder there will always
													//be 6 projects in each group.
				
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);

			}// for each group set up
			
			break;
			
		case 1:
			
			if(numOfGroups == 1){
				
				Group g = new Group(subjectNum, 1, projCount[subjectNum]);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, projCount[subjectNum], g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
			}
			else if(numOfGroups == 2){
				
				Group g = new Group(subjectNum, 1, 4);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 4, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
				g = new Group(subjectNum, 2, 3);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 3, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
			}//end of case where total number of projects = 7
			else{
				
				Group g = new Group(subjectNum, 1, 4);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 4, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
				g = new Group(subjectNum, 2, 3);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 3, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
				for (int i = 2; i < numOfGroups; i++) {

					g = new Group(subjectNum, i + 1, 6);
					searchForJudges(subjectNum, g);
					searchForProjects(subjectNum, 6, g);
					
					arrayOfGroupLists.get(subjectNum).add(g);
					
					//decrement the amount of groups needed for this subject
					//every time we make a group
					groupCount[subjectNum]--;
					
					//debugging
					System.out.println(g);

				}// for each group set up
				
			}//end of case where remainder is 1 but total numOfProj > 7
			
			break;
			
		case 2:
			
			if(numOfGroups == 2){
				
				Group g = new Group(subjectNum, 1, 4);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 4, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
				g = new Group(subjectNum, 2, 4);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 4, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
			}//end of case where total number of projects = 8
			else{
				
				Group g = new Group(subjectNum, 1, 4);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 4, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
				g = new Group(subjectNum, 2, 4);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 4, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
				for (int i = 2; i < numOfGroups; i++) {

					g = new Group(subjectNum, i + 1, 6);
					searchForJudges(subjectNum, g);
					searchForProjects(subjectNum, 6, g);
					
					arrayOfGroupLists.get(subjectNum).add(g);
					
					//decrement the amount of groups needed for this subject
					//every time we make a group
					groupCount[subjectNum]--;
					
					//debugging
					System.out.println(g);

				}// for each group set up
				
			}//end of case where remainder is 2 but total numOfProj > 8
			
			break;
			
		case 3:
			
			if(numOfGroups == 2){
				
				Group g = new Group(subjectNum, 1, 5);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 5, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
				g = new Group(subjectNum, 2, 4);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 4, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
			}//end of case where total number of projects = 9
			else{
				
				Group g = new Group(subjectNum, 1, 5);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 5, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
				g = new Group(subjectNum, 2, 4);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 4, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
				for (int i = 2; i < numOfGroups; i++) {

					g = new Group(subjectNum, i + 1, 6);
					searchForJudges(subjectNum, g);
					searchForProjects(subjectNum, 6, g);
					
					arrayOfGroupLists.get(subjectNum).add(g);
					
					//decrement the amount of groups needed for this subject
					//every time we make a group
					groupCount[subjectNum]--;
					
					//debugging
					System.out.println(g);

				}// for each group set up
				
			}//end of case where remainder is 3 but total numOfProj > 9
			
			break;
			
		case 4:
			
			if(numOfGroups == 2){
				
				Group g = new Group(subjectNum, 1, 5);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 5, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
				g = new Group(subjectNum, 2, 5);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 5, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
			}//end of case where total number of projects = 10
			else{
				
				Group g = new Group(subjectNum, 1, 5);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 5, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
				g = new Group(subjectNum, 2, 5);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 5, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
				for (int i = 2; i < numOfGroups; i++) {

					g = new Group(subjectNum, i + 1, 6);
					searchForJudges(subjectNum, g);
					searchForProjects(subjectNum, 6, g);
					
					arrayOfGroupLists.get(subjectNum).add(g);
					
					//decrement the amount of groups needed for this subject
					//every time we make a group
					groupCount[subjectNum]--;
					
					//debugging
					System.out.println(g);

				}// for each group set up
				
			}//end of case where remainder is 4 but total numOfProj > 10
			
			break;
			
		case 5:
			
			if(numOfGroups == 2){
				
				Group g = new Group(subjectNum, 1, 6);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 6, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
				g = new Group(subjectNum, 2, 5);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 5, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
			}//end of case where total number of projects = 10
			else{
				
				Group g = new Group(subjectNum, 1, 6);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 6, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
				g = new Group(subjectNum, 2, 5);
				searchForJudges(subjectNum, g);
				searchForProjects(subjectNum, 5, g);
				arrayOfGroupLists.get(subjectNum).add(g);
				
				//decrement the amount of groups needed for this subject
				//every time we make a group
				groupCount[subjectNum]--;
				
				//debugging
				System.out.println(g);
				
				for (int i = 2; i < numOfGroups; i++) {

					g = new Group(subjectNum, i + 1, 6);
					searchForJudges(subjectNum, g);
					searchForProjects(subjectNum, 6, g);
					
					arrayOfGroupLists.get(subjectNum).add(g);
					
					//decrement the amount of groups needed for this subject
					//every time we make a group
					groupCount[subjectNum]--;
					
					//debugging
					System.out.println(g);

				}// for each group set up
				
			}//end of case where remainder is 5 but total numOfProj > 11
			
			break;

		}// end of switch(remainder) for assigning projects

	}// end of assignProjects method

	private void searchForJudges(int subjectNum, Group g) {
		
		ListIterator<Judge> itr = judgeList.listIterator();
		//count of judges for this group
		int judgeCounter = 0;
		while(itr.hasNext() && g.getJudgeCount() < 3){
			if(itr.next().judgesSubject(subjectNum)){
				
				g.setJudge(judgeCounter, itr.previous());
				
				for(int i = 0; i < NUMBER_OF_SUBJECTS; i++){
					if(g.getJudge(judgeCounter).judgesSubject(subjectNum))
						judgeCount[subjectNum]--;
				}
				
				//increase count of judges for this group
				judgeCounter++;
				//remove the assigned judge from the pool of available judges
				itr.remove();
				
			}
		}
		
	}// end of searchForJudges method
	
	private void searchForProjects(int subjectNum, int numOfProjects, Group g){
		
		ListIterator<Project> itr = projList.listIterator();
		//count of projects for this group
		int projCounter = 0;
		while(itr.hasNext() && projCounter < numOfProjects){
			if(itr.next().getSub() == subjectNum){
				g.setProject(projCounter, itr.previous());
			}//end of if current project is in the subject of this group
			
			//increase count of projects for this group
			projCounter++;
			//remove the project assigned from the list to be assigned
			itr.remove();
		}//end of while still searching for projects
		
	}//end of searchForProjects method
	
	private void openFileScan(String fileName){
		
		try {
			fileScanner = new Scanner(new File(fileName));
		} catch (FileNotFoundException e1) {
			JOptionPane
					.showMessageDialog(
							contentPane,
							"There was an error opening the file. The system will now exit.",
							"I/O ERROR", 2, scienceIcon);
			e1.printStackTrace();
			System.exit(1);
		}
		
	}//end open FileScan method

}// end JudgeGUI class
