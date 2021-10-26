//package examples.RMIShape;

import javax.swing.*;
import java.awt.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.ArrayList;


public class ClassListClient {
    public static void main(String args[]) {


        createClientGUI();

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
            while (!menu1.equals("exit")) {
                System.out.println("Login or Register?: (or exit)");
                menu1 = scanner.nextLine();
                if (menu1.equals("Read")) {
                    for (StudentServant s : sList) {
                        if (s.getStatus()) {
                            System.out.println(s.getName() + " is ");
                        }
                    }
                } else if (menu1.equals("Register")) {
                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    User u = new User("Student", username, password);
                    Student you = aClassList.newStudent(u);


                    //System.out.println(you.getName() + " has registered");


                } else if (menu1.equals("Login")) {
                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    Student you = aClassList.getStudent(username);
                    if (you.getState().password.equals(password)) {
                        you.setStatusOn();
                        System.out.println("Wait for questions and type answer [<your answer>, \"refresh\", \"logout\"]");
                        answer = scanner.nextLine();
                        while (!answer.equals("logout")) {
                            if (answer.equals("refresh")) {
                                System.out.println(you.getQuestion());
                                System.out.println("Type answer: ");
                                answer = scanner.nextLine();
                                you.sendAnswer(answer);
                            } else {
                                you.sendAnswer(answer);
                                System.out.println("Answer submitted");
                                System.out.println("Wait for questions and type answer [<your answer>, \"refresh\", \"logout\"]");
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



	             
