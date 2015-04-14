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

	Image scienceImage, sciFairImage;
	ImageIcon scienceIcon, sciFairIcon;

	Container contentPane;

	String projectFile, judgeFile, outputFile, currentLine;

	Scanner fileScanner;
	PrintWriter out;

	// CardLayout current;

	LinkedList<Judge> judgeList;
	LinkedList<Project> projList;

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

		labelSize = new Dimension(1500, 60);
		Component hStrut = Box.createHorizontalStrut(HORIZONTAL_STRUT_SIZE);

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

		judgeList = new LinkedList<Judge>();
		projList = new LinkedList<Project>();

	}// end constructor

	public static void main(String[] srgs) {

		JudgeGUI myGUI = new JudgeGUI();
		myGUI.setVisible(true);

	}// end main method

	@Override
	public void actionPerformed(ActionEvent e) {

		int result;

		switch (e.getActionCommand()) {

		case "OK":

			projectFile = projectField.getText();
			judgeFile = judgeField.getText();
			outputFile = outputField.getText();

			try {
				fileScanner = new Scanner(new File(projectFile));
			} catch (FileNotFoundException e1) {
				JOptionPane
						.showMessageDialog(
								contentPane,
								"There was an error opening the file. The system will now exit.",
								"I/O ERROR", 2, scienceIcon);
				e1.printStackTrace();
				System.exit(1);
			}
			// guts go here

			readProjects(fileScanner);

			try {
				fileScanner = new Scanner(new File(judgeFile));
			} catch (FileNotFoundException e1) {
				JOptionPane
						.showMessageDialog(
								contentPane,
								"There was an error opening the file. The system will now exit.",
								"I/O ERROR", 2, scienceIcon);
				e1.printStackTrace();
				System.exit(1);
			}

			readJudges(fileScanner);

			//methods for assigning projects and judges to groups go here
			
			
			//for debugging purposes
			System.out.println(projList);
			System.out.println(judgeList);

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
				p.doesSub0();
				break;
				
			case "Chemistry":
				p.doesSub1();
				break;
				
			case "Earth/Space Science":
				p.doesSub2();
				break;
				
			case "Environmental Science":
				p.doesSub3();
				break;
				
			case "Life Science":
				p.doesSub4();
				break;
				
			case "Mathematics/Physics":
				p.doesSub5();
				break;
				
			default:
				//not sure what
				
				break;
				
			}// end of switch to set project subject

			projList.add(p);

		}// end of while(fileScanner.hasNext()) for projectFile

	}// end of readProjects() method

	void readJudges(Scanner fScan) {

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
					j.doesSub0();
					break;

				case "Chemistry,":
				case "Chemistry":
					j.doesSub1();
					break;

				case "Earth/Space":
					j.doesSub2();
					break;

				case "Environmental":
					j.doesSub3();
					break;

				case "Life":
					j.doesSub4();
					break;

				case "Mathematics/Physics,":
				case "Mathematics/Physics":
					j.doesSub5();
					break;

				case "Science,":
				case "Science":
					break;

				default:
					// not sure what might need to go here
					break;

				}// end of switch for setting subj boolean cars for judges

			}// end of while(lineScanner.hasNext()) for currentLine in judgeFile

			judgeList.add(j);

		}// end of while(fileScanner.hasNextLine()) for judgeFile

	}// end of readJudgesMethod
	
	
	

}// end JudgeGUI class
