import java.rmi.*;
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
        User leader = new User("Instructor", username, password);
        String request = "";
        String question = "";
        try{
            
            ClassList aClasslist = new ClassListServant(leader);
            System.out.println("Welcome " + leader.username);
            // links a string to aClassList instance for clients
			Naming.rebind("ClassList", aClasslist); 
            System.out.println("ClassList server ready");
            while (!request.equals("exit")){
                System.out.println("Free Response (FR), Multiple Choice (MC), get answers (GA) or exit?");
                request = scanner.nextLine();
                if (request.equals("FR")){
                    question = aClasslist.getInstructor().makeFreeResponse();
                    for (StudentServant s : aClasslist.allStudents()){
						s.setQuestion(question);
					}
                } else if (request.equals("MC")){
                    question = aClasslist.getInstructor().makeMultipleChoice();
                    for (StudentServant s : aClasslist.allStudents()){
						s.setQuestion(question);
					}
                } else if (request.equals("GA")){
                    for (StudentServant s : aClasslist.allStudents()){
						System.out.println(s.getName() + " has typed " + s.getAnswer());
					}
                }
            }
            System.out.println("Thank you" + leader.username);
            scanner.close();
            
        }catch(Exception e) {
            System.out.println("ClassList server main " + e.getMessage());
        }
    }
}
