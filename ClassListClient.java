//package examples.RMIShape;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;


public class ClassListClient{
   public static void main(String args[]){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Login or Register?: ");
		String menu1 = scanner.nextLine();
		
 		// if(System.getSecurityManager() == null){
        // 	System.setSecurityManager(new SecurityManager());
        // } else System.out.println("Already has a security manager, so cant set RMI SM");
        ClassList aClassList = null;
        try{
            aClassList  = (ClassList) Naming.lookup("//127.0.0.1/ClassList");
 			System.out.println("Found server");
 			ArrayList<StudentServant> sList = aClassList.allStudents();
 			System.out.println("Got ArrayList");
			if(menu1.equals("Read")){
				for (Student s : sList){
					if (s.getStatus()){
						System.out.println(s.getName() + " is " );
					}
        			// User u = (i.next()).getState();
        			// u.print();
        		}
        	} else if (menu1.equals("Register")) {
				System.out.println("Enter username: ");
				String username = scanner.nextLine();
				System.out.println("Enter password: ");
				String password = scanner.nextLine();
				User u = new User("Student", username, password, true);
				Student you = aClassList.newStudent(u);
				System.out.println(username + " has registered");
			} else if (menu1.equals("Login")) {
				System.out.println("Enter username: ");
				String username = scanner.nextLine();
				System.out.println("Enter password: ");
				String password = scanner.nextLine();
				Student you = aClassList.getStudent(username);
				if (you.getState().password.equals(password)){
					you.setStatus();
				}
				System.out.println(username + " has logged in");
			}
		}catch(RemoteException e) {System.out.println("allStudents: " + e.getMessage());
	    }catch(Exception e) {System.out.println("Lookup: " + e);}
		scanner.close();
    }
}


	             
