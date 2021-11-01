/** 
 * ClassListServer uses remote method invocation to store a ClassList object to a server.  
 * Instructors wait ten seconds after posting a question to receive responses from students 
 * and students wait ten seconds after submitting a question before the new question appears.
 * Both have to be fast enough to get the latest  posts or will have to type 'GA' from the 
 * instructor to get answers or 'read' from the student to read the new question.  Upon exit,
 * users will append to a csv file.  All users that sign in here become instructors.
 * Authors: Alvin Thai, Daniel Holguin, Jarom Montgomerry, Nicholas Knaggs
 * Professor Silva ACO 432
 */


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//import java.rmi.server.UnicastRemoteObject;
public class ClassListServer {
    //
    private static JTextField userNameTField;
    private static JTextField passwordTField;
    private static JTextField questionTField;
    private static JTextField answerATField;
    private static JTextField answerBTField;
    private static JTextField answerCTField;
    private static JTextField answerDTField;
    private static JRadioButton answerARadioBtn;
    private static JRadioButton answerBRadioBtn;
    private static JRadioButton answerCRadioBtn;
    private static JRadioButton answerDRadioBtn;

    private static ButtonGroup radioButtonGroup;
    private static JCheckBox isMultiChoiceCheckBox;
    // for list of answers
    private static DefaultComboBoxModel<String> answersList;
    private static DefaultComboBoxModel<String> usersList;


    private static ArrayList<User> registeredUsers;

    private static final String FILE_NAME = "registeredusers.csv";
    private static final int TIME_TO_WAIT = 10;


    public static void main(String[] args) {

        //create swing GUI and populate list with default info
        createInstructorGUI();
        answersList.addElement("Answers will show here");
        usersList.addElement("Users will show here");
        //creates the registerusers.csv file if not already created
        createRegisteredUsersFile(FILE_NAME);


        Scanner scanner = new Scanner(System.in);
        System.out.println("Main OK, Please sign in an instructor.");
        System.out.println("What is the instructor's username?");
        String username = scanner.nextLine();
        System.out.println("What is the instructor's password?");
        String password = scanner.nextLine();


        User leader = new User("Instructor", username, password, "online");
        String request = "";
        String question = "";
        try {

            ClassList aClasslist = new ClassListServant(leader);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("ClassList", aClasslist);
            // grab users from file and add them to ArrayList within aClassList
            registeredUsers = readRegisterUsersFile();
            for (User person : registeredUsers) {
                aClasslist.newStudent(person);
            }
            // add leader to the ClassListServant for reading and changing status
            aClasslist.newStudent(leader);
            aClasslist.getStudent(leader.getName()).setStatusOn();
            aClasslist.getStudent(leader.getName()).getState().setTypeInstructor();

            System.out.println("Welcome Instructor " + leader.username);
            // links a string to aClassList instance for clients
            System.out.println("ClassList server ready");
            while (!request.equalsIgnoreCase("exit")) {

                //adds the users to the swing GUI component
                usersList.removeAllElements();
                for (Student s : aClasslist.allStudents()) {
                    User user = s.getState();
                    usersList.addElement(user.printUserStatus());
                }
                System.out.println("Free Response (FR), Multiple Choice (MC), get answers (GA), logout, or exit?");
                request = scanner.nextLine();
                if (request.equalsIgnoreCase("FR")) {
                    question = aClasslist.getInstructor().makeFreeResponse();
                    for (StudentServant s : aClasslist.allStudents()) {
                        s.setQuestion(question);
                    }
                    TimeUnit.SECONDS.sleep(TIME_TO_WAIT);
                    for (StudentServant s : aClasslist.allStudents()) {
                        if (!s.getAnswer().equals("") && !s.getAnswer().equals("logout") && !s.getAnswer().equals("read")) {
                            System.out.println("(" + s.getStatus() + ") " + s.getName() + " has typed " + s.getAnswer());
                        }
                    }
                } else if (request.equalsIgnoreCase("MC")) {
                    question = aClasslist.getInstructor().makeMultipleChoice();
                    for (StudentServant s : aClasslist.allStudents()) {
                        s.setQuestion(question);
                    }
                    TimeUnit.SECONDS.sleep(TIME_TO_WAIT);
                    for (StudentServant s : aClasslist.allStudents()) {
                        if (!s.getAnswer().equals("") && !s.getAnswer().equals("logout") && !s.getAnswer().equals("read")) {
                            System.out.println("(" + s.getStatus() + ") " + s.getName() + " has typed '" + s.getAnswer() + "'");
                        }
                    }
                } else if (request.equalsIgnoreCase("GA")) {

                    answersList.removeAllElements(); //clears current contents of the Jlist
                    for (StudentServant s : aClasslist.allStudents()) {
                        if (!s.getAnswer().equals("") && !s.getAnswer().equals("logout") && !s.getAnswer().equals("read")) {
                            System.out.println("(" + s.getStatus() + ") " + s.getName() + " has typed '" + s.getAnswer() + "'");
                            //add the elements to a Jlist java swing component
                            answersList.addElement("(" + s.getStatus() + ") " + s.getName() + " has typed '" + s.getAnswer() + "'");
                        }
                    }
                } else if (request.equalsIgnoreCase("logout")) {
                    System.out.println("Thank you " + aClasslist.getInstructor().getState().username);
                    aClasslist.getInstructor().getState().setStatusOff();
                    aClasslist.getInstructor().getState().setTypeStudent();
                    // gets the instructor in the StudentServant list and turns him/her to an offline student
                    aClasslist.getStudent(aClasslist.getInstructor().getState().username).setStatusOff();
                    aClasslist.getStudent(aClasslist.getInstructor().getState().username).getState().setTypeStudent();
                    System.out.println("Please assign another teacher.  Register or Login?");
                    request = scanner.nextLine();
                    if (request.equalsIgnoreCase("register")) {
                        System.out.println("What's the instructor's name?");
                        username = scanner.nextLine();
                        System.out.println("What's the instructor's password?");
                        password = scanner.nextLine();
                        aClasslist.setInstructor(new User("Instructor", username, password, "online"));
                        aClasslist.newStudent(new User("Instructor", username, password, "online"));
                        Naming.rebind("ClassList", aClasslist);
                    } else if (request.equalsIgnoreCase("login")) {
                        System.out.println("Enter username: ");
                        username = scanner.nextLine();
                        System.out.println("Enter password: ");
                        password = scanner.nextLine();
                        leader = aClasslist.getStudent(username).getState();
                        if (aClasslist.getStudent(username).getPass().equals(password)) {
                            leader.setTypeInstructor();
                            aClasslist.setInstructor(leader);
                            aClasslist.getStudent(username).setStatusOn();
                        }
                    }
                }
            }
            System.out.println("Thank you " + leader.username);
            for (Student s : aClasslist.allStudents()) {
                registerUserToFile(FILE_NAME, s.getName(), s.getPass(), "Student");
            }
            ;
            scanner.close();

        } catch (Exception e) {
            System.out.println("ClassList server main " + e.getMessage());
        }
    }

