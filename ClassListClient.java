/**	
 * ClassListClient is where users may sign in or register to the remote ClassList object
 * All users that log in here become students.  Students type 'read' to get the latest question
 * and have ten seconds to respond before the question the instructor is at posts.
 * Authors: Alvin Thai, Daniel Holguin, Jarom Montgomerry, Nicholas Knaggs
 * Professor Silva ACO 432
 */

//package examples.RMIShape;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class ClassListClient {

    private static final int TIME_TO_WAIT = 10;

	private static DefaultComboBoxModel<String> usersList;

    public static void main(String[] args) {

		//creating GUI and populating Jlist GUI with default info
        createClientGUI();
		usersList.addElement("Users will show here");

        Scanner scanner = new Scanner(System.in);
        String menu1 = "";
        String answer = "";

        // if(System.getSecurityManager() == null){
        // 	System.setSecurityManager(new SecurityManager());
        // } else System.out.println("Already has a security manager, so cant set RMI SM");
        ClassList aClassList = null;
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            aClassList = (ClassList) registry.lookup("ClassList");
            System.out.println("Found server");
            ArrayList<StudentServant> sList = aClassList.allStudents();
            System.out.println("Got ArrayList");
            while (!menu1.equalsIgnoreCase("exit")) {
				//prints every user to GUI jlist componenet
				usersList.removeAllElements();
				for (Student s : sList) {
					User user = s.getState();
					usersList.addElement(user.printUserStatus());
				}
                System.out.println("Login or Register?: (or exit)");
                menu1 = scanner.nextLine();
                if (menu1.equalsIgnoreCase("Register")) {
                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    User u = new User("Student", username, password, "offline");
                    aClassList.newStudent(u);
                    System.out.println("Please log in");
                } else if (menu1.equalsIgnoreCase("Login")) {
                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    Student you = aClassList.getStudent(username);
                    if (you.getPass().equals(password)) {
                        you.setStatusOn();
                        registry = LocateRegistry.getRegistry("localhost", 1099);
                        aClassList = (ClassList) registry.lookup("ClassList");
                        for (Student s : sList) {
                            User u = s.getState();
                            u.print();
                        }
                        System.out.println("Type read to read the question and type answer [<your answer>, \"read\", \"logout\"]");
                        System.out.println(you.getQuestion());
                        answer = scanner.nextLine();
                        while (!answer.equalsIgnoreCase("logout")) {
                            if (answer.equalsIgnoreCase("read")) {
								//prints every user to GUI jlist componenet
								usersList.removeAllElements();
								for (Student s : sList) {
									User user = s.getState();
									usersList.addElement(user.printUserStatus());
								}
                                // updates data from registry
                                registry = LocateRegistry.getRegistry("localhost", 1099);
                                aClassList = (ClassList) registry.lookup("ClassList");
                                sList = aClassList.allStudents();
                                for (Student s : sList) {
                                    User u = s.getState();
                                    u.print();
                                }
                                System.out.println(you.getQuestion());
                                System.out.println("Type answer: [<another answer>, \"read\", \"logout\"]");
                                answer = scanner.nextLine();
                                you.sendAnswer(answer);
                            } else {
                                you.sendAnswer(answer);
                                System.out.println("Answer submitted");
                                System.out.println("Wait for question and type answer [<your answer>, \"read\", \"logout\"]");
                                TimeUnit.SECONDS.sleep(TIME_TO_WAIT);
                                System.out.println(you.getQuestion());
                                answer = scanner.nextLine();
                            }
                        }
                        you.setStatusOff();
                    } else {
                        System.out.println("Wrong username or password.  Please try again.");
                    }
                }
            }
            scanner.close();
        } catch (RemoteException e) {
            System.out.println("allStudents: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lookup: " + e);
        }

    }

    public static void createClientGUI() {
        //Creating the Frame
        JFrame frame = new JFrame("Client GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        //creating a main panel of grid layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1));
        //adding panels to the main panel
        mainPanel.add(loginPanel());
        mainPanel.add(questionPanel());
		mainPanel.add(openChoicePanel());
        mainPanel.add(multiChoicePanel());
		mainPanel.add(displayUsersPanel());
        mainPanel.add(buttonPanel());

        //add the main panel to the frame
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

    public static JPanel loginPanel() {
		JPanel loginPanel = new JPanel();
		//username field and label
		JLabel userNameTFieldLabel = new JLabel("User name");
		JTextField userNameTField = new JTextField("", 10);
		loginPanel.add(userNameTFieldLabel);
		loginPanel.add(userNameTField);
		//password field and label
		JLabel passwordTFieldLabel = new JLabel("Password");
		JTextField passwordTField = new JTextField("", 10);
		loginPanel.add(passwordTFieldLabel);
		loginPanel.add(passwordTField);
		// Buttons
		JButton loginBtn = new JButton("Login");
		JButton registerBtn = new JButton("Register");
		// adding action listener
		loginPanel.add(loginBtn);
		loginPanel.add(registerBtn);

		return loginPanel;
    }

    public static JPanel multiChoicePanel() {

        JPanel multiChoicePanel = new JPanel();
        //create radio button group so only 1 button can be selected at a time
        ButtonGroup group = new ButtonGroup();
        //create radio button and their label A through B
        JRadioButton radioAbtn = new JRadioButton();
        radioAbtn.setText("A");
        JRadioButton radioBbtn = new JRadioButton();
        radioBbtn.setText("B");
        JRadioButton radioCbtn = new JRadioButton();
        radioCbtn.setText("C");
        JRadioButton radioDbtn = new JRadioButton();
        radioDbtn.setText("D");
        //add radio buttons to group
        group.add(radioAbtn);
        group.add(radioBbtn);
        group.add(radioCbtn);
        group.add(radioDbtn);
        //add radio button to panel
        multiChoicePanel.add(radioAbtn);
        multiChoicePanel.add(radioBbtn);
        multiChoicePanel.add(radioCbtn);
        multiChoicePanel.add(radioDbtn);
        //Submit button
        JButton multiChoiceSubmitBtn = new JButton("Submit");
        multiChoicePanel.add(multiChoiceSubmitBtn);

        return multiChoicePanel;
    }

    public static JPanel openChoicePanel() {

        JPanel openChoicePanel = new JPanel();
        //creates label text field and submit button
        JLabel openChoiceTFieldLabel = new JLabel("Open Ended Answer");
        JTextField openChoiceTField = new JTextField("", 25);
        JButton submitOpenChoiceBtn = new JButton("Submit");
        // adds label text field and submit button to the panel
        openChoicePanel.add(openChoiceTFieldLabel);
        openChoicePanel.add(openChoiceTField);
        openChoicePanel.add(submitOpenChoiceBtn);

        return openChoicePanel;
    }

    public static JPanel questionPanel() {

        JPanel questionPanel = new JPanel();
        //creates and adds label to panel
        JLabel questionLabel = new JLabel("Questions will display here");
        questionPanel.add(questionLabel);
        return questionPanel;
    }

	public static JPanel displayUsersPanel() {

		JPanel displayUsersPanel = new JPanel(new GridLayout(1, 1, 15, 15));

		usersList = new DefaultComboBoxModel<String>();
		JList list = new JList(usersList);

		JScrollPane listScrollPane = new JScrollPane(list);
		list.getPreferredSize();
		list.setBorder(new EmptyBorder(10, 10, 50, 10));
		list.setFixedCellHeight(25);
		list.setFixedCellWidth(450);
		listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		listScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		displayUsersPanel.add(listScrollPane);

		return displayUsersPanel;
	}

    public static JPanel buttonPanel() {

        JPanel buttonPanel = new JPanel();
        //creates and adds Refresh and Logout buttons to panel
        JButton refreshBtn = new JButton("Refresh");
        JButton logoutBtn = new JButton("Logout");
        buttonPanel.add(refreshBtn);
        buttonPanel.add(logoutBtn);

        return buttonPanel;
    }
}


	             
