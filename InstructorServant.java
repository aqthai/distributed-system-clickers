//package examples.RMIShape;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class InstructorServant extends UnicastRemoteObject implements Instructor {
    User theUser;
    Scanner scanner = new Scanner(System.in);
     
    public InstructorServant(User u)throws RemoteException{
    	theUser = u;
    }
    
	public String makeMultipleChoice() throws RemoteException {
        String [] mcQuestion = new String[4]; 

        System.out.println("Give the question: ");
	    mcQuestion[0] = scanner.nextLine();
        System.out.println("Give the first choice: ");
	    mcQuestion[1] = "[1]" + scanner.nextLine();
        System.out.println("Give the second choice: ");
	    mcQuestion[2] = "[2]" + scanner.nextLine();
        System.out.println("Give the third choice: ");
	    mcQuestion[3] = "[3]" + scanner.nextLine();

        String question = "";
        for (String part : mcQuestion){
            question += (part + "\n");
        }
        
        return question;
	}

	public String makeFreeResponse() throws RemoteException {
        System.out.println("Give the question: ");
	    String question = scanner.nextLine();
        return question;
	}
	
   public User getState() throws RemoteException{
        return this.theUser;
   }
    
}