    public static void createRegisteredUsersFile(String fileName) {
        try {
            File registeredUsersFile = new File(fileName);
            if (registeredUsersFile.createNewFile()) {
                System.out.println("File created: " + registeredUsersFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred creating registered users file.");
            e.printStackTrace();
        }
    }

    public static void registerUserToFile(String fileName, String userName, String password, String type) throws IOException {

        ArrayList<User> currentListRegisteredUsers = readRegisterUsersFile();

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Student");
            sb.append(",");
            sb.append(userName);
            sb.append(",");
            sb.append(password);
            sb.append("\n");
            User user = new User("Student", userName, password);
            // only adds user to file if not already
            if (!currentListRegisteredUsers.contains(user)) {
                writer.write(sb.toString() + "\n");
                System.out.println(userName + " Already Registered to File");
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<User> readRegisterUsersFile() {

        ArrayList<User> registeredUsers = new ArrayList<User>();
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(FILE_NAME);
            br = new BufferedReader(fr);
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                String[] values = currentLine.split(",");
                //type,userName,password
                User user = new User(values[0], values[1], values[2]);
                registeredUsers.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return registeredUsers;
    }

    public static void createInstructorGUI() {
        //Creating the Frame
        JFrame frame = new JFrame("Instructor GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        //creating a main panel of grid layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1));
        //adding panels to the main panel
        mainPanel.add(loginPanel());
        mainPanel.add(questionPanel());
        mainPanel.add(multiChoicePanel());
        mainPanel.add(displayAnswersPanel());
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
        userNameTField = new JTextField("", 10);
        loginPanel.add(userNameTFieldLabel);
        loginPanel.add(userNameTField);
        //password field and label
        JLabel passwordTFieldLabel = new JLabel("Password");
        passwordTField = new JTextField("", 10);
        loginPanel.add(passwordTFieldLabel);
        loginPanel.add(passwordTField);
        // Buttons
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        // adding action listener
        loginBtn.addActionListener(new LoginButtonActionListener());
        registerBtn.addActionListener(new RegisterButtonActionListener());
        loginPanel.add(loginBtn);
        loginPanel.add(registerBtn);

        return loginPanel;
    }

    public static JPanel questionPanel() {
        JPanel questionPanel = new JPanel();
        //questionPanel.setLayout(new GridLayout(2,2));
        //creates label text field and submit button
        JLabel questionTFieldLabel = new JLabel("Question");
        questionTField = new JTextField("", 25);
        questionPanel.add(questionTFieldLabel);
        questionPanel.add(questionTField);
        //create label for checkbox and checkbox
        JLabel checkboxLabel = new JLabel("Multiple Choice");
        isMultiChoiceCheckBox = new JCheckBox();

        isMultiChoiceCheckBox.addActionListener(new MultiChoiceCheckBoxActionListener());
        questionPanel.add(checkboxLabel);
        questionPanel.add(isMultiChoiceCheckBox);
        //Multichoice options

        //submit button
//        JButton submitQuestionBtn = new JButton("Submit");
//        questionPanel.add(submitQuestionBtn);


        //adding action listen to submit button
        //submitQuestionBtn.addActionListener(new QuestionButtonActionListener());

        return questionPanel;
    }

    public static JPanel multiChoicePanel() {
        JPanel multiChoicePanel = new JPanel(new GridLayout(5, 5));
        //questionPanel.setLayout(new GridLayout(2,2));
        //creates label text field and submit button
        //answer A label radio button and field
        answerARadioBtn = new JRadioButton("A");
        answerARadioBtn.setActionCommand("A");
        answerATField = new JTextField("", 25);
        answerATField.setEditable(false);
        multiChoicePanel.add(answerARadioBtn);
        multiChoicePanel.add(answerATField);
        //answer B
        answerBRadioBtn = new JRadioButton("B");
        answerBRadioBtn.setActionCommand("B");
        answerBTField = new JTextField("", 25);
        multiChoicePanel.add(answerBRadioBtn);
        answerBTField.setEditable(false);
        multiChoicePanel.add(answerBTField);
        //answer C
        answerCRadioBtn = new JRadioButton("C");
        answerCRadioBtn.setActionCommand("C");
        answerCTField = new JTextField("", 25);
        multiChoicePanel.add(answerCRadioBtn);
        answerCTField.setEditable(false);
        multiChoicePanel.add(answerCTField);
        //answer D
        answerDRadioBtn = new JRadioButton("D");
        answerDRadioBtn.setActionCommand("D");
        answerDTField = new JTextField("", 25);
        multiChoicePanel.add(answerDRadioBtn);
        answerDTField.setEditable(false);
        multiChoicePanel.add(answerDTField);
        //button group
        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(answerARadioBtn);
        radioButtonGroup.add(answerBRadioBtn);
        radioButtonGroup.add(answerCRadioBtn);
        radioButtonGroup.add(answerDRadioBtn);
        //setting all the buttons to set enable false unless it's multichoice
        answerARadioBtn.setEnabled(false);
        answerBRadioBtn.setEnabled(false);
        answerCRadioBtn.setEnabled(false);
        answerDRadioBtn.setEnabled(false);
        //submit button
        JButton submitQuestionBtn = new JButton("Submit");
        multiChoicePanel.add(submitQuestionBtn);

        //adding action listen to submit button
        submitQuestionBtn.addActionListener(new QuestionButtonActionListener());

        return multiChoicePanel;
    }

    public static JPanel buttonPanel() {

        JPanel buttonPanel = new JPanel();
        //creates and adds Refresh and Logout buttons to panel
        JButton refreshBtn = new JButton("Refresh");
        JButton logoutBtn = new JButton("Logout");
        //adding action listeners to the buttons
        refreshBtn.addActionListener(new RefreshButtonActionListener());
        logoutBtn.addActionListener(new LogoutButtonActionListener());
        buttonPanel.add(refreshBtn);
        buttonPanel.add(logoutBtn);

        return buttonPanel;
    }


    public static JPanel displayAnswersPanel() {

        JPanel displayAnswersPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        //creates and adds Refresh and Logout buttons to panel
        JButton displayAnswersBtn = new JButton("Display Answers");
        answersList = new DefaultComboBoxModel<String>();
        JList list = new JList(answersList);

        JScrollPane listScrollPane = new JScrollPane(list);
        list.getPreferredSize();
        list.setBorder(new EmptyBorder(10, 10, 50, 10));
        list.setFixedCellHeight(25);
        list.setFixedCellWidth(450);
        listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        listScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        displayAnswersPanel.add(displayAnswersBtn);
        displayAnswersPanel.add(listScrollPane);

        return displayAnswersPanel;
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


    static class LoginButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.print(userNameTField.getText() + " ");
            System.out.println(passwordTField.getText());
            System.out.println("log in btn pressed");
        }
    }

    static class RegisterButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            System.out.print(userNameTField.getText() + " ");
            System.out.println(passwordTField.getText());
            System.out.println("register btn pressed");
        }
    }


    static class QuestionButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (isMultiChoiceCheckBox.isSelected()) {
                System.out.println("the box was selected");
            }
            System.out.println("The question is " + questionTField.getText());
            System.out.println("is check box selected " + isMultiChoiceCheckBox.isSelected());
            radioButtonGroup.clearSelection();
        }
    }

    static class RefreshButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            System.out.println("refresh button clicked");
        }
    }

    static class LogoutButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("logout button clicked");
        }
    }

    static class MultiChoiceCheckBoxActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JRadioButton[] buttons = new JRadioButton[]{answerARadioBtn, answerBRadioBtn, answerCRadioBtn, answerDRadioBtn};
            if (isMultiChoiceCheckBox.isSelected()) {
                answerATField.setEditable(true);
                answerBTField.setEditable(true);
                answerCTField.setEditable(true);
                answerDTField.setEditable(true);
                for (JRadioButton btn : buttons) {
                    btn.setEnabled(true);
                }
                radioButtonGroup.clearSelection();

            } else {
                answerATField.setEditable(false);
                answerBTField.setEditable(false);
                answerCTField.setEditable(false);
                answerDTField.setEditable(false);
                for (JRadioButton btn : buttons) {
                    btn.setEnabled(false);
                }
                radioButtonGroup.clearSelection();
            }
        }
    }
}


