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

        String fileName = "registeredusers.csv";

        createRegisteredUsersFile(fileName);

        registerUserToFile(fileName, "test", "pass1", "Student");
        registerUserToFile(fileName, "test2", "pass2", "Student");
        registerUserToFile(fileName, "test3", "pass3", "Student");

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
}

