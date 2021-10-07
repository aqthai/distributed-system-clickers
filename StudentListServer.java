import java.rmi.*;
//import java.rmi.server.UnicastRemoteObject;
public class StudentListServer {
	public static void main(String args[]){
        // create security manager to give privileges to execute
		System.setSecurityManager(new SecurityManager());
		
        System.out.println("Main OK");
        try{
            StudentList aStudentlist = new StudentListServant();
            System.out.println("After create");
            // links a string to aShapeList instance for clients
			Naming.rebind("StudentList", aStudentlist); 
            System.out.println("QuestionList server ready");
        }catch(Exception e) {
            System.out.println("QuestionList server main " + e.getMessage());
        }
    }
}
