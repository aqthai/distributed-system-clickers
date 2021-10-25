import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

//import java.rmi.server.UnicastRemoteObject;
public class ClassListServer {
	public static void main(String args[]){
        // create security manager to give privileges to execute
		// System.setSecurityManager(new SecurityManager());

        String fileName = "registeredusers.csv";

        createRegisteredUsersFile(fileName);

        readRegisterUsersFile();
		
        System.out.println("Main OK, Please sign in an instructor.");
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the instructor's username?");
        String username = scanner.nextLine();
        System.out.println("What is the instructor's password?");
        String password = scanner.nextLine();
        User leader = new User("Instructor", username, password, "online");
        String request = "";
        String question = "";
        try{
            
            ClassList aClasslist = new ClassListServant(leader);
            aClasslist.newStudent(leader);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("ClassList", aClasslist);
            System.out.println("Welcome Instructor " + leader.username);
            // links a string to aClassList instance for clients
            System.out.println("ClassList server ready");
            while (!request.equalsIgnoreCase("exit")){
                System.out.println("Free Response (FR), Multiple Choice (MC), get answers (GA), logout, or exit?");
                request = scanner.nextLine();
                if (request.equalsIgnoreCase("FR")){
                    question = aClasslist.getInstructor().makeFreeResponse();
                    for (StudentServant s : aClasslist.allStudents()){
						s.setQuestion(question);
					}
                } else if (request.equalsIgnoreCase("MC")){
                    question = aClasslist.getInstructor().makeMultipleChoice();
                    for (StudentServant s : aClasslist.allStudents()){
						s.setQuestion(question);
					}
                } else if (request.equalsIgnoreCase("GA")){
                    for (StudentServant s : aClasslist.allStudents()){
                        if (!s.getAnswer().equals("") && !s.getAnswer().equals("logout") && !s.getAnswer().equals("read")){
                            System.out.println("(" + s.getStatus() + ") " + s.getName() + " has typed " + s.getAnswer());
                        }
					}
                } else if (request.equalsIgnoreCase("logout")){
                    System.out.println("Thank you " + aClasslist.getInstructor().getState().username);
                    aClasslist.getInstructor().getState().setStatusOff();
                    aClasslist.getInstructor().getState().setTypeStudent();
                    // gets the instructor in the StudentServant list and turns him/her to an offline student
                    aClasslist.getStudent(aClasslist.getInstructor().getState().username).setStatusOff();
                    aClasslist.getStudent(aClasslist.getInstructor().getState().username).getState().setTypeStudent();
                    System.out.println("Please assign another teacher.  Register or Login?");
                    request = scanner.nextLine();
                    if (request.equalsIgnoreCase("register")){
                        System.out.println("What's the instructor's name?");
                        username = scanner.nextLine();
                        System.out.println("What's the instructor's password?");
                        password = scanner.nextLine();
                        aClasslist.setInstructor(new User("Instructor", username, password, "online"));
                        aClasslist.newStudent(new User("Instructor", username, password, "online"));
                        Naming.rebind("ClassList", aClasslist); 
                    } else if (request.equalsIgnoreCase("login")){
                        System.out.println("Enter username: ");
                        username = scanner.nextLine();
                        System.out.println("Enter password: ");
                        password = scanner.nextLine();
                        leader = aClasslist.getStudent(username).getState();
                        if (aClasslist.getStudent(username).getPass().equals(password)){
                            leader.setTypeInstructor();
                            aClasslist.setInstructor(leader);
                            aClasslist.getStudent(username).setStatusOn();
                        }
                    } 
                }
            }
            System.out.println("Thank you " + leader.username);
            scanner.close();
            
        }catch(Exception e) {
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

        ArrayList<User> registeredUsers = new ArrayList<User>();
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
