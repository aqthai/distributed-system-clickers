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
        User leader = new User("Instructor", username, password, true);
        scanner.close();
        try{
            
            ClassList aClasslist = new ClassListServant(leader);
            System.out.println("Welcome " + leader.username + ". Please open a client and sign in.");
            // links a string to aClassList instance for clients
			Naming.rebind("ClassList", aClasslist); 
            System.out.println("ClassList server ready");
        }catch(Exception e) {
            System.out.println("ClassList server main " + e.getMessage());
        }
    }
}
