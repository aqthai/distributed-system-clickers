import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

//import java.rmi.server.UnicastRemoteObject;
public class ClassListServer {
	public static void main(String args[]){
        // create security manager to give privileges to execute
		// System.setSecurityManager(new SecurityManager());
		
        System.out.println("Main OK, Please register an instructor.");
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
            System.out.println("Welcome " + leader.username);
            // links a string to aClassList instance for clients
			Naming.rebind("ClassList", aClasslist); 
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
                        if (!s.getAnswer().equals("")){
                            System.out.println("(" + s.getStatus() + ") " + s.getName() + " has typed " + s.getAnswer());
                        }
					}
                } else if (request.equalsIgnoreCase("logout")){
                    System.out.println("Thank you " + leader.username);
                    aClasslist.getInstructor().getState().setStatusOff();
                    aClasslist.getInstructor().getState().setTypeStudent();
                    System.out.println("Please assign another teacher.  Register or Login?");
                    request = scanner.nextLine();
                    if (request.equalsIgnoreCase("register")){
                        System.out.println("What's the instructor's name?");
                        username = scanner.nextLine();
                        System.out.println("What's the instructor's password?");
                        password = scanner.nextLine();
                        aClasslist.setInstructor(new User("Instructor", username, password, "online"));
                        aClasslist.newStudent(new User("Instructor", username, password, "online"));
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
}
