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
    
	public String [] makeMultipleChoice() throws RemoteException {
        String [] mcQuestion = new String[4]; 

        System.out.println("Give the question: ");
	    mcQuestion[0] = scanner.nextLine();
        System.out.println("Give the first choice: ");
	    mcQuestion[1] = scanner.nextLine();
        System.out.println("Give the second choice: ");
	    mcQuestion[2] = scanner.nextLine();
        System.out.println("Give the third choice: ");
	    mcQuestion[3] = scanner.nextLine();
        
        return mcQuestion;
	}

	public String [] makeFreeResponse() throws RemoteException {
        System.out.println("Give the question: ");
	    String question = scanner.nextLine();
        System.out.println("Give the answer: ");
	    String answer = scanner.nextLine();
        
        String [] frQuestion = {question, answer};  
        return frQuestion;
	}

    /**
     * getAnswers reads from array of answers of question
     * @return an array of String responses from Question
     * @throws RemoteException
     */
    public String [] viewAnswers() throws RemoteException {
        // should be size of answers from online students
        String [] answers = new String [0];
        return answers;
    }
	
   public User getState() throws RemoteException{
        return theUser;
   }
    
}