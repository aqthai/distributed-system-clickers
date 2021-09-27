import java.rmi.*;
//import java.rmi.server.UnicastRemoteObject;
public class QuestionListServer {
	public static void main(String args[]){
        // create security manager to give privileges to execute
		System.setSecurityManager(new SecurityManager());
		
        System.out.println("Main OK");
        try{
            QuestionList aQuestionlist = new QuestionListServant();
            System.out.println("After create");
            // links a string to aShapeList instance for clients
			Naming.rebind("QuestionList", aQuestionlist); 
            System.out.println("QuestionList server ready");
        }catch(Exception e) {
            System.out.println("QuestionList server main " + e.getMessage());
        }
    }
}
