import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClassListServer {
    public static void main(String args[]) throws IOException {
        // create security manager to give privileges to execute
        // System.setSecurityManager(new SecurityManager());

        createInstructorGUI();
        String fileName = "registeredusers.csv";

        createRegisteredUsersFile(fileName);
        readRegisterUsersFile();


        System.out.println("Main OK, Please register an instructor.");
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the instructor's username?");
        String username = scanner.nextLine();
        System.out.println("What is the instructor's password?");
        String password = scanner.nextLine();
        User leader = new User("Instructor", username, password);
        String request = "";
        String question = "";
        try {

            ClassList aClasslist = new ClassListServant(leader);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("ClassList", aClasslist);
            System.out.println("Welcome " + leader.username);
            // links a string to aClassList instance for clients
            Naming.rebind("ClassList", aClasslist);
            System.out.println("ClassList server ready");
            while (!request.equals("exit")) {
                System.out.println("Free Response (FR), Multiple Choice (MC), get answers (GA), logout, or exit?");
                request = scanner.nextLine();
                if (request.equals("FR")) {
                    question = aClasslist.getInstructor().makeFreeResponse();
                    for (StudentServant s : aClasslist.allStudents()) {
                        s.setQuestion(question);
                    }
                } else if (request.equals("MC")) {
                    question = aClasslist.getInstructor().makeMultipleChoice();
                    for (StudentServant s : aClasslist.allStudents()) {
                        s.setQuestion(question);
                    }
                } else if (request.equals("GA")) {
                    for (StudentServant s : aClasslist.allStudents()) {
                        System.out.println(s.getName() + " has typed " + s.getAnswer());
                    }
                } else if (request.equals("logout")) {
                    System.out.println("Thank you " + leader.username);
                    System.out.println("Please assign another teacher.  What's the instructor's name?");
                    username = scanner.nextLine();
                    System.out.println("What's the instructor's password?");
                    password = scanner.nextLine();
                    aClasslist.setInstructor(new User("Instructor", username, password));
                } else if (request.equals("exit")) {
                    System.out.println("Thank you " + leader.username);
                    scanner.close();
                }
            }

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
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void registerUserToFile(String fileName, String userName, String password, String type) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            StringBuilder sb = new StringBuilder();
            sb.append(userName);
            sb.append(",");
            sb.append(password);
            sb.append(",");
            sb.append(type);
            sb.append('\n');

            writer.write(sb.toString());

            System.out.println("done!");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void readRegisterUsersFile() {

        ArrayList<User> registeredUsers = new ArrayList<>();
        // file name
        String FILE_NAME = "registeredusers.csv";
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(FILE_NAME);
            br = new BufferedReader(fr);
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                String[] values = currentLine.split(",");
                //userName,password,type
                User user = new User(values[0], values[1], values[2]);
                registeredUsers.add(user);

            }
            //prints every user that's in the file
            for (User user : registeredUsers) {
                System.out.println(user.toString());
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
    }
    public static void createInstructorGUI(){
        //Creating the Frame
        JFrame frame = new JFrame("Instructor GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        //creating a main panel of grid layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));
        //adding panels to the main panel
        mainPanel.add(loginPanel());
        mainPanel.add(questionPanel());
        mainPanel.add(multiChoicePanel());
        mainPanel.add(openChoicePanel());
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
        loginPanel.add(passwordTField );
        //type field and label
        JLabel typeTFieldLabel = new JLabel("Type");
        JTextField typeTField = new JTextField("", 10);
        loginPanel.add(typeTFieldLabel);
        loginPanel.add(typeTField );
        // Buttons
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        loginPanel.add(loginBtn);
        loginPanel.add(registerBtn);

        return loginPanel;
    }
    public static JPanel multiChoicePanel(){

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
    public static JPanel openChoicePanel(){

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
    public static JPanel questionPanel(){

        JPanel questionPanel = new JPanel();
        //creates and adds label to panel
        JLabel questionLabel = new JLabel("Questions will display here");
        questionPanel.add(questionLabel);
        return questionPanel;
    }
    public static JPanel buttonPanel(){

        JPanel buttonPanel = new JPanel();
        //creates and adds Refresh and Logout buttons to panel
        JButton refreshBtn = new JButton("Refresh");
        JButton logoutBtn = new JButton("Logout");
        buttonPanel.add(refreshBtn);
        buttonPanel.add(logoutBtn);

        return buttonPanel;
    }
}